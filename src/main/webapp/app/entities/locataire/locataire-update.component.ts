import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILocataire, Locataire } from 'app/shared/model/locataire.model';
import { LocataireService } from './locataire.service';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse/adresse.service';

@Component({
  selector: 'jhi-locataire-update',
  templateUrl: './locataire-update.component.html',
})
export class LocataireUpdateComponent implements OnInit {
  isSaving = false;
  adresses: IAdresse[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    email: [],
    telephone: [],
    dateNaissance: [],
    adresseId: [],
  });

  constructor(
    protected locataireService: LocataireService,
    protected adresseService: AdresseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ locataire }) => {
      if (!locataire.id) {
        const today = moment().startOf('day');
        locataire.dateNaissance = today;
      }

      this.updateForm(locataire);

      this.adresseService
        .query({ filter: 'locataire-is-null' })
        .pipe(
          map((res: HttpResponse<IAdresse[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAdresse[]) => {
          if (!locataire.adresseId) {
            this.adresses = resBody;
          } else {
            this.adresseService
              .find(locataire.adresseId)
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

  updateForm(locataire: ILocataire): void {
    this.editForm.patchValue({
      id: locataire.id,
      nom: locataire.nom,
      prenom: locataire.prenom,
      email: locataire.email,
      telephone: locataire.telephone,
      dateNaissance: locataire.dateNaissance ? locataire.dateNaissance.format(DATE_TIME_FORMAT) : null,
      adresseId: locataire.adresseId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const locataire = this.createFromForm();
    if (locataire.id !== undefined) {
      this.subscribeToSaveResponse(this.locataireService.update(locataire));
    } else {
      this.subscribeToSaveResponse(this.locataireService.create(locataire));
    }
  }

  private createFromForm(): ILocataire {
    return {
      ...new Locataire(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      email: this.editForm.get(['email'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value
        ? moment(this.editForm.get(['dateNaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      adresseId: this.editForm.get(['adresseId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocataire>>): void {
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
