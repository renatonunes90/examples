import { TestBed } from '@angular/core/testing';

import { CommRegistryService } from './comm-registry.service';

describe('CommRegistryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CommRegistryService = TestBed.get(CommRegistryService);
    expect(service).toBeTruthy();
  });
});
