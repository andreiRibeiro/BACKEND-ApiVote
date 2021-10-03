package br.com.aaribeiro.vote.service;

import br.com.aaribeiro.vote.dto.PlanDTO;
import br.com.aaribeiro.vote.exceptions.DataInvalidException;
import br.com.aaribeiro.vote.exceptions.DataNotFoundException;
import br.com.aaribeiro.vote.model.Plan;
import br.com.aaribeiro.vote.repository.PlanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
@Slf4j
@Transactional
@Service
public class PlanService {
    private final PlanRepository planRepository;

    public PlanDTO create(PlanDTO planDTO) {
        try {
            Plan plan = planRepository.save(Plan.builder()
                    .description(planDTO.getDescription())
                    .name(planDTO.getName())
                    .build()
            );
            log.info(String.format("Pauta [%s] criada com sucesso", plan.getName()));

            return PlanDTO.builder()
                    .name(plan.getName())
                    .uuid(plan.getUuid())
                    .description(plan.getDescription())
                    .dateRegistry(plan.getDateRegistry().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")))
                    .build();

        } catch (RuntimeException e){
            throw new RuntimeException("Ocorreu um erro ao criar Pauta: " + e.getMessage());
        }
    }

    public PlanDTO findByUuid(String uuid) {
        try {
            return planRepository.findByUuid(uuid)
                    .map(result -> PlanDTO.builder()
                            .name(result.getName())
                            .uuid(result.getUuid())
                            .description(result.getDescription())
                            .dateRegistry(result.getDateRegistry().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")))
                            .closingData(nonNull(result.getClosingDate()) ? result.getClosingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")) : null)
                            .totalVotings(result.getVotings().size())
                            .build()
                    )
                    .orElseThrow(() -> new DataNotFoundException("Pauta não localizada"));
        } catch (DataNotFoundException e){
            throw new DataNotFoundException(e.getMessage());
        } catch (RuntimeException e){
            throw new RuntimeException("Ocorreu um erro ao consultar Pauta: " + e.getMessage());
        }
    }

    public Page<PlanDTO> findAll(Pageable pageable) {
        try {
            return planRepository.findAll(pageable)
                    .map(result -> PlanDTO.builder()
                            .uuid(result.getUuid())
                            .name(result.getName())
                            .dateRegistry(result.getDateRegistry().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")))
                            .closingData(nonNull(result.getClosingDate()) ? result.getClosingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")) : null)
                            .totalVotings(result.getVotings().size())
                            .build()
                    );
        } catch (RuntimeException e){
            throw new RuntimeException("Ocorreu um erro ao listar todas as Pautas: " + e.getMessage());
        }
    }

    public String openPlanForVoting(String uuid, int openingTime) {
        try {
            return planRepository.findByUuid(uuid)
                    .map(plan -> {
                        //Verifica se a pauta ja teve uma sessão de abertura registrada
                        if (isNull(plan.getClosingDate())) {
                            plan.setClosingDate(LocalDateTime.now().plusMinutes(openingTime));
                            planRepository.save(plan);

                            String msg = String.format("Sessão para votação da pauta [%s] aberta. Esta sessão encerrá em %s",
                                    plan.getName(),
                                    plan.getClosingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))
                            );

                            log.info(msg);
                            return msg;
                        } else {
                            throw new DataInvalidException(String.format(
                                    "Pauta [%s] já teve sua sessão de abertura registrada em %s",
                                    plan.getName(),
                                    plan.getClosingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))
                            ));
                        }
                    })
                    .orElseThrow(() -> new DataNotFoundException("Pauta não localizada"));
        } catch (DataNotFoundException e){
            throw new DataNotFoundException(e.getMessage());
        } catch (DataInvalidException e){
            throw new DataInvalidException(e.getMessage());
        } catch (RuntimeException e){
            throw new RuntimeException("Ocorreu um erro ao abrir votação de Pauta: " + e.getMessage());
        }
    }

    public PlanDTO getVotingResults(String uuid) {
        try {
            return planRepository.findByUuid(uuid)
                    .map(plan -> {
                        PlanDTO planDTO = PlanDTO.builder()
                                .uuid(plan.getUuid())
                                .name(plan.getName())
                                .dateRegistry(plan.getDateRegistry().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")))
                                .closingData(nonNull(plan.getClosingDate()) ? plan.getClosingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")) : null)
                                .totalVotings(plan.getVotings().size())
                                .build();

                        planDTO.setTotalVotings(plan.getVotings().size());
                        return planDTO;
                    })
                    .orElseThrow(() -> new DataNotFoundException("Pauta não localizada"));
        } catch (DataNotFoundException e){
            throw new DataNotFoundException(e.getMessage());
        } catch (RuntimeException e){
            throw new RuntimeException("Ocorreu um erro ao obter total de votos da Pauta: " + e.getMessage());
        }
    }

    public void delete(String uuid){
        try {
            planRepository.findByUuid(uuid)
                    .map(plan -> {
                        plan.setDeleted(true);
                        planRepository.save(plan);
                        return plan;
                    })
                    .orElseThrow(() -> new DataNotFoundException("Pauta não localizada"));
        } catch (DataNotFoundException e){
            throw new DataNotFoundException(e.getMessage());
        } catch (RuntimeException e){
            throw new RuntimeException("Ocorreu um erro ao excluir Pauta: " + e.getMessage());
        }
    }
}