import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Actor } from '../models/actor.model';
import { PaginatedResponse } from '../models/paginated-response.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ActorService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.actorsApi}/actors`;

  getActors(params: {
    page?: number;
    size?: number;
    firstName?: string;
    sort?: string;
  } = {}): Observable<PaginatedResponse<Actor>> {

    let httpParams = new HttpParams();

    if (params.page !== undefined) {
      httpParams = httpParams.set('page', params.page);
    }

    if (params.size !== undefined) {
      httpParams = httpParams.set('size', params.size);
    }

    if (params.firstName && params.firstName.trim().length > 0) {
      httpParams = httpParams.set('firstName', params.firstName.trim());
    }

    const sortValue = params.sort ?? 'firstName,asc';
    httpParams = httpParams.set('sort', sortValue);

    return this.http.get<PaginatedResponse<Actor>>(this.baseUrl, {
      params: httpParams
    });
  }

  getById(id: number): Observable<Actor> {
    return this.http.get<Actor>(`${this.baseUrl}/${id}`);
  }

  create(actor: Partial<Actor>): Observable<Actor> {
    return this.http.post<Actor>(this.baseUrl, actor);
  }

  update(id: number, actor: Partial<Actor>): Observable<Actor> {
    return this.http.put<Actor>(`${this.baseUrl}/${id}`, actor);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
