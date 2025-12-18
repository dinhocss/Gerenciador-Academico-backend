package uff.br.gerenciador_academico.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.exception.RegraDeNegocioException;
import uff.br.gerenciador_academico.model.Aluno;
import uff.br.gerenciador_academico.model.Inscricao;
import uff.br.gerenciador_academico.model.Turma;
import uff.br.gerenciador_academico.repository.AlunoRepository;
import uff.br.gerenciador_academico.repository.InscricaoRepository;
import uff.br.gerenciador_academico.repository.TurmaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

    @InjectMocks
    private InscricaoService inscricaoService;

    @Mock
    private InscricaoRepository inscricaoRepository;
    @Mock
    private AlunoRepository alunoRepository;
    @Mock
    private TurmaRepository turmaRepository;

    @Test
    @DisplayName("Deve realizar inscrição com sucesso")
    void deveInscreverComSucesso(){
        //Cenário: Aluno e Turma existem
        Aluno aluno = new Aluno();
        Turma turma = new Turma();
        aluno.setId(1L);
        turma.setId(10L);

        Inscricao inscricaoParaSalvar = new Inscricao();
        inscricaoParaSalvar.setAluno(aluno);
        inscricaoParaSalvar.setTurma(turma);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(turmaRepository.findById(10L)).thenReturn(Optional.of(turma));
        when(inscricaoRepository.save(any())).thenReturn(inscricaoParaSalvar);

        //Ação
        Inscricao salva = inscricaoService.inserirInscricao(inscricaoParaSalvar);

        //Verificação
        assertNotNull(salva);
        verify(inscricaoRepository).save(any());
    }

    @Test
    @DisplayName("Deve falhar se o aluno não existir")
    void deveFalharAlunoInexistente(){
        Inscricao inscricao = new Inscricao();
        Aluno aluno = new Aluno();
        aluno.setId(99L);
        inscricao.setAluno(aluno);

        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, ()->{
            inscricaoService.inserirInscricao(inscricao);
        });
    }

    @Test
    @DisplayName("Deve falhar se o ID do aluno for nulo")
    void deveFalharIdAlunoNulo(){
        Inscricao inscricao = new Inscricao();
        Aluno aluno = new Aluno();
        inscricao.setAluno(aluno);

        assertThrows(RegraDeNegocioException.class, ()->{
            inscricaoService.inserirInscricao(inscricao);
        });
    }
}
