export class ClientUpInfo {
  roles: string;
  teams: string;
  password: string;

  firstName: string;
  lastName: string;
  username: string;
  email: string;
  leadSource: string;
  phoneNumber: string;
  trainingLocation: string;
  studentComments: string;
  clientCourse: string;
  status: string;
  statusAsOfDay: string;

  constructor(firstName: string, lastName: string, username: string, email: string, leadSource: string,
              phoneNumber: string, trainingLocation: string, clientCourse: string, studentComments: string) {
    this.firstName = firstName;
    this.lastName = lastName;

    this.username = username;
    this.email = email;
    this.password = "password12";
    this.roles = 'ROLE_CLIENT';
    this.teams = 'TEAM_CLIENT';
    this.status = 'NO';
    this.statusAsOfDay = '';
    this.clientCourse = clientCourse;

  }
}
