import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { LogoutComponent } from './components/logout/logout.component';

export const routes: Routes = [
    {path: '', component: HomeComponent },
    {path: 'logout', component: LogoutComponent },
    {path: 'home', component: HomeComponent },
    { path: 'user', component: UserComponent },
    { path: 'admin', component: AdminComponent },
    //{ path: 'authorized', component: AuthorizedComponent },
    //{path: 'logout', component: LogoutComponent},
    { path: '**', redirectTo: '', pathMatch: 'full' }
];
