import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home.component';
import { SigninComponent } from './pages/auth/signin/signin.component';
import { SignupComponent } from './pages/auth/sign-up/sign-up.component';
import { ForgotPasswordComponent } from './pages/auth/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './pages/auth/reset-password/reset-password.component';
import { FreelancerDashboardComponent } from './pages/freelancer-dashboard/freelancer-dashboard.component';
import { InternDashboardComponent } from './pages/intern-dashboard/intern-dashboard.component';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { AdminManagementComponent } from './pages/admin-management/admin-management.component';
import { AuthGuard } from './shared/guards/auth.guard';
import { BookingsComponent } from './pages/booking/booking.component';


export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'signin', component: SigninComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },

  // Freelancer routes
  { 
    path: 'freelancer-dashboard', 
    component: FreelancerDashboardComponent, 
    canActivate: [AuthGuard] 
  },
  {
    path: 'freelancer/booking',
    component: BookingsComponent,  // <-- create this component
    canActivate: [AuthGuard]
  },

  // Intern routes
  { 
    path: 'intern-dashboard', 
    component: InternDashboardComponent, 
    canActivate: [AuthGuard] 
  },
  {
    path: 'intern/booking',
    component: BookingsComponent,  // <-- create this component
    canActivate: [AuthGuard]
  },

  // Admin routes
  { 
    path: 'admin-dashboard', 
    component: AdminDashboardComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'admin-management', 
    component: AdminManagementComponent, 
    canActivate: [AuthGuard] 
  }
]

