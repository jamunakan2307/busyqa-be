export class CourseInfo {
  courseLenght: number;
  courseName: string;
  courseAmount: number;
  status: string;

  constructor(courseLenght: number, courseName: string, courseAmount: number, status: string) {
    this.courseLenght = courseLenght;
    this.courseName = courseName;
    this.courseAmount = courseAmount;
    this.status = status;

  }
}
