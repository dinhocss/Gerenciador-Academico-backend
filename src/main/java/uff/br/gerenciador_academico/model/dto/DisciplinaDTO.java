package uff.br.gerenciador_academico.model.dto;

import uff.br.gerenciador_academico.model.Disciplina;

public record DisciplinaDTO (
        Long id,
        String nome,
        Integer cargaHoraria
){
    public static DisciplinaDTO fromEntity(Disciplina disciplina){
        return new DisciplinaDTO(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getCargaHoraria()
        );
    }

    public Disciplina toEntity(){
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(this.nome);
        disciplina.setCargaHoraria(cargaHoraria);
        return  disciplina;
    }
}
