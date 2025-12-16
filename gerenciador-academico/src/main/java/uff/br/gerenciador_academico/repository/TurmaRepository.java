package uff.br.gerenciador_academico.repository;

import uff.br.gerenciador_academico.model.Turma;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma,Long> {
    List<Turma> findByCodigo(String codigo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Turma t where t.id = :id")
    Optional<Turma> findByIdWithLock(@Param("id") Long id);

    @Query("select t from Turma t left outer join fetch t.professor p " +
            "left outer join fetch t.disciplina d " +
            "left outer join fetch t.inscricoes i " +
            "left outer join fetch i.aluno a " +
            " where t.id = :id")
    Optional<Turma> findByIdWithStudents(@Param("id") Long id);

    @Query("select t from Turma t left outer join fetch t.professor p " +
            "left outer join fetch t.disciplina d " +
            "where d.id = :disciplinaId ")
    List<Turma> findByDisciplinaId(@Param("disciplinaId") Long disciplinaId);

    boolean existsByDisciplinaId(Long disciplinaId);
    boolean existsByProfessorId(Long professorId);
    @Query(
            value = "select t from Turma t " +
                    "left outer join fetch t.disciplina d " +
                    "left outer join fetch t.professor p " +
                    "where t.codigo like :codigo ",
            countQuery="select count(t) from Turma t " +
                    "join t.disciplina d " +
                    "where t.codigo like :codigo "
    )
    Page<Turma> findByDisciplinaNomeComPaginacao(Pageable pageable, @Param("codigo") String codigo);
}
