import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { Store } from '@ngrx/store';
import { selectLoading, selectPage } from '../store/film.reducer';
import { FilmsActions } from '../store/film.actions';
import { Film } from '../../models/film.model';

@Component({
  selector: 'app-films-list',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressBarModule,
  ],
  templateUrl: './films-list.component.html',
  styleUrls: ['./films-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FilmsListComponent implements OnInit {
  private readonly store = inject(Store);

  displayedColumns = ['filmId', 'title', 'releaseYear', 'rating'];

  filmsPage$ = this.store.select(selectPage);
  loading$ = this.store.select(selectLoading);

  searchCtrl = new FormControl('');

  pageIndex = 0;
  pageSize = 10;

  ngOnInit(): void {
    this.loadPage();

    this.searchCtrl.valueChanges.subscribe(value => {
      this.pageIndex = 0;
      this.store.dispatch(
        FilmsActions.loadFilms({
          page: 0,
          size: this.pageSize,
          title: value ?? '',
        })
      );
    });
  }

  loadPage(): void {
    this.store.dispatch(
      FilmsActions.loadFilms({
        page: this.pageIndex,
        size: this.pageSize,
        title: this.searchCtrl.value ?? '',
      })
    );
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadPage();
  }

  trackByFilmId(index: number, film: Film) {
    return film.filmId;
  }
}
