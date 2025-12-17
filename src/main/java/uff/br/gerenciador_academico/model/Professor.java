package uff.br.gerenciador_academico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="O 'Nome' deve ser informado.")
    private String nome;

    @NotEmpty(message="O 'Email' deve ser informado.")
    private String email;

}
