import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBiens } from 'app/shared/model/biens.model';

@Component({
  selector: 'jhi-biens-detail',
  templateUrl: './biens-detail.component.html',
})
export class BiensDetailComponent implements OnInit {
  biens: IBiens | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biens }) => (this.biens = biens));
  }

  previousState(): void {
    window.history.back();
  }
}
