import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLocativeSharedModule } from 'app/shared/shared.module';
import { BiensComponent } from './biens.component';
import { BiensDetailComponent } from './biens-detail.component';
import { BiensUpdateComponent } from './biens-update.component';
import { BiensDeleteDialogComponent } from './biens-delete-dialog.component';
import { biensRoute } from './biens.route';

@NgModule({
  imports: [GestLocativeSharedModule, RouterModule.forChild(biensRoute)],
  declarations: [BiensComponent, BiensDetailComponent, BiensUpdateComponent, BiensDeleteDialogComponent],
  entryComponents: [BiensDeleteDialogComponent],
})
export class GestLocativeBiensModule {}
