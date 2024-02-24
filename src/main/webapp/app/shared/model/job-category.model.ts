import { IJob } from 'app/shared/model/job.model';

export interface IJobCategory {
  id?: number;
  name?: string;
  description?: string | null;
  jobs?: IJob[] | null;
}

export const defaultValue: Readonly<IJobCategory> = {};
