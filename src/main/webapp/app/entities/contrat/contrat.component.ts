import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContrat } from 'app/shared/model/contrat.model';
import { ContratService } from './contrat.service';
import { ContratDeleteDialogComponent } from './contrat-delete-dialog.component';

@Component({
  selector: 'jhi-contrat',
  templateUrl: './contrat.component.html',
})
export class ContratComponent implements OnInit, OnDestroy {
  contrats?: IContrat[];
  eventSubscriber?: Subscription;

  constructor(protected contratService: ContratService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.contratService.query().subscribe((res: HttpResponse<IContrat[]>) => (this.contrats = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContrats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContrat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContrats(): void {
    this.eventSubscriber = this.eventManager.subscribe('contratListModification', () => this.loadAll());
  }

  delete(contrat: IContrat): void {
    const modalRef = this.modalService.open(ContratDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contrat = contrat;
  }
}
