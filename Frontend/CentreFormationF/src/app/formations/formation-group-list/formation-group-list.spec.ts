import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormationGroupList } from './formation-group-list';

describe('FormationGroupList', () => {
  let component: FormationGroupList;
  let fixture: ComponentFixture<FormationGroupList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormationGroupList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormationGroupList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
