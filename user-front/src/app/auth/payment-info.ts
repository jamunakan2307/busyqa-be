export class PaymentInfo {
  amountPaid: number;
  paymentNotes: string;

  constructor(amountPaid: number, paymentNotes: string) {
    this.amountPaid = amountPaid;
    this.paymentNotes = paymentNotes;

  }
}
