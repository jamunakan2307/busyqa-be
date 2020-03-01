export class BatchInfo {
  courseName: string;
  trainerName: string;
  startDate: string;
  endDate: string;
  courseLocation: string;
  batchDetails: string;
  taxPercentage: number;
  courseAmount: number;
  netCourseAmount: number;
  courseLenght: number;
  batchStatus: string;
  trainerEmail: string;
  registrationFees:number;

  constructor(courseName: string, trainerName: string, startDate: string, endDate: string, courseLocation: string, batchDetails: string, taxPercentage: number, courseAmount: number, netCourseAmount: number, courseLenght: number, batchStatus: string, trainerEmail: string, registrationFees: number) {
    this.courseName = courseName;
    this.trainerName = trainerName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.courseLocation = courseLocation;
    this.batchDetails = batchDetails;
    this.taxPercentage = taxPercentage;
    this.courseAmount = courseAmount;
    this.netCourseAmount = netCourseAmount;
    this.courseLenght = courseLenght;
    this.batchStatus = batchStatus;
    this.trainerEmail = trainerEmail;
    this.registrationFees = registrationFees;

  }
}
