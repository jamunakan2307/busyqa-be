import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { MultiDataSet, Label } from 'ng2-charts';

@Component({
  selector: 'app-report-view',
  templateUrl: './report-view.component.html',
  styleUrls: ['./report-view.component.css']
})
export class ReportViewComponent implements OnInit {

  isLoaded = false;
  doughnutChartData: MultiDataSet;
  doughnutChartLabels: Label[];
  doughnutChartType: ChartType;

  barChartLabels: Label[] = [];
  barChartData: ChartDataSets[] = [];
  reterivedbarChartData: ChartDataSets[] = [];

  public barChartOptions: ChartOptions = {
    responsive: true,
  };
  public barChartType: ChartType = 'bar';
  public barChartLegend = false;
  public barChartPlugins = [];

  //Second Chart 

  barChartLabels2: Label[] = [];
  barChartData2: ChartDataSets[] = [];
  reterivedbarChartData2: ChartDataSets[] = [];

  public barChartOptions2: ChartOptions = {
    responsive: true,
  };
  public barChartType2: ChartType = 'bar';
  public barChartLegend2 = false;
  public barChartPlugins2 = [];

  leadsCount: number;
  studentCount:number;
  internCount:number;
  resumeCount:number;
  mockCount:number;
  aluminiCount:number;
  constructor(private router: Router, private userService: UserService) {
  }
  ngOnInit() {

      this.userService.getAllCount()
      .subscribe(data => {
        if (data.status === 200) {
          this.doughnutChartData = [
            [data.result.leadsCount, data.result.studentCount, data.result.internCount, data.result.resumeCount,data.result.mockCount,data.result.aluminiCount ],
          ];
          this.leadsCount = data.result.leadsCount;
          this.aluminiCount = data.result.aluminiCount;
          this.studentCount = data.result.studentCount;
          this.internCount = data.result.internCount;
          this.resumeCount = data.result.resumeCount;
          this.mockCount = data.result.mockCount;

          this.doughnutChartLabels = ['Leads', 'Student', 'Intern', 'Resume', 'Mock', 'Alumini'];
          this.doughnutChartType = 'doughnut';
        } else {
          alert(data.message);
        }
      });

      this.userService.getByCourseCount()
      .subscribe(data2 => {
        if (data2.status === 200) { 
          for(let i = 0; i< data2.result.length; i++ ){
            this.barChartLabels = ['Count'];
            this.reterivedbarChartData = [
              { data : [data2.result[i].couseAluminitCount], label:'(Alumini) ' + data2.result[i].courseName },
              { data : [data2.result[i].couseInternCount], label:'(Intern) '+ data2.result[i].courseName},
              { data : [data2.result[i].couseLeadsCount], label:'(Leads) '+ data2.result[i].courseName},
              { data : [data2.result[i].couseMockCount], label:'(Mock) '+ data2.result[i].courseName},
              { data : [data2.result[i].couseResumeCount], label:'(Resume) '+ data2.result[i].courseName},
              { data : [data2.result[i].couseStudentCount], label:'(Student) '+ data2.result[i].courseName}        
            ];

            this.barChartData =  this.barChartData.concat(this.reterivedbarChartData);

          }
          this.isLoaded = true;
        } else {
          alert(data2.message);
        }
      });


      this.userService.getBySeasonCount()
      .subscribe(data3 => {
        if (data3.status === 200) { 
          for(let i = 0; i< data3.result.length; i++ ){
            this.barChartLabels2 = ['Count'];

            this.reterivedbarChartData2 = [
              { data : [data3.result[i].couseAluminitCount], label:'(Alumini) ' + data3.result[i].courseName},
              { data : [data3.result[i].couseInternCount], label:'(Intern) '+ data3.result[i].courseName},
              { data : [data3.result[i].couseLeadsCount], label:'(Leads) '+ data3.result[i].courseName},
              { data : [data3.result[i].couseMockCount], label:'(Mock )'+ data3.result[i].courseName},
              { data : [data3.result[i].couseResumeCount], label:'(Resume) '+ data3.result[i].courseName},
              { data : [data3.result[i].couseStudentCount], label:'(Student) '+ data3.result[i].courseName}        
            ];
            this.barChartData2 =  this.barChartData2.concat(this.reterivedbarChartData2);
          }
          this.isLoaded = true;
        } else {
          alert(data3.message);
        }
      });

  }
}
