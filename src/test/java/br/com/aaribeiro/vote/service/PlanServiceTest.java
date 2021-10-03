package br.com.aaribeiro.vote.service;

import br.com.aaribeiro.vote.dto.PlanDTO;
import br.com.aaribeiro.vote.model.Plan;
import br.com.aaribeiro.vote.repository.PlanRepository;
import br.com.aaribeiro.vote.util.PlanCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import static org.mockito.Mockito.when;

public class PlanServiceTest {
    @InjectMocks
    private PlanService planService;

    @Mock
    private PlanRepository planRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Create plan when succesful")
    public void create_WhenSucessful(){
        when(planRepository.save(Mockito.any(Plan.class)))
                .thenReturn(PlanCreator.createPlan());

        PlanDTO dto = planService.create(PlanCreator.createPlanDTO());

        Assertions.assertEquals("549352a3-17b2-4d22-8942-e2586cfbf711", dto.getUuid());
    }

    @Test
    @DisplayName("Find plan when succesful")
    public void findByUuid_WhenSucessful(){
        when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithVotes().getUuid())))
                .thenReturn(Optional.of(PlanCreator.createPlanWithVotes()));

        planService.findByUuid(PlanCreator.createPlanDTO().getUuid());
    }

    @Test
    @DisplayName("Find plan when not found")
    public void findByUuid_WhenNotFound(){
        String msg = "";
        try {
            when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithVotes().getUuid())))
                    .thenReturn(Optional.of(PlanCreator.createPlanWithVotes()));

            planService.findByUuid("1234-abcd");
        } catch (RuntimeException e){
            msg = e.getMessage();
        }
        Assertions.assertEquals("Pauta não localizada", msg);
    }

    @Test
    @DisplayName("Open plan for voting when succesful")
    public void openPlanForVoting_WhenSucessful(){
        when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithClosingDateOk().getUuid())))
                .thenReturn(Optional.of(PlanCreator.createPlanWithVotes()));

        planService.openPlanForVoting(PlanCreator.createPlanWithClosingDateOk().getUuid(), 2);
    }

    @Test
    @DisplayName("Open plan for voting when voting has already been registered")
    public void openPlanForVoting_WhenVotingHasAlreadyBeenRegistred(){
        String msg = "";
        try {
            when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithClosingDateOk().getUuid())))
                    .thenReturn(Optional.of(PlanCreator.createPlanWithVotes()));

            planService.openPlanForVoting(PlanCreator.createPlanWithClosingDateOk().getUuid(), 2);
            planService.openPlanForVoting(PlanCreator.createPlanWithClosingDateOk().getUuid(), 3);
        } catch (RuntimeException e){
            msg = e.getMessage();
        }
        Assertions.assertEquals(
                String.format("Pauta [%s] já teve sua sessão de abertura registrada em %s",
                        PlanCreator.createPlanWithClosingDateOk().getName(),
                        LocalDateTime.now().plusMinutes(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))
                ), msg
        );
    }

    @Test
    @DisplayName("Get voting results when succesful")
    public void getVotingResults_WhenSucessful(){
        when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithVotes().getUuid())))
                .thenReturn(Optional.of(PlanCreator.createPlanWithVotes()));

        PlanDTO planDTO = planService.getVotingResults(PlanCreator.createPlanWithVotes().getUuid());
        Assertions.assertEquals(2, planDTO.getTotalVotings());
    }
}
