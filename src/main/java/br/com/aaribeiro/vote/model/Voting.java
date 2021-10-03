package br.com.aaribeiro.vote.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "voting")
public class Voting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "uuid")
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "vote")
    private String vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan")
    private Plan plan;
}
