package com.talentech.backend.repository;
import com.talentech.backend.model.PedidoArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoArticuloRepository extends JpaRepository<PedidoArticulo, Long> {

    List<PedidoArticulo> findByPedidoId(Long pedidoId);

    List<PedidoArticulo> findByArticuloId(Long articuloId);

    void deleteByPedidoId(Long pedidoId);

    // Calcular el total de un pedido
    @Query("SELECT SUM(pa.subtotal) FROM PedidoArticulo pa WHERE pa.pedido.id = :pedidoId")
    Double calcularTotalPedido(@Param("pedidoId") Long pedidoId);
}

