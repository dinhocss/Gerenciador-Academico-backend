package uff.br.gerenciador_academico.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uff.br.gerenciador_academico.exception.EntidadeNaoEncontradaException;
import uff.br.gerenciador_academico.model.Aluno;
import uff.br.gerenciador_academico.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {
    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    // --- TESTES DE RECUPERAR ---
    @Test
    @DisplayName("Deve retornar lista de alunos com sucesso")
    void deveRecuperarAlunos(){
        //Cenário
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Claudio");
        when(alunoRepository.findAll()).thenReturn(List.of(aluno1));

        //Ação
        List<Aluno> resultado = alunoService.recuperarAlunos();

        //Verificação
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Claudio", resultado.get(0).getNome());
        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar um aluno quando o ID existir")
    void deveRecuperarAlunoPorID(){
        Long id = 1L;
        Aluno aluno = new Aluno();
        aluno.setId(id);
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        Optional<Aluno> resultado = alunoService.recuperarAlunoPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
    }

    // --- TESTES DE INSERIR ---
    @Test
    @DisplayName("Deve salvar um aluno com sucesso")
    void deveInserirAluno(){
        Aluno alunoParaSalvar = new Aluno();
        alunoParaSalvar.setNome("Novo Aluno");

        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoParaSalvar);

        Aluno resultado = alunoService.inserirAluno(alunoParaSalvar);

        assertNotNull(resultado);
        assertEquals("Novo Aluno", resultado.getNome());
        verify(alunoRepository, times(1)).save(any(Aluno.class));
    }

    // --- TESTES DE ALTERAR ---
    @Test
    @DisplayName("Deve alterar aluno com sucesso quando ID existe")
    void deveAlterarAlunoComSucesso(){
        Long id = 1L;

        //Aluno existente no banco
        Aluno alunoExistente = new Aluno();
        alunoExistente.setId(id);
        alunoExistente.setNome("Nome Antigo");

        //Dados novos chegando
        Aluno alunoNovosDados = new Aluno();
        alunoNovosDados.setNome("Nome Novo");
        alunoNovosDados.setEmail("novo@email.com");

        //Mock de findByIdWithLock
        when(alunoRepository.findByIdWithLock(id)).thenReturn(Optional.of(alunoExistente));
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(i -> i.getArgument(0));

        //Ação
        Aluno resultado = alunoService.alterarAluno(id, alunoNovosDados);

        //Verificação
        assertEquals("Nome Novo", resultado.getNome());
        assertEquals("novo@email.com", resultado.getEmail());
        verify(alunoRepository, times(1)).save(alunoExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar alterar ID inexistente")
    void deveLancarErroIdInexistente(){
        Long id = 99L;
        Aluno alunoNovo = new Aluno();

        //Simulando que não achou nada
        when(alunoRepository.findByIdWithLock(id)).thenReturn(Optional.empty());

        //Verificando se a exceção é lançada
        assertThrows(EntidadeNaoEncontradaException.class, ()->{
           alunoService.alterarAluno(id, alunoNovo);
        });

        verify(alunoRepository, never()).save(any());
    }

    // --- TESTES DE REMOVER ---
    @Test
    @DisplayName("Deve deletar aluno com sucesso")
    void deveRemoverAluno(){
        Long id = 1L;
        Aluno aluno = new Aluno();
        aluno.setId(id);

        //Achando o aluno primeiro
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        alunoService.removerAlunoPorId(id);

        verify(alunoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar remover ID inexistente")
    void deveLancarErroRemoverIdInexistente(){
        Long id = 99L;
        when(alunoRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntidadeNaoEncontradaException.class, ()->{
           alunoService.removerAlunoPorId(id);
        });

        verify(alunoRepository, never()).deleteById(any());
    }
}
