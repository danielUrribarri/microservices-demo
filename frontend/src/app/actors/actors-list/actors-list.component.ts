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
import { MatIconModule } from '@angular/material/icon';

import { Store } from '@ngrx/store';
import { selectActorList, selectActorLoading } from '../store/actor.selectors';
import { ActorsActions } from '../store/actor.actions';
import { Actor } from '../../models/actor.model';
import { selectLoading, selectPage } from '../store/actor.reducer';

@Component({
  selector: 'app-actors-list',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressBarModule,
    MatIconModule,
  ],
  templateUrl: './actors-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ActorsListComponent implements OnInit {
  private readonly store = inject(Store);

  actorPage$ = this.store.select(selectPage);
  loading$ = this.store.select(selectLoading);

  displayedColumns = ['actorId', 'firstName', 'lastName', 'actions'];

  searchCtrl = new FormControl('');
  pageIndex = 0;
  pageSize = 10;

  ngOnInit(): void {
    this.loadPage();
    this.actorPage$.subscribe((x) => console.log(x))

    this.searchCtrl.valueChanges.subscribe(value => {
      this.pageIndex = 0;
      this.store.dispatch(
        ActorsActions.loadActors({
          page: 0,
          size: this.pageSize,
          firstName: value ?? '',
        })
      );
    });
  }

  loadPage(): void {
    this.store.dispatch(
      ActorsActions.loadActors({
        page: this.pageIndex,
        size: this.pageSize,
        firstName: this.searchCtrl.value ?? '',
      })
    );
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadPage();
  }

  onDelete(actorId: number): void {
    this.store.dispatch(ActorsActions.deleteActor({ actorId }));
  }

  trackByActorId(index: number, actor: Actor) {
    return actor.actorId;
  }
}

