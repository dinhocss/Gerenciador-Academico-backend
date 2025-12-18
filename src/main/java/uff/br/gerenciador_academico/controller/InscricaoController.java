package uff.br.gerenciador_academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uff.br.gerenciador_academico.model.dto.InscricaoDTO;
import uff.br.gerenciador_academico.model.Inscricao;
import uff.br.gerenciador_academico.service.InscricaoService;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.model.ResultadoPaginado;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/inscricoes")
@CrossOrigin(origins="http://localhost:5173")
@RequiredArgsConstructor
public class InscricaoController {
    private final InscricaoService inscricaoService;

    @GetMapping
    public ResponseEntity<List<InscricaoDTO>> recuperarInscricoes(){
        List<Inscricao> inscricoes = inscricaoService.recuperarInscricoes();

        List<InscricaoDTO> inscricoesDTO = inscricoes.stream()
                .map(InscricaoDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(inscricoesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoDTO> recuprarInscricaoPorId(@PathVariable Long id){
        return inscricaoService.recuperarInscricaoPorId(id)
                .map(InscricaoDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Inscrição não encontrada com id: " + id));
    }

    @PostMapping
    public ResponseEntity<InscricaoDTO> cadastrarInscricao(@RequestBody @Valid InscricaoDTO inscricao){
        Inscricao inscricaoSalvar = inscricao.toEntity();
        Inscricao inscricaoSalva = inscricaoService.inserirInscricao(inscricaoSalvar);
        return ResponseEntity.status(HttpStatus.CREATED).body(InscricaoDTO.fromEntity(inscricaoSalva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerInscricao(@PathVariable Long id){
        inscricaoService.removerInscricaoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<Page<InscricaoDTO>> pesquisarInscricoes(Pageable pageable){
        Page<Inscricao> paginaInscricoes = inscricaoService.recuperarInscricoesComPaginacao(pageable);

        Page<InscricaoDTO> paginaDto = paginaInscricoes.map(InscricaoDTO::fromEntity);

        return ResponseEntity.ok(paginaDto);
    }

}
