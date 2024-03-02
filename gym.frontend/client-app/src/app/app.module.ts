import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { UserService } from "./user.service";
import { HttpClientModule } from "@angular/common/http";
import { appConfig } from "./app.config";
import { MaterialModule } from "./material/material.module";

@NgModule({
    declarations: [
       
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        MaterialModule
        ],
    providers: [UserService],
    bootstrap: []
})
export class AppModule {}