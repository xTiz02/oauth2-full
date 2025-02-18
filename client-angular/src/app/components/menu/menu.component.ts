import { Component, Signal, computed, inject } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { ThemeService } from '../../services/theme.service';
import {FormsModule,} from '@angular/forms';
import { TokenService } from '../../services/token.service';
import { environment } from '../../../environments/environment';
import { HttpParams } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-menu',
  imports: [ MatIconModule, MatButtonModule, MatToolbarModule, MatSlideToggleModule, FormsModule ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  readonly themeService = inject(ThemeService);
  tokenService = inject(TokenService);
  authService = inject(AuthService);
  authorize_uri = environment.authorize_uri;
  logout_url = environment.logout_url;
  isChecked:boolean = false;
  isLoggedIn: Signal<boolean> = this.authService.logged();

  params: any = {
    client_id: environment.client_id,
    redirect_uri: environment.redirect_uri,
    scope: environment.scope,
    response_type: environment.response_type,
    response_mode: environment.response_mode,
    //code_challenge_method: environment.code_challenge_method
  }

  constructor() {
    this.isChecked = this.themeService.currentTheme() === 'dark';
  }

  ngOnInit(){
    
  }
  toHome(){
    location.href = '/home';
  }

  onLogin(): void { 
    //const code_verifier = this.generateCodeVerifier();
    //this.tokenService.setVerifier(code_verifier);
    //this.params.code_challenge = this.generateCodeChallenge(code_verifier);
    const httpParams = new HttpParams({fromObject: this.params});
    const codeUrl = this.authorize_uri + httpParams.toString();
    location.href = codeUrl;
  }
  
  onLogout(): void {
    const httpParams = new HttpParams({fromObject: {post_logout_redirect_uri: environment.post_logout_redirect_uri}});
    location.href = this.logout_url + '?' + httpParams.toString();
    
    // this.authService.logout().subscribe(
    //   data => {
    //     console.log(data);
    //     this.tokenService.clear();
    //     this.isLoggedIn = false;
    //   },
    //   err => {
    //     console.log(err);
    //   }
    // );
  }

  // generateCodeVerifier(): string {
  //   let result = '';
  //   const char_length = CHARACTERS.length;
  //   for(let i =0; i < 44; i++) {
  //     result += CHARACTERS.charAt(Math.floor(Math.random() * char_length));
  //   }
  //   return result;
  // }

  // generateCodeChallenge(code_verifier: string): string {
  //   const codeverifierHash = CryptoJS.SHA256(code_verifier).toString(CryptoJS.enc.Base64);
  //   const code_challenge = codeverifierHash
  //   .replace(/=/g, '')
  //   .replace(/\+/g, '-')
  //   .replace(/\//g, '_');
  //   return code_challenge;
  // }
}
