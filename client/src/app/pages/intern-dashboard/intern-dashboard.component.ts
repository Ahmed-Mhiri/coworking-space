import { Component, OnInit, TemplateRef, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'; // Import ReactiveFormsModule and related classes
import { Lecture } from '../../shared/models/Lecture';
import { LectureService } from '../../shared/services/lecture.service';
import { InternNavbarComponent } from "../../shared/components/intern-navbar/intern-navbar.component";
import { ProgressStatus } from '../../shared/models/ProgressStatus'; // Assuming ProgressStatus is an enum or type
import { Subject, takeUntil } from 'rxjs'; // For managing subscriptions
import { NzNotificationService } from 'ng-zorro-antd/notification'; // For notifications

// --- NG-ZORRO IMPORTS ---
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzModalModule, NzModalService } from 'ng-zorro-antd/modal'; // NzModalService
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzProgressModule } from 'ng-zorro-antd/progress';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzEmptyModule } from 'ng-zorro-antd/empty';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip'; // For tooltips on buttons

@Component({
  selector: 'app-intern-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule, // Use Reactive Forms
    InternNavbarComponent,
    // Add NG-ZORRO Modules
    NzButtonModule, NzIconModule, NzModalModule, NzFormModule, NzInputModule,
    NzSelectModule, NzProgressModule, NzTagModule, NzPopconfirmModule, NzEmptyModule, NzDividerModule,
    NzToolTipModule, // Add NzToolTipModule
  ],
  templateUrl: './intern-dashboard.component.html',
  styleUrls: [] // Remove .scss file, rely on Tailwind and Ng-Zorro
})
export class InternDashboardComponent implements OnInit, OnDestroy {
  lectures: Lecture[] = [];
  lectureForm: FormGroup; // Reactive Form group
  editingLectureId: number | null = null;
  selectedFiles: File[] = [];
  selectedLecture: Lecture | null = null; // For file preview modal

  progressOptions = [
    { label: 'Not Yet Started', value: 'NOT_YET_STARTED' },
    { label: 'In Progress', value: 'IN_PROGRESS' },
    { label: 'Done', value: 'DONE' }
  ];

  private destroy$ = new Subject<void>(); // For managing subscriptions

  constructor(
    public lectureService: LectureService, // Keep public if used directly in template for file URLs
    private fb: FormBuilder, // FormBuilder for reactive forms
    private modalService: NzModalService, // Ng-Zorro Modal Service
    private notification: NzNotificationService // Ng-Zorro Notification Service
  ) {
    // Initialize Reactive Form
    this.lectureForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      progress: ['NOT_YET_STARTED', [Validators.required]] // Default value
    });
  }

  ngOnInit(): void {
    this.loadLectures();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadLectures(): void {
    this.lectureService.getLectures()
      .pipe(takeUntil(this.destroy$)) // Use takeUntil
      .subscribe({
        next: (data: Lecture[]) => {
          this.lectures = data;
          this.notification.create('success', 'Lectures Chargées', 'Vos cours ont été mis à jour.');
        },
        error: (err: any) => {
          console.error('❌ Error loading lectures:', err);
          this.notification.create('error', 'Erreur', 'Impossible de charger vos cours.');
        }
      });
  }

  openLectureModal(modalContent: TemplateRef<{}>, lecture?: Lecture): void {
    if (lecture) {
      this.editingLectureId = lecture.id!;
      // Use patchValue to fill the form with existing lecture data
      this.lectureForm.patchValue({
        name: lecture.name,
        description: lecture.description,
        progress: lecture.progress
      });
    } else {
      this.editingLectureId = null;
      // Reset form for adding a new lecture
      this.lectureForm.reset({ progress: 'NOT_YET_STARTED' });
    }
    this.selectedFiles = []; // Clear selected files when opening modal

    this.modalService.create({
      nzTitle: this.editingLectureId ? 'Modifier le Cours' : 'Ajouter un Nouveau Cours',
      nzContent: modalContent,
      nzFooter: [
        { label: 'Annuler', onClick: () => this.modalService.closeAll() },
        {
          label: this.editingLectureId ? 'Mettre à jour' : 'Créer',
          type: 'primary',
          onClick: () => this.addOrUpdateLecture(),
          // Disable button if form is invalid
          disabled: () => this.lectureForm.invalid
        }
      ],
      nzOnCancel: () => this.lectureForm.reset({ progress: 'NOT_YET_STARTED' }) // Reset on cancel
    });
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.selectedFiles = Array.from(input.files);
    }
  }

  addOrUpdateLecture(): void {
    // Mark all controls as dirty to show validation errors
    this.lectureForm.markAllAsTouched();
    this.lectureForm.updateValueAndValidity();

    if (this.lectureForm.invalid) {
      this.notification.create('warning', 'Formulaire invalide', 'Veuillez remplir tous les champs requis.');
      return;
    }

    const lecturePayload = this.lectureForm.value; // Get values directly from reactive form

    if (this.editingLectureId !== null) {
      // Update existing lecture
      this.lectureService.updateLecture(this.editingLectureId, lecturePayload)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: () => {
            if (this.selectedFiles.length > 0) {
              this.uploadLectureFiles(this.editingLectureId!);
            } else {
              this.notification.create('success', 'Cours Mis à Jour', 'Le cours a été mis à jour avec succès.');
              this.loadLectures();
              this.modalService.closeAll();
            }
          },
          error: (err: any) => {
            console.error('❌ Error updating lecture:', err);
            this.notification.create('error', 'Erreur', 'Impossible de mettre à jour le cours.');
          }
        });
    } else {
      // Create new lecture
      this.lectureService.createLecture(lecturePayload)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (res: Lecture) => {
            if (this.selectedFiles.length > 0) {
              // Upload files for the newly created lecture
              this.uploadLectureFiles(res.id!, res); // Pass res to ensure immediate display if needed
            } else {
              this.notification.create('success', 'Cours Ajouté', 'Le nouveau cours a été ajouté avec succès.');
              this.loadLectures();
              this.modalService.closeAll();
            }
          },
          error: (err: any) => {
            console.error('❌ Error adding lecture:', err);
            this.notification.create('error', 'Erreur', 'Impossible d\'ajouter le cours.');
          }
        });
    }
  }

  private uploadLectureFiles(lectureId: number, newLectureData?: Lecture): void {
    this.lectureService.uploadFiles(lectureId, this.selectedFiles)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.notification.create('success', 'Fichiers Téléchargés', 'Les fichiers du cours ont été téléchargés.');
          this.loadLectures(); // Reload lectures to show updated file lists
          this.modalService.closeAll();
        },
        error: (err: any) => {
          console.error('❌ Error uploading files:', err);
          this.notification.create('error', 'Erreur de Fichier', 'Impossible de télécharger les fichiers du cours.');
          // Even if file upload fails, we should still close the modal and update lectures
          this.loadLectures();
          this.modalService.closeAll();
        }
      });
  }

  deleteLecture(lectureId: number): void {
    this.lectureService.deleteLecture(lectureId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.notification.create('success', 'Cours Supprimé', 'Le cours a été supprimé avec succès.');
          this.lectures = this.lectures.filter(l => l.id !== lectureId); // Optimistic update
        },
        error: (err: any) => {
          console.error(`❌ Error deleting lecture ${lectureId}:`, err);
          this.notification.create('error', 'Erreur de Suppression', 'Impossible de supprimer le cours.');
        }
      });
  }

  openFilesOverlay(modalContent: TemplateRef<{}>, lecture: Lecture): void {
    this.selectedLecture = lecture;
    this.modalService.create({
      nzTitle: `${lecture.name} - Fichiers`,
      nzContent: modalContent,
      nzFooter: null, // No footer buttons for this modal
      nzWidth: 700 // Adjust width as needed
    });
  }

  closeFilesOverlay(): void {
    this.selectedLecture = null;
    this.modalService.closeAll(); // Close any open modal
  }

  // --- Template Helper Functions ---
  getProgressPercent(status: ProgressStatus): number {
    if (status === 'DONE') return 100;
    if (status === 'IN_PROGRESS') return 50;
    return 0;
  }

  getProgressStatusColor(status: ProgressStatus): string {
    if (status === 'DONE') return 'success'; // Green
    if (status === 'IN_PROGRESS') return 'processing'; // Blue
    return 'default'; // Gray
  }

  getFileType(file: string): string {
    if (/\.(jpeg|jpg|png|gif|bmp)$/i.test(file)) return 'image';
    if (/\.(mp4|webm|ogg)$/i.test(file)) return 'video';
    if (/\.(mp3|wav|ogg)$/i.test(file)) return 'audio';
    if (/\.(pdf)$/i.test(file)) return 'pdf';
    return 'file';
  }

  extractFilename(path: string): string {
    return path.split(/[/\\]/).pop() || '';
  }
}