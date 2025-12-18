package uff.br.gerenciador_academico.model.dto;

import uff.br.gerenciador_academico.model.Inscricao;
import uff.br.gerenciador_academico.model.Aluno;
import uff.br.gerenciador_academico.model.Turma;

import java.time.LocalDateTime;

public record InscricaoDTO (
        Long id,
        LocalDateTime dataHora,
        Long alunoId,
        Long turmaId
){
    public static InscricaoDTO fromEntity(Inscricao inscricao){
        return new InscricaoDTO(
                inscricao.getId(),
                inscricao.getDataHora(),
                inscricao.getAluno().getId(),
                inscricao.getTurma().getId()
        );
    }

    public Inscricao toEntity(){
        Inscricao inscricao = new Inscricao();
        inscricao.setId(this.id);
        inscricao.setDataHora(this.dataHora);

        if(this.alunoId != null){
            Aluno aluno = new Aluno();
            aluno.setId(this.alunoId);
            inscricao.setAluno(aluno);
        }

        if(this.turmaId != null){
            Turma turma = new Turma();
            turma.setId(this.turmaId);
            inscricao.setTurma(turma);
        }
        return inscricao;
    }
}
