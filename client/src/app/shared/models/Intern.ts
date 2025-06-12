import { User } from './User';
import { Lecture } from './Lecture';
import { Booking } from './Booking';

export interface Intern extends User {
  role: 'INTERN';
  lectures: Lecture[];
  bookings: Booking[];
}