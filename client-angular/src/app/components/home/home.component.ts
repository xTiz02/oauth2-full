import { Component, inject } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatSnackBar} from '@angular/material/snack-bar';
//RouterLink is used to navigate to different routes
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TokenService } from '../../services/token.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-home',
  imports: [ MatButtonModule ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  tokenService = inject(TokenService);
  authService = inject(AuthService);
  private _snackBar = inject(MatSnackBar);
  btnDisabled: boolean = true;
  constructor(private router: Router,private activatedRoute: ActivatedRoute) { }



  ngOnInit(): void {
    if(this.tokenService.getAccessToken()){
      this.authService.toLogged();
      this.btnDisabled = false;
    }else{
      this.activatedRoute.queryParams.subscribe( data => {
        const code = data['code'];
        if(!code){
          return;
        }
        console.log(code);
        //const code_verifier = this.tokenService.getVerifier();
        //this.tokenService.deleteVerifier();
        this.getToken(code);
      });
    }
  } 

  getToken(code: string): void {
    this.authService.getToken(code).subscribe(
      data => {
        console.log(data);
        this.tokenService.setTokens(data.access_token, data.refresh_token, data.id_token);
        this.authService.toLogged();
        this.btnDisabled = false;
      },
      err => {
        console.log(err);
      }
    );
  }

  redirectUser(action: string): void {
    switch(action){
      case 'user':
        if(this.tokenService.isUser()){
          this.router.navigate(['/user']);
        }else{
          this.openSnackBar();
        }
        break;
      case 'admin':
        if(this.tokenService.isAdmin()){
          this.router.navigate(['/admin']);
          return;
        }else{
          this.openSnackBar();
        }
        break;
      default:
        this.openSnackBar("No tienes permisos para acceder a ninguna página");
        break;
    }
     
  }

  openSnackBar(message: string = 'No tienes permisos para acceder a esta página'): void {
    this._snackBar.open(message, 'Cerrar', {
      duration: 2000,
    });
  }
}
