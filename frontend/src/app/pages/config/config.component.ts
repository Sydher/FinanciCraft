import { Component } from '@angular/core';
import { ConfigCategoryComponent } from './config-category/config-category.component';
import { ConfigAccountComponent } from './config-account/config-account.component';

@Component({
  selector: 'app-config',
  standalone: true,
  imports: [
    ConfigCategoryComponent,
    ConfigAccountComponent
  ],
  templateUrl: './config.component.html',
  styleUrl: './config.component.scss'
})
export class ConfigComponent {

  // Empty

}
