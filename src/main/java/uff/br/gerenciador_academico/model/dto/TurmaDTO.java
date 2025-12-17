package uff.br.gerenciador_academico.model.dto;

import uff.br.gerenciador_academico.model.Turma;

public record TurmaDTO (
        Long id,
        String nomeDisciplina,
        String semestre,
        String professorNome
){
    public static TurmaDTO fromEntity(Turma turma){
        return new TurmaDTO(
                turma.getId(),
                turma.getDisciplina() != null ? turma.getDisciplina().getNome() : "Sem Disciplina",
                turma.getSemestre(),
                turma.getProfessor() != null ? turma.getProfessor().getNome() : "Sem Professor"
        );
    }
}
