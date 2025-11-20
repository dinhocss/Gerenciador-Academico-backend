package uff.br.gerenciador_academico.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="A 'Data/Hora' deve ser informada.")
    private LocalDateTime dataHora;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="aluno_id", nullable = false)
    private Aluno aluno;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="turma_id", nullable = false)
    private Turma turma;
}
