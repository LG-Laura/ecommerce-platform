package com.talentech.backend.model;

// Importamos JsonIgnore para evitar bucles infinitos en la serialización JSON
import com.fasterxml.jackson.annotation.JsonIgnore;
// Importamos las anotaciones de JPA para el mapeo a la base de datos
import jakarta.persistence.*;
// Importamos validaciones para campos requeridos
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// Importamos LocalDateTime para manejar fechas y horas
import java.time.LocalDateTime;
// Importamos ArrayList para manejar listas
import java.util.ArrayList;
// Importamos List para manejar listas de elementos
import java.util.List;

// @Entity marca esta clase como una entidad de base de datos
@Entity
// @Table especifica el nombre de la tabla donde se guardarán los pedidos
@Table(name = "pedidos")
// Clase que representa un pedido o orden de compra en el sistema
public class Pedido {

    // @Id marca este campo como la clave primaria de la tabla
    @Id
    // @GeneratedValue indica que el ID se genera automáticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Campo que almacena el identificador único del pedido
    private Long id;

    // @ManyToOne define una relación muchos-a-uno (muchos pedidos pueden tener un cliente)
    // fetch = FetchType.EAGER significa que se carga el cliente inmediatamente
    @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn especifica la columna que conecta las tablas
    @JoinColumn(name = "cliente_id", nullable = false)
    // Validación para que siempre haya un cliente asociado al pedido
    @NotNull(message = "El cliente no puede estar vacío")
    // Campo que almacena la referencia al cliente que hizo el pedido
    private Cliente cliente;

    // @Column especifica el nombre de la columna en la base de datos
    @Column(name = "fecha_pedido")
    // Campo que almacena cuándo se creó el pedido
    private LocalDateTime fechaPedido;

    // Campo que almacena cuándo se debe entregar el pedido
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    // Validación para que el estado no esté vacío
    @NotBlank(message = "El estado no puede estar vacío")
    // Campo que almacena el estado del pedido (PENDIENTE, PROCESANDO, ENTREGADO, etc.)
    private String estado;

    // @OneToMany define una relación uno-a-muchos (un pedido puede tener muchos artículos)
    // mappedBy indica que la relación es manejada por el campo "pedido" en PedidoArticulo
    // cascade = CascadeType.ALL significa que las operaciones se propagan a los hijos
    // fetch = FetchType.LAZY significa que se cargan solo cuando se necesitan
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonIgnore evita bucles infinitos al serializar a JSON
    @JsonIgnore
    // Lista que contiene todos los artículos asociados a este pedido
    private List<PedidoArticulo> pedidoArticulos = new ArrayList<>();

    // Campo que almacena el total calculado del pedido
    @Column(name = "total")
    private Double total;

    // Campo opcional para almacenar observaciones sobre el pedido
    @Column(name = "observaciones")
    private String observaciones;

    // Constructor vacío necesario para JPA
    public Pedido() {
        // Establecemos la fecha actual como fecha de creación del pedido
        this.fechaPedido = LocalDateTime.now();
        // Por defecto, el pedido se crea en estado PENDIENTE
        this.estado = "PENDIENTE";
    }

    // Constructor con parámetros para crear un pedido con datos específicos
    public Pedido(Cliente cliente, LocalDateTime fechaEntrega, String estado, String observaciones) {
        // Asignamos el cliente que realizó el pedido
        this.cliente = cliente;
        // La fecha de pedido siempre es la fecha actual
        this.fechaPedido = LocalDateTime.now();
        // Asignamos la fecha de entrega solicitada
        this.fechaEntrega = fechaEntrega;
        // Si no se especifica estado, usamos PENDIENTE por defecto
        this.estado = estado != null ? estado : "PENDIENTE";
        // Asignamos las observaciones del pedido
        this.observaciones = observaciones;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<PedidoArticulo> getPedidoArticulos() { return pedidoArticulos; }
    public void setPedidoArticulos(List<PedidoArticulo> pedidoArticulos) { this.pedidoArticulos = pedidoArticulos; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public void calcularTotal() {
        if (pedidoArticulos != null && !pedidoArticulos.isEmpty()) {
            this.total = pedidoArticulos.stream()
                    .filter(pa -> pa.getSubtotal() != null)
                    .mapToDouble(PedidoArticulo::getSubtotal)
                    .sum();
        } else {
            this.total = 0.0;
        }
    }

    public void recalcularTotal() {
        calcularTotal();
    }

    // Método para agregar un artículo al pedido de forma práctica
    public void agregarArticulo(Articulo articulo, Integer cantidad) {
        PedidoArticulo pedidoArticulo = new PedidoArticulo(this, articulo, cantidad, articulo.getPrecio());
        this.pedidoArticulos.add(pedidoArticulo);
        calcularTotal();
    }

    @PrePersist
    @PreUpdate

    public void preSave() {
        calcularTotal();
    }
}


