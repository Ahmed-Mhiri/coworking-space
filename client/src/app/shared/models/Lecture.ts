import { Intern } from './Intern';
import { ProgressStatus } from './ProgressStatus';

export interface Lecture {
  id: number;
  name: string;
  progress: ProgressStatus;
  files: string[]; // File paths or URLs
  intern: Intern;
  description: string;

}