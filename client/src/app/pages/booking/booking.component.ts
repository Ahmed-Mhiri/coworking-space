import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common'; // Ensure DatePipe is imported
import { Subject, takeUntil } from 'rxjs';

import { Booking } from '../../shared/models/Booking';
import { Room } from '../../shared/models/Room';
import { BookingService } from '../../shared/services/booking.service';
import { AuthService } from '../../shared/services/auth.service';
import { RoomService } from '../../shared/services/room.service';
import { FreelancerNavbarComponent } from "../../shared/components/freelancer-navbar/freelancer-navbar.component";
import { InternNavbarComponent } from "../../shared/components/intern-navbar/intern-navbar.component";

// --- NG-ZORRO IMPORTS ---
import { NzStepsModule } from 'ng-zorro-antd/steps';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzTimePickerModule } from 'ng-zorro-antd/time-picker';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzEmptyModule } from 'ng-zorro-antd/empty';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzNotificationModule, NzNotificationService } from 'ng-zorro-antd/notification';
import { NzCollapseModule } from 'ng-zorro-antd/collapse';
import { NzDividerModule } from 'ng-zorro-antd/divider';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [
    CommonModule, FormsModule, ReactiveFormsModule, FreelancerNavbarComponent, InternNavbarComponent,
    NzStepsModule, NzFormModule, NzDatePickerModule, NzTimePickerModule, NzButtonModule,
    NzIconModule, NzEmptyModule, NzSpinModule, NzCardModule, NzTagModule,
    NzRadioModule, NzInputModule, NzNotificationModule, NzCollapseModule, NzDividerModule
  ],
  templateUrl: './booking.component.html',
  providers: [DatePipe] // Crucial: Provide DatePipe
})
export class BookingsComponent implements OnInit, OnDestroy {
  currentStep = 0;

  bookings: Booking[] = [];
  availableRooms: Room[] = [];
  selectedRoom: Room | null = null;
  occupancy: number | null = null;
  totalPrice: number = 0;

  loadingAvailability = false;
  loadingSubmit = false;

  bookingForm: FormGroup;
  paymentForm: FormGroup;

  userId!: number;
  isFreelancer: boolean = false;

  paymentMethod: 'creditCard' | 'paypal' = 'creditCard';

  private destroy$ = new Subject<void>();

  constructor(
    private bookingService: BookingService,
    private authService: AuthService,
    private roomService: RoomService,
    private notification: NzNotificationService,
    private fb: FormBuilder,
    private datePipe: DatePipe // Inject DatePipe
  ) {
    this.bookingForm = this.fb.group({
      // Initialize with null or actual Date objects if default values are desired
      date: [null, Validators.required],
      startTime: [null, Validators.required],
      endTime: [null, Validators.required]
    });

    this.paymentForm = this.fb.group({
      cardName: ['', Validators.required],
      cardNumber: ['', [Validators.required, Validators.pattern('^[0-9 ]{13,19}$')]],
      expiry: ['', [Validators.required, Validators.pattern('^(0[1-9]|1[0-2])\\/\\d{2}$')]],
      cvv: ['', [Validators.required, Validators.pattern('^[0-9]{3,4}$')]],
    });
  }

  ngOnInit(): void {
    this.authService.currentUser$
      .pipe(takeUntil(this.destroy$))
      .subscribe(user => {
        if (user) {
          this.userId = user.id;
          this.isFreelancer = user.role === 'FREELANCER';
          this.loadBookings();
        } else {
          this.bookings = [];
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadBookings(): void {
    if (!this.userId) return;
    this.bookingService.getBookingsForUser(this.userId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (res: Booking[]) => this.bookings = res,
        error: err => this.notification.create('error', 'Erreur', 'Impossible de charger vos réservations.')
      });
  }

  checkAvailability(): void {
    // Debugging: Log form status
    console.log('Booking Form Status:', this.bookingForm.value);
    console.log('Booking Form Valid:', this.bookingForm.valid);
    console.log('Booking Form Dirty:', this.bookingForm.dirty);
    console.log('Booking Form Touched:', this.bookingForm.touched);

    if (this.bookingForm.invalid) {
        Object.values(this.bookingForm.controls).forEach(control => {
            control.markAsDirty(); // Mark all controls as dirty to trigger validation messages
            control.updateValueAndValidity(); // Recalculate validity
        });
        this.notification.create('warning', 'Formulaire incomplet', 'Veuillez sélectionner une date et des heures valides.');
        return; // Stop execution if form is invalid
    }

    const { date, startTime, endTime } = this.bookingForm.value;

    // Additional check to ensure actual Date objects are present, not null/undefined
    if (!date || !(date instanceof Date) || !startTime || !(startTime instanceof Date) || !endTime || !(endTime instanceof Date)) {
        this.notification.create('error', 'Erreur de date/heure', 'Les dates et heures sélectionnées sont invalides.');
        return;
    }

    this.availableRooms = [];
    this.selectedRoom = null;
    this.totalPrice = 0;
    this.occupancy = null;
    this.loadingAvailability = true;

    this.bookingService.getAvailableRooms(
        this.formatDate(date),
        this.formatTime(startTime),
        this.formatTime(endTime)
      )
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (rooms: Room[]) => {
          this.availableRooms = rooms;
          this.loadingAvailability = false;
          this.currentStep = 1; // THIS IS WHERE THE STEP CHANGE HAPPENS
          if (rooms.length === 0) {
              this.notification.create('info', 'Aucune salle', 'Aucune salle disponible pour la période sélectionnée.');
          } else {
              this.notification.create('success', 'Salles trouvées', `${rooms.length} salles disponibles.`);
          }
        },
        error: err => {
          console.error('Error fetching available rooms', err);
          this.loadingAvailability = false;
          this.notification.create('error', 'Erreur', 'Impossible de récupérer les salles disponibles. Veuillez vérifier votre connexion ou réessayer.');
        }
      });
  }

  selectRoom(room: Room): void {
    this.selectedRoom = room;

    const { date, startTime } = this.bookingForm.value;

    this.roomService.getRoomOccupancy(room.id, this.formatDate(date), this.formatTime(startTime))
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (count: number) => this.occupancy = count,
        error: err => this.notification.create('error', 'Erreur', 'Impossible de récupérer l\'occupation de la salle.')
      });

    this.updatePrice();
  }

  submitBooking(): void {
    if (!this.selectedRoom || !this.userId) {
        this.notification.create('error', 'Erreur', 'Veuillez sélectionner une salle et vous assurer que vous êtes connecté.');
        return;
    }

    if (this.paymentMethod === 'creditCard') {
      if (this.paymentForm.invalid) {
        Object.values(this.paymentForm.controls).forEach(c => { c.markAsDirty(); c.updateValueAndValidity(); });
        this.notification.create('warning', 'Paiement invalide', 'Veuillez corriger les informations de votre carte de crédit.');
        return;
      }
      this.processPaymentAndCreateBooking();
    } else if (this.paymentMethod === 'paypal') {
      this.payWithPayPalAndCreateBooking();
    }
  }

  private processPaymentAndCreateBooking(): void {
      this.loadingSubmit = true;
      this.notification.create('info', 'Paiement en cours', 'Traitement de votre paiement...');

      setTimeout(() => {
          // Simulate payment success. In a real app, this would be an API call.
          this.createBooking();
      }, 1500);
  }

  private payWithPayPalAndCreateBooking(): void {
      this.loadingSubmit = true;
      this.notification.create('info', 'Redirection PayPal', 'Vous serez redirigé vers PayPal pour le paiement...');

      setTimeout(() => {
          // Simulate PayPal success. In a real app, this would be a callback from PayPal.
          this.notification.create('success', 'PayPal', 'Paiement simulé avec PayPal réussi.');
          this.createBooking();
      }, 2000);
  }

  private createBooking(): void {
    const { date, startTime, endTime } = this.bookingForm.value;

    const bookingPayload = {
      date: this.formatDate(date),
      startTime: this.formatTime(startTime),
      endTime: this.formatTime(endTime),
      room: {
        id: this.selectedRoom!.id,
        type: this.selectedRoom!.type
      },
      amountPaid: this.totalPrice,
      paid: true
    };

    this.bookingService.createBooking(this.userId, this.isFreelancer, bookingPayload)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (res: Booking) => {
          this.notification.create('success', 'Réservation Confirmée', `Votre réservation pour la salle #${res.room.id} a été ajoutée.`);
          this.loadBookings();
          this.resetBookingForm();
          this.loadingSubmit = false;
          this.currentStep = 0;
        },
        error: err => {
          console.error('Error creating booking', err);
          this.notification.create('error', 'Erreur de Réservation', 'Impossible de créer la réservation. Veuillez réessayer.');
          this.loadingSubmit = false;
        }
      });
  }

  private resetBookingForm(): void {
    this.bookingForm.reset({
      date: new Date(),
      startTime: new Date(),
      endTime: new Date()
    });
    this.paymentForm.reset();
    this.availableRooms = [];
    this.selectedRoom = null;
    this.totalPrice = 0;
    this.occupancy = null;
    this.paymentMethod = 'creditCard';
  }

  goToStep(step: number): void {
    this.currentStep = step;
  }

  confirmBookingAndPay() {
    if (!this.selectedRoom) {
      this.notification.create('warning', 'Salle non sélectionnée', 'Veuillez sélectionner une salle pour continuer.');
      return;
    }
    this.currentStep = 2;
  }

  private updatePrice(): void {
    if (!this.selectedRoom) { this.totalPrice = 0; return; }
    // Ensure that startTime and endTime are properly converted to Date objects for calculation
    const start = new Date(`1970-01-01T${this.formatTime(this.bookingForm.value.startTime)}`);
    const end = new Date(`1970-01-01T${this.formatTime(this.bookingForm.value.endTime)}`);
    const hours = (end.getTime() - start.getTime()) / 3600000;
    this.totalPrice = hours > 0 ? hours * this.selectedRoom.pricePerHour : 0;
  }

  public disabledPastDates = (current: Date): boolean => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return current && current.getTime() < today.getTime();
  };

  // --- REVISED: More robust type handling for format functions ---
  private formatDate = (d: Date | string | null | undefined): string => {
    if (!d) return ''; // Handle null/undefined
    if (typeof d === 'string' && d.match(/^\d{4}-\d{2}-\d{2}$/)) {
      return d; // Already in YYYY-MM-DD format
    }
    // If it's a Date object, or potentially a string that needs parsing
    const dateObj = d instanceof Date ? d : new Date(d);
    return this.datePipe.transform(dateObj, 'yyyy-MM-dd') || '';
  };

  private formatTime = (d: Date | string | null | undefined): string => {
    if (!d) return ''; // Handle null/undefined
    if (typeof d === 'string') {
        // If it's already "HH:mm:ss" or "HH:mm"
        if (d.match(/^\d{2}:\d{2}(:\d{2})?$/)) {
            return d.length === 5 ? `${d}:00` : d; // Ensure seconds are there
        }
        // Attempt to parse string like "HH:mm" into a Date object
        const timeParts = d.split(':');
        if (timeParts.length >= 2) {
            const tempDate = new Date();
            tempDate.setHours(parseInt(timeParts[0], 10), parseInt(timeParts[1], 10), 0, 0);
            return this.datePipe.transform(tempDate, 'HH:mm:ss') || '00:00:00';
        }
    }
    // If it's a Date object
    if (d instanceof Date) {
        return this.datePipe.transform(d, 'HH:mm:ss') || '00:00:00';
    }

    return '00:00:00'; // Default fallback
  };
}