import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PedidoService {
  private apiUrl = 'http://localhost:8080/api/pedidos';

  constructor(private http: HttpClient) {}

  crearPedidoConArticulos(pedidoData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/con-articulos`, pedidoData);
  }
}
