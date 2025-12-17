package uff.br.gerenciador_academico.repository;

import uff.br.gerenciador_academico.model.Disciplina;
import uff.br.gerenciador_academico.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Professor p where p.id = : id")
    Optional<Professor> findByIdWithLock(@Param("id") Long id);
}

