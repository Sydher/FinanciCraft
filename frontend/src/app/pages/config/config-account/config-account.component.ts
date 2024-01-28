import { Component, OnInit } from '@angular/core';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { CardModule } from 'primeng/card';
import { environment } from '../../../../environments/environment';
import { AccountDTO, AccountService, ApiModule, BASE_PATH, CategoryDTO, CategoryService } from '../../../apimodule';
import { HttpClientModule } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AbstractCrudComponent } from '../../../shared/abstract/abstract-crud/abstract-crud.component';
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import { BadgeComponent } from '../../../shared/components/badge/badge.component';

@Component({
  selector: 'app-config-account',
  standalone: true,
  imports: [
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
    AutoCompleteModule,
    BadgeComponent,
  ],
  providers: [
    { provide: BASE_PATH, useValue: environment.API_BASE_PATH },
    MessageService,
    ConfirmationService
  ],
  templateUrl: './config-account.component.html',
  styleUrl: './config-account.component.scss'
})
export class ConfigAccountComponent extends AbstractCrudComponent<AccountDTO> implements OnInit {

  allCategories: CategoryDTO[];
  filteredCategories: any[];

  constructor(protected override formBuilder: FormBuilder,
    protected override confirmationService: ConfirmationService,
    protected override messageService: MessageService,
    private accountService: AccountService,
    private categoryService: CategoryService) {

    super(formBuilder, confirmationService, messageService);
    this.allCategories = [];
    this.filteredCategories = [];
  }

  ngOnInit(): void {
    this.refreshItemsList();

    this.categoryService.getAllCategories().subscribe(response => {
      if (response.content) this.allCategories = response.content;
    });
  }

  protected override initForm(item?: AccountDTO): FormGroup {
    return this.formBuilder.group({
      id: new FormControl(item?.id, []),
      name: new FormControl(item?.name, [Validators.required]),
      categoryId: new FormControl(item?.categoryId, [Validators.required]),
    });
  }

  protected override refreshItemsList(): void {
    this.accountService.getAllAccounts().subscribe(response => {
      if (response.content) { this.items = response.content; }
    });
  }

  protected override addItem(): void {
    const account: AccountDTO = {
      name: this.formCrud.get("name")?.value,
      balance: 0,
      categoryId: this.formCrud.get("categoryId")?.value.id,
    };
    this.accountService.createAccount(account).subscribe(_ => {
      this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Compte créé', life: 3000 });
      this.closeModal();
    });
  }

  protected override editItem(): void {
    const account: AccountDTO = {
      id: this.formCrud.get("id")?.value,
      name: this.formCrud.get("name")?.value,
      balance: 0,
      categoryId: this.formCrud.get("categoryId")?.value.id,
    };
    this.accountService.updateAccount(account, `${account.id}`).subscribe(_ => {
      this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Compte modifié', life: 3000 });
      this.closeModal();
    });
  }

  protected override deleteItem(item: AccountDTO) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûr de vouloir supprimer le compte ?',
      header: 'Attention ! Suppression d\'un compte',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (item.id) this.accountService.deleteAccount(item.id).subscribe(response => console.debug(response));
        this.items = this.items.filter((val) => val.id !== item.id);
        this.messageService.add({ severity: 'info', summary: 'OK', detail: 'Compte supprimé', life: 3000 });
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
          if (item.id) this.accountService.deleteAccount(item.id).subscribe(response => console.debug(response));
        });
        this.items = this.items.filter((val) => !this.selectedItems?.includes(val));
        this.selectedItems = null;
        this.messageService.add({ severity: 'info', summary: 'OK', detail: 'Sélection supprimée', life: 3000 });
      }
    });
  }

  filterCategory(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.allCategories as any[]).length; i++) {
      let country = (this.allCategories as any[])[i];
      if (country.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(country);
      }
    }

    this.filteredCategories = filtered;
  }

  getCategoryById(id: number): CategoryDTO {
    const cat = this.allCategories.find(c => c.id === id);
    return cat ? cat : { name: "", icon: "", color: "" };
  }

}