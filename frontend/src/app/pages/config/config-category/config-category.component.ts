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
import { ApiModule, BASE_PATH, CategoryDTO, CategoryService } from '../../../apimodule';
import { HttpClientModule } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AbstractCrudComponent } from '../../../shared/abstract/abstract-crud/abstract-crud.component';

@Component({
  selector: 'app-config-category',
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
    CardModule
  ],
  providers: [
    { provide: BASE_PATH, useValue: environment.API_BASE_PATH },
    MessageService,
    ConfirmationService
  ],
  templateUrl: './config-category.component.html',
  styleUrl: './config-category.component.scss'
})
export class ConfigCategoryComponent extends AbstractCrudComponent<CategoryDTO> implements OnInit {

  constructor(protected override formBuilder: FormBuilder,
    protected override confirmationService: ConfirmationService,
    protected override messageService: MessageService,
    private categoryService: CategoryService) {

    super(formBuilder, confirmationService, messageService);
  }

  ngOnInit(): void {
    this.refreshItemsList();
  }

  protected override initForm(item?: CategoryDTO): FormGroup {
    return this.formBuilder.group({
      id: new FormControl(item?.id, []),
      name: new FormControl(item?.name, [Validators.required]),
      icon: new FormControl(item?.icon, [Validators.required]),
      color: new FormControl(item?.color, [Validators.required]),
    });
  }

  protected override refreshItemsList(): void {
    this.categoryService.getAllCategories().subscribe(response => {
      if (response.content) { this.items = response.content; }
    });
  }

  protected override addItem(): void {
    const category: CategoryDTO = {
      name: this.formCrud.get("name")?.value,
      icon: this.formCrud.get("icon")?.value,
      color: this.formCrud.get("color")?.value,
    };
    this.categoryService.createCategory(category).subscribe(_ => {
      this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Catégorie créée', life: 3000 });
      this.closeModal();
    });
  }

  protected override editItem(): void {
    const category: CategoryDTO = {
      id: this.formCrud.get("id")?.value,
      name: this.formCrud.get("name")?.value,
      icon: this.formCrud.get("icon")?.value,
      color: this.formCrud.get("color")?.value,
    };
    this.categoryService.updateCategory(category, `${category.id}`).subscribe(_ => {
      this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Catégorie modifiée', life: 3000 });
      this.closeModal();
    });
  }

  protected override deleteItem(item: CategoryDTO) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûr de vouloir supprimer la catégorie ?',
      header: 'Attention ! Suppression d\'une catégorie',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (item.id) this.categoryService.deleteCategory(item.id).subscribe(response => console.debug(response));
        this.items = this.items.filter((val) => val.id !== item.id);
        this.messageService.add({ severity: 'info', summary: 'OK', detail: 'Catégorie supprimée', life: 3000 });
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
          if (item.id) this.categoryService.deleteCategory(item.id).subscribe(response => console.debug(response));
        });
        this.items = this.items.filter((val) => !this.selectedItems?.includes(val));
        this.selectedItems = null;
        this.messageService.add({ severity: 'info', summary: 'OK', detail: 'Sélection supprimée', life: 3000 });
      }
    });
  }

}