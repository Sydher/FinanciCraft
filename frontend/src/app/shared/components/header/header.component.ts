import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { MenubarModule } from 'primeng/menubar';

@Component({
  selector: 'app-shared-header',
  standalone: true,
  imports: [MenubarModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {

  items: MenuItem[] | undefined;

  ngOnInit() {
    this.items = [
      {
        label: 'Accueil',
        icon: 'pi pi-home',
        routerLink: '/'
      },
      {
        label: 'Comptes',
        icon: 'pi pi-money-bill',
        routerLink: '/accounts'
      },
      {
        label: 'Budgets',
        icon: 'pi pi-chart-pie',
        routerLink: '/budgets'
      },
      {
        label: 'Statistiques',
        icon: 'pi pi-chart-line',
        routerLink: '/stats'
      },
      {
        label: 'Config',
        icon: 'pi pi-cog',
        routerLink: '/config'
      }
    ];
  }

}