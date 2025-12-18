package uff.br.gerenciador_academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uff.br.gerenciador_academico.model.dto.TurmaDTO;
import uff.br.gerenciador_academico.model.Turma;
import uff.br.gerenciador_academico.service.TurmaService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
@RequiredArgsConstructor
public class TurmaController {
    private final TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> recuperarTurmas(){
        List<Turma> turmas = turmaService.recuperarTurmas();

        List<TurmaDTO> turmasDTO = turmas.stream()
                .map(TurmaDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(turmasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> recuperarPorId(@PathVariable Long id){
        return turmaService.recuperarTurmaPorId(id)
                .map(TurmaDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> cadastrarTurmas(@RequestBody @Valid TurmaDTO turma){
        Turma turmaSalvar = turma.toEntity();
        Turma turmaSalva = turmaService.inserirTurma(turmaSalvar);
        return ResponseEntity.status(HttpStatus.CREATED).body(TurmaDTO.fromEntity(turmaSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> alterarTurma(@PathVariable Long id, @RequestBody @Valid TurmaDTO turma){
       Turma turmaNovosDados = turma.toEntity();
       Turma turmaAtualizada = turmaService.alterarTurma(id, turmaNovosDados);
       return ResponseEntity.ok(TurmaDTO.fromEntity(turmaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTurma(@PathVariable Long id){
        turmaService.removerTurmaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<Page<TurmaDTO>> pesquisar(@RequestParam(value = "codigo", required = false, defaultValue = "") String codigo, Pageable pageable){
        Page<Turma> paginaTurmas = turmaService.recuperarTurmasComPaginacao(pageable, codigo);
        return ResponseEntity.ok(paginaTurmas.map(TurmaDTO::fromEntity));
    }
}
