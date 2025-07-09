package com.talentech.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
// Importamos las anotaciones de JPA para el mapeo a la base de datos
import jakarta.persistence.*;
// Importamos validaciones para valores mínimos
import jakarta.validation.constraints.Min;
// Importamos validaciones para campos requeridos
import jakarta.validation.constraints.NotNull;

// @Entity marca esta clase como una entidad de base de datos
@Entity
// @Table especifica el nombre de la tabla intermedia que relaciona pedidos y artículos
@Table(name = "pedido_articulos")
// Clase que representa la relación entre un pedido y un artículo específico
// Esta es una tabla intermedia que almacena cantidad y precio de cada artículo en cada pedido
public class PedidoArticulo {

    // @Id marca este campo como la clave primaria de la tabla
    @Id
    // @GeneratedValue indica que el ID se genera automáticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Campo que almacena el identificador único de esta relación pedido-artículo
    private Long id;

    // @ManyToOne define relación muchos-a-uno (muchos PedidoArticulo pueden tener un Pedido)
    // fetch = FetchType.EAGER significa que se carga el pedido inmediatamente
    @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn especifica la columna que conecta con la tabla pedidos
    @JoinColumn(name = "pedido_id", nullable = false)
    // @JsonIgnore evita bucles infinitos al serializar a JSON
    @JsonIgnore
    // Campo que almacena la referencia al pedido al que pertenece este artículo
    private Pedido pedido;

    // @ManyToOne define relación muchos-a-uno (muchos PedidoArticulo pueden tener un Articulo)
    @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn especifica la columna que conecta con la tabla articulos
    @JoinColumn(name = "articulo_id", nullable = false)
    // Campo que almacena la referencia al artículo que se está comprando
    private Articulo articulo;

    // Validación para que la cantidad no sea nula
    @NotNull(message = "La cantidad no puede estar vacía")
    // Validación para que la cantidad sea al menos 1
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    // Campo que almacena cuántas unidades de este artículo se compraron
    private Integer cantidad;

    // Campo que almacena el precio unitario del artículo al momento de la compra
    // Es importante guardarlo porque el precio puede cambiar en el futuro
    @Column(name = "precio_unitario")
    private Double precioUnitario;

    // Campo que almacena el subtotal (cantidad * precio unitario)
    @Column(name = "subtotal")
    private Double subtotal;

    public PedidoArticulo() {}

    public PedidoArticulo(Pedido pedido, Articulo articulo, Integer cantidad, Double precioUnitario) {
        this.pedido = pedido;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    private void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = cantidad * precioUnitario;
        } else {
            this.subtotal = 0.0;
        }
    }

    // @PrePersist se ejecuta antes de insertar el registro en la base de datos
    @PrePersist
    @PreUpdate
    public void preSave() {
        calcularSubtotal();
    }

    public void recalcularSubtotal() {
        calcularSubtotal();
    }
}


