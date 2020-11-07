import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'biens',
        loadChildren: () => import('./biens/biens.module').then(m => m.GestLocativeBiensModule),
      },
      {
        path: 'locataire',
        loadChildren: () => import('./locataire/locataire.module').then(m => m.GestLocativeLocataireModule),
      },
      {
        path: 'contrat',
        loadChildren: () => import('./contrat/contrat.module').then(m => m.GestLocativeContratModule),
      },
      {
        path: 'adresse',
        loadChildren: () => import('./adresse/adresse.module').then(m => m.GestLocativeAdresseModule),
      },
      {
        path: 'pays',
        loadChildren: () => import('./pays/pays.module').then(m => m.GestLocativePaysModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GestLocativeEntityModule {}
