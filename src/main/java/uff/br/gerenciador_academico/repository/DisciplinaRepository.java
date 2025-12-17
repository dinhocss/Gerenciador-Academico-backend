package uff.br.gerenciador_academico.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uff.br.gerenciador_academico.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select d from Disciplina d where d.id = :id")
    Optional<Disciplina> findByIdWithLock(@Param("id") Long id);

    @Query(
            value = "select d from Disciplina d where d.nome like :nome order by d.nome ",
            countQuery = "select count(d) from Disciplina d where d.nome like :nome "
    )
    Page<Disciplina> findAllWithPageable(Pageable pageable, @Param("nome") String nome);

}
