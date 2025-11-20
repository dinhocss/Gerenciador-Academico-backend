package uff.br.gerenciador_academico.model;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="O 'Nome' deve ser informado.")
    private String nome;

    @NotNull(message="A 'Carga Horária' deve ser informada.")
    @Min(value=1,  message="A 'Carga Horária' deve ser maior que 0")
    @Max(value=100, message="A 'Carga Horária' deve ser menor ou igual a 100")
    private Integer cargaHoraria;




}
