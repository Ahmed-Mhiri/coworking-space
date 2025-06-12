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
import { NzButtonModule } from 'ng-zorro-antd/button'; // Added for the menu toggle button
import { NzDrawerModule } from 'ng-zorro-antd/drawer'; // For the mobile navigation

@Component({
  selector: 'app-intern-navbar',
  standalone: true, // Make sure it's standalone if it wasn't already
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
    NzButtonModule, // Include NzButtonModule
    NzDrawerModule
  ],
  templateUrl: './intern-navbar.component.html',
  styleUrls: [] // Remove the link to the .scss file as we're moving to Tailwind/Ng-Zorro classes
})
export class InternNavbarComponent {
  // Renamed from menuOpen to isDrawerVisible to match Ng-Zorro drawer naming
  isDrawerVisible = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  // Method to open the Ng-Zorro Drawer
  openDrawer(): void {
    this.isDrawerVisible = true;
  }

  // Method to close the Ng-Zorro Drawer
  closeDrawer(): void {
    this.isDrawerVisible = false;
  }

  // The signOut logic remains the same
  signOut(): void {
    this.authService.signOut().subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: err => {
        console.error('Sign out failed', err);
        // Optionally show a user-friendly error notification
        this.router.navigate(['/']); // Still redirect even on error for now
      }
    });
  }
}