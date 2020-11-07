import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBiens } from 'app/shared/model/biens.model';
import { BiensService } from './biens.service';

@Component({
  templateUrl: './biens-delete-dialog.component.html',
})
export class BiensDeleteDialogComponent {
  biens?: IBiens;

  constructor(protected biensService: BiensService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.biensService.delete(id).subscribe(() => {
      this.eventManager.broadcast('biensListModification');
      this.activeModal.close();
    });
  }
}
