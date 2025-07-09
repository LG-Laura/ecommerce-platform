import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Articulo {
  id: number;
  nombre: string;
  precio: number;
  descripcion: string;
  stock: number;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root',
})
export class ArticuloService {
  private apiUrl = 'http://localhost:8080/api/articulos'; // notá el cambio de ruta

  constructor(private http: HttpClient) {}

  obtenerArticulos(): Observable<Articulo[]> {
    return this.http.get<Articulo[]>(this.apiUrl);
  }
}
