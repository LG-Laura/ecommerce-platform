import { TestBed } from '@angular/core/testing';

import { Articulo } from './articulo';

describe('Article', () => {
  let service: Articulo;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Article);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
