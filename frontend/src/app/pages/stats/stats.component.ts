import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ChartModule } from 'primeng/chart';
import { ApiModule, BASE_PATH } from '../../apimodule';
import { CardModule } from 'primeng/card';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-stats',
  standalone: true,
  imports: [
    HttpClientModule,
    ApiModule,
    CardModule,
    ChartModule,
  ],
  providers: [{ provide: BASE_PATH, useValue: environment.API_BASE_PATH }],
  templateUrl: './stats.component.html',
  styleUrl: './stats.component.scss'
})
export class StatsComponent implements OnInit {

  data: any;
  options: any;

  ngOnInit() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');

    this.data = {
      labels: ['A', 'B', 'C'],
      datasets: [
        {
          data: [540, 325, 702],
          backgroundColor: [documentStyle.getPropertyValue('--blue-500'), documentStyle.getPropertyValue('--yellow-500'), documentStyle.getPropertyValue('--green-500')],
          hoverBackgroundColor: [documentStyle.getPropertyValue('--blue-400'), documentStyle.getPropertyValue('--yellow-400'), documentStyle.getPropertyValue('--green-400')]
        }
      ]
    };

    this.options = {
      plugins: {
        legend: {
          labels: {
            usePointStyle: true,
            color: textColor
          }
        }
      }
    };
  }

}
