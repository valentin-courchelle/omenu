import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { AppComponent } from './app.component';

// Importer le module et la configuration générés
import { ApiModule } from './generated/api.module';
import {Configuration} from './generated/configuration'

// Fonction de configuration
export function apiConfigurationFactory(): Configuration {
  return new Configuration({
    basePath: 'http://localhost:8080/omenu/api'
  });
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    ApiModule.forRoot(apiConfigurationFactory) // Passez la configuration via forRoot
  ],
  providers: [
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
