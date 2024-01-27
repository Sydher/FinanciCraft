import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-shared-badge',
  standalone: true,
  imports: [],
  templateUrl: './badge.component.html',
  styleUrl: './badge.component.scss'
})
export class BadgeComponent {

  @Input() icon: string;
  @Input() text: string;
  @Input() color: string;

  constructor() {
    this.icon = "";
    this.text = "";
    this.color = "";
  }

}
