import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormateurForm } from './formateur-form';

describe('FormateurForm', () => {
  let component: FormateurForm;
  let fixture: ComponentFixture<FormateurForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormateurForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormateurForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
