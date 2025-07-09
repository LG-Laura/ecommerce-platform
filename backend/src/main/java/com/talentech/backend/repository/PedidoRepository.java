package com.talentech.backend.repository;

import com.talentech.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.email = :email")
    List<Pedido> findByClienteEmail(@Param("email") String email);

    List<Pedido> findByEstado(String estado);

    List<Pedido> findByClienteIdAndEstado(Long clienteId, String estado);

    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findByFechaPedidoBetween(@Param("fechaInicio") LocalDateTime fechaInicio,
                                          @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId AND p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findByClienteIdAndFechaPedidoBetween(@Param("clienteId") Long clienteId,
                                                      @Param("fechaInicio") LocalDateTime fechaInicio,
                                                      @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId")
    Long countByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT p FROM Pedido p ORDER BY p.fechaPedido DESC")
    List<Pedido> findAllOrderByFechaPedidoDesc();
}



