package uff.br.gerenciador_academico.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import uff.br.gerenciador_academico.model.enums.StatusMatricula;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message ="O 'Nome' deve ser informado.")
    private String nome;

    @NotEmpty(message="O 'Email' deve ser informado.")
    private String email;

    @NotEmpty(message="A 'Matricula' deve ser informada.")
    private String matricula;

    @NotEmpty(message="O 'CPF' deve ser informado.")
    private String cpf;

    @NotNull(message="A 'Data de Nascimento' deve ser informada.")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private StatusMatricula statusMatricula;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy="aluno")
    private Set<Inscricao> inscricoes;

}
