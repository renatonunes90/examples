import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommRegistryComponent } from './comm-registry.component';

describe('CommRegistryComponent', () => {
  let component: CommRegistryComponent;
  let fixture: ComponentFixture<CommRegistryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CommRegistryComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommRegistryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
