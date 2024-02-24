import dayjs from 'dayjs';

export interface IBooking {
  id?: number;
  eventdate?: string | null;
  mobile?: string | null;
  brand?: string | null;
  model?: string | null;
  estimatedhours?: number | null;
  note?: string | null;
}

export const defaultValue: Readonly<IBooking> = {};
