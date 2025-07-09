import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Articulo } from './articulo.service';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartItemsSubject = new BehaviorSubject<Articulo[]>([]);
  cartItems$ = this.cartItemsSubject.asObservable();

  get items(): Articulo[] {
    return this.cartItemsSubject.value;
  }

  addProduct(product: Articulo): void {
    const updated = [...this.items, product];
    this.cartItemsSubject.next(updated);
  }

  removeProduct(index: number): void {
    const updated = this.items.filter((_, i) => i !== index);
    this.cartItemsSubject.next(updated);
  }

  clearCart(): void {
    this.cartItemsSubject.next([]);
  }
}

