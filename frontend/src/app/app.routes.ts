import { Routes } from '@angular/router';
import {FilmsListComponent} from './films/films-list/films-list.component';
import {ActorsListComponent} from './actors/actors-list/actors-list.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'films',
    pathMatch: 'full',
  },
  {
    path: 'films',
    component: FilmsListComponent,
  },
  {
    path: 'actors',
    component: ActorsListComponent,
  },
];
