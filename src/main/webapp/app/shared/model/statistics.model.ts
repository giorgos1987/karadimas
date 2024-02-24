export interface IStatistics {
  id?: number;
  todaysales?: number | null;
  totalCustomersNumb?: number | null;
  custmerTotal?: number | null;
  schedTotalNexWeek?: number | null;
  totalCarts?: number | null;
  totalPending?: number | null;
  totalPayments?: number | null;
  pendingPayments?: number | null;
  pendingNumberPayments?: number | null;
  totalCars?: number | null;
  totalTrucks?: number | null;
  totalOther1?: number | null;
  totalOther2?: number | null;
  latestpayments?: string | null;
  ltstsupplierpaym?: string | null;
  recentrlycompltd?: string | null;
}

export const defaultValue: Readonly<IStatistics> = {};
