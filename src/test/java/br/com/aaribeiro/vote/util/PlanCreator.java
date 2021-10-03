package br.com.aaribeiro.vote.util;

import br.com.aaribeiro.vote.dto.PlanDTO;
import br.com.aaribeiro.vote.model.Plan;
import java.time.LocalDateTime;
import java.util.List;

public class PlanCreator {

    public static PlanDTO createPlanDTO(){
        return PlanDTO.builder()
                .name("Pauta 1")
                .uuid("549352a3-17b2-4d22-8942-e2586cfbf711")
                .description("Pauta para votar questões x e y")
                .build();
    }

    public static Plan createPlan(){
        return Plan.builder()
                .name("Pauta 1")
                .uuid("549352a3-17b2-4d22-8942-e2586cfbf711")
                .description("Pauta para votar questões x e y")
                .build();
    }

    public static Plan createPlanWithClosingDateOk(){
        return Plan.builder()
                .name("Pauta 1")
                .uuid("549352a3-17b2-4d22-8942-e2586cfbf711")
                .description("Pauta para votar questões x e y")
                .closingDate(LocalDateTime.of(2030, 3, 5, 12, 0))
                .build();
    }

    public static Plan createPlanWithClosingDateExceded(){
        return Plan.builder()
                .name("Pauta 1")
                .uuid("549352a3-17b2-4d22-8942-e2586cfbf711")
                .description("Pauta para votar questões x e y")
                .closingDate(LocalDateTime.of(2018, 3, 5, 12, 0))
                .build();
    }

    public static Plan createPlanWithVotes(){
        Plan plan = Plan.builder()
                .name("Pauta 1")
                .uuid("549352a3-17b2-4d22-8942-e2586cfbf711")
                .description("Pauta para votar questões x e y")
                .build();

        plan.setVotings(List.of(VotingCreator.createVoting1(), VotingCreator.createVoting2()));
        return plan;
    }
}
