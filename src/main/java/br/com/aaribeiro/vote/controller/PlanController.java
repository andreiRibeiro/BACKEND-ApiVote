package br.com.aaribeiro.vote.controller;

import br.com.aaribeiro.vote.dto.PlanDTO;
import br.com.aaribeiro.vote.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/plan")
@Api(tags = "Processa informações referente a pauta")
public class PlanController {
    private final PlanService planService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Criar pauta")
    public ResponseEntity<PlanDTO> create(@RequestBody @Valid PlanDTO planDTO){
        return ResponseEntity.status(HttpStatus.OK).body(planService.create(planDTO));
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar pauta")
    public ResponseEntity<PlanDTO> findByUuid(@PathVariable String uuid){
        return ResponseEntity.status(HttpStatus.OK).body(planService.findByUuid(uuid));
    }

    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar pautas")
    public ResponseEntity<Page<PlanDTO>> findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                 @RequestParam(value = "size", defaultValue = "10", required = false) int size){
        return ResponseEntity.status(HttpStatus.OK).body(planService.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id")));
    }

    @PostMapping(value = "/open/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Abrir sessão para votação de pauta")
    public ResponseEntity<String> openPlanForVoting(@PathVariable String uuid,
                                                     @RequestParam(value = "openingTime", defaultValue = "1", required = false) int openingTime){
        return ResponseEntity.status(HttpStatus.OK).body(planService.openPlanForVoting(uuid, openingTime));
    }

    @GetMapping(value = "/results/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Obter resultado de votação da pauta")
    public ResponseEntity<PlanDTO> getVotingResults(@PathVariable String uuid){
        return ResponseEntity.status(HttpStatus.OK).body(planService.getVotingResults(uuid));
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Excluir pauta")
    public ResponseEntity<?> delete(@PathVariable String uuid){
        planService.delete(uuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
