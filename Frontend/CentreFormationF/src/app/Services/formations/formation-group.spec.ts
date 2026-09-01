import { TestBed } from '@angular/core/testing';

import { FormationGroup } from './formation-group';

describe('FormationGroup', () => {
  let service: FormationGroup;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormationGroup);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
