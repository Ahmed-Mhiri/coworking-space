import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { take } from 'rxjs';

import { AuthService } from '../../../shared/services/auth.service';

// --- NG-ZORRO IMPORTS ---
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzSelectModule } from 'ng-zorro-antd/select'; // For the Role dropdown
import { NzNotificationModule, NzNotificationService } from 'ng-zorro-antd/notification'; // For professional toast messages
import { NzIconModule } from 'ng-zorro-antd/icon';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    RouterLink,
    // Add new Zorro modules
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzSelectModule,
    NzNotificationModule,
    NzIconModule
  ],
  templateUrl: './sign-up.component.html',
  styleUrls: [] // Remove the link to the .scss file
})
export class SignupComponent {
  isLoading = false;
  user = {
    username: '',
    email: '',
    password: '',
    role: null // Use null for placeholder visibility in nz-select
  };

  constructor(
    private authService: AuthService,
    private router: Router,
    private notification: NzNotificationService // Inject the Notification service
  ) {}

  onSubmit(): void {
    const { username, email, password, role } = this.user;

    if (!username || !email || !password || !role) {
      this.notification.create('error', 'Erreur', 'Veuillez remplir tous les champs.');
      return;
    }

    this.isLoading = true;

    this.authService.signUp(this.user)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.isLoading = false;
          this.notification.create('success', 'Inscription Réussie !', `Bienvenue, ${this.user.username} 🥳`);
          setTimeout(() => this.router.navigate(['/signin']), 2000);
        },
        error: (err) => {
          this.isLoading = false;
          this.notification.create('error', 'Échec de l\'inscription', err.error.message || 'Une erreur inconnue est survenue.');
        }
      });
  }
}