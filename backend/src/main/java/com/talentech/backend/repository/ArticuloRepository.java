package com.talentech.backend.repository;

import com.talentech.backend.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    // Buscar por nombre exacto
    List<Articulo> findByNombre(String nombre);

    // Buscar artículos cuyo nombre contenga un texto
    List<Articulo> findByNombreContaining(String text);

    // Buscar artículos con precio mayor a un valor dado
    List<Articulo> findByPrecioGreaterThan(Double precio);

    // Buscar artículos con precio entre dos valores
    List<Articulo> findByPrecioBetween(Double min, Double max);

    // Buscar por nombre ignorando mayúsculas y minúsculas
    List<Articulo> findByNombreIgnoreCase(String nombre);

    // Buscar artículos ordenados por precio ascendente
    List<Articulo> findAllByOrderByPrecioAsc();

    // Buscar artículos ordenados por precio descendente
    List<Articulo> findAllByOrderByPrecioDesc();

    // Buscar artículos por nombre y precio mayor a cierto valor
    List<Articulo> findByNombreAndPrecioGreaterThan(String nombre, Double precio);

    // Buscar artículos activos
    List<Articulo> findByActivoTrue();

    // Buscar artículos activos y con stock > 0
    List<Articulo> findByActivoTrueAndStockGreaterThan(int stock);

    // Buscar artículos con stock bajo
    List<Articulo> findByStockLessThan(Integer stockMinimo);
}
