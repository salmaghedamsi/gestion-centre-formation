import { TestBed } from '@angular/core/testing';

import { GroupStudent } from './group-student';

describe('GroupStudent', () => {
  let service: GroupStudent;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GroupStudent);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
