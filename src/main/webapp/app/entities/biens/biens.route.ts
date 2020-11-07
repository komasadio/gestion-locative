import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBiens, Biens } from 'app/shared/model/biens.model';
import { BiensService } from './biens.service';
import { BiensComponent } from './biens.component';
import { BiensDetailComponent } from './biens-detail.component';
import { BiensUpdateComponent } from './biens-update.component';

@Injectable({ providedIn: 'root' })
export class BiensResolve implements Resolve<IBiens> {
  constructor(private service: BiensService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBiens> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((biens: HttpResponse<Biens>) => {
          if (biens.body) {
            return of(biens.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Biens());
  }
}

export const biensRoute: Routes = [
  {
    path: '',
    component: BiensComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gestLocativeApp.biens.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BiensDetailComponent,
    resolve: {
      biens: BiensResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestLocativeApp.biens.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BiensUpdateComponent,
    resolve: {
      biens: BiensResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestLocativeApp.biens.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BiensUpdateComponent,
    resolve: {
      biens: BiensResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestLocativeApp.biens.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
