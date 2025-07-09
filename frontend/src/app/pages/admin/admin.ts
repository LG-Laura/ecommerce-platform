import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  imports: [],
  templateUrl: './admin.html',
  styleUrl: './admin.css'
})
export class Admin implements OnInit {
  productos: any[] = [];
  productosFiltrados: any[] = [];
  searchText: string = '';

  constructor() {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  async cargarProductos() {
    try {
      // Simulación temporal de datos (reemplazá esto por tu servicio real)
      this.productos = [
        { id: 1, nombre: 'Mouse', descripcion: 'Óptico USB', precio: 3500, stock: 10, activo: true },
        { id: 2, nombre: 'Teclado', descripcion: 'Mecánico RGB', precio: 15000, stock: 5, activo: false }
      ];
      this.productosFiltrados = [...this.productos];
    } catch (error) {
      console.error('Error al cargar productos', error);
    }
  }

  buscarProductos() {
    const query = this.searchText.toLowerCase();
    this.productosFiltrados = this.productos.filter(producto =>
      producto.nombre.toLowerCase().includes(query) ||
      (producto.descripcion && producto.descripcion.toLowerCase().includes(query))
    );
  }

  formatearMoneda(valor: number): string {
    return '$' + valor.toFixed(2);
  }

  editarProducto(id: number) {
    console.log('Editar producto', id);
    // Abrí un modal con los datos
  }

  actualizarStock(id: number, stockActual: number) {
    console.log('Actualizar stock', id, stockActual);
  }

  eliminarProducto(id: number) {
    if (confirm('¿Estás segura de que querés eliminar el producto?')) {
      this.productos = this.productos.filter(p => p.id !== id);
      this.buscarProductos();
    }
  }
}
