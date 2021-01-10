import { TestBed } from '@angular/core/testing';

import { NewsRequestService } from './news-request.service';

describe('NewsRequestService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NewsRequestService = TestBed.get(NewsRequestService);
    expect(service).toBeTruthy();
  });
});
