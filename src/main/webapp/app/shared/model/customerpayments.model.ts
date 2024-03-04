import dayjs from 'dayjs';
import { ICustomers } from 'app/shared/model/customers.model';
import { Paystatus } from 'app/shared/model/enumerations/paystatus.model';

export interface ICustomerpayments {
  id?: number;
  totalAmount?: string | null;
  remainder?: string | null;
  downPayment?: string | null;
  ispaid?: Paystatus | null;
  invoiceDate?: string | null;
  dueDate?: string | null;
  notes?: string | null;
  attachmentsContentType?: string | null;
  attachments?: string | null;
  lastupdated?: string | null;
  customers?: ICustomers;
}

export const defaultValue: Readonly<ICustomerpayments> = {};
