package uff.br.gerenciador_academico.service;

import jakarta.transaction.Transactional;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.exception.RegraDeNegocioException;
import uff.br.gerenciador_academico.model.Professor;
import uff.br.gerenciador_academico.repository.ProfessorRepository;
import uff.br.gerenciador_academico.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final TurmaRepository turmaRepository;

    public List<Professor> recuperarProfessores(){
        return professorRepository.findAll();
    }

    public Professor recuperarProfessorPorId(Long id){
        return professorRepository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Professor de id = " + id + " não encontrado."));
    }

    public Professor inserirProfessor(Professor professor){
        return professorRepository.save(professor);
    }

    @Transactional
    public Professor alterarProfessor(Long id, Professor professor){
        Professor professorAtualiza = professorRepository.findByIdWithLock(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Professor de id = " + id + " não encontrado."));

        professorAtualiza.setNome(professor.getNome());
        professorAtualiza.setEmail(professor.getEmail());

        return professorRepository.save(professorAtualiza);
    }

    public void removerProfessorPorId(Long id){
        if(turmaRepository.existsByProfessorId(id)){
            throw new RegraDeNegocioException("Não é possível remover um professor que possui turmas vinculadas a ele");
        }

        Professor professor = professorRepository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Professor de id = " + id + " não encontrado."));

        professorRepository.delete(professor);
    }
}
