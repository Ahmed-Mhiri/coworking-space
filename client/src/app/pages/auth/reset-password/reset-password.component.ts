import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { take } from 'rxjs';

import { AuthService } from '../../../shared/services/auth.service';

// --- NG-ZORRO IMPORTS ---
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzNotificationModule, NzNotificationService } from 'ng-zorro-antd/notification';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzAlertModule } from 'ng-zorro-antd/alert'; // <-- 1. IMPORT THE ALERT MODULE

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    RouterLink,
    // Add Zorro modules
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzNotificationModule,
    NzIconModule,
    NzAlertModule // <-- 2. ADD THE ALERT MODULE TO THE IMPORTS ARRAY
  ],
  templateUrl: './reset-password.component.html',
  styleUrls: []
})
export class ResetPasswordComponent implements OnInit {
  token = '';
  newPassword = '';
  isLoading = false;
  initialError = '';

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private notification: NzNotificationService
  ) {}

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token') || '';
    if (!this.token) {
      this.initialError = 'Lien de réinitialisation invalide ou expiré. Veuillez refaire une demande.';
    }
  }

  onSubmit(): void {
    if (!this.token || !this.newPassword) return;

    this.isLoading = true;

    this.authService.resetPassword(this.token, this.newPassword)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.isLoading = false;
          this.notification.create('success', 'Succès', 'Votre mot de passe a été réinitialisé avec succès.');
          setTimeout(() => this.router.navigate(['/signin']), 2500);
        },
        error: (err) => {
          this.isLoading = false;
          this.notification.create('error', 'Échec', err.error.message || 'La réinitialisation a échoué. Le lien est peut-être expiré.');
        }
      });
  }
}