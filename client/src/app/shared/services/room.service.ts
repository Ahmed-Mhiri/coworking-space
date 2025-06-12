import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
import { Room } from "../models/Room";

@Injectable({ providedIn: 'root' })
export class RoomService {
  private api = `${environment.apiBaseUrl}/rooms`;

  constructor(private http: HttpClient) {}

  createRoom(room: any) {
    return this.http.post(this.api, room);
  }

  getAllRooms(): Observable<Room[]> {
  return this.http.get<Room[]>(this.api);
}

  updateRoom(id: number, room: any) {
    return this.http.put(`${this.api}/${id}`, room);
  }

  deleteRoom(id: number) {
    return this.http.delete(`${this.api}/${id}`);
  }
  getRoomOccupancy(roomId: number, date: string, time: string): Observable<number> {
    const params = new HttpParams()
      .set('date', date)   // format: 'YYYY-MM-DD'
      .set('time', time);  // format: 'HH:mm:ss' or 'HH:mm'

    return this.http.get<number>(`${environment.apiBaseUrl}/admin/rooms/${roomId}/occupancy`, { params });
  }
}