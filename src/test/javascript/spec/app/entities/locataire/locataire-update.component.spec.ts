import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestLocativeTestModule } from '../../../test.module';
import { LocataireUpdateComponent } from 'app/entities/locataire/locataire-update.component';
import { LocataireService } from 'app/entities/locataire/locataire.service';
import { Locataire } from 'app/shared/model/locataire.model';

describe('Component Tests', () => {
  describe('Locataire Management Update Component', () => {
    let comp: LocataireUpdateComponent;
    let fixture: ComponentFixture<LocataireUpdateComponent>;
    let service: LocataireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestLocativeTestModule],
        declarations: [LocataireUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LocataireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocataireUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocataireService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Locataire(123);
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
        const entity = new Locataire();
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
