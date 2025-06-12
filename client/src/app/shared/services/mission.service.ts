import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Mission } from "../models/Mission";

@Injectable({ providedIn: 'root' })
export class MissionService {
  private api = `${environment.apiBaseUrl}/missions`;

  constructor(private http: HttpClient) {}

  createMission(mission: Partial<Mission>) {
    return this.http.post<Mission>(this.api, mission, { withCredentials: true });
  }

  getMissions() {
    return this.http.get<Mission[]>(this.api, { withCredentials: true });
  }

  updateMission(missionId: number, mission: Partial<Mission>) {
    return this.http.put<Mission>(`${this.api}/${missionId}`, mission, { withCredentials: true });
  }

  deleteMission(missionId: number) {
    return this.http.delete<void>(`${this.api}/${missionId}`, { withCredentials: true });
  }
}

/*private missions: Mission[] = [];

  getMissions(): Mission[] {
    return this.missions;
  }

  addMission(mission: Mission): void {
    this.missions.unshift(mission);
  }*/