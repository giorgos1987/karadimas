import dayjs from 'dayjs';
import { ICart } from 'app/shared/model/cart.model';
import { ICustomerpayments } from 'app/shared/model/customerpayments.model';
import { Typeofcustomer } from 'app/shared/model/enumerations/typeofcustomer.model';

export interface ICustomerDetails {
  id?: number;
  name?: string | null;
  plates?: string | null;
  customertype?: Typeofcustomer | null;
  lastseen?: string | null;
  firstseen?: string | null;
  proselesysis?: string | null;
  mobile?: string | null;
  phone?: string | null;
  companyphone?: string | null;
  email?: string | null;
  notes?: string | null;
  addressLine1?: string | null;
  addressLine2?: string | null;
  city?: string | null;
  country?: string | null;
  carts?: ICart[] | null;
  customerpayments?: ICustomerpayments[] | null;
}

export const defaultValue: Readonly<ICustomerDetails> = {};
