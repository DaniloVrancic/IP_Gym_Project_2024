import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { UserService } from "./user.service";
import { HttpClientModule } from "@angular/common/http";
import { appConfig } from "./app.config";
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
    declarations: [
       
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        MatButtonModule,
        MatIconModule
        ],
    providers: [UserService],
    bootstrap: []
})
export class AppModule {}