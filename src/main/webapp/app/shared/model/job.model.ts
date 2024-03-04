import { ICart } from 'app/shared/model/cart.model';

export interface IJob {
  id?: number;
  name?: string;
  description?: string | null;
  note?: string | null;
  price?: number | null;
  priority?: number | null;
  imageContentType?: string | null;
  image?: string | null;
  carts?: ICart[] | null;
}

export const defaultValue: Readonly<IJob> = {};
