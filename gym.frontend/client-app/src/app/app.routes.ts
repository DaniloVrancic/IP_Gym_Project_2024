import { Routes } from '@angular/router';
import { RegisterFormComponent } from './register.form/register/register.form.component';
import { LoginFormComponent } from './register.form/login.form/login.form.component';
import { StartPageComponent } from './start-page/start-page/start-page.component';

export const routes: Routes = [
    { path: 'start-page', component: StartPageComponent},
    { path: 'register-form', component: RegisterFormComponent},
    { path: 'login-form', component: LoginFormComponent},
    { path: '',   redirectTo: '/start-page', pathMatch: 'full' }, // redirect to `start-page`
];
