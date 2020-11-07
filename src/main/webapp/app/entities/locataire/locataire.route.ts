import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILocataire, Locataire } from 'app/shared/model/locataire.model';
import { LocataireService } from './locataire.service';
import { LocataireComponent } from './locataire.component';
import { LocataireDetailComponent } from './locataire-detail.component';
import { LocataireUpdateComponent } from './locataire-update.component';

@Injectable({ providedIn: 'root' })
export class LocataireResolve implements Resolve<ILocataire> {
  constructor(private service: LocataireService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocataire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((locataire: HttpResponse<Locataire>) => {
          if (locataire.body) {
            return of(locataire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Locataire());
  }
}

export const locataireRoute: Routes = [
  {
    path: '',
    component: LocataireComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gestLocativeApp.locataire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LocataireDetailComponent,
    resolve: {
      locataire: LocataireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestLocativeApp.locataire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LocataireUpdateComponent,
    resolve: {
      locataire: LocataireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestLocativeApp.locataire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LocataireUpdateComponent,
    resolve: {
      locataire: LocataireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestLocativeApp.locataire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
