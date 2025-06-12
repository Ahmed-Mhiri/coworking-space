import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
import { Room } from "../models/Room";

export interface RoomDTO {
  id: number;
  type: string;
  sizeLimit: number;
  pricePerHour: number;
}

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private api = `${environment.apiBaseUrl}/admin/dashboard`;

  constructor(private http: HttpClient) {}

  // ===== ROOM & USER METRICS =====

  getPeoplePerRoomType(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/people-per-room-type`);
  }

  getUserTypesInRoom(roomId: number): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/user-types-in-room/${roomId}`);
  }

  getRoomsByType(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/rooms-by-type`);
  }

  // ===== FINANCIAL METRICS =====

  getRevenueByDate(date: string): Observable<number> {
    const params = new HttpParams().set('date', date);
    return this.http.get<number>(`${this.api}/revenue/date`, { params });
  }

  getRevenueBetween(start: string, end: string): Observable<number> {
    const params = new HttpParams()
      .set('start', start)
      .set('end', end);
    return this.http.get<number>(`${this.api}/revenue/range`, { params });
  }

  getMonthlyRevenueTrend(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/revenue/monthly-trend`);
  }

  getRevenuePerRoomType(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/revenue/room-type`);
  }

  getDailyRevenueTrend(days: number = 30): Observable<Record<string, number>> {
    const params = new HttpParams().set('days', days.toString());
    return this.http.get<Record<string, number>>(`${this.api}/revenue/daily-trend`, { params });
  }

  getRevenueByUserType(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/revenue/user-type`);
  }

  // ===== BOOKING & USAGE PATTERNS =====

  countBookingsOnDate(date: string): Observable<number> {
    const params = new HttpParams().set('date', date);
    return this.http.get<number>(`${this.api}/bookings/count`, { params });
  }

  getDailyBookingsTrend(days: number = 30): Observable<Record<string, number>> {
    const params = new HttpParams().set('days', days.toString());
    return this.http.get<Record<string, number>>(`${this.api}/bookings/daily-trend`, { params });
  }

  getAverageBookingDuration(): Observable<number> {
    return this.http.get<number>(`${this.api}/bookings/average-duration`);
  }

  getAverageSessionLengthPerRoomType(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/session-length/room-type`);
  }

  getOccupancyRatePerRoomTypeForMonth(yearMonth: string): Observable<Record<string, number>> {
  const params = new HttpParams().set('yearMonth', yearMonth);
  return this.http.get<Record<string, number>>(`${this.api}/occupancy-rate/monthly`, { params });
}

  getRoomUtilizationRate(start: string, end: string): Observable<Record<string, number>> {
    const params = new HttpParams()
      .set('start', start)
      .set('end', end);
    return this.http.get<Record<string, number>>(`${this.api}/utilization-rate`, { params });
  }

  getHourlyBookingDistribution(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/peak-hours`);
  }

  getTopBookedRoomTypes(limit: number = 3): Observable<{ [roomType: string]: number }> {
  const params = new HttpParams().set('limit', limit.toString());
  return this.http.get<{ [roomType: string]: number }>(`${this.api}/top-room-types`, { params });
}

  // ===== USER ACTIVITY & ENGAGEMENT =====

  getMissionProgressStats(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/missions/progress`);
  }

  getLectureProgressStats(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/lectures/progress`);
  }

  getActiveUsersLastNDays(days: number): Observable<Record<string, number>> {
    const params = new HttpParams().set('days', days.toString());
    return this.http.get<Record<string, number>>(`${this.api}/users/active`, { params });
  }

  getBookingFrequencyByUserType(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/users/booking-frequency`);
  }

  countInactiveUsers(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.api}/users/inactive`);
  }

  countChurnedUsers(daysWithoutBooking: number): Observable<Record<string, number>> {
    const params = new HttpParams().set('daysWithoutBooking', daysWithoutBooking.toString());
    return this.http.get<Record<string, number>>(`${this.api}/users/churned`, { params });
  }

  getTopUsersByBookings(limit: number = 5): Observable<{ [username: string]: number }> {
  const params = new HttpParams().set('limit', limit.toString());
  return this.http.get<{ [username: string]: number }>(`${this.api}/users/top`, { params });
}

  // ===== ADMIN DASHBOARD ALERTS =====

  getUnderutilizedRoomTypes(thresholdPercentage: number): Observable<string[]> {
    const params = new HttpParams().set('thresholdPercentage', thresholdPercentage.toString());
    return this.http.get<string[]>(`${this.api}/alerts/underutilized-rooms`, { params });
  }
}