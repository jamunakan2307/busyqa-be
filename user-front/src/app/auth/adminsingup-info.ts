export class AdminSignUpInfo {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  roles: string[];
  teams: string[];
  password: string;
  status: string;
  statusAsOfDay: string;

  constructor(firstName: string, lastName: string, username: string, email: string, password: string, roles: string[], teams: string[]) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = roles;
    this.teams = teams;
    this.status = 'YES';
    this.statusAsOfDay = '';
  }
}
