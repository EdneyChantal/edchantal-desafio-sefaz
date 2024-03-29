import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { PersonDetailComponent } from 'app/entities/apibank/person/person-detail.component';
import { Person } from 'app/shared/model/apibank/person.model';

describe('Component Tests', () => {
  describe('Person Management Detail Component', () => {
    let comp: PersonDetailComponent;
    let fixture: ComponentFixture<PersonDetailComponent>;
    const route = ({ data: of({ person: new Person(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [PersonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PersonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load person on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.person).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
