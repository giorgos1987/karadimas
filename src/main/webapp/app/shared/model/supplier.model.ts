import { ISupplierpayments } from 'app/shared/model/supplierpayments.model';

export interface ISupplier {
  id?: number;
  company?: string | null;
  webPage?: string | null;
  notes?: string | null;
  lastName?: string | null;
  firstName?: string | null;
  emailAddress?: string | null;
  jobTitle?: string | null;
  businessPhone?: string | null;
  homePhone?: string | null;
  mobilePhone?: string | null;
  faxNumber?: string | null;
  address?: string | null;
  city?: string | null;
  stateProvince?: string | null;
  zipPostalCode?: string | null;
  countryRegion?: string | null;
  attachmentsContentType?: string | null;
  attachments?: string | null;
  supplierpayments?: ISupplierpayments[] | null;
}

export const defaultValue: Readonly<ISupplier> = {};
