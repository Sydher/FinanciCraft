import { Component, OnInit } from '@angular/core';
import { AccountsListComponent } from './accounts-list/accounts-list.component';
import { ButtonModule } from 'primeng/button';
import { AccountsDetailsComponent } from './accounts-details/accounts-details.component';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [
    AccountsListComponent,
    AccountsDetailsComponent,
    ButtonModule,
  ],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.scss'
})
export class AccountsComponent implements OnInit {

  accountIdToShow: number;

  constructor() {
    this.accountIdToShow = 1; // TODO 0 par défaut
  }

  ngOnInit(): void {
    //
  }

  receiveOpenAccount(accountId: number) {
    this.accountIdToShow = accountId;
  }

  back(): void {
    this.accountIdToShow = 0;
  }

}
