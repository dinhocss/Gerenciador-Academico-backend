package uff.br.gerenciador_academico.model.dto;

import jakarta.validation.constraints.NotEmpty;
import uff.br.gerenciador_academico.model.Aluno;

public record AlunoDTO (
        Long id,

        @NotEmpty(message="O 'Nome' deve ser informado.")
        String nome,

        @NotEmpty(message="A 'Matrícula' deve ser informada.")
        String matricula,

        @NotEmpty(message="O 'CPF' deve ser informado.")
        String cpf
) {
    public static AlunoDTO fromEntity(Aluno aluno){
        return new AlunoDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getMatricula(),
                aluno.getCpf()
        );
    }

    public Aluno toEntity(){
        Aluno aluno = new Aluno();
        aluno.setId(this.id);
        aluno.setNome(this.nome);
        aluno.setMatricula(this.matricula);
        aluno.setCpf(this.cpf);
        return aluno;
    }
}
