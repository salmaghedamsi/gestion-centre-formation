import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormationGroupForm } from './formation-group-form';

describe('FormationGroupForm', () => {
  let component: FormationGroupForm;
  let fixture: ComponentFixture<FormationGroupForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormationGroupForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormationGroupForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
