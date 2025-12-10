import { createActionGroup, emptyProps, props } from '@ngrx/store';
import {PaginatedResponse} from '../../models/paginated-response.model';
import {Film, FilmRequest} from '../../models/film.model';

export const FilmsActions = createActionGroup({
  source: 'Films',
  events: {
    'Load Films': props<{ page: number; size: number; title?: string }>(),
    'Load Films Success': props<{ page: PaginatedResponse<Film> }>(),
    'Load Films Failure': props<{ error: string }>(),

    'Create Film': props<{ film: FilmRequest }>(),
    'Create Film Success': props<{ film: Film }>(),
    'Create Film Failure': props<{ error: string }>(),
  },
});
