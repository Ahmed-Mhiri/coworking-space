import { User } from './User';
import { Mission } from './Mission';
import { Booking } from './Booking';

export interface Freelancer extends User {
  role: 'FREELANCER';
  missions: Mission[];
  bookings: Booking[];
}