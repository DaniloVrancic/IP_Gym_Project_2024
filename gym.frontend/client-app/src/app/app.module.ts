import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { UserService } from "./user.service";
import { HttpClientModule } from "@angular/common/http";
import { appConfig } from "./app.config";

@NgModule({
    declarations: [
       
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
    ],
    providers: [UserService],
    bootstrap: []
})
export class AppModule {}