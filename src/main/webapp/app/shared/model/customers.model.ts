import dayjs from 'dayjs';
import { ICart } from 'app/shared/model/cart.model';
import { ICustomerpayments } from 'app/shared/model/customerpayments.model';
import { Typeofcustomer } from 'app/shared/model/enumerations/typeofcustomer.model';

export interface ICustomers {
  id?: number;
  name?: string | null;
  car?: string | null;
  notes?: string | null;
  phone?: string | null;
  tyres?: string | null;
  plates?: string | null;
  customertype?: Typeofcustomer | null;
  lastseen?: string | null;
  firstseen?: string | null;
  proselesysis?: string | null;
  mobile?: string | null;
  companyphone?: string | null;
  email?: string | null;
  addressLine?: string | null;
  cityCountry?: string | null;
  carts?: ICart[] | null;
  customerpayments?: ICustomerpayments[] | null;
}

export const defaultValue: Readonly<ICustomers> = {};
