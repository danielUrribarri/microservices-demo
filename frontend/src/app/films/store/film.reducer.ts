import { createFeature, createReducer, on } from '@ngrx/store';
import {FilmsActions} from './film.actions';
import {PaginatedResponse} from '../../models/paginated-response.model';
import {Film} from '../../models/film.model';

export interface FilmsState {
  page: PaginatedResponse<Film> | null;
  loading: boolean;
  error: string | null;
}

const initialState: FilmsState = {
  page: null,
  loading: false,
  error: null,
};

export const filmsFeature = createFeature({
  name: 'films',
  reducer: createReducer(
    initialState,

    on(FilmsActions.loadFilms, (state) => ({
      ...state,
      loading: true,
      error: null,
    })),

    on(FilmsActions.loadFilmsSuccess, (state, { page }) => ({
      ...state,
      loading: false,
      page,
    })),

    on(FilmsActions.loadFilmsFailure, (state, { error }) => ({
      ...state,
      loading: false,
      error,
    })),

    on(FilmsActions.createFilm, (state) => ({
      ...state,
      loading: true,
      error: null,
    })),

    on(FilmsActions.createFilmSuccess, (state, { film }) => ({
      ...state,
      loading: false,
      page: state.page
        ? {
          ...state.page,
          content: [film, ...state.page.content],
          totalElements: state.page.totalElements + 1,
        }
        : null,
    })),

    on(FilmsActions.createFilmFailure, (state, { error }) => ({
      ...state,
      loading: false,
      error,
    }))
  ),
});

export const {
  name: filmsFeatureKey,
  reducer: filmsReducer,
  selectPage,
  selectLoading,
  selectError,
} = filmsFeature;
