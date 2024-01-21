import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { environment } from './../../../environments/environment';
import { AccountService, ApiModule, BASE_PATH } from '../../apimodule';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [HttpClientModule, ApiModule, TableModule],
  providers: [{ provide: BASE_PATH, useValue: environment.API_BASE_PATH }],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.scss'
})
export class AccountsComponent implements OnInit {

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAll2().subscribe(data => console.log(data));
  }
  
}
