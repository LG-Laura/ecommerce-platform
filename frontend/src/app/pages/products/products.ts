import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ArticuloService, Articulo } from '../../services/articulo.service';
import { CartService } from '../../services/cart.service'; // ✅

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products implements OnInit {
  productos: any[] = [];

    constructor(private articuloService: ArticuloService, private cartService: CartService) {}

    ngOnInit(): void {
      this.articuloService.obtenerArticulos().subscribe({
            next: (data: Articulo[]) => {
              this.productos = data;
            },
            error: (err: any) => {
              console.error('Error al obtener artículos:', err);
            }
          });
        }

    agregarAlCarrito(producto: Articulo): void {
      this.cartService.addProduct(producto); // 👈 esto actualiza el observable
      console.log('Agregado al carrito:', producto);
    }
}
