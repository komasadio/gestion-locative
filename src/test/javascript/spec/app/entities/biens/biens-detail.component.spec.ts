import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestLocativeTestModule } from '../../../test.module';
import { BiensDetailComponent } from 'app/entities/biens/biens-detail.component';
import { Biens } from 'app/shared/model/biens.model';

describe('Component Tests', () => {
  describe('Biens Management Detail Component', () => {
    let comp: BiensDetailComponent;
    let fixture: ComponentFixture<BiensDetailComponent>;
    const route = ({ data: of({ biens: new Biens(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestLocativeTestModule],
        declarations: [BiensDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BiensDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BiensDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load biens on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.biens).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
