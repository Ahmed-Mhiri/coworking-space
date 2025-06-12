import { Component, ViewChild, ElementRef, AfterViewInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { forkJoin } from 'rxjs';
import { Chart, registerables, ChartOptions, ChartType } from 'chart.js';

// Previous Imports
import { DashboardService } from '../../shared/services/dashboard.service';
import { AdminNavbarComponent } from "../../shared/components/admin-navbar/admin-navbar.component";

// --- NG-ZORRO IMPORTS ---
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzStatisticModule } from 'ng-zorro-antd/statistic';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { FormsModule } from '@angular/forms';


Chart.register(...registerables);

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    AdminNavbarComponent,
    FormsModule,
    NzGridModule,
    NzStatisticModule,
    NzIconModule,
    NzDatePickerModule,
    NzButtonModule
  ],
  templateUrl: './admin-dashboard.component.html',
})
export class AdminDashboardComponent implements AfterViewInit {
  // @ViewChild declarations to get the <canvas> elements
  @ViewChild('monthlyRevenueTrendChart') monthlyRevenueTrendChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('revenueByUserTypeChart') revenueByUserTypeChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('dailyBookingsTrendChart') dailyBookingsTrendChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('topBookedRoomTypesChart') topBookedRoomTypesChartRef!: ElementRef<HTMLCanvasElement>;

  // --- FIX 1: Add properties to store the actual Chart.js instances ---
  private monthlyRevenueTrendChart?: Chart;
  private revenueByUserTypeChart?: Chart;
  private dailyBookingsTrendChart?: Chart;
  private topBookedRoomTypesChart?: Chart;

  dateRange: Date[] = [];
  private themedChartColors = ['#a0d911', '#5b8c00', '#f4ffb8', '#8c8c8c', '#595959', '#bfbfbf'];

  constructor(
    private dashboardService: DashboardService,
    @Inject(PLATFORM_ID) private platformId: Object,
  ) { }

  ngAfterViewInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.loadAllMetrics();
    }
  }

  onDateRangeChange(range: Date[]): void {
    console.log('New date range selected:', range);
    // You would re-fetch your data here and call loadAllMetrics() again
  }

  private loadAllMetrics() {
    forkJoin({
      monthlyRevenueTrend: this.dashboardService.getMonthlyRevenueTrend(),
      revenueByUserType: this.dashboardService.getRevenueByUserType(),
      dailyBookingsTrend: this.dashboardService.getDailyBookingsTrend(30),
      topBookedRoomTypes: this.dashboardService.getTopBookedRoomTypes(),
    }).subscribe(results => {
      this.initMonthlyRevenueTrendChart(results.monthlyRevenueTrend);
      this.initRevenueByUserTypeChart(results.revenueByUserType);
      this.initDailyBookingsTrendChart(results.dailyBookingsTrend);
      this.initTopBookedRoomTypesChart(results.topBookedRoomTypes);
    });
  }

  private createChart(
    existingChart: Chart | undefined,
    chartRef: ElementRef<HTMLCanvasElement>,
    type: ChartType,
    data: any,
    options?: ChartOptions
  ): Chart | undefined {
    if (!chartRef) return undefined;
    const ctx = chartRef.nativeElement.getContext('2d');
    if (!ctx) return undefined;
    
    // Best practice: Destroy the old chart before creating a new one to prevent memory leaks
    existingChart?.destroy();

    const defaultOptions: ChartOptions = { responsive: true, maintainAspectRatio: false, /* ... */ };
    return new Chart(ctx, { type, data, options: { ...defaultOptions, ...options } });
  }

  // --- FIX 2: Store the returned chart instance in the class properties ---
  private initMonthlyRevenueTrendChart(data: Record<string, number>) {
    this.monthlyRevenueTrendChart = this.createChart(this.monthlyRevenueTrendChart, this.monthlyRevenueTrendChartRef, 'line', {
      labels: Object.keys(data),
      datasets: [{ label: 'Revenu Mensuel', data: Object.values(data), borderColor: this.themedChartColors[0], backgroundColor: 'rgba(160, 217, 17, 0.1)', fill: true, tension: 0.4, pointBackgroundColor: this.themedChartColors[0] }]
    });
  }

  private initRevenueByUserTypeChart(data: Record<string, number>) {
    this.revenueByUserTypeChart = this.createChart(this.revenueByUserTypeChart, this.revenueByUserTypeChartRef, 'doughnut', {
      labels: Object.keys(data),
      datasets: [{ data: Object.values(data), backgroundColor: this.themedChartColors }]
    }, { plugins: { legend: { position: 'top' } } });
  }

  private initDailyBookingsTrendChart(data: Record<string, number>) {
    this.dailyBookingsTrendChart = this.createChart(this.dailyBookingsTrendChart, this.dailyBookingsTrendChartRef, 'bar', {
      labels: Object.keys(data),
      datasets: [{ label: 'Réservations', data: Object.values(data), backgroundColor: this.themedChartColors[1], borderRadius: 4 }]
    });
  }
  
  private initTopBookedRoomTypesChart(data: { [roomType: string]: number }) {
    this.topBookedRoomTypesChart = this.createChart(this.topBookedRoomTypesChart, this.topBookedRoomTypesChartRef, 'bar', {
      labels: Object.keys(data),
      datasets: [{ label: 'Nombre de réservations', data: Object.values(data), backgroundColor: this.themedChartColors, borderRadius: 4 }]
    }, { indexAxis: 'y' });
  }

  // --- FIX 3: The corrected export function ---
  public exportChartAsImage() {
  // CHECKPOINT A: This message tells us the button click is working.
  console.log('Checkpoint A: exportChartAsImage() function was called.');

  const chart = this.monthlyRevenueTrendChart;

  // CHECKPOINT B: This checks if the chart object actually exists.
  if (!chart) {
    console.error('ERROR: The chart object (this.monthlyRevenueTrendChart) is undefined. This is the most likely problem. It means the chart did not initialize correctly.');
    alert('Debug Info: The chart object was not found! The chart may not be visible on the page.');
    return;
  }
  console.log('Checkpoint B: Chart object found successfully.', chart);

  try {
    const imageBase64 = chart.toBase64Image('image/png');

    // CHECKPOINT C: This confirms the image data was created from the chart.
    if (!imageBase64 || imageBase64.length < 100) {
      console.error('ERROR: The function toBase64Image() did not return valid data.');
      alert('Debug Info: Failed to generate image data from the chart.');
      return;
    }
    console.log('Checkpoint C: Base64 image data created successfully.');

    const downloadLink = document.createElement('a');
    downloadLink.href = imageBase64;
    downloadLink.download = 'monthly-revenue-trend.png';

    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);

    // CHECKPOINT D: If you see this, the download should have started.
    console.log('Checkpoint D: Download was triggered.');

  } catch (e) {
    console.error('An unexpected error occurred during the export process:', e);
    alert('An unexpected error occurred. Check the developer console for details.');
  }
}
}