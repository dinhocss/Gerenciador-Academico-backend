package uff.br.gerenciador_academico.service;

import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.model.Aluno;
import uff.br.gerenciador_academico.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public List<Aluno> recuperarAlunos(){
        return alunoRepository.findAll();
    }

    public Optional<Aluno> recuperarAlunoPorId(Long id){
        return alunoRepository.findById(id);
    }

    public Aluno inserirAluno(Aluno aluno){
        return alunoRepository.save(aluno);
    }


    @Transactional
    public Aluno alterarAluno(Long id, Aluno alunoNovo){
        Aluno alunoAtualiza = alunoRepository.findByIdWithLock(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Aluno com id = " + id + " não encontrado."));

        alunoAtualiza.setNome(alunoNovo.getNome());
        alunoAtualiza.setEmail(alunoNovo.getEmail());
        alunoAtualiza.setMatricula(alunoNovo.getMatricula());
        alunoAtualiza.setCpf(alunoNovo.getCpf());
        alunoAtualiza.setDataNascimento(alunoNovo.getDataNascimento());
        alunoAtualiza.setInscricoes(alunoNovo.getInscricoes());
        return alunoRepository.save(alunoAtualiza);
    }

    public void removerAlunoPorId(Long id){
        Aluno aluno = alunoRepository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Aluno com id = " + id + " não encontrado."));
        alunoRepository.deleteById(id);
    }

    public Page<Aluno> recuperarAlunosComPaginacao(Pageable pageable, String nome){
        return alunoRepository.findAllWithPageable(pageable, "%" + nome + "%");
    }
}
