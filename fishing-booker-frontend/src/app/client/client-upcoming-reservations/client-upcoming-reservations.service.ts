import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientUpcomingReservationsService {

  constructor(private _http: HttpClient) { }

  public getReservations(): Observable<any[]> {
    return this._http.get<any[]>('http://localhost:8080/reservations/client-upcoming/' + localStorage.getItem('userId'))
    .pipe(
      tap(data => console.log("data: ", data))
    )
  }

  public cancelReservation(reservation: any) {
    return this._http.get('http://localhost:8080/reservations/cancel/' + reservation.id)
    .pipe(
      tap(data => console.log("data: ", data))
    )
  }
}
