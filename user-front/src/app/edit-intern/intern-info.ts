export class InternInfo {
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  projectAssignment: string;
  coopStarDate: string;
  coopEndDate: string;
  clientStatus: string;

  constructor(firstName: string, lastName: string, email: string, phoneNumber: string, projectAssignment: string, coopStarDate: string, coopEndDate: string, clientStatus: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.projectAssignment = projectAssignment;
    this.coopStarDate = coopStarDate;
    this.coopEndDate = coopEndDate;
    this.clientStatus = clientStatus;

  }
}
