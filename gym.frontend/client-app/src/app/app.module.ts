import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { UserService } from "./user.service";
import { HttpClientModule } from "@angular/common/http";
import { appConfig } from "./app.config";
import { MaterialModule } from "./material/material.module";
import { RecaptchaFormsModule, RecaptchaModule } from "ng-recaptcha";



@NgModule({
    declarations: [
       
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        MaterialModule,
        RecaptchaModule,
        RecaptchaFormsModule
        ],
    providers: [UserService],
    bootstrap: []
})
export class AppModule {}