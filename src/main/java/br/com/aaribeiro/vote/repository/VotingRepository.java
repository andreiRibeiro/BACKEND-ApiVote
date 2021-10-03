package br.com.aaribeiro.vote.repository;

import br.com.aaribeiro.vote.model.Plan;
import br.com.aaribeiro.vote.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VotingRepository extends JpaRepository<Voting, Long> {

    Optional<Voting> findByCpfAndPlan(String cpf, Plan plan);
}
