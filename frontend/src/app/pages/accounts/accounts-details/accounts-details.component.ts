import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AccountDTO, AccountService, ApiModule, BASE_PATH, CategoryDTO, CategoryService, TransactionDTO, TransactionService } from '../../../apimodule';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { DialogModule } from 'primeng/dialog';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { HttpClientModule } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { Pageable } from '../../../apimodule/model/pageable';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AbstractCrudComponent } from '../../../shared/abstract/abstract-crud/abstract-crud.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CalendarModule } from 'primeng/calendar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-accounts-details',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    ApiModule,
    ReactiveFormsModule,
    ToastModule,
    ToolbarModule,
    ButtonModule,
    DialogModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputTextModule,
    TableModule,
    ConfirmDialogModule,
    CardModule,
    CalendarModule,
  ],
  providers: [
    { provide: BASE_PATH, useValue: environment.API_BASE_PATH },
    MessageService,
    ConfirmationService
  ],
  templateUrl: './accounts-details.component.html',
  styleUrl: './accounts-details.component.scss'
})
export class AccountsDetailsComponent extends AbstractCrudComponent<TransactionDTO> implements OnInit {

  accountId: number;

  constructor(protected override formBuilder: FormBuilder,
    protected override confirmationService: ConfirmationService,
    protected override messageService: MessageService,
    private accountService: AccountService,
    private categoryService: CategoryService,
    private transactionService: TransactionService) {

    super(formBuilder, confirmationService, messageService);
    this.accountId = 1;//TODO 0
  }

  ngOnInit(): void {
    this.refreshItemsList();
  }

  protected override initForm(item?: TransactionDTO): FormGroup {
    const theDate = item?.date ? new Date(item.date) : null;
    return this.formBuilder.group({
      id: new FormControl(item?.id, []),
      name: new FormControl(item?.name, [Validators.required]),
      amount: new FormControl(item?.amount, [Validators.required]),
      date: new FormControl(theDate, [Validators.required]),
    });
  }

  protected override refreshItemsList(): void {
    const pageable: Pageable = {};
    this.transactionService.findAllTransactionByAccount(this.accountId, pageable).subscribe(response => {
      if (response.content && response.content.content) { this.items = response.content.content; }
    });
  }

  protected override addItem(): void {
    const transaction: TransactionDTO = {
      name: this.formCrud.get("name")?.value,
      amount: this.formCrud.get("amount")?.value,
      date: this.formCrud.get("date")?.value,
      accountId: this.accountId,
    };
    this.transactionService.createTransaction(transaction).subscribe(_ => {
      this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Transaction ajoutée', life: 3000 });
      this.closeModal();
    });
  }

  protected override editItem(): void {
    const transaction: TransactionDTO = {
      id: this.formCrud.get("id")?.value,
      name: this.formCrud.get("name")?.value,
      amount: this.formCrud.get("amount")?.value,
      date: this.formCrud.get("date")?.value,
      accountId: this.accountId,
    };
    this.transactionService.updateTransaction(transaction, `${transaction.id}`).subscribe(_ => {
      this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Transaction modifiée', life: 3000 });
      this.closeModal();
    });
  }

  protected override deleteItem(item: TransactionDTO) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûr de vouloir supprimer la transaction ?',
      header: 'Attention ! Suppression d\'un transaction',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (item.id) this.transactionService.deleteTransaction(item.id).subscribe(response => console.debug(response));
        this.items = this.items.filter((val) => val.id !== item.id);
        this.messageService.add({ severity: 'info', summary: 'OK', detail: 'Transaction supprimé', life: 3000 });
      }
    });
  }

  protected override deleteSelectedItems(): void {
    this.confirmationService.confirm({
      message: 'Etes-vous sûr de vouloir supprimer la sélection ?',
      header: 'Attention ! Suppression en masse',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedItems?.forEach(item => {
          if (item.id) this.transactionService.deleteTransaction(item.id).subscribe(response => console.debug(response));
        });
        this.items = this.items.filter((val) => !this.selectedItems?.includes(val));
        this.selectedItems = null;
        this.messageService.add({ severity: 'info', summary: 'OK', detail: 'Sélection supprimée', life: 3000 });
      }
    });
  }

}
