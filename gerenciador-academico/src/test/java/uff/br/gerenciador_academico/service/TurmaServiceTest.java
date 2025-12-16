package uff.br.gerenciador_academico.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uff.br.gerenciador_academico.exception.RegraDeNegocioException;
import uff.br.gerenciador_academico.model.Disciplina;
import uff.br.gerenciador_academico.model.Professor;
import uff.br.gerenciador_academico.model.Turma;
import uff.br.gerenciador_academico.repository.DisciplinaRepository;
import uff.br.gerenciador_academico.repository.ProfessorRepository;
import uff.br.gerenciador_academico.repository.TurmaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

    @InjectMocks
    private TurmaService turmaService;

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @Test
    void deveInserirTurmaComSucesso(){
        //Cenario
        Professor professorMock = new Professor();
        professorMock.setId(1L);

        Disciplina disciplinaMock = new Disciplina();
        disciplinaMock.setId(10L);

        Turma turmaParaSalvar = new Turma();
        turmaParaSalvar.setProfessor(professorMock);
        turmaParaSalvar.setDisciplina(disciplinaMock);

        //Simulando o comportamento do Repository (Mock)
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professorMock));
        when(disciplinaRepository.findById(10L)).thenReturn(Optional.of(disciplinaMock));
        when(turmaRepository.save(any(Turma.class))).thenReturn(turmaParaSalvar);

        //Execução
        Turma turmaSalva = turmaService.inserirTurma(turmaParaSalvar);

        //Verificação
        assertNotNull(turmaSalva);
        verify(turmaRepository, times(1)).save(any(Turma.class));
    }

    @Test
    void naoDeveInserirTurmaSemProfessor(){
        //Cenario
        Turma turmaSemProfessor = new Turma();
        turmaSemProfessor.setProfessor(null);

        //Execução e verificação
        RegraDeNegocioException erro = assertThrows(RegraDeNegocioException.class, () -> {
            turmaService.inserirTurma(turmaSemProfessor);
        });

        assertEquals("É obrigatório informar o ID do professor.", erro.getMessage());
        verify(turmaRepository, never()).save(any());
    }
}
