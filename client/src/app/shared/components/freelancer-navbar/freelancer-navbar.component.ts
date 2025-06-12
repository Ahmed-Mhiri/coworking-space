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
import { NzDrawerModule } from 'ng-zorro-antd/drawer'; // For the mobile navigation

@Component({
  selector: 'app-freelancer-navbar',
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
    NzIconModule,
    NzDrawerModule
  ],
  templateUrl: './freelancer-navbar.component.html',
  styleUrls: [] // Remove the link to the .scss file
})
export class FreelancerNavbarComponent {
  // The old menuOpen logic is replaced with this for the drawer
  isDrawerVisible = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  openDrawer(): void {
    this.isDrawerVisible = true;
  }

  closeDrawer(): void {
    this.isDrawerVisible = false;
  }

  // The signOut logic remains the same
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