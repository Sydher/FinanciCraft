import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ChartModule } from 'primeng/chart';
import { AccountDTO, AccountService, ApiModule, BASE_PATH, CategoryDTO, StatisticsService } from '../../apimodule';
import { CardModule } from 'primeng/card';
import { environment } from '../../../environments/environment';
import { DatePipe, registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { PrimeNGConfig } from 'primeng/api';
import { AutoCompleteCompleteEvent, AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';

registerLocaleData(localeFr);

@Component({
  selector: 'app-stats',
  standalone: true,
  imports: [
    HttpClientModule,
    ApiModule,
    CardModule,
    ChartModule,
    ReactiveFormsModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputTextModule,
    CalendarModule,
    AutoCompleteModule,
    ButtonModule,
  ],
  providers: [{ provide: BASE_PATH, useValue: environment.API_BASE_PATH }],
  templateUrl: './stats.component.html',
  styleUrl: './stats.component.scss'
})
export class StatsComponent implements OnInit {

  // Handle date format
  datePipe: DatePipe;

  // Stats datas
  data: any;
  options: any;

  // Stats lib components
  documentStyle: CSSStyleDeclaration;
  textColor: string;

  // Search form
  formSearch: FormGroup;
  allAccounts: AccountDTO[];
  filteredAccounts: AccountDTO[];

  constructor(private readonly statisticsService: StatisticsService, private readonly accountService: AccountService, private formBuilder: FormBuilder, private config: PrimeNGConfig) {
    this.datePipe = new DatePipe('fr-FR');
    this.documentStyle = getComputedStyle(document.documentElement);
    this.textColor = this.documentStyle.getPropertyValue('--text-color');

    this.options = {
      plugins: {
        legend: {
          labels: {
            usePointStyle: true,
            color: this.textColor
          }
        }
      }
    };

    this.config.setTranslation({
      "dayNames": ["Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"],
      "dayNamesShort": ["Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"],
      "dayNamesMin": ["Di", "Lu", "Mar", "Mer", "Je", "Ve", "Sa"],
      "monthNames": ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"],
      "monthNamesShort": ["Jan", "Fev", "Mar", "Avr", "Mai", "Jun", "Jui", "Août", "Sept", "Oct", "Nov", "Dec"],
      "firstDayOfWeek": 1,
    });

    this.allAccounts = [];
    this.filteredAccounts = [];

    this.formSearch = this.initForm();
  }

  ngOnInit() {
    this.formSearch = this.initForm();

    this.accountService.getAllAccounts().subscribe(response => {
      if (response.content) {
        this.allAccounts = response.content;
      }
    });
  }

  filterAccounts(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.allAccounts as any[]).length; i++) {
      let country = (this.allAccounts as any[])[i];
      if (country.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(country);
      }
    }

    this.filteredAccounts = filtered;
  }

  calcul(): void {
    this.statisticsService.getStatsByMainCategory(this.formSearch.get("accountId")?.value.id, this.toStr(this.formSearch.get("dateMin")?.value), this.toStr(this.formSearch.get("dateMax")?.value)).subscribe(response => {
      if (response.content) {
        const stats = response.content.stats;
        const categories = stats.map(stat => stat.category);
        const keys = categories.map(category => category.name);
        const values = stats.map(stat => stat.amount);
        const colors = categories.map(category => category.color);

        this.data = {
          labels: keys,
          datasets: [
            {
              data: values,
              backgroundColor: colors
            }
          ]
        };
      }
    });
  }

  private initForm(): FormGroup {
    return this.formBuilder.group({
      accountId: new FormControl(null, [Validators.required]),
      dateMin: new FormControl(null, [Validators.required]),
      dateMax: new FormControl(null, [Validators.required]),
    });
  }

  private toStr(date: Date): string {
    const d = this.datePipe.transform(date, 'yyyy-MM-dd');
    return d ? d : "";
  }

}
