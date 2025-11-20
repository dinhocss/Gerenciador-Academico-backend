package uff.br.gerenciador_academico.repository;

import uff.br.gerenciador_academico.model.Aluno;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByName(String nome);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Aluno a where a.id = :id")
    Optional<Aluno> findByIdWithLock(@Param("id") Long id);

    @Query("select a from Aluno a left outer join fetch a.inscricoes i where a.id = :id")
    Optional<Aluno> findById(@Param("id") Long id);

    @Query("select a from Aluno a order by a.nome")
    List<Aluno> findAll();

    @Query(
            value = "select a from Aluno a where a.nome like :nome order by a.nome ",
            countQuery = "select count(a) from Aluno a where a.nome like :nome "
    )
    Page<Aluno> findAllWithPageable(Pageable pageable, @Param("nome") String nome);
}
