package br.com.aaribeiro.vote.service;

import br.com.aaribeiro.vote.component.VotingComponent;
import br.com.aaribeiro.vote.model.Plan;
import br.com.aaribeiro.vote.repository.PlanRepository;
import br.com.aaribeiro.vote.repository.VotingRepository;
import br.com.aaribeiro.vote.util.PlanCreator;
import br.com.aaribeiro.vote.util.VotingCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.Mockito.when;

public class VotingServiceTest {
    @InjectMocks
    private VotingService votingService;

    @Mock
    private VotingRepository votingRepository;

    @Mock
    private PlanRepository planRepository;

    @Mock
    private VotingComponent votingComponent;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Voting when succesful")
    public void create_WhenSucessful(){
        when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithClosingDateOk().getUuid())))
                .thenReturn(Optional.of(PlanCreator.createPlanWithClosingDateOk()));

        when(votingRepository.findByCpfAndPlan(Mockito.eq("123"), Mockito.any(Plan.class)))
                .thenReturn(Optional.of(VotingCreator.createVoting1()));

        when(votingComponent.checkCpf(Mockito.eq(VotingCreator.createVoting1().getCpf())))
                .thenReturn(true);

        votingService.create(VotingCreator.createVoting1().getCpf(), PlanCreator.createPlanWithClosingDateOk().getUuid(), VotingCreator.createVoting1().getVote());


        when(votingRepository.findByCpfAndPlan(Mockito.eq("456"), Mockito.any(Plan.class)))
                .thenReturn(Optional.of(VotingCreator.createVoting2()));

        when(votingComponent.checkCpf(Mockito.eq(VotingCreator.createVoting2().getCpf())))
                .thenReturn(true);

        votingService.create(VotingCreator.createVoting2().getCpf(), PlanCreator.createPlanWithClosingDateOk().getUuid(), VotingCreator.createVoting2().getVote());
    }

    @Test
    @DisplayName("Voting when cpf repeted")
    public void create_WhenCpfRepeted(){
        String msg = "";
        try {
            when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithClosingDateOk().getUuid())))
                    .thenReturn(Optional.of(PlanCreator.createPlanWithClosingDateOk()));

            when(votingRepository.findByCpfAndPlan(Mockito.eq(VotingCreator.createVoting1().getCpf()), Mockito.any(Plan.class)))
                    .thenReturn(Optional.of(VotingCreator.createVoting1()));

            when(votingComponent.checkCpf(Mockito.eq(VotingCreator.createVoting1().getCpf())))
                    .thenReturn(true);

            votingService.create(VotingCreator.createVoting1().getCpf(), PlanCreator.createPlanWithClosingDateOk().getUuid(), VotingCreator.createVoting1().getVote());
        } catch (RuntimeException e){
            msg = e.getMessage();
        }
        Assertions.assertEquals("Este CPF é inválido ou já votou nesta sessão", msg);
    }

    @Test
    @DisplayName("Voting when cpf invalid")
    public void create_WhenCpfInvalid(){
        String msg = "";
        try {
            when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithClosingDateOk().getUuid())))
                    .thenReturn(Optional.of(PlanCreator.createPlanWithClosingDateOk()));

            when(votingRepository.findByCpfAndPlan(Mockito.eq("123"), Mockito.any(Plan.class)))
                    .thenReturn(Optional.of(VotingCreator.createVoting1()));

            when(votingComponent.checkCpf(Mockito.eq(VotingCreator.createVoting1().getCpf())))
                    .thenReturn(false);

            votingService.create(VotingCreator.createVoting1().getCpf(), PlanCreator.createPlanWithClosingDateOk().getUuid(), VotingCreator.createVoting1().getVote());
        } catch (RuntimeException e){
            msg = e.getMessage();
        }
        Assertions.assertEquals("Este CPF é inválido ou já votou nesta sessão", msg);
    }

    @Test
    @DisplayName("Voting when closing date exceded")
    public void create_WhenClosingDateExceded(){
        String msg = "";
        try {
            when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlanWithClosingDateExceded().getUuid())))
                    .thenReturn(Optional.of(PlanCreator.createPlanWithClosingDateExceded()));

            when(votingRepository.findByCpfAndPlan(Mockito.eq(VotingCreator.createVoting1().getCpf()), Mockito.any(Plan.class)))
                    .thenReturn(Optional.of(VotingCreator.createVoting1()));

            when(votingComponent.checkCpf(Mockito.eq(VotingCreator.createVoting1().getCpf())))
                    .thenReturn(true);

            votingService.create(VotingCreator.createVoting1().getCpf(), PlanCreator.createPlanWithClosingDateExceded().getUuid(), VotingCreator.createVoting1().getVote());
        } catch (RuntimeException e){
            msg = e.getMessage();
        }
        Assertions.assertEquals("Ocorreu um erro ao computar voto: Tempo para votação excedido. Esta sessão encerrou em 05/03/2018 12:00:00", msg);
    }

    @Test
    @DisplayName("Voting when closing date is null")
    public void create_WhenClosingDateIsNull(){
        String msg = "";
        try {
            when(planRepository.findByUuid(Mockito.eq(PlanCreator.createPlan().getUuid())))
                    .thenReturn(Optional.of(PlanCreator.createPlan()));

            when(votingRepository.findByCpfAndPlan(Mockito.eq(VotingCreator.createVoting1().getCpf()), Mockito.any(Plan.class)))
                    .thenReturn(Optional.of(VotingCreator.createVoting1()));

            when(votingComponent.checkCpf(Mockito.eq(VotingCreator.createVoting1().getCpf())))
                    .thenReturn(true);

            votingService.create(VotingCreator.createVoting1().getCpf(), PlanCreator.createPlan().getUuid(), VotingCreator.createVoting1().getVote());
        } catch (RuntimeException e){
            msg = e.getMessage();
        }
        Assertions.assertEquals("Esta pauta ainda não foi aberta para votação", msg);
    }
}
