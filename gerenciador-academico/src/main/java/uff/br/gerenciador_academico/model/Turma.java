package uff.br.gerenciador_academico.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="O 'Código da Turma' deve ser informado.")
    private String codigo;

    @NotEmpty(message="O 'Ano' deve ser informado.")
    private String ano;

    @NotEmpty(message="O 'Semestre' deve ser informado.")
    private String semestre;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="professor_id", nullable = false)
    private Professor professor;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="disciplina_id",nullable = false)
    private Disciplina disciplina;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy="turma")
    private Set<Inscricao> inscricoes = new HashSet<>();
}
