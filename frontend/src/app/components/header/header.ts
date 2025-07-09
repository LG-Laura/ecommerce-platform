import { Component, HostListener, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CartModalComponent } from '../../pages/cart-modal/cart-modal';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule, CartModalComponent],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header implements OnInit {
  cantidadProductos = 0;
  mostrarCarrito = false;
  mostrarOpcionesCliente = false;
  mobileMenuOpen = false; // ✅ agregado

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.cartService.cartItems$.subscribe(items => {
      this.cantidadProductos = items.length;
    });
  }

  toggleCarrito() {
    this.mostrarCarrito = !this.mostrarCarrito;
  }

  cerrarCarrito = () => {
    this.mostrarCarrito = false;
  };

  toggleClienteMenu() {
    this.mostrarOpcionesCliente = !this.mostrarOpcionesCliente;
  }

  toggleMobileMenu(): void {
    this.mobileMenuOpen = !this.mobileMenuOpen;
  }

  closeMobileMenu(): void {
    this.mobileMenuOpen = false;
    this.mostrarOpcionesCliente = false;
    this.mostrarCarrito = false;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.cliente-dropdown')) {
      this.mostrarOpcionesCliente = false;
    }
  }
}
