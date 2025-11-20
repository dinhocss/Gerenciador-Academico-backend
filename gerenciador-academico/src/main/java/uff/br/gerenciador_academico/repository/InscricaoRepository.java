package uff.br.gerenciador_academico.repository;

import uff.br.gerenciador_academico.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    @Query("select i from Inscricao i " +
            "left outer join fetch i.aluno a " +
            "left outer join fetch i.turma t " +
            "where t.id = :turmaId")
    List<Inscricao> findByTurmaId(@Param("turmaId") String turmaId);

    @Query("select i from Inscricao i " +
            "left outer join fetch i.aluno a " +
            "left outer join fetch i.turma t " +
            "where a.id = :alunoId")
    List<Inscricao> findByAlunoId(@Param("alunoId") String alunoId);

}
