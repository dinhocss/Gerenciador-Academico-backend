package uff.br.gerenciador_academico.controller;

import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.model.Aluno;
import uff.br.gerenciador_academico.model.ResultadoPaginado;
import uff.br.gerenciador_academico.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins="http://localhost:5173")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<Aluno>> recuperarAlunos(){
        return ResponseEntity.ok(alunoService.recuperarAlunos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> recuperarPorId(@PathVariable Long id){
        return alunoService.recuperarAlunoPorId(id).map(ResponseEntity::ok).orElseThrow(()-> new EntidadeNaoEncontradaException("Aluno não encontrado com id: " + id));
    }

}
