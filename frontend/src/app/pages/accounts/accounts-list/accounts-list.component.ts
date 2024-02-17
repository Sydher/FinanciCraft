import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AccountDTO, AccountService, ApiModule, CategoryDTO, CategoryService } from '../../../apimodule';
import { HttpClientModule } from '@angular/common/http';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { BadgeComponent } from '../../../shared/components/badge/badge.component';

@Component({
  selector: 'app-accounts-list',
  standalone: true,
  imports: [
    HttpClientModule,
    ApiModule,
    CardModule,
    ButtonModule,
    BadgeComponent,
  ],
  templateUrl: './accounts-list.component.html',
  styleUrl: './accounts-list.component.scss'
})
export class AccountsListComponent implements OnInit {

  @Output() ouputOpenAccount = new EventEmitter<AccountDTO>();

  accounts: AccountDTO[];
  categories: Map<number, CategoryDTO>;

  constructor(private accountService: AccountService, private categoryService: CategoryService) {
    this.accounts = [];
    this.categories = new Map();
  }

  ngOnInit(): void {
    this.accountService.getAllAccounts().subscribe(responseGetAll => {
      if (responseGetAll.content) {
        this.accounts = responseGetAll.content;
      }

      this.accounts.forEach(account => {
        const accountId = account.id !== undefined ? account.id : 0;
        const categoryId = account.categoryId !== undefined ? account.categoryId : 0;

        // Get Account Category
        this.categoryService.findCategory(categoryId).subscribe(responseCat => {
          if (responseCat.content) {
            this.categories.set(categoryId, responseCat.content);
          }
        });

        // Get Account Balance
        this.accountService.getAccountBalance(accountId).subscribe(responseBalance => {
          if (responseBalance.content) {
            account.balance = responseBalance.content;
          }
        });
      });
    });
  }

  getCategoryIcon(account: AccountDTO): string {
    const categoryId = account.categoryId !== undefined ? account.categoryId : 0;
    const icon = this.categories.get(categoryId)?.icon;
    return icon !== undefined ? icon : "pi-thumbs-down-fill";
  }

  getCategoryName(account: AccountDTO): string {
    const categoryId = account.categoryId !== undefined ? account.categoryId : 0;
    const name = this.categories.get(categoryId)?.name;
    return name !== undefined ? name : "Inconnu";
  }

  getCategoryColor(account: AccountDTO): string {
    const categoryId = account.categoryId !== undefined ? account.categoryId : 0;
    const color = this.categories.get(categoryId)?.color;
    return color !== undefined ? color : "Inconnu";
  }

  openAccount(account: AccountDTO): void {
    this.ouputOpenAccount.emit(account);
  }

}
