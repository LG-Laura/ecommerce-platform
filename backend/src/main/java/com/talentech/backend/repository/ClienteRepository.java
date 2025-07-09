package com.talentech.backend.repository;

import com.talentech.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByDni(String dni);

    // Estos son los métodos que te están faltando
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);

    // Buscar por nombre o apellido que contenga un texto
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Cliente> buscarPorNombreOApellido(@Param("texto") String texto);
}
