import { Component, inject } from '@angular/core';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.scss'
})
export class LogoutComponent {
  tokenService = inject(TokenService);

  ngOnInit(){
    this.tokenService.clear();
  }
}
