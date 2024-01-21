import { Component, OnInit } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { TableModule } from 'primeng/table';
import { environment } from './../../../environments/environment';
import { ApiModule, BASE_PATH, CategoryDTO, CategoryService } from '../../apimodule';
import { HttpClientModule } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';

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
    TableModule
  ],
  providers: [{ provide: BASE_PATH, useValue: environment.API_BASE_PATH }, MessageService],
  templateUrl: './config.component.html',
  styleUrl: './config.component.scss'
})
export class ConfigComponent implements OnInit {

  formCreateCategory: FormGroup;
  categories: CategoryDTO[];

  dialogAddCategory: boolean;

  constructor(private categoryService: CategoryService, private formBuilder: FormBuilder) {
    this.formCreateCategory = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      icon: new FormControl('', [Validators.required]),
      color: new FormControl('', [Validators.required]),
    });
    this.categories = [];
    this.dialogAddCategory = false;
  }

  ngOnInit(): void {
    this.categoryService.getAll1().subscribe(response => {
      if (response.content) { this.categories = response.content; }
    });
  }

  addCategory(): void {
    const category: CategoryDTO = {
      name: this.formCreateCategory.get("name")?.value,
      icon: this.formCreateCategory.get("icon")?.value,
      color: this.formCreateCategory.get("color")?.value,
    };
    this.categoryService.create1(category).subscribe(response => console.log(response));
  }

  openNew(): void {
    this.dialogAddCategory = true;
  }

  closeNew(): void {
    this.dialogAddCategory = false;
  }

}