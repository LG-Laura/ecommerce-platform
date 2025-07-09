import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Products } from './pages/products/products';
import { Admin } from './pages/admin/admin';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'productos', component: Products },
  { path: 'admin', component: Admin },
  { path: '**', redirectTo: '' },
];
