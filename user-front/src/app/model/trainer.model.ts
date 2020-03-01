export class TrainerInfo {
  trainerName: string;
  trainerEmail: string;
  contactNumber: number;
  trainerMe: string;
  status: string;

  constructor(trainerName: string, trainerEmail: string, contactNumber: number, trainerMe: string, status: string) {
    this.trainerName = trainerName;
    this.trainerEmail = trainerEmail;
    this.contactNumber = contactNumber;
    this.trainerMe = trainerMe;
    this.status = status;

  }
}
