import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable, single } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private httpClient:HttpClient = inject(HttpClient);
  #logged = signal<boolean>(false);
  logged = computed(() => this.#logged);
  token_url = environment.token_url;
  logout_url = environment.logout_url;
  
  constructor() { }

  isLogged(): boolean {
    return this.logged()();
  }


  toLogged(): void {
    this.#logged.set(true);
  }

  toLoggedOut(): void {
    this.#logged.set(false);
  }


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

  public getNewAccessToken(refreshToken : string): Observable<any> {
    let body = new URLSearchParams();
    //body.set('grant_type', 'refresh_token');
    //body.set('client_id', environment.client_id);
    body.set('refresh_token', refreshToken);
    body.set('grant_type', "refresh_token");
    const basic_auth = 'Basic '+ btoa('client:secret');
    const headers_object = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Accept': '*/*',
      'Authorization': basic_auth
    });
    const httpOptions = { headers: headers_object};
    return this.httpClient.post<any>(this.token_url, body, httpOptions);
  }

  public getUserInfo(accessToken: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' +  accessToken);
    return this.httpClient.get<any>(environment.user_info_url, {headers: headers});
  }

  // public logout(): Observable<any> {
  //   const httpParams = new HttpParams({fromObject: {post_logout_redirect_uri: environment.post_logout_redirect_uri}});
  //   location.href = this.logout_url + '?' + httpParams.toString();
  //   return this.httpClient.get<any>(this.logout_url + "?" + httpParams.toString());
  // }
}
