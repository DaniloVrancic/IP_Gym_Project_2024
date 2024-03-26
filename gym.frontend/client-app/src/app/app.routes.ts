import { Routes } from '@angular/router';
import { RegisterFormComponent } from './register.form/register/register.form.component';
import { LoginFormComponent } from './register.form/login.form/login.form.component';
import { StartPageComponent } from './start-page/start-page/start-page.component';
import { EditProfileComponent } from './home/main.page/edit.profile/edit.profile/edit.profile.component';
import { MainPageComponent } from './home/main.page/main.page.component';
import { PurchasePageComponent } from './home/purchase.page/purchase.page.component';

export const routes: Routes = [
    { path: 'start-page', component: StartPageComponent},
    { path: 'register-form', component: RegisterFormComponent},
    { path: 'login-form', component: LoginFormComponent},
    { path: '',   redirectTo: '/start-page', pathMatch: 'full' }, // redirect to `start-page`
    { path: 'settings', component: EditProfileComponent},
    { path: 'main-page', component: MainPageComponent},
    { path: 'purchase-page', component: PurchasePageComponent}
];
