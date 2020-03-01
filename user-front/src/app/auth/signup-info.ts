export class SignUpInfo {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  phoneNumber: string;
  studentComments: string;
  leadSource: string;
  status: string;
  batchId: number;

  constructor(firstName: string, lastName: string, username: string, email: string, password: string, phoneNumber: string, studentComments: string, leadSource: string, status: string, batchId: number) {
    this.password = "password12";
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.leadSource = leadSource;
    this.phoneNumber = phoneNumber;
    this.studentComments = studentComments;
    this.status = "NO";
    this.batchId = batchId;


  }
}
