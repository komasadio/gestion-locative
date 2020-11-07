import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestLocativeTestModule } from '../../../test.module';
import { LocataireDetailComponent } from 'app/entities/locataire/locataire-detail.component';
import { Locataire } from 'app/shared/model/locataire.model';

describe('Component Tests', () => {
  describe('Locataire Management Detail Component', () => {
    let comp: LocataireDetailComponent;
    let fixture: ComponentFixture<LocataireDetailComponent>;
    const route = ({ data: of({ locataire: new Locataire(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestLocativeTestModule],
        declarations: [LocataireDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LocataireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocataireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load locataire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.locataire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
