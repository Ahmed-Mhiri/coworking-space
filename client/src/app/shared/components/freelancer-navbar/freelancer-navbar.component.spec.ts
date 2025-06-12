import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FreelancerNavbarComponent } from './freelancer-navbar.component';

describe('FreelancerNavbarComponent', () => {
  let component: FreelancerNavbarComponent;
  let fixture: ComponentFixture<FreelancerNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FreelancerNavbarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FreelancerNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
