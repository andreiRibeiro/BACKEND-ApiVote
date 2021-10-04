package br.com.aaribeiro.vote.controller;

import br.com.aaribeiro.vote.service.VotingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/voting")
@Api(tags = "Processa informações referente ao voto")
public class VotingController {
    private final VotingService votingService;

    @GetMapping(value = "/{planUuid}/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Votar em uma pauta")
    public ResponseEntity<String> create(@PathVariable String planUuid,
                                         @PathVariable String cpf,
                                         @RequestParam String vote){
        return ResponseEntity.ok().body(votingService.create(cpf, planUuid, vote));
    }
}
