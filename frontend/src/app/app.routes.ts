import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AccountsComponent } from './pages/accounts/accounts.component';
import { ConfigComponent } from './pages/config/config.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'accounts', component: AccountsComponent },
    { path: 'config', component: ConfigComponent },
];
