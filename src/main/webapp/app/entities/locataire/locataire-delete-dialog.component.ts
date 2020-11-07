import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocataire } from 'app/shared/model/locataire.model';
import { LocataireService } from './locataire.service';

@Component({
  templateUrl: './locataire-delete-dialog.component.html',
})
export class LocataireDeleteDialogComponent {
  locataire?: ILocataire;

  constructor(protected locataireService: LocataireService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.locataireService.delete(id).subscribe(() => {
      this.eventManager.broadcast('locataireListModification');
      this.activeModal.close();
    });
  }
}
