package uff.br.gerenciador_academico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uff.br.gerenciador_academico.model.Aluno;
import uff.br.gerenciador_academico.model.dto.AlunoDTO;
import uff.br.gerenciador_academico.service.AlunoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlunoService alunoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 201 Created ao cadastrar aluno válido")
    void deveCadastraraAluno() throws Exception {
        AlunoDTO aluno = new AlunoDTO(null, "John Doe", "123", "999.999.999-99");

        //Mock do service devolvendo um aluno salvo
        Aluno alunoSalvo = aluno.toEntity();
        alunoSalvo.setId(1L);
        when(alunoService.inserirAluno(any())).thenReturn(alunoSalvo);

        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluno)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request se faltar dados")
    void deveFalharSemNome() throws Exception {
        AlunoDTO dtoInvalido = new AlunoDTO(null, null, "123", "999.999.999-99");

        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isUnprocessableEntity());
    }
}
