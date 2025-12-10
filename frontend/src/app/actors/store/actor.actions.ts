import { createActionGroup, props } from '@ngrx/store';
import { Actor } from '../../models/actor.model';
import { PaginatedResponse } from '../../models/paginated-response.model';

export const ActorsActions = createActionGroup({
  source: 'Actors',
  events: {
    'Load Actors': props<{ page: number; size: number; firstName?: string }>(),
    'Load Actors Success': props<{ page: PaginatedResponse<Actor> }>(),
    'Load Actors Failure': props<{ error: string }>(),

    'Create Actor': props<{ actor: Actor }>(),
    'Create Actor Success': props<{ actor: Actor }>(),
    'Create Actor Failure': props<{ error: string }>(),

    'Delete Actor': props<{ actorId: number }>(),
    'Delete Actor Success': props<{ actorId: number }>(),
    'Delete Actor Failure': props<{ error: string }>(),
  }
});

