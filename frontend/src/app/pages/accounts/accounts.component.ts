import { Component, OnInit } from '@angular/core';
import { AccountsListComponent } from './accounts-list/accounts-list.component';
import { ButtonModule } from 'primeng/button';
import { AccountsDetailsComponent } from './accounts-details/accounts-details.component';
import { AccountDTO } from '../../apimodule';

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

  accountToShow!: AccountDTO;
  accountIdToShow: number;

  constructor() {
    this.accountIdToShow = 0;
  }

  ngOnInit(): void {
    //
  }

  receiveOpenAccount(account: AccountDTO) {
    this.accountToShow = account;
    this.accountIdToShow = account.id ? account.id : 0;
  }

  back(): void {
    this.accountIdToShow = 0;
  }

}
