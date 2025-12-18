package uff.br.gerenciador_academico.service;

import jakarta.transaction.Transactional;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.exception.RegraDeNegocioException;
import uff.br.gerenciador_academico.model.Disciplina;
import uff.br.gerenciador_academico.repository.DisciplinaRepository;
import uff.br.gerenciador_academico.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final TurmaRepository turmaRepository;

    public List<Disciplina> recuperarDisciplinas(){
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> recuperarDisciplinaPorId(Long id){
        return disciplinaRepository.findById(id);
    }

    public Disciplina inserirDisciplina(Disciplina disciplina){
        return disciplinaRepository.save(disciplina);
    }

    @Transactional
    public Disciplina alterarDisciplina(Long id, Disciplina novaDisciplina){
        Disciplina disciplinaAtualiza = disciplinaRepository.findByIdWithLock(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Disciplina com o id = " + id + " não encontrada."));

        disciplinaAtualiza.setNome(novaDisciplina.getNome());
        disciplinaAtualiza.setCargaHoraria(novaDisciplina.getCargaHoraria());
        return disciplinaRepository.save(disciplinaAtualiza);
    }

    public void removerDisciplinaPorId(Long id){
        if(turmaRepository.existsByDisciplinaId(id)){
            throw new RegraDeNegocioException("Não é possível remover uma disciplina associada a uma turma.");
        }
        Disciplina disciplina = disciplinaRepository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Disciplina de id = " + id + " não encontrada"));
        disciplinaRepository.delete(disciplina);
    }

    public Page<Disciplina> recuperarDisciplinasComPaginacao(Pageable pageable, String nome){
        return disciplinaRepository.findAllWithPageable(pageable, "%" + nome + "%");
    }
}
