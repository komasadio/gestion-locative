import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAdresse, Adresse } from 'app/shared/model/adresse.model';
import { AdresseService } from './adresse.service';
import { IPays } from 'app/shared/model/pays.model';
import { PaysService } from 'app/entities/pays/pays.service';

@Component({
  selector: 'jhi-adresse-update',
  templateUrl: './adresse-update.component.html',
})
export class AdresseUpdateComponent implements OnInit {
  isSaving = false;
  pays: IPays[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [],
    ville: [],
    commune: [],
    departement: [],
    paysId: [],
  });

  constructor(
    protected adresseService: AdresseService,
    protected paysService: PaysService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adresse }) => {
      this.updateForm(adresse);

      this.paysService
        .query({ filter: 'adresse-is-null' })
        .pipe(
          map((res: HttpResponse<IPays[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPays[]) => {
          if (!adresse.paysId) {
            this.pays = resBody;
          } else {
            this.paysService
              .find(adresse.paysId)
              .pipe(
                map((subRes: HttpResponse<IPays>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPays[]) => (this.pays = concatRes));
          }
        });
    });
  }

  updateForm(adresse: IAdresse): void {
    this.editForm.patchValue({
      id: adresse.id,
      libelle: adresse.libelle,
      ville: adresse.ville,
      commune: adresse.commune,
      departement: adresse.departement,
      paysId: adresse.paysId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adresse = this.createFromForm();
    if (adresse.id !== undefined) {
      this.subscribeToSaveResponse(this.adresseService.update(adresse));
    } else {
      this.subscribeToSaveResponse(this.adresseService.create(adresse));
    }
  }

  private createFromForm(): IAdresse {
    return {
      ...new Adresse(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      commune: this.editForm.get(['commune'])!.value,
      departement: this.editForm.get(['departement'])!.value,
      paysId: this.editForm.get(['paysId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdresse>>): void {
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

  trackById(index: number, item: IPays): any {
    return item.id;
  }
}
