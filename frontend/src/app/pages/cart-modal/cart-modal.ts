import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CartService } from '../../services/cart.service';
import { PedidoService } from '../../services/pedido.service';
import { Articulo } from '../../services/articulo.service';

@Component({
  selector: 'app-cart-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cart-modal.html',
  styleUrls: ['./cart-modal.css']
})
export class CartModalComponent implements OnInit {
  items: Articulo[] = [];

  @Input() visible: boolean = false;
  @Input() close: () => void = () => {};

  pedidoForm!: FormGroup;

  constructor(
    private cartService: CartService,
    private pedidoService: PedidoService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.cartService.cartItems$.subscribe(data => {
      this.items = data;
    });

    this.pedidoForm = this.fb.group({
      emailCliente: ['', [Validators.required, Validators.email]],
      fechaEntrega: ['', Validators.required],
      observaciones: ['']
    });
  }

  remove(index: number) {
    this.cartService.removeProduct(index);
  }

  clear() {
    this.cartService.clearCart();
  }

  handleBackdropClick(event: MouseEvent) {
    this.close();
  }

  enviarPedido() {
    const form = this.pedidoForm.value;

    const pedido = {
      emailCliente: form.emailCliente,
      fechaEntrega: form.fechaEntrega,
      observaciones: form.observaciones,
      articulos: this.items.map(item => ({
        articuloId: item.id,
        cantidad: 1 // Si luego agregás cantidad, lo reemplazás aquí
      }))
    };

    this.pedidoService.crearPedidoConArticulos(pedido).subscribe({
      next: res => {
        alert('✅ Pedido enviado correctamente');
        this.cartService.clearCart();
        this.pedidoForm.reset();
        this.close();
      },
      error: err => {
        console.error(err);
        alert('❌ Error al enviar el pedido');
      }
    });
  }
}
