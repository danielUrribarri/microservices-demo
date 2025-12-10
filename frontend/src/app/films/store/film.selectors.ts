import { createSelector } from '@ngrx/store';
import { selectPage, selectLoading, selectError } from './film.reducer';

export const selectFilmList = createSelector(
  selectPage,
  (page) => page?.content ?? []
);

export const selectFilmTotalElements = createSelector(
  selectPage,
  (page) => page?.totalElements ?? 0
);

export const selectFilmLoading = selectLoading;
export const selectFilmError = selectError;
