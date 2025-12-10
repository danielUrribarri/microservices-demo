import { inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import {FilmsService} from '../film.service';
import {FilmsActions} from './film.actions';

@Injectable()
export class FilmsEffects {
  private readonly actions$ = inject(Actions);
  private readonly filmsService = inject(FilmsService);

  loadFilms$ = createEffect(() =>
    this.actions$.pipe(
      ofType(FilmsActions.loadFilms),
      mergeMap(({ page, size, title }) =>
        this.filmsService.getFilms({ page, size, title }).pipe(
          map((result) => FilmsActions.loadFilmsSuccess({ page: result })),
          catchError((error) =>
            of(FilmsActions.loadFilmsFailure({ error: String(error) }))
          )
        )
      )
    )
  );

  createFilm$ = createEffect(() =>
    this.actions$.pipe(
      ofType(FilmsActions.createFilm),
      mergeMap(({ film }) =>
        this.filmsService.create(film).pipe(
          map((created) => FilmsActions.createFilmSuccess({ film: created })),
          catchError((error) =>
            of(FilmsActions.createFilmFailure({ error: String(error) }))
          )
        )
      )
    )
  );

  constructor() {}
}

export class FilmEffects {
}
