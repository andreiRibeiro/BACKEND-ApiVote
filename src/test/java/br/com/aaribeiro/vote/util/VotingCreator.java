package br.com.aaribeiro.vote.util;

import br.com.aaribeiro.vote.enums.VoteEnum;
import br.com.aaribeiro.vote.model.Voting;

public class VotingCreator {

    public static Voting createVoting1(){
        return Voting.builder()
                .plan(PlanCreator.createPlan())
                .vote(VoteEnum.SIM.name())
                .cpf("01267820080")
                .uuid("257352a3-17b2-4d22-8942-e2586cfbf877")
                .build();
    }

    public static Voting createVoting2(){
        return Voting.builder()
                .plan(PlanCreator.createPlan())
                .vote("Não")
                .cpf("53494737002")
                .uuid("628352a3-17b2-4d22-8942-e2586cfbf230")
                .build();
    }
}
