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
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox'; // For a "Remember me" option later
import { NzAlertModule } from 'ng-zorro-antd/alert';
import { NzIconModule } from 'ng-zorro-antd/icon';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    RouterLink,
    // Add new Zorro modules
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzCheckboxModule,
    NzAlertModule,
    NzIconModule
  ],
  templateUrl: './signin.component.html',
  styleUrls: [] // Remove the link to the .scss file
})
export class SigninComponent {
  credentials = {
    email: '',
    password: ''
  };

  errorMessage = '';
  isLoading = false; // For the button's loading spinner

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    if (!this.credentials.email || !this.credentials.password) {
      this.errorMessage = 'Veuillez remplir tous les champs.';
      return;
    }

    this.isLoading = true; // Start loading spinner
    this.errorMessage = '';

    this.authService.signIn(this.credentials)
      .pipe(take(1)) // ENHANCEMENT: Automatically unsubscribes after 1 emission (success or error)
      .subscribe({
        next: (user) => {
          this.isLoading = false; // Stop loading spinner
          if (!user) {
            this.errorMessage = 'Utilisateur non trouvé après connexion.';
            return;
          }

          // Role-based redirect logic (no changes here)
          switch (user.role) {
            case 'FREELANCER': this.router.navigate(['/freelancer-dashboard']); break;
            case 'INTERN': this.router.navigate(['/intern-dashboard']); break;
            case 'ADMIN': this.router.navigate(['/admin-dashboard']); break;
            default: this.errorMessage = 'Rôle utilisateur non reconnu.';
          }
        },
        error: () => {
          this.isLoading = false; // Stop loading spinner
          this.errorMessage = 'Échec de la connexion. Veuillez vérifier vos identifiants.';
        }
      });
  }
}