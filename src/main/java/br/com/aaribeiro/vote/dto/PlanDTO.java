package br.com.aaribeiro.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlanDTO {
    @NotNull(message = "Field [name] not be null or empty")
    private String name;

    private String uuid;
    private String description;
    private String dateRegistry;
    private String closingData;
    private long totalVotesYes;
    private long totalVotesNo;
    private int totalVotings;
}
