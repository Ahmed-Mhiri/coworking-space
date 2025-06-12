import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { take } from 'rxjs';

import { AuthService } from '../../../shared/services/auth.service';

// --- NG-ZORRO IMPORTS ---
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzAlertModule } from 'ng-zorro-antd/alert'; // For Success/Error messages
import { NzIconModule } from 'ng-zorro-antd/icon';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    // Add Zorro modules
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzAlertModule,
    NzIconModule
  ],
  templateUrl: './forgot-password.component.html',
  styleUrls: [] // Remove the link to the .scss file
})
export class ForgotPasswordComponent {
  email = '';
  message = '';
  error = '';
  isLoading = false; // For the button's loading spinner

  constructor(private authService: AuthService) {}

  onSubmit(): void {
    if (!this.email) return;

    this.isLoading = true;
    this.message = '';
    this.error = '';

    this.authService.forgotPassword(this.email)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.isLoading = false;
          this.message = 'Si un compte est associé à cet email, un lien de réinitialisation a été envoyé.';
          this.error = '';
          this.email = ''; // Clear the input on success
        },
        error: () => {
          this.isLoading = false;
          this.error = "Une erreur s'est produite lors de l'envoi. Veuillez réessayer plus tard.";
          this.message = '';
        }
      });
  }
}