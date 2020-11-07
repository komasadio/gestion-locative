import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContrat, Contrat } from 'app/shared/model/contrat.model';
import { ContratService } from './contrat.service';
import { IBiens } from 'app/shared/model/biens.model';
import { BiensService } from 'app/entities/biens/biens.service';
import { ILocataire } from 'app/shared/model/locataire.model';
import { LocataireService } from 'app/entities/locataire/locataire.service';

type SelectableEntity = IBiens | ILocataire;

@Component({
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html',
})
export class ContratUpdateComponent implements OnInit {
  isSaving = false;
  biens: IBiens[] = [];
  locataires: ILocataire[] = [];

  editForm = this.fb.group({
    id: [],
    dateDebut: [],
    montantLoyerNu: [],
    montantCharges: [],
    periodiciteLoyer: [],
    montantDepotGarantie: [],
    infosComplementaires: [],
    bienId: [],
    locataireId: [],
  });

  constructor(
    protected contratService: ContratService,
    protected biensService: BiensService,
    protected locataireService: LocataireService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrat }) => {
      if (!contrat.id) {
        const today = moment().startOf('day');
        contrat.dateDebut = today;
      }

      this.updateForm(contrat);

      this.biensService
        .query({ filter: 'contrat-is-null' })
        .pipe(
          map((res: HttpResponse<IBiens[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBiens[]) => {
          if (!contrat.bienId) {
            this.biens = resBody;
          } else {
            this.biensService
              .find(contrat.bienId)
              .pipe(
                map((subRes: HttpResponse<IBiens>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBiens[]) => (this.biens = concatRes));
          }
        });

      this.locataireService
        .query({ filter: 'contrat-is-null' })
        .pipe(
          map((res: HttpResponse<ILocataire[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILocataire[]) => {
          if (!contrat.locataireId) {
            this.locataires = resBody;
          } else {
            this.locataireService
              .find(contrat.locataireId)
              .pipe(
                map((subRes: HttpResponse<ILocataire>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocataire[]) => (this.locataires = concatRes));
          }
        });
    });
  }

  updateForm(contrat: IContrat): void {
    this.editForm.patchValue({
      id: contrat.id,
      dateDebut: contrat.dateDebut ? contrat.dateDebut.format(DATE_TIME_FORMAT) : null,
      montantLoyerNu: contrat.montantLoyerNu,
      montantCharges: contrat.montantCharges,
      periodiciteLoyer: contrat.periodiciteLoyer,
      montantDepotGarantie: contrat.montantDepotGarantie,
      infosComplementaires: contrat.infosComplementaires,
      bienId: contrat.bienId,
      locataireId: contrat.locataireId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contrat = this.createFromForm();
    if (contrat.id !== undefined) {
      this.subscribeToSaveResponse(this.contratService.update(contrat));
    } else {
      this.subscribeToSaveResponse(this.contratService.create(contrat));
    }
  }

  private createFromForm(): IContrat {
    return {
      ...new Contrat(),
      id: this.editForm.get(['id'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? moment(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      montantLoyerNu: this.editForm.get(['montantLoyerNu'])!.value,
      montantCharges: this.editForm.get(['montantCharges'])!.value,
      periodiciteLoyer: this.editForm.get(['periodiciteLoyer'])!.value,
      montantDepotGarantie: this.editForm.get(['montantDepotGarantie'])!.value,
      infosComplementaires: this.editForm.get(['infosComplementaires'])!.value,
      bienId: this.editForm.get(['bienId'])!.value,
      locataireId: this.editForm.get(['locataireId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrat>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
