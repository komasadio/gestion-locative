import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILocataire } from 'app/shared/model/locataire.model';

type EntityResponseType = HttpResponse<ILocataire>;
type EntityArrayResponseType = HttpResponse<ILocataire[]>;

@Injectable({ providedIn: 'root' })
export class LocataireService {
  public resourceUrl = SERVER_API_URL + 'api/locataires';

  constructor(protected http: HttpClient) {}

  create(locataire: ILocataire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locataire);
    return this.http
      .post<ILocataire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(locataire: ILocataire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locataire);
    return this.http
      .put<ILocataire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocataire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocataire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(locataire: ILocataire): ILocataire {
    const copy: ILocataire = Object.assign({}, locataire, {
      dateNaissance: locataire.dateNaissance && locataire.dateNaissance.isValid() ? locataire.dateNaissance.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance ? moment(res.body.dateNaissance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((locataire: ILocataire) => {
        locataire.dateNaissance = locataire.dateNaissance ? moment(locataire.dateNaissance) : undefined;
      });
    }
    return res;
  }
}
