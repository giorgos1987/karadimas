import dayjs from 'dayjs';
import { ISupplier } from 'app/shared/model/supplier.model';
import { Paystatus } from 'app/shared/model/enumerations/paystatus.model';

export interface ISupplierpayments {
  id?: number;
  invoiceDate?: string | null;
  dueDate?: string | null;
  ispaid?: Paystatus | null;
  amountDue?: number | null;
  notes?: string | null;
  attachmentsContentType?: string | null;
  attachments?: string | null;
  tax?: string | null;
  shipping?: string | null;
  lastupdated?: string | null;
  supplier?: ISupplier;
}

export const defaultValue: Readonly<ISupplierpayments> = {};
