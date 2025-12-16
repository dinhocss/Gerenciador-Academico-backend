package uff.br.gerenciador_academico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uff.br.gerenciador_academico.model.Turma;
import uff.br.gerenciador_academico.service.TurmaService;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
@RequiredArgsConstructor
public class TurmaController {
    private final TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<Turma>> recuperarTodas(){
        return ResponseEntity.ok(turmaService.recuperarTurmas());
    }

    @GetMapping("/{id")
    public ResponseEntity<Turma> recuperarPorId(@PathVariable Long id){
        return turmaService.recuperarTurmas().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
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
