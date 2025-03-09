import { enableProdMode } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';

import { environment } from './environments/environment';
import { provideHttpClient } from '@angular/common/http';

// Import API générée
import { ApiModule } from './app/generated/api.module';
import { Configuration } from './app/generated/configuration';

// Fonction de configuration pour Swagger
export function apiConfigurationFactory(): Configuration {
  return new Configuration({
    basePath: 'http://localhost:8080/omenu/api'
  });
}

if (environment.production) {
  enableProdMode();
}

// 🛠 Assurer que providers n'est jamais undefined
const apiProviders = ApiModule.forRoot(apiConfigurationFactory).providers ?? [];

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes), 
    provideHttpClient(),   
    ...apiProviders        
  ],
}).catch((err) => console.error(err));
