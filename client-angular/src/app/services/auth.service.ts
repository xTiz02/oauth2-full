import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private httpClient:HttpClient = inject(HttpClient);

  token_url = environment.token_url;

  constructor() { }


  public getToken(code: string): Observable<any> {
    let body = new URLSearchParams();
    body.set('grant_type', environment.grant_type);
    body.set('client_id', environment.client_id);
    body.set('redirect_uri', environment.redirect_uri);
    //body.set('scope', environment.scope);
    //body.set('code_verifier',code_verifier);
    body.set('code', code);
    const basic_auth = 'Basic '+ btoa('client:secret');
    const headers_object = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Accept': '*/*',
      'Authorization': basic_auth
    });
    const httpOptions = { headers: headers_object};
    return this.httpClient.post<any>(this.token_url, body, httpOptions);
  }
}
