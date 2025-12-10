import { createSelector } from '@ngrx/store';
import { selectPage, selectLoading, selectError } from './actor.reducer';

export const selectActorList = createSelector(
  selectPage,
  (page) => page?.content ?? []
);

export const selectActorTotalElements = createSelector(
  selectPage,
  (page) => page?.totalElements ?? 0
);

export const selectActorLoading = selectLoading;
export const selectActorError = selectError;
