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
import { environment } from './../../../environments/environment';
import { ApiModule, BASE_PATH, CategoryDTO, CategoryService } from '../../apimodule';
import { HttpClientModule } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-config',
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
    ConfirmDialogModule
  ],
  providers: [
    { provide: BASE_PATH, useValue: environment.API_BASE_PATH },
    MessageService,
    ConfirmationService
  ],
  templateUrl: './config.component.html',
  styleUrl: './config.component.scss'
})
export class ConfigComponent implements OnInit {

  formCategory: FormGroup;
  categories: CategoryDTO[];
  selectedCategories!: CategoryDTO[] | null;

  showAddDialog: boolean;
  showEditDialog: boolean;

  constructor(private categoryService: CategoryService, private formBuilder: FormBuilder, private confirmationService: ConfirmationService, private messageService: MessageService) {
    this.formCategory = this.initForm();
    this.categories = [];
    this.showAddDialog = false;
    this.showEditDialog = false;
  }

  ngOnInit(): void {
    this.refreshGetCategories();
  }

  private initForm(cat?: CategoryDTO): FormGroup {
    return this.formBuilder.group({
      id: new FormControl(cat?.id, []),
      name: new FormControl(cat?.name, [Validators.required]),
      icon: new FormControl(cat?.icon, [Validators.required]),
      color: new FormControl(cat?.color, [Validators.required]),
    });
  }

  private refreshGetCategories(): void {
    this.categoryService.getAll1().subscribe(response => {
      if (response.content) { this.categories = response.content; }
    });
  }

  addCategory(): void {
    const category: CategoryDTO = {
      name: this.formCategory.get("name")?.value,
      icon: this.formCategory.get("icon")?.value,
      color: this.formCategory.get("color")?.value,
    };
    this.categoryService.create1(category).subscribe(response => {
      console.debug(response);
      this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Catégorie créée', life: 3000 });
      this.closeModal();
    });
  }

  openNew(): void {
    this.formCategory = this.initForm();
    this.showAddDialog = true;
  }

  closeModal(): void {
    this.refreshGetCategories();
    this.showAddDialog = false;
    this.showEditDialog = false;
  }

  deleteSelectedCategories(): void {
    console.debug("test")
    this.confirmationService.confirm({
      message: 'Etes-vous sûr de vouloir supprimer la sélection ?',
      header: 'Attention ! Suppression en masse',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedCategories?.forEach(category => {
          if (category.id) this.categoryService.delete1(category.id).subscribe(response => console.debug(response));
        });
        this.categories = this.categories.filter((val) => !this.selectedCategories?.includes(val));
        this.selectedCategories = null;
        this.messageService.add({ severity: 'info', summary: 'OK', detail: 'Sélection supprimée', life: 3000 });
      }
    });
  }

  deleteCategory(category: CategoryDTO) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûr de vouloir supprimer la catégorie ?',
      header: 'Attention ! Suppression d\'une catégorie',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (category.id) this.categoryService.delete1(category.id).subscribe(response => console.debug(response));
        this.categories = this.categories.filter((val) => val.id !== category.id);
        //this.product = {};
        this.messageService.add({ severity: 'info', summary: 'OK', detail: 'Catégorie supprimée', life: 3000 });
      }
    });
  }

  editCategory(): void {
    const category: CategoryDTO = {
      id: this.formCategory.get("id")?.value,
      name: this.formCategory.get("name")?.value,
      icon: this.formCategory.get("icon")?.value,
      color: this.formCategory.get("color")?.value,
    };
    this.categoryService.update1(category, `${category.id}`).subscribe(response => {
      console.debug(response);
      this.messageService.add({ severity: 'success', summary: 'OK', detail: 'Catégorie modifiée', life: 3000 });
      this.closeModal();
    });
  }

  openEdit(category: CategoryDTO): void {
    this.formCategory = this.initForm(category);
    this.showEditDialog = true;
  }

}