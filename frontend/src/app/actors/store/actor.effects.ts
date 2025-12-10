import { inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import { ActorsActions } from './actor.actions';
import { ActorService } from '../actor.service';

@Injectable()
export class ActorsEffects {
  private readonly actions$ = inject(Actions);
  private readonly service = inject(ActorService);

  loadActors$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ActorsActions.loadActors),
      mergeMap(({ page, size, firstName }) =>
        this.service.getActors({ page, size, firstName }).pipe(
          map(result => ActorsActions.loadActorsSuccess({ page: result })),
          catchError(error =>
            of(ActorsActions.loadActorsFailure({ error: String(error) }))
          )
        )
      )
    )
  );

  createActor$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ActorsActions.createActor),
      mergeMap(({ actor }) =>
        this.service.create(actor).pipe(
          map(created => ActorsActions.createActorSuccess({ actor: created })),
          catchError(error =>
            of(ActorsActions.createActorFailure({ error: String(error) }))
          )
        )
      )
    )
  );

  deleteActor$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ActorsActions.deleteActor),
      mergeMap(({ actorId }) =>
        this.service.delete(actorId).pipe(
          map(() => ActorsActions.deleteActorSuccess({ actorId })),
          catchError(error =>
            of(ActorsActions.deleteActorFailure({ error: String(error) }))
          )
        )
      )
    )
  );
}

