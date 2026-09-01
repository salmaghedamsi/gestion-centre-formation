export interface PaymentSummary {
  paymentType: 'MONTHLY' | 'PER_SESSION';
  pricePerUnit: number;
  totalAmountPaid: number;
  unitsPaid: number;
  unitsConsumed: number;
  balanceUnits: number;
}