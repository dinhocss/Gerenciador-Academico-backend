package uff.br.gerenciador_academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uff.br.gerenciador_academico.model.dto.DisciplinaDTO;
import uff.br.gerenciador_academico.model.Disciplina;
import uff.br.gerenciador_academico.service.DisciplinaService;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.model.ResultadoPaginado;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/disciplinas")
@CrossOrigin(origins="http://localhost:5173")
@RequiredArgsConstructor
public class DisciplinaController {
    private final DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> recuperarDisciplinas(){
        List<Disciplina> disciplinas = disciplinaService.recuperarDisciplinas();

        List<DisciplinaDTO> disciplinasDTO = disciplinas.stream()
                .map(DisciplinaDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(disciplinasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> recuperarDisciplinaPorId(@PathVariable Long id){
        return disciplinaService.recuperarDisciplinaPorId(id)
                .map(DisciplinaDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(()->new EntidadeNaoEncontradaException("Disciplina não encontrada com id: " + id));
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTO> cadastrarDisciplina(@RequestBody @Valid DisciplinaDTO disciplina){
        Disciplina disciplinaSalvar = disciplina.toEntity();
        Disciplina disciplinaSalva = disciplinaService.inserirDisciplina(disciplinaSalvar);
        return ResponseEntity.status(HttpStatus.CREATED).body(DisciplinaDTO.fromEntity(disciplinaSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> alterarDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplina){
        Disciplina disciplinaNovosDados = disciplina.toEntity();
        Disciplina disciplinaAtualizada = disciplinaService.alterarDisciplina(id, disciplinaNovosDados);
        return ResponseEntity.ok(DisciplinaDTO.fromEntity(disciplinaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerDisciplina(@PathVariable Long id){
        disciplinaService.removerDisciplinaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<Page<DisciplinaDTO>> pesquisarDisciplina(@RequestParam(value = "codigo", required = false, defaultValue = "") String codigo, Pageable pageable){
        Page<Disciplina> paginaDeDisciplinas = disciplinaService.recuperarDisciplinasComPaginacao(pageable, codigo);
        Page<DisciplinaDTO> paginaDeDtos = paginaDeDisciplinas.map(DisciplinaDTO::fromEntity);

        return ResponseEntity.ok(paginaDeDtos);
    }
}
