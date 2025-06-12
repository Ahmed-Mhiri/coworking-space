import { Room } from './Room';
import { Freelancer } from './Freelancer';
import { Intern } from './Intern';

export interface Booking {
  id: number;
  date: string; // ISO date string
  startTime: string; // e.g., "09:00:00"
  endTime: string;
  room: Room;
  freelancer?: Freelancer | null;
  intern?: Intern | null;
  amountPaid: number;
  paid: boolean;
}