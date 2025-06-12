import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

import { AuthService } from '../../services/auth.service';

// --- NG-ZORRO IMPORTS ---
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzIconModule } from 'ng-zorro-antd/icon';

@Component({
  selector: 'app-admin-navbar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    // Add Zorro modules
    NzLayoutModule,
    NzMenuModule,
    NzDropDownModule,
    NzAvatarModule,
    NzIconModule
  ],
  templateUrl: './admin-navbar.component.html',
  styleUrls: [] // Remove the link to the .scss file
})
export class AdminNavbarComponent {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  // No changes needed to the navigation logic
  navigateToDashboard() {
    this.router.navigate(['/admin-dashboard']);
  }

  navigateToManagement() {
    this.router.navigate(['/admin-management']);
  }

  // No changes needed to the sign out logic
  signOut() {
    this.authService.signOut().subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: err => {
        console.error('Sign out failed', err);
        this.router.navigate(['/']);
      }
    });
  }
}