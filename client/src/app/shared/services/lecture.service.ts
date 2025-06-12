import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
import { Lecture } from "../models/Lecture";

@Injectable({
  providedIn: 'root'  // <- this makes it globally available
})

export class LectureService {
  private api = `${environment.apiBaseUrl}/lectures`;

  constructor(private http: HttpClient) {}

  createLecture(lecture: Partial<Lecture>): Observable<Lecture> {
    return this.http.post<Lecture>(`${this.api}`, lecture, { withCredentials: true });
  }

  getLectures(): Observable<Lecture[]> {
    return this.http.get<Lecture[]>(`${this.api}`, { withCredentials: true });
  }

  updateLecture(lectureId: number, lecture: Partial<Lecture>): Observable<Lecture> {
    return this.http.put<Lecture>(`${this.api}/${lectureId}`, lecture, { withCredentials: true });
  }

  deleteLecture(lectureId: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${lectureId}`, { withCredentials: true });
  }

  uploadFiles(lectureId: number, files: File[]): Observable<void> {
  const formData = new FormData();
  files.forEach(file => formData.append('files', file)); // ✅ Key is 'files'
  return this.http.post<void>(`${this.api}/${lectureId}/files`, formData, {
    withCredentials: true
  });
}

getLectureFileUrl(lectureId: number, filename: string): string {
  return `${environment.apiBaseUrl}/files/lectures/${lectureId}/${encodeURIComponent(filename)}`;
}
}