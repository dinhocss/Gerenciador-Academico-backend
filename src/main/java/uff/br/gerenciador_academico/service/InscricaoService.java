package uff.br.gerenciador_academico.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.exception.RegraDeNegocioException;
import uff.br.gerenciador_academico.model.Aluno;
import uff.br.gerenciador_academico.model.Inscricao;
import uff.br.gerenciador_academico.model.Turma;
import uff.br.gerenciador_academico.repository.InscricaoRepository;
import uff.br.gerenciador_academico.repository.TurmaRepository;
import uff.br.gerenciador_academico.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InscricaoService {
    private final InscricaoRepository inscricaoRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    //1. Recuperar todas as inscrições
    public List<Inscricao> recuperarInscricoes(){
        return inscricaoRepository.findAll();
    }

    //2. Recuperar por ID
    public Optional<Inscricao> recuperarInscricaoPorId(Long id){
        return inscricaoRepository.findById(id);
    }

    //3. Inserir (Contém lógica de validação)
    public Inscricao inserirInscricao(Inscricao inscricao){
        if(inscricao.getAluno() == null || inscricao.getAluno().getId() == null){
            throw new RegraDeNegocioException("É obrigatório informar o ID do aluno.");
        }
        Aluno aluno = alunoRepository.findById(inscricao.getAluno().getId())
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Aluno não encontrado com id: " + inscricao.getAluno().getId()));

        if(inscricao.getTurma()==null || inscricao.getTurma().getId() == null){
            throw new RegraDeNegocioException("É obrigatório informar o ID da turma.");
        }
        Turma turma = turmaRepository.findById(inscricao.getTurma().getId())
                .orElseThrow(()->new RegraDeNegocioException("Turma não encontrada com id: " + inscricao.getTurma().getId()));

        inscricao.setAluno(aluno);
        inscricao.setTurma(turma);

        return inscricaoRepository.save(inscricao);
    }

    //4. Alterar (Caso seja ncessário poderá ser criado métodos para modificar alguns campos futuros referentes as inscrições. Por enquanto não vamos permitir alteração de inscrições.

    //5. Remover
    public void removerInscricaoPorId(Long id){
        if(!inscricaoRepository.existsById(id)){
            throw new EntidadeNaoEncontradaException("Inscrição com id = " + id + " não encontrada.");
        }
        inscricaoRepository.deleteById(id);
    }

    //6. Paginação
    public Page<Inscricao> recuperarInscricoesComPaginacao(Pageable pageable){
        return inscricaoRepository.findAll(pageable);
    }

}

