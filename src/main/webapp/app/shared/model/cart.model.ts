import dayjs from 'dayjs';
import { IJob } from 'app/shared/model/job.model';
import { ICustomerDetails } from 'app/shared/model/customer-details.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

export interface ICart {
  id?: number;
  name?: string | null;
  plate?: string | null;
  placeddate?: string | null;
  scheduleddate?: string | null;
  deliverydate?: string | null;
  brand?: string | null;
  model?: string | null;
  numbertyres?: number | null;
  status?: OrderStatus;
  notes?: string | null;
  mechanic?: string | null;
  phone?: string | null;
  address?: string | null;
  workorder?: number | null;
  kilometers?: string | null;
  paymentMethod?: string | null;
  paymentReference?: string | null;
  totalPrice?: number | null;
  jobs?: IJob[] | null;
  customerDetails?: ICustomerDetails;
}

export const defaultValue: Readonly<ICart> = {};
