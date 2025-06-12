package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.models.*;
import com.coworkingspace.server.repositories.*;
import com.coworkingspace.server.services.AdminStatsService;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class AdminStatsServiceImpl implements AdminStatsService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final FreelancerRepository freelancerRepository;
    private final InternRepository internRepository;

    public AdminStatsServiceImpl(
            BookingRepository bookingRepository,
            RoomRepository roomRepository,
            FreelancerRepository freelancerRepository,
            InternRepository internRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.freelancerRepository = freelancerRepository;
        this.internRepository = internRepository;
    }

    // ===== ROOM & USER METRICS =====

    @Override
    public Map<RoomType, Long> countPeoplePerRoomType() {
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getRoom().getType(),
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> countUserTypesInRoom(Long roomId) {
        Map<String, Long> result = new HashMap<>();
        result.put("freelancer", bookingRepository.countFreelancersByRoomId(roomId));
        result.put("intern", bookingRepository.countInternsByRoomId(roomId));
        return result;
    }

    @Override
    public Map<RoomType, Long> countRoomsByType() {
        return roomRepository.findAll().stream()
                .collect(Collectors.groupingBy(Room::getType, Collectors.counting()));
    }

    // ===== FINANCIAL METRICS =====

    @Override
    public double getTotalRevenueOnDate(LocalDate date) {
        return bookingRepository.findByDate(date).stream()
                .mapToDouble(Booking::getAmountPaid).sum();
    }

    @Override
    public double getTotalRevenueBetween(LocalDate start, LocalDate end) {
        return bookingRepository.findByDateBetween(start, end).stream()
                .mapToDouble(Booking::getAmountPaid).sum();
    }

    @Override
    public Map<YearMonth, Double> getMonthlyRevenueTrend() {
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> YearMonth.from(b.getDate()),
                        Collectors.summingDouble(Booking::getAmountPaid)
                ));
    }

    @Override
    public Map<RoomType, Double> getRevenuePerRoomType() {
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getRoom().getType(),
                        Collectors.summingDouble(Booking::getAmountPaid)
                ));
    }

    @Override
    public Map<LocalDate, Double> getDailyRevenueTrend(int days) {
        LocalDate threshold = LocalDate.now().minusDays(days);
        return bookingRepository.findAll().stream()
                .filter(b -> !b.getDate().isBefore(threshold))
                .collect(Collectors.groupingBy(
                        Booking::getDate,
                        Collectors.summingDouble(Booking::getAmountPaid)
                ));
    }

    @Override
    public Map<Role, Double> getRevenueByUserType() {
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getFreelancer() != null ? Role.FREELANCER : Role.INTERN,
                        Collectors.summingDouble(Booking::getAmountPaid)
                ));
    }

    // ===== BOOKING & USAGE PATTERNS =====

    @Override
    public long countBookingsOnDate(LocalDate date) {
        return bookingRepository.countByDate(date);
    }

    @Override
    public Map<LocalDate, Long> getDailyBookingsTrend(int days) {
        LocalDate threshold = LocalDate.now().minusDays(days);
        return bookingRepository.findAll().stream()
                .filter(b -> !b.getDate().isBefore(threshold))
                .collect(Collectors.groupingBy(
                        Booking::getDate,
                        Collectors.counting()
                ));
    }

    @Override
    public double getAverageBookingDurationInHours() {
        return bookingRepository.findAll().stream()
                .mapToDouble(b -> Duration.between(b.getStartTime(), b.getEndTime()).toMinutes() / 60.0)
                .average().orElse(0.0);
    }

    @Override
    public Map<RoomType, Double> getAverageSessionLengthPerRoomType() {
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getRoom().getType(),
                        Collectors.averagingDouble(b -> Duration.between(b.getStartTime(), b.getEndTime()).toMinutes() / 60.0)
                ));
    }

    @Override
    public Map<RoomType, Double> getOccupancyRatePerRoomTypeForMonth(YearMonth month) {
        Map<RoomType, List<Room>> roomsByType = roomRepository.findAll().stream()
                .collect(Collectors.groupingBy(Room::getType));

        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();
        LocalDateTime monthStart = startDate.atStartOfDay();
        LocalDateTime monthEnd = endDate.plusDays(1).atStartOfDay(); // exclusive upper bound

        List<Booking> bookingsInMonth = bookingRepository.findBookingsOverlappingDateRange(monthStart, monthEnd);

        Map<Long, List<Booking>> bookingsByRoomId = bookingsInMonth.stream()
                .collect(Collectors.groupingBy(b -> b.getRoom().getId()));

        final double HOURS_PER_DAY = 24.0;
        final int daysInMonth = month.lengthOfMonth();
        final double HOURS_IN_MONTH = HOURS_PER_DAY * daysInMonth;

        return roomsByType.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Room> rooms = entry.getValue();

                            double totalOccupancy = rooms.stream()
                                    .mapToDouble(room -> {
                                        List<Booking> roomBookings = bookingsByRoomId.getOrDefault(room.getId(), Collections.emptyList());

                                        double bookedHours = roomBookings.stream()
                                                .mapToDouble(b -> {
                                                    if (b.getDate() == null || b.getStartTime() == null || b.getEndTime() == null) return 0;

                                                    LocalDateTime bookingStart = LocalDateTime.of(b.getDate(), b.getStartTime());
                                                    LocalDateTime bookingEnd = LocalDateTime.of(b.getDate(), b.getEndTime());

                                                    LocalDateTime effectiveStart = bookingStart.isBefore(monthStart) ? monthStart : bookingStart;
                                                    LocalDateTime effectiveEnd = bookingEnd.isAfter(monthEnd) ? monthEnd : bookingEnd;

                                                    long minutes = Duration.between(effectiveStart, effectiveEnd).toMinutes();
                                                    return Math.max(minutes, 0) / 60.0;
                                                }).sum();

                                        return Math.min((bookedHours / HOURS_IN_MONTH) * 100.0, 100.0);
                                    }).sum();

                            return rooms.isEmpty() ? 0.0 : totalOccupancy / rooms.size();
                        }
                ));
    }

    // ===== USER BEHAVIOR =====

    @Override
    public Map<ProgressStatus, Long> getMissionProgressStats() {
        return freelancerRepository.findAll().stream()
                .flatMap(f -> f.getMissions().stream())
                .collect(Collectors.groupingBy(Mission::getProgress, Collectors.counting()));
    }

    @Override
    public Map<ProgressStatus, Long> getLectureProgressStats() {
        return internRepository.findAll().stream()
                .flatMap(i -> i.getLectures().stream())
                .collect(Collectors.groupingBy(Lecture::getProgress, Collectors.counting()));
    }

    @Override
    public Map<Role, Long> getActiveUsersLastNDays(int days) {
        LocalDate cutoff = LocalDate.now().minusDays(days);
        return bookingRepository.findAll().stream()
                .filter(b -> !b.getDate().isBefore(cutoff))
                .flatMap(b -> Stream.of(b.getFreelancer(), b.getIntern()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        u -> u instanceof Freelancer ? Role.FREELANCER : Role.INTERN,
                        Collectors.mapping(User::getId, Collectors.toSet())
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (long) e.getValue().size()));
    }

    @Override
    public Map<Role, Long> getBookingFrequencyByUserType() {
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getFreelancer() != null ? Role.FREELANCER : Role.INTERN,
                        Collectors.counting()
                ));
    }
    @Override
    public Map<Role, Long> countInactiveUsers() {
        long inactiveFreelancers = freelancerRepository.findAll().stream()
                .filter(f -> f.getBookings() == null || f.getBookings().isEmpty())
                .count();

        long inactiveInterns = internRepository.findAll().stream()
                .filter(i -> i.getBookings() == null || i.getBookings().isEmpty())
                .count();

        Map<Role, Long> result = new HashMap<>();
        result.put(Role.FREELANCER, inactiveFreelancers);
        result.put(Role.INTERN, inactiveInterns);
        return result;
    }

    @Override
    public Map<Role, Long> countChurnedUsers(int daysWithoutBooking) {
        LocalDate cutoffDate = LocalDate.now().minusDays(daysWithoutBooking);

        long churnedFreelancers = freelancerRepository.findAll().stream()
                .filter(f -> f.getBookings().stream()
                        .map(Booking::getDate)
                        .max(LocalDate::compareTo)
                        .orElse(LocalDate.MIN)
                        .isBefore(cutoffDate))
                .count();

        long churnedInterns = internRepository.findAll().stream()
                .filter(i -> i.getBookings().stream()
                        .map(Booking::getDate)
                        .max(LocalDate::compareTo)
                        .orElse(LocalDate.MIN)
                        .isBefore(cutoffDate))
                .count();

        Map<Role, Long> result = new HashMap<>();
        result.put(Role.FREELANCER, churnedFreelancers);
        result.put(Role.INTERN, churnedInterns);
        return result;
    }

    @Override
    public LinkedHashMap<String, Long> getTopUsersByBookings(int limit) {
        Map<String, Long> userBookingCounts = bookingRepository.findAll().stream()
                .flatMap(b -> {
                    List<String> users = new ArrayList<>();
                    if (b.getFreelancer() != null) users.add("Freelancer: " + b.getFreelancer().getUsername());
                    if (b.getIntern() != null) users.add("Intern: " + b.getIntern().getUsername());
                    return users.stream();
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Sort by count desc, limit, and collect into LinkedHashMap to preserve order
        return userBookingCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Override
    public List<RoomType> getUnderutilizedRoomTypes(double thresholdPercentage) {
        LocalDate start = LocalDate.now().minusMonths(1);
        List<Booking> bookings = bookingRepository.findByDateAfter(start);

        // Total booked hours per room type
        Map<RoomType, Double> bookedHours = bookings.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getRoom().getType(),
                        Collectors.summingDouble(b -> Duration.between(b.getStartTime(), b.getEndTime()).toMinutes() / 60.0)
                ));

        // Total available hours = number of rooms of that type × 8 hours/day × 30 days
        Map<RoomType, Long> roomCount = roomRepository.findAll().stream()
                .collect(Collectors.groupingBy(Room::getType, Collectors.counting()));

        List<RoomType> underutilized = new ArrayList<>();
        for (RoomType type : roomCount.keySet()) {
            double availableHours = roomCount.get(type) * 8.0 * 30;
            double usagePercent = (bookedHours.getOrDefault(type, 0.0) / availableHours) * 100.0;
            if (usagePercent < thresholdPercentage) {
                underutilized.add(type);
            }
        }
        return underutilized;
    }
    @Override
    public Map<RoomType, Double> getRoomUtilizationRate(LocalDate start, LocalDate end) {
        List<Booking> bookings = bookingRepository.findByDateBetween(start, end);

        Map<RoomType, Double> totalBookedHours = bookings.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getRoom().getType(),
                        Collectors.summingDouble(b -> Duration.between(b.getStartTime(), b.getEndTime()).toMinutes() / 60.0)
                ));

        Map<RoomType, Long> roomTypeCounts = roomRepository.findAll().stream()
                .collect(Collectors.groupingBy(Room::getType, Collectors.counting()));

        long numberOfDays = ChronoUnit.DAYS.between(start, end) + 1;
        double assumedDailyHours = 8.0;

        Map<RoomType, Double> utilizationRates = new HashMap<>();
        for (RoomType type : roomTypeCounts.keySet()) {
            double totalAvailable = roomTypeCounts.get(type) * assumedDailyHours * numberOfDays;
            double booked = totalBookedHours.getOrDefault(type, 0.0);
            utilizationRates.put(type, (booked / totalAvailable) * 100.0);
        }

        return utilizationRates;
    }
    @Override
    public Map<LocalTime, Long> getHourlyBookingDistribution() {
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getStartTime().truncatedTo(ChronoUnit.HOURS),
                        Collectors.counting()
                ));
    }
    @Override
    public Map<String, Long> getTopBookedRoomTypes(int limit) {
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getRoom().getType().name(), // convert enum to String
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // preserve order
                ));
    }

}
