import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Room } from '../models/Room';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private api = `${environment.apiBaseUrl}/admin`;

  constructor(private http: HttpClient) {}

  createRoom(room: Room) {
    return this.http.post<Room>(`${this.api}/rooms`, room);
  }

  getAllRooms() {
    return this.http.get<Room[]>(`${this.api}/rooms`);
  }

  updateRoom(id: number, room: Room) {
    return this.http.put<Room>(`${this.api}/rooms/${id}`, room);
  }

  deleteRoom(id: number) {
    return this.http.delete(`${this.api}/rooms/${id}`);
  }
  // New method to get occupancy count at a specific date and time
  getRoomOccupancy(roomId: number, date: string, time: string): Observable<number> {
    const params = new HttpParams()
      .set('date', date)   // format: 'YYYY-MM-DD'
      .set('time', time);  // format: 'HH:mm:ss' or 'HH:mm'

    return this.http.get<number>(`${this.api}/rooms/${roomId}/occupancy`, { params });
  }


}
