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

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
@RequiredArgsConstructor
public class TurmaController {
    private final TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> recuperarTodas(){
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
    public ResponseEntity<Turma> cadastrar(@RequestBody Turma turma){
        return ResponseEntity.ok(turmaService.inserirTurma(turma));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> alterar(@PathVariable Long id, @RequestBody Turma turma){
        return ResponseEntity.ok(turmaService.alterarTurma(id, turma));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        turmaService.removerTurmaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<Page<Turma>> pesquisar(@RequestParam(value = "codigo", required = false, defaultValue = "") String codigo, Pageable pageable){
        return ResponseEntity.ok(turmaService.recuperarTurmasComPaginacao(pageable, codigo));
    }
}
