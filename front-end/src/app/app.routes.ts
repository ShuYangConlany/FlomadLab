import { Routes } from '@angular/router';
import { FlightOffersComponent } from './api/flight-offers/flight-offers.component';
import { AppComponent } from './app.component';


export const routes: Routes = [
    { path: '', component: FlightOffersComponent },
    { path: 'flight_offers', component: AppComponent } 
];
