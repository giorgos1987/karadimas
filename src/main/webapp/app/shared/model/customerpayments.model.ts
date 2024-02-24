import dayjs from 'dayjs';
import { ICustomerDetails } from 'app/shared/model/customer-details.model';
import { Paystatus } from 'app/shared/model/enumerations/paystatus.model';

export interface ICustomerpayments {
  id?: number;
  ispaid?: Paystatus | null;
  invoiceDate?: string | null;
  dueDate?: string | null;
  tax?: string | null;
  shipping?: string | null;
  amountDue?: number | null;
  notes?: string | null;
  attachmentsContentType?: string | null;
  attachments?: string | null;
  lastupdated?: string | null;
  customerDetails?: ICustomerDetails;
}

export const defaultValue: Readonly<ICustomerpayments> = {};
