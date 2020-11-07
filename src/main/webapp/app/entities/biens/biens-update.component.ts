import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBiens, Biens } from 'app/shared/model/biens.model';
import { BiensService } from './biens.service';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse/adresse.service';

@Component({
  selector: 'jhi-biens-update',
  templateUrl: './biens-update.component.html',
})
export class BiensUpdateComponent implements OnInit {
  isSaving = false;
  adresses: IAdresse[] = [];

  editForm = this.fb.group({
    id: [],
    estMeuble: [],
    description: [],
    type: [],
    surface: [],
    adresseId: [],
  });

  constructor(
    protected biensService: BiensService,
    protected adresseService: AdresseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biens }) => {
      this.updateForm(biens);

      this.adresseService
        .query({ filter: 'biens-is-null' })
        .pipe(
          map((res: HttpResponse<IAdresse[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAdresse[]) => {
          if (!biens.adresseId) {
            this.adresses = resBody;
          } else {
            this.adresseService
              .find(biens.adresseId)
              .pipe(
                map((subRes: HttpResponse<IAdresse>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAdresse[]) => (this.adresses = concatRes));
          }
        });
    });
  }

  updateForm(biens: IBiens): void {
    this.editForm.patchValue({
      id: biens.id,
      estMeuble: biens.estMeuble,
      description: biens.description,
      type: biens.type,
      surface: biens.surface,
      adresseId: biens.adresseId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const biens = this.createFromForm();
    if (biens.id !== undefined) {
      this.subscribeToSaveResponse(this.biensService.update(biens));
    } else {
      this.subscribeToSaveResponse(this.biensService.create(biens));
    }
  }

  private createFromForm(): IBiens {
    return {
      ...new Biens(),
      id: this.editForm.get(['id'])!.value,
      estMeuble: this.editForm.get(['estMeuble'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      surface: this.editForm.get(['surface'])!.value,
      adresseId: this.editForm.get(['adresseId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBiens>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAdresse): any {
    return item.id;
  }
}
