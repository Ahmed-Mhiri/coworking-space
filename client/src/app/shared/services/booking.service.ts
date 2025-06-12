import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
import { Booking } from "../models/Booking";
import { Room } from "../models/Room";

@Injectable({ providedIn: 'root' })
export class BookingService {
  private api = `${environment.apiBaseUrl}/bookings`;

  constructor(private http: HttpClient) {}

  createBooking(userId: number, isFreelancer: boolean, booking: any): Observable<Booking> {
    return this.http.post<Booking>(`${this.api}/user/${userId}?isFreelancer=${isFreelancer}`, booking);
  }

  getBookingsForUser(userId: number): Observable<Booking[]> {
  return this.http.get<Booking[]>(`${this.api}/user/${userId}`);
}

getAvailableRooms(date: string, startTime: string, endTime: string): Observable<Room[]> {
  const params = new HttpParams()
    .set('date', date)
    .set('startTime', startTime)
    .set('endTime', endTime);

  return this.http.get<Room[]>(`${this.api}/available-rooms`, { params });
}
}