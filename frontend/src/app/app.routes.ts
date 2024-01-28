import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AccountsComponent } from './pages/accounts/accounts.component';
import { ConfigComponent } from './pages/config/config.component';
import { StatsComponent } from './pages/stats/stats.component';
import { BudgetsComponent } from './pages/budgets/budgets.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'accounts', component: AccountsComponent },
    { path: 'budgets', component: BudgetsComponent },
    { path: 'stats', component: StatsComponent },
    { path: 'config', component: ConfigComponent },
];
