import { Freelancer } from './Freelancer';
import { ProgressStatus } from './ProgressStatus';

export interface Mission {
  id: number;
  name: string;
  deadline: string; // Or use `Date` if you're parsing it
  progress: ProgressStatus;
  freelancer: Freelancer;
  description: string;
  //createdAt?: Date; // Optional, if you want to track when the mission was created
  // updatedAt?: Date; // Optional, if you want to track when the mission was last updated
}