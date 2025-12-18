package uff.br.gerenciador_academico.model.dto;

import uff.br.gerenciador_academico.model.Turma;
import uff.br.gerenciador_academico.model.Professor;
import uff.br.gerenciador_academico.model.Disciplina;

public record TurmaDTO (
        Long id,
        String nomeDisciplina,
        String semestre,
        String professorNome,
        Long disciplinaId,
        Long professorId,
        String codigo,
        String ano
){
    public static TurmaDTO fromEntity(Turma turma){
        return new TurmaDTO(
                turma.getId(),
                turma.getDisciplina() != null ? turma.getDisciplina().getNome() : "Sem Disciplina",
                turma.getSemestre(),
                turma.getProfessor() != null ? turma.getProfessor().getNome() : "Sem Professor",
                turma.getDisciplina() != null ? turma.getDisciplina().getId(): null,
                turma.getProfessor() != null ? turma.getProfessor().getId() : null,
                turma.getCodigo(),
                turma.getAno()
        );
    }

    public Turma toEntity(){
        Turma turma = new Turma();
        turma.setId(this.id);
        turma.setSemestre(this.semestre);
        turma.setCodigo(this.codigo);
        turma.setAno(this.ano);

        if(this.disciplinaId!=null){
            Disciplina d = new Disciplina();
            d.setId(this.disciplinaId);
            turma.setDisciplina(d);
        }

        if(this.professorId!=null){
            Professor p = new Professor();
            p.setId(this.professorId);
            turma.setProfessor(p);
        }

        return turma;
    }
}
