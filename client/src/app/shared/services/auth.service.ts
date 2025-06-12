import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { BehaviorSubject, catchError, Observable, of, switchMap, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiBaseUrl + '/auth';

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  signUp(signUpData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, signUpData);
  }

  signIn(credentials: { email: string; password: string }): Observable<User | null> {
    return this.http.post(`${this.apiUrl}/signin`, credentials, { withCredentials: true }).pipe(
      switchMap(() => {
        // Instead of setTimeout, return loadUser() observable so we wait for user data
        return this.loadUser();
      })
    );
  }

  signOut(): Observable<any> {
    return this.http.post(`${this.apiUrl}/signout`, {}, { withCredentials: true })
      .pipe(
        tap(() => {
          this.currentUserSubject.next(null);
        })
      );
  }

  loadUser(): Observable<User | null> {
    return this.http.get<User>(`${this.apiUrl.replace('/auth', '/user')}/me`, { withCredentials: true }).pipe(
      tap(user => this.currentUserSubject.next(user)),
      catchError(() => {
        this.currentUserSubject.next(null);
        return of(null);
      })
    );
  }

  isAuthenticated(): boolean {
    return !!this.currentUserSubject.value;
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/forgot-password`, { email });
  }

  resetPassword(token: string, newPassword: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/reset-password`, { token, newPassword });
  }
}
