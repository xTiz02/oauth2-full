import { Injectable } from '@angular/core';


const ACCESS_TOKEN = 'access_token';
const REFRESH_TOKEN = 'refresh_token';
const CODE_VERIFIER = 'code_verifier';


@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }


  setTokens(access_token: string, refresh_token: string): void {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.setItem(ACCESS_TOKEN, access_token);
    localStorage.removeItem(REFRESH_TOKEN);
    localStorage.setItem(REFRESH_TOKEN, refresh_token);
  }

  getAccessToken(): string | null {
    return localStorage.getItem(ACCESS_TOKEN);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(REFRESH_TOKEN);
  }
  clear(): void {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
  }

  isLogged(): boolean {
    return localStorage.getItem(ACCESS_TOKEN) != null;
  }

  getRoles(): string[] {
    if(!this.isLogged()) {
      return [];
    }
    const token = this.getAccessToken();
    const payload = token!.split(".")[1];//token: asdasdasd(header).asdasdads(payload).asdasdas(firma)
    const payloadDecoded = atob(payload);
    const values = JSON.parse(payloadDecoded);
    const roles = values.permissions;
    console.log(roles);
    return roles;
  }

  isAdmin(): boolean {
    return this.getRoles().find(role => role === 'ROLE_ADMIN') !== undefined;
  }

  isUser(): boolean {
    return this.getRoles().find(role => role === 'ROLE_USER') !== undefined;
  }

  // setVerifier(code_verifier: string): void {
  //   if(localStorage.getItem(CODE_VERIFIER)) {
  //     this.deleteVerifier();
  //   }
  //   const encrypted = CryptoJS.AES.encrypt(code_verifier, environment.secret_pkce);
  //   localStorage.setItem(CODE_VERIFIER, encrypted.toString());
  // }

  // getVerifier(): string {
  //   const encrypted = localStorage.getItem(CODE_VERIFIER);
  //   const decrypted = CryptoJS.AES.decrypt(encrypted!, environment.secret_pkce).toString(CryptoJS.enc.Utf8);
  //   return decrypted;
  // }
  
  // deleteVerifier(): void {
  //   localStorage.removeItem(CODE_VERIFIER);
  // }
}
