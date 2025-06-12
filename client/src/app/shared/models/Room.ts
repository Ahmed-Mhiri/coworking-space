import { RoomType } from './RoomType';

export interface Room {
  id: number;
  type: RoomType;
  sizeLimit: number;
  pricePerHour: number;
}
