package br.com.aaribeiro.vote.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @Column(name = "uuid", unique = true)
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "description")
    private String description;

    @Builder.Default
    @Column(name = "date_registry")
    private LocalDateTime dateRegistry = LocalDateTime.now();

    @Column(name = "closing_date")
    private LocalDateTime closingDate;

    @Builder.Default
    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan")
    private List<Voting> votings;
}
