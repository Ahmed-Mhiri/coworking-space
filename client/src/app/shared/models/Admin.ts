import { User } from './User';

export interface Admin extends User {
  role: 'ADMIN';
  // Add admin-specific fields if needed
}