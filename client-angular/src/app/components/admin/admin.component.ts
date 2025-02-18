import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-admin',
  imports: [],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {
  auth = inject(AuthService);
  tokenService = inject(TokenService);
  userInfo: string = "";
  message: string = "Información del usuario";

  ngOnInit(): void {
    const token = this.tokenService.getAccessToken();
    if(token){
      this.auth.getUserInfo(token).subscribe(
        (data) => {
          this.userInfo = JSON.stringify(data);
        }
        ,(err) => {
          console.log(err);
          this.message = "Error al obtener la información del usuario";
        }
      );
    }else{
      this.message = "No hay token de acceso";
    }
  }


  refreshToken(): void {
    const token = this.tokenService.getRefreshToken();
    if(token){
      this.auth.getNewAccessToken(token).subscribe(
        (data) => {
          console.log(data);
          this.tokenService.clear();
          this.tokenService.setTokens(data.access_token, data.refresh_token, data.id_token);
          this.message = "Token de acceso actualizado";
        }
        ,(err) => {
          console.log(err);
          //redirigir al home
          this.tokenService.clear();
          this.auth.toLoggedOut();
          location.href = '/home';
          this.message = "Error al actualizar el token de acceso";
        }
      );
    }
  }

}
