package uff.br.gerenciador_academico.model.dto;

import uff.br.gerenciador_academico.model.Professor;

public record ProfessorDTO (
        Long id,
        String nome,
        String email

){
    public static ProfessorDTO fromEntity(Professor professor){
        return new ProfessorDTO(
                professor.getId(),
                professor.getNome(),
                professor.getEmail()
        );
    }

    public Professor toEntity(){
        Professor professor = new Professor();
        professor.setNome(this.nome);
        professor.setEmail(this.email);
        return professor;
    }
}
