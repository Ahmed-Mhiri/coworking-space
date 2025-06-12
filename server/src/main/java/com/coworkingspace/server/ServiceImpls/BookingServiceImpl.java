package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.DTOs.BookingDTO;
import com.coworkingspace.server.DTOs.RoomDTO;
import com.coworkingspace.server.mappers.BookingMapper;
import com.coworkingspace.server.mappers.RoomMapper;
import com.coworkingspace.server.models.Booking;
import com.coworkingspace.server.models.Freelancer;
import com.coworkingspace.server.models.Intern;
import com.coworkingspace.server.models.Room;
import com.coworkingspace.server.repositories.BookingRepository;
import com.coworkingspace.server.repositories.FreelancerRepository;
import com.coworkingspace.server.repositories.InternRepository;
import com.coworkingspace.server.repositories.RoomRepository;
import com.coworkingspace.server.services.BookingService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final FreelancerRepository freelancerRepository;
    private final InternRepository internRepository;
    private final RoomMapper roomMapper;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(RoomRepository roomRepository, BookingRepository bookingRepository,
                              FreelancerRepository freelancerRepository, InternRepository internRepository,
                              RoomMapper roomMapper, BookingMapper bookingMapper) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.freelancerRepository = freelancerRepository;
        this.internRepository = internRepository;
        this.roomMapper = roomMapper;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingDTO createBooking(Long userId, boolean isFreelancer, BookingDTO dto) {
        Room room = roomRepository.findById(dto.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!checkRoomAvailability(room.getId(), dto.getDate(), dto.getStartTime(), dto.getEndTime())) {
            throw new IllegalStateException("Room is fully booked for the requested time.");
        }

        double price = calculatePrice(room, dto.getStartTime(), dto.getEndTime());

        Booking booking = bookingMapper.toEntity(dto);
        booking.setAmountPaid(price);
        booking.setRoom(room);
        booking.setPaid(true); // Assume payment is successful

        if (isFreelancer) {
            Freelancer freelancer = freelancerRepository.findById(userId).orElseThrow();
            booking.setFreelancer(freelancer);
        } else {
            Intern intern = internRepository.findById(userId).orElseThrow();
            booking.setIntern(intern);
        }

        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDTO(saved);
    }

    @Override
    public boolean checkRoomAvailability(Long roomId, LocalDate date, LocalTime from, LocalTime to) {
        List<Booking> bookings = bookingRepository.findByRoomIdAndDate(roomId, date);

        long count = bookings.stream().filter(b ->
                !(to.isBefore(b.getStartTime()) || from.isAfter(b.getEndTime()))
        ).count();

        Room room = roomRepository.findById(roomId).orElseThrow();
        return count < room.getSizeLimit();
    }

    @Override
    public double calculatePrice(Room room, LocalTime from, LocalTime to) {
        long hours = Duration.between(from, to).toHours();
        return room.getPricePerHour() * hours;
    }

    @Override
    public List<BookingDTO> getBookingsForUser(Long userId) {
        List<Booking> bookings = bookingRepository.findAllByFreelancerIdOrInternId(userId, userId);
        return bookings.stream().map(bookingMapper::toDTO).toList();
    }

    @Override
    public List<RoomDTO> getAvailableRooms(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Room> allRooms = roomRepository.findAll();

        List<Booking> conflictingBookings = bookingRepository.findBookingsByDateAndTimeRange(date, startTime, endTime);

        Set<Long> bookedRoomIds = conflictingBookings.stream()
                .map(b -> b.getRoom().getId())
                .collect(Collectors.toSet());

        return allRooms.stream()
                .filter(room -> !bookedRoomIds.contains(room.getId()))
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

}
