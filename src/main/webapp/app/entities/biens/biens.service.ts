import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBiens } from 'app/shared/model/biens.model';

type EntityResponseType = HttpResponse<IBiens>;
type EntityArrayResponseType = HttpResponse<IBiens[]>;

@Injectable({ providedIn: 'root' })
export class BiensService {
  public resourceUrl = SERVER_API_URL + 'api/biens';

  constructor(protected http: HttpClient) {}

  create(biens: IBiens): Observable<EntityResponseType> {
    return this.http.post<IBiens>(this.resourceUrl, biens, { observe: 'response' });
  }

  update(biens: IBiens): Observable<EntityResponseType> {
    return this.http.put<IBiens>(this.resourceUrl, biens, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBiens>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiens[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
