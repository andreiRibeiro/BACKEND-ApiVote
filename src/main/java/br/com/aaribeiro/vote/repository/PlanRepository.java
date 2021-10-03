package br.com.aaribeiro.vote.repository;

import br.com.aaribeiro.vote.model.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>, PagingAndSortingRepository<Plan, Long> {

    @Query("SELECT p FROM plan p " +
            "WHERE p.uuid = :uuid " +
            "AND p.deleted <> true")
    Optional<Plan> findByUuid(String uuid);

    @Query("SELECT p FROM plan p " +
            "WHERE p.deleted <> true")
    Page<Plan> findAll(Pageable pageable);
}
