import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLocativeSharedModule } from 'app/shared/shared.module';
import { LocataireComponent } from './locataire.component';
import { LocataireDetailComponent } from './locataire-detail.component';
import { LocataireUpdateComponent } from './locataire-update.component';
import { LocataireDeleteDialogComponent } from './locataire-delete-dialog.component';
import { locataireRoute } from './locataire.route';

@NgModule({
  imports: [GestLocativeSharedModule, RouterModule.forChild(locataireRoute)],
  declarations: [LocataireComponent, LocataireDetailComponent, LocataireUpdateComponent, LocataireDeleteDialogComponent],
  entryComponents: [LocataireDeleteDialogComponent],
})
export class GestLocativeLocataireModule {}
