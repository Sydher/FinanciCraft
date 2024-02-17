import { Component, OnInit } from '@angular/core';
import { AccountService, TransactionService } from '../../../apimodule';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CONFIG_SHORTCUT_ACCOUNT_ID, CONFIG_SHORTCUT_TRANSACTIONS } from '../../../shared/utils/appconst';

@Component({
  selector: 'app-config-shortcut',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputTextModule,
    CardModule,
    ButtonModule,
    ToastModule,
  ],
  providers: [
    MessageService,
  ],
  templateUrl: './config-shortcut.component.html',
  styleUrl: './config-shortcut.component.scss'
})
export class ConfigShortcutComponent implements OnInit {

  formShortcut: FormGroup;

  constructor(private formBuilder: FormBuilder, private messageService: MessageService, private accountService: AccountService, private transactionService: TransactionService) {
    this.formShortcut = this.initForm();
  }

  ngOnInit(): void {
    const item = localStorage.getItem(CONFIG_SHORTCUT_ACCOUNT_ID);
    if (item !== null) {
      const accountId = this.getAccountId();
      this.formShortcut = this.initForm(accountId);
      this.reloadShortcuts();
    }
  }

  initForm(accountId?: number): FormGroup {
    return this.formBuilder.group({
      accountId: new FormControl(accountId, [Validators.required]),
    });
  }

  reloadShortcuts(): void {
    this.transactionService.findAllTransactionByAccount(this.getAccountId(), 0, 100, []).subscribe(response => {
      const r = response.content;
      if (r && r.content) {
        localStorage.setItem(CONFIG_SHORTCUT_TRANSACTIONS, JSON.stringify(r.content));
      }
    });
  }

  getAccountId(): number {
    const item = localStorage.getItem(CONFIG_SHORTCUT_ACCOUNT_ID);
    return item !== null ? Number(item) : 0;
  }

  save(): void {
    localStorage.setItem(CONFIG_SHORTCUT_ACCOUNT_ID, this.formShortcut.get("accountId")?.value);
    this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Configuration des raccourcis sauvegardée', life: 3000 });
    this.reloadShortcuts();
  }

  consult(): void {
    this.messageService.add({ severity: 'info', summary: 'Config', detail: CONFIG_SHORTCUT_ACCOUNT_ID + " : " + localStorage.getItem(CONFIG_SHORTCUT_ACCOUNT_ID), life: 5000 });
    this.messageService.add({ severity: 'info', summary: 'Config', detail: CONFIG_SHORTCUT_TRANSACTIONS + " : " + localStorage.getItem(CONFIG_SHORTCUT_TRANSACTIONS), life: 5000 });
  }

}
