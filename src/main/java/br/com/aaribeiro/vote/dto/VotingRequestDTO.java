package br.com.aaribeiro.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VotingRequestDTO {
    private String uuid;
    private String cpf;
}
