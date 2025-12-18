package uff.br.gerenciador_academico.controller;

import org.springframework.http.HttpStatus;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.model.Aluno;
import uff.br.gerenciador_academico.model.dto.AlunoDTO;
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
    public ResponseEntity<List<AlunoDTO>> recuperarAlunos(){
        List<Aluno> alunos = alunoService.recuperarAlunos();

        List<AlunoDTO> alunosDTO = alunos.stream()
                .map(AlunoDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(alunosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> recuperarPorId(@PathVariable Long id){
        return alunoService.recuperarAlunoPorId(id)
                .map(AlunoDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Aluno não encontrado com id: " + id));
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> cadastrarAluno(@RequestBody @Valid AlunoDTO aluno){
        Aluno alunoSalvar = aluno.toEntity();
        Aluno alunoSalvo = alunoService.inserirAluno(alunoSalvar);
        return ResponseEntity.status(HttpStatus.CREATED).body(AlunoDTO.fromEntity(alunoSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> alterarAluno(@PathVariable Long id, @RequestBody AlunoDTO aluno){
        Aluno alunoNovosDados = aluno.toEntity();
        Aluno alunoAtualizado = alunoService.alterarAluno(id, alunoNovosDados);
        return ResponseEntity.ok(AlunoDTO.fromEntity(alunoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAluno(@PathVariable Long id){
        alunoService.removerAlunoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<Page<AlunoDTO>> pesquisarAluno(@RequestParam(value = "codigo", required = false, defaultValue="") String codigo, Pageable pageable){
        Page<Aluno> paginaDeAlunos = alunoService.recuperarAlunosComPaginacao(pageable, codigo);
        Page<AlunoDTO> paginaDeDtos = paginaDeAlunos.map(AlunoDTO::fromEntity);

        return ResponseEntity.ok(paginaDeDtos);
    }
}
