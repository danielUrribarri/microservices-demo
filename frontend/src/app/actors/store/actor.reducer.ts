import { createFeature, createReducer, on } from '@ngrx/store';
import { ActorsActions } from './actor.actions';
import { PaginatedResponse } from '../../models/paginated-response.model';
import { Actor } from '../../models/actor.model';

export interface ActorsState {
  page: PaginatedResponse<Actor> | null;
  loading: boolean;
  error: string | null;
}

const initialState: ActorsState = {
  page: null,
  loading: false,
  error: null,
};

export const actorsFeature = createFeature({
  name: 'actors',
  reducer: createReducer(
    initialState,

    on(ActorsActions.loadActors, state => ({
      ...state,
      loading: true,
      error: null,
    })),

    on(ActorsActions.loadActorsSuccess, (state, { page }) => ({
      ...state,
      loading: false,
      page,
    })),

    on(ActorsActions.loadActorsFailure, (state, { error }) => ({
      ...state,
      loading: false,
      error,
    })),

    on(ActorsActions.createActorSuccess, (state, { actor }) => ({
      ...state,
      loading: false,
      page: state.page
        ? {
            ...state.page,
            content: [actor, ...state.page.content],
            totalElements: state.page.totalElements + 1
          }
        : null,
    })),

    on(ActorsActions.deleteActorSuccess, (state, { actorId }) => ({
      ...state,
      loading: false,
      page: state.page
        ? {
            ...state.page,
            content: state.page.content.filter(a => a.actorId !== actorId),
            totalElements: state.page.totalElements - 1,
          }
        : null,
    })),
  ),
});

export const {
  name: actorsFeatureKey,
  reducer: actorsReducer,
  selectPage,
  selectLoading,
  selectError,
} = actorsFeature;
