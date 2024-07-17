package com.Orale.Alura.ForoHub.repository;
import com.Orale.Alura.ForoHub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>{


    boolean existsByTituloAndAutor(String titulo, String mensaje);

    boolean existsByTituloAndAutorAndIdNot(String titulo, String autor, Long id);

    void deleteById(Long id);
}
