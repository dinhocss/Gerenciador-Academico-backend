package uff.br.gerenciador_academico.service;

import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.exception.RegraDeNegocioException;
import uff.br.gerenciador_academico.model.Turma;
import uff.br.gerenciador_academico.model.Professor;
import uff.br.gerenciador_academico.model.Disciplina;
import uff.br.gerenciador_academico.repository.TurmaRepository;
import uff.br.gerenciador_academico.repository.ProfessorRepository;
import uff.br.gerenciador_academico.repository.DisciplinaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashSet;


@RequiredArgsConstructor
@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;

    public List<Turma> recuperarTurmas(){
        return turmaRepository.findAll();
    }

    public Turma inserirTurma(Turma turma){
        if(turma.getProfessor() == null || turma.getProfessor().getId() == null){
            throw new RegraDeNegocioException("É obrigatório informar o ID do professor.");
        }

        Professor professor = professorRepository.findById(turma.getProfessor().getId()).orElseThrow(()->new EntidadeNaoEncontradaException(("Professor de id = " + turma.getProfessor().getId() + " não encontrado.")));

        if(turma.getDisciplina() == null || turma.getDisciplina().getId() == null){
            throw new RegraDeNegocioException("É obrigatório informara o ID da disciplina.");
        }

        Disciplina disciplina = disciplinaRepository.findById(turma.getDisciplina().getId()).orElseThrow(()->new EntidadeNaoEncontradaException("Disciplina de id = " + turma.getDisciplina().getId() + " não encontrada."));

        turma.setProfessor(professor);
        turma.setDisciplina(disciplina);
        turma.setInscricoes(new HashSet<>());

        return turmaRepository.save(turma);
    }

    @Transactional
    public Turma alterarTurma(Long id, Turma turma){
        Turma turmaAtualiza = turmaRepository.findByIdWithLock(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Turma com id = " + id + " não encontrada."));

        turmaAtualiza.setCodigo(turma.getCodigo());
        turmaAtualiza.setAno(turma.getAno());
        turmaAtualiza.setSemestre(turma.getSemestre());

        if(turma.getProfessor()!=null && turma.getProfessor().getId()!=null){
            Professor professor = professorRepository.findById(turma.getProfessor().getId()).orElseThrow(()-> new EntidadeNaoEncontradaException("Professor de id = " + turma.getProfessor().getId() + " não encontrado."));
                turmaAtualiza.setProfessor(professor);
        }

        if(turma.getDisciplina()!=null && turma.getDisciplina().getId()!=null){
            Disciplina disciplina = disciplinaRepository.findById(turma.getDisciplina().getId()).orElseThrow(()-> new EntidadeNaoEncontradaException("Disciplina de id = " + turma.getDisciplina().getId() + " não encontrada."));
            turmaAtualiza.setDisciplina(disciplina);
        }

        return turmaRepository.save(turmaAtualiza);
    }

    public void removerTurmaPorId(Long id){
        Turma turma = turmaRepository.findById(id).orElseThrow(()->new EntidadeNaoEncontradaException("Turma com id = " + id + " não encontrada."));

        if(turma.getInscricoes() != null && !turma.getInscricoes().isEmpty()){
            throw new RegraDeNegocioException("Não é possível remover uma turma que possui alunos inscritos.");
        }

        turmaRepository.deleteById(id);
    }

    public Page<Turma> recuperarTurmasComPaginacao(Pageable pageable, String codigo){
        return turmaRepository.findByDisciplinaNomeComPaginacao(pageable, "%" + codigo + "%");
    }
}
