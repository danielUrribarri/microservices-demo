import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Film, FilmRequest } from '../models/film.model';
import { PaginatedResponse } from '../models/paginated-response.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FilmsService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.filmsApi}/films`;

  getFilms(params: { page?: number; size?: number; title?: string } = {}): Observable<PaginatedResponse<Film>> {
    let httpParams = new HttpParams();

    if (params.page !== undefined) {
      httpParams = httpParams.set('page', params.page);
    }
    if (params.size !== undefined) {
      httpParams = httpParams.set('size', params.size);
    }
    if (params.title && params.title.trim().length > 0) {
      httpParams = httpParams.set('title', params.title.trim());
    }

    return this.http.get<PaginatedResponse<Film>>(this.baseUrl, { params: httpParams });
  }

  getById(id: number): Observable<Film> {
    return this.http.get<Film>(`${this.baseUrl}/${id}`);
  }

  create(film: FilmRequest): Observable<Film> {
    return this.http.post<Film>(this.baseUrl, film);
  }

  update(id: number, film: FilmRequest): Observable<Film> {
    return this.http.put<Film>(`${this.baseUrl}/${id}`, film);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
