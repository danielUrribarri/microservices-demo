import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideStore } from '@ngrx/store';
import { provideHttpClient } from '@angular/common/http';
import { filmsFeatureKey, filmsReducer } from './films/store/film.reducer';
import { provideEffects } from '@ngrx/effects';
import { FilmsEffects } from './films/store/film.effects';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import { ActorsEffects } from './actors/store/actor.effects';
import { actorsFeatureKey, actorsReducer } from './actors/store/actor.reducer';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(),
    provideRouter(routes),
    provideStore({
      [filmsFeatureKey]: filmsReducer,
      [actorsFeatureKey]: actorsReducer, 
    }),
    provideEffects([FilmsEffects, ActorsEffects]),
    provideStoreDevtools({
      logOnly: false,
    }),
  ]
};
