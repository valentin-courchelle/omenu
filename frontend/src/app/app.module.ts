import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { AppComponent } from './app.component';

// Import API générée
import { ApiModule } from './generated/api.module';
import { Configuration } from './generated/configuration';

// Fonction de configuration pour Swagger
export function apiConfigurationFactory(): Configuration {
  return new Configuration({
    basePath: 'http://localhost:8080/omenu/api'
  });
}

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    ApiModule.forRoot(apiConfigurationFactory)
  ],
  providers: [
    provideHttpClient(),
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
