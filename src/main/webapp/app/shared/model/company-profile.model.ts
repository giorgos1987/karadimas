export interface ICompanyProfile {
  id?: number;
  companyName?: string | null;
  address1?: string | null;
  address2?: string | null;
  suburb?: string | null;
  state?: string | null;
  postcode?: string | null;
  phone?: string | null;
  mobile?: string | null;
  email?: string | null;
  emailInfo?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  language?: string | null;
}

export const defaultValue: Readonly<ICompanyProfile> = {};
