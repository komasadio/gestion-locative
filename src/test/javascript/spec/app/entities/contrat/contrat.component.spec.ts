import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestLocativeTestModule } from '../../../test.module';
import { ContratComponent } from 'app/entities/contrat/contrat.component';
import { ContratService } from 'app/entities/contrat/contrat.service';
import { Contrat } from 'app/shared/model/contrat.model';

describe('Component Tests', () => {
  describe('Contrat Management Component', () => {
    let comp: ContratComponent;
    let fixture: ComponentFixture<ContratComponent>;
    let service: ContratService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestLocativeTestModule],
        declarations: [ContratComponent],
      })
        .overrideTemplate(ContratComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContratComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContratService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Contrat(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contrats && comp.contrats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
