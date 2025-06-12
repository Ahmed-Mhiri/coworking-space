import { Component, OnInit, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { take } from 'rxjs';

import { Room } from '../../shared/models/Room';
import { AdminService } from '../../shared/services/admin.service';
import { AdminNavbarComponent } from '../../shared/components/admin-navbar/admin-navbar.component';

// --- NG-ZORRO IMPORTS ---
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzModalModule, NzModalService } from 'ng-zorro-antd/modal';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzInputNumberModule } from 'ng-zorro-antd/input-number'; // <-- 1. IMPORT THE MISSING MODULE
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzTimePickerModule } from 'ng-zorro-antd/time-picker';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzEmptyModule } from 'ng-zorro-antd/empty';

@Component({
  selector: 'app-admin-management',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AdminNavbarComponent,
    // Add NG-ZORRO Modules
    NzButtonModule, NzIconModule, NzModalModule, NzFormModule, NzInputModule,
    NzInputNumberModule, // <-- 2. ADD THE MODULE TO THE IMPORTS ARRAY
    NzSelectModule, NzPopconfirmModule, NzDatePickerModule, NzTimePickerModule,
    NzTagModule, NzDividerModule, NzEmptyModule
  ],
  templateUrl: './admin-management.component.html',
  styleUrls: []
})
export class AdminManagementComponent implements OnInit {
  // ... the rest of your component logic remains exactly the same
  rooms: Room[] = [];
  selectedDateTime: Date = new Date();
  occupancyMap: Map<number, number> = new Map();
  Object = Object;

  roomForm: FormGroup;
  editingRoom: Room | null = null;
  roomTypes = ['CAFE_ROOM', 'OPEN_ROOM', 'MEETING_ROOM', 'PRIVATE_OFFICE', 'LOUNGE', 'CONFERENCE_ROOM', 'BRAINSTORMING_ROOM', 'QUIET_ZONE'];

  constructor(
    private adminService: AdminService,
    private fb: FormBuilder,
    private modalService: NzModalService
  ) {
    this.roomForm = this.fb.group({
      type: ['', [Validators.required]],
      sizeLimit: [null, [Validators.required, Validators.min(1)]],
      pricePerHour: [null, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit() {
    this.loadRooms();
  }

  loadRooms() {
    this.adminService.getAllRooms().pipe(take(1)).subscribe(rooms => {
      this.rooms = rooms;
      this.loadOccupancies();
    });
  }

  loadOccupancies() {
    this.occupancyMap.clear();
    const dateStr = this.selectedDateTime.toISOString().slice(0, 10);
    const timeStr = this.selectedDateTime.toTimeString().slice(0, 8);

    this.rooms.forEach(room => {
      this.adminService.getRoomOccupancy(room.id!, dateStr, timeStr)
        .pipe(take(1))
        .subscribe(count => {
          this.occupancyMap.set(room.id!, count);
        });
    });
  }

  openRoomModal(modalContent: TemplateRef<{}>, room?: Room): void {
    if (room) {
      this.editingRoom = room;
      this.roomForm.patchValue(room);
    } else {
      this.editingRoom = null;
      this.roomForm.reset();
    }

    this.modalService.create({
      nzTitle: this.editingRoom ? 'Modifier la Salle' : 'Ajouter une Nouvelle Salle',
      nzContent: modalContent,
      nzFooter: [
        { label: 'Annuler', onClick: () => this.modalService.closeAll() },
        {
          label: this.editingRoom ? 'Mettre à jour' : 'Ajouter',
          type: 'primary',
          onClick: () => this.saveRoom(),
          disabled: () => this.roomForm.invalid
        }
      ],
      nzOnCancel: () => this.roomForm.reset()
    });
  }

  saveRoom() {
    if (this.roomForm.invalid) {
      Object.values(this.roomForm.controls).forEach(control => {
        control.markAsDirty();
        control.updateValueAndValidity({ onlySelf: true });
      });
      return;
    }

    const formValue = this.roomForm.value;
    const request$ = this.editingRoom
      ? this.adminService.updateRoom(this.editingRoom.id!, { ...this.editingRoom, ...formValue })
      : this.adminService.createRoom(formValue);

    request$.pipe(take(1)).subscribe(() => {
      this.loadRooms();
      this.modalService.closeAll();
    });
  }

  deleteRoom(roomId: number) {
    this.adminService.deleteRoom(roomId).pipe(take(1)).subscribe(() => {
      this.loadRooms();
    });
  }

  get roomsGroupedByType(): Record<string, Room[]> {
    return this.rooms.reduce((groups, room) => {
      (groups[room.type] = groups[room.type] || []).push(room);
      return groups;
    }, {} as Record<string, Room[]>);
  }

  formatRoomType(type: string): string {
    return type.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
  }
}