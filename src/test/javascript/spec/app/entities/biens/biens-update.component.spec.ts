import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestLocativeTestModule } from '../../../test.module';
import { BiensUpdateComponent } from 'app/entities/biens/biens-update.component';
import { BiensService } from 'app/entities/biens/biens.service';
import { Biens } from 'app/shared/model/biens.model';

describe('Component Tests', () => {
  describe('Biens Management Update Component', () => {
    let comp: BiensUpdateComponent;
    let fixture: ComponentFixture<BiensUpdateComponent>;
    let service: BiensService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestLocativeTestModule],
        declarations: [BiensUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BiensUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiensUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiensService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Biens(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Biens();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
