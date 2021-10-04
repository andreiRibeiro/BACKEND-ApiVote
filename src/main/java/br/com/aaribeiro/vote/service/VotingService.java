package br.com.aaribeiro.vote.service;

import br.com.aaribeiro.vote.component.VotingComponent;
import br.com.aaribeiro.vote.enums.VoteEnum;
import br.com.aaribeiro.vote.exceptions.DataInvalidException;
import br.com.aaribeiro.vote.exceptions.DataNotFoundException;
import br.com.aaribeiro.vote.model.Plan;
import br.com.aaribeiro.vote.model.Voting;
import br.com.aaribeiro.vote.repository.PlanRepository;
import br.com.aaribeiro.vote.repository.VotingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.util.Objects.isNull;

@Slf4j
@AllArgsConstructor
@Service
public class VotingService {
    private final VotingRepository votingRepository;
    private final VotingComponent votingComponent;
    private final PlanRepository planRepository;

    public String create(String cpfOrigi, String planUuid, String vote){
        try {
            String cpf = cpfOrigi.replaceAll("[-.]","");

            log.info(String.format("Validando CPF [%s]", cpf));

            Plan plan = planRepository.findByUuid(planUuid)
                    .orElseThrow(() -> new DataNotFoundException("Pauta não localizada"));

            //Verifica se foi aberto sessão para votação da pauta
            if (isNull(plan.getClosingDate())) {
                throw new DataInvalidException("Esta pauta ainda não foi aberta para votação");
            }

            //Verifica se o tempo da sessão ja foi excedido
            if (LocalDateTime.now().isAfter(plan.getClosingDate())) {
                throw new RuntimeException("Tempo para votação excedido. Esta sessão encerrou em " + plan.getClosingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
            }

            //Verifica se CPF ja votou na pauta e se CPF é valido para votar
            if (votingRepository.findByCpfAndPlan(cpf, plan).isEmpty() && votingComponent.checkCpf(cpf)) {
                votingRepository.save(Voting.builder()
                        .vote(VoteEnum.valueOf(vote.toUpperCase()).name())
                        .cpf(cpf)
                        .plan(plan)
                        .build()
                );
                String msg = String.format("Voto registrado com sucesso para CPF [%s]", cpf);
                log.info(msg);
                return msg;
            } else {
                throw new DataInvalidException("Este CPF é inválido ou já votou nesta sessão");
            }

        } catch (DataNotFoundException e){
            throw new DataNotFoundException(e.getMessage());
        } catch (DataInvalidException e){
            throw new DataInvalidException(e.getMessage());
        } catch (RuntimeException e){
            throw new RuntimeException("Ocorreu um erro ao computar voto: " + e.getMessage());
        }
    }
}