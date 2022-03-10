import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CottageDetailOwnerService {

  constructor(private _http: HttpClient) { }

  getCottage(id: number): Observable<any> {
    return this._http.get<any>('http://localhost:8080/cottages/' + id)
    .pipe(
      tap(data => console.log("data: ", data))
    )
  }

  addFreePeriod(freePeriod: any): Observable<any> {
    return this._http.post<Observable<any>>('http://localhost:8080/timeRanges', freePeriod);
  }

  addAdditionalService(additionalService: any): Observable<any> {
    return this._http.post<Observable<any>>('http://localhost:8080/additionalServices', additionalService);
  }

  addAction(action: any): Observable<any> {
    return this._http.post<Observable<any>>('http://localhost:8080/actions', action);
  }
}
