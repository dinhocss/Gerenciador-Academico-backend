package uff.br.gerenciador_academico.controller;

import org.springframework.http.HttpStatus;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.model.Professor;
import uff.br.gerenciador_academico.model.dto.ProfessorDTO;
import uff.br.gerenciador_academico.model.ResultadoPaginado;
import uff.br.gerenciador_academico.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
@CrossOrigin(origins="http://localhost:5173")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> recuperarProfessores(){
        List<Professor> professores = professorService.recuperarProfessores();

        List<ProfessorDTO> professoresDTO = professores.stream()
                .map(ProfessorDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(professoresDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> recuperarPorId(@PathVariable Long id){
        return professorService.recuperarProfessorPorId(id)
                .map(ProfessorDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Professor não encontrado com id: " + id));
    }

    @PostMapping public ResponseEntity<ProfessorDTO> cadastrarProfessor(@RequestBody @Valid ProfessorDTO professor){
        Professor professorSalvar = professor.toEntity();
        Professor professorSalvo = professorService.inserirProfessor(professorSalvar);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProfessorDTO.fromEntity(professorSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> alterarProfessor(@PathVariable Long id, @RequestBody ProfessorDTO professor){
        Professor professorNovosDados = professor.toEntity();
        Professor professorAtualizado = professorService.alterarProfessor(id, professorNovosDados);
        return ResponseEntity.ok(ProfessorDTO.fromEntity(professorAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProfessor(@PathVariable Long id){
        professorService.removerProfessorPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<Page<ProfessorDTO>> pesquisarProfessor(@RequestParam(value = "codigo", required = false, defaultValue="") String codigo, Pageable pageable){
        Page<Professor> paginaDeProfessores = professorService.recuperarProfessoresComPaginacao(pageable, codigo);
        Page<ProfessorDTO> paginaDeDtos = paginaDeProfessores.map(ProfessorDTO::fromEntity);

        return ResponseEntity.ok(paginaDeDtos);
    }
}
