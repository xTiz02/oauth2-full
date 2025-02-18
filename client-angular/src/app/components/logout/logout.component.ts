import { Component, inject } from '@angular/core';
import { TokenService } from '../../services/token.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.scss'
})
export class LogoutComponent {
  tokenService = inject(TokenService);
  authServ = inject(AuthService);
  ngOnInit(){
    this.tokenService.clear();
    this.authServ.toLoggedOut();
  }
}
