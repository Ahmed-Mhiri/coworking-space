import { Component, OnInit, TemplateRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MissionService } from '../../shared/services/mission.service';
import { Mission } from '../../shared/models/Mission';
import { ProgressStatus } from '../../shared/models/ProgressStatus';
import { FreelancerNavbarComponent } from "../../shared/components/freelancer-navbar/freelancer-navbar.component";
import { take } from 'rxjs';

// --- NG-ZORRO IMPORTS ---
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzModalModule, NzModalService } from 'ng-zorro-antd/modal';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzProgressModule } from 'ng-zorro-antd/progress';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzEmptyModule } from 'ng-zorro-antd/empty';
import { NzDividerModule } from 'ng-zorro-antd/divider';

@Component({
  selector: 'app-freelancer-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule, // Use Reactive Forms
    FreelancerNavbarComponent,
    // Add NG-ZORRO Modules
    NzButtonModule, NzIconModule, NzModalModule, NzFormModule, NzInputModule,
    NzDatePickerModule, NzSelectModule, NzProgressModule, NzTagModule,
    NzPopconfirmModule, NzEmptyModule, NzDividerModule
  ],
  templateUrl: './freelancer-dashboard.component.html',
  styleUrls: [] // Remove .scss file
})
export class FreelancerDashboardComponent implements OnInit {
  missions: Mission[] = [];
  missionForm: FormGroup;
  editingMissionId: number | null = null;
  
  progressOptions = [
    { label: 'Not Yet Started', value: 'NOT_YET_STARTED' },
    { label: 'In Progress', value: 'IN_PROGRESS' },
    { label: 'Done', value: 'DONE' }
  ];

  constructor(
    private missionService: MissionService,
    private fb: FormBuilder,
    private modalService: NzModalService
  ) {
    this.missionForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      deadline: [null, [Validators.required]],
      progress: ['NOT_YET_STARTED', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadMissions();
  }

  loadMissions(): void {
    this.missionService.getMissions().pipe(take(1)).subscribe({
      next: (data: Mission[]) => this.missions = data
    });
  }

  openMissionModal(modalContent: TemplateRef<{}>, mission?: Mission): void {
    if (mission) {
      this.editingMissionId = mission.id!;
      this.missionForm.patchValue(mission);
    } else {
      this.editingMissionId = null;
      this.missionForm.reset({ progress: 'NOT_YET_STARTED' });
    }

    this.modalService.create({
      nzTitle: this.editingMissionId ? 'Modifier la Mission' : 'Ajouter une Nouvelle Mission',
      nzContent: modalContent,
      nzFooter: [
        { label: 'Annuler', onClick: () => this.modalService.closeAll() },
        {
          label: this.editingMissionId ? 'Mettre à jour' : 'Créer',
          type: 'primary',
          onClick: () => this.addOrUpdateMission(),
          disabled: () => this.missionForm.invalid
        }
      ],
      nzOnCancel: () => this.missionForm.reset()
    });
  }

  addOrUpdateMission(): void {
    if (this.missionForm.invalid) {
      Object.values(this.missionForm.controls).forEach(control => {
        control.markAsDirty();
        control.updateValueAndValidity({ onlySelf: true });
      });
      return;
    }

    const missionPayload = this.missionForm.value;
    const request$ = this.editingMissionId
      ? this.missionService.updateMission(this.editingMissionId, missionPayload)
      : this.missionService.createMission(missionPayload);

    request$.pipe(take(1)).subscribe(() => {
      this.loadMissions();
      this.modalService.closeAll();
    });
  }

  deleteMission(missionId: number): void {
    this.missionService.deleteMission(missionId).pipe(take(1)).subscribe(() => {
      this.loadMissions();
    });
  }

  // --- Template Helper Functions ---
  getProgressPercent(status: ProgressStatus): number {
    if (status === 'DONE') return 100;
    if (status === 'IN_PROGRESS') return 50;
    return 0;
  }

  getProgressStatusColor(status: ProgressStatus): string {
    if (status === 'DONE') return 'success';
    if (status === 'IN_PROGRESS') return 'processing';
    return 'default';
  }
}