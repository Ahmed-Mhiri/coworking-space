package com.coworkingspace.server.Controllers;

import com.coworkingspace.server.models.ProgressStatus;
import com.coworkingspace.server.models.Role;
import com.coworkingspace.server.models.RoomType;
import com.coworkingspace.server.services.AdminStatsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/dashboard")
public class AdminDashboardController {

    private final AdminStatsService adminStatsService;

    public AdminDashboardController(AdminStatsService adminStatsService) {
        this.adminStatsService = adminStatsService;
    }

    // ===== ROOM & USER METRICS =====

    @GetMapping("/people-per-room-type")
    public ResponseEntity<Map<RoomType, Long>> getPeoplePerRoomType() {
        return ResponseEntity.ok(adminStatsService.countPeoplePerRoomType());
    }

    @GetMapping("/user-types-in-room/{roomId}")
    public ResponseEntity<Map<String, Long>> getUserTypesInRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(adminStatsService.countUserTypesInRoom(roomId));
    }

    @GetMapping("/rooms-by-type")
    public ResponseEntity<Map<RoomType, Long>> getRoomsByType() {
        return ResponseEntity.ok(adminStatsService.countRoomsByType());
    }

    // ===== FINANCIAL METRICS =====

    @GetMapping("/revenue/date")
    public ResponseEntity<Double> getRevenueByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(adminStatsService.getTotalRevenueOnDate(date));
    }

    @GetMapping("/revenue/range")
    public ResponseEntity<Double> getRevenueBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(adminStatsService.getTotalRevenueBetween(start, end));
    }

    @GetMapping("/revenue/monthly-trend")
    public ResponseEntity<Map<YearMonth, Double>> getMonthlyRevenueTrend() {
        return ResponseEntity.ok(adminStatsService.getMonthlyRevenueTrend());
    }

    @GetMapping("/revenue/room-type")
    public ResponseEntity<Map<RoomType, Double>> getRevenuePerRoomType() {
        return ResponseEntity.ok(adminStatsService.getRevenuePerRoomType());
    }

    @GetMapping("/revenue/daily-trend")
    public ResponseEntity<Map<LocalDate, Double>> getDailyRevenueTrend(@RequestParam int days) {
        return ResponseEntity.ok(adminStatsService.getDailyRevenueTrend(days));
    }

    @GetMapping("/revenue/user-type")
    public ResponseEntity<Map<Role, Double>> getRevenueByUserType() {
        return ResponseEntity.ok(adminStatsService.getRevenueByUserType());
    }

    // ===== BOOKING & USAGE PATTERNS =====

    @GetMapping("/bookings/count")
    public ResponseEntity<Long> countBookingsOnDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(adminStatsService.countBookingsOnDate(date));
    }

    @GetMapping("/bookings/daily-trend")
    public ResponseEntity<Map<LocalDate, Long>> getDailyBookingsTrend(@RequestParam int days) {
        return ResponseEntity.ok(adminStatsService.getDailyBookingsTrend(days));
    }

    @GetMapping("/bookings/average-duration")
    public ResponseEntity<Double> getAverageBookingDuration() {
        return ResponseEntity.ok(adminStatsService.getAverageBookingDurationInHours());
    }

    @GetMapping("/session-length/room-type")
    public ResponseEntity<Map<RoomType, Double>> getAverageSessionLengthPerRoomType() {
        return ResponseEntity.ok(adminStatsService.getAverageSessionLengthPerRoomType());
    }

    @GetMapping("/occupancy-rate/monthly")
    public ResponseEntity<Map<RoomType, Double>> getOccupancyRatePerRoomTypeForMonth(
            @RequestParam("yearMonth") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        return ResponseEntity.ok(adminStatsService.getOccupancyRatePerRoomTypeForMonth(yearMonth));
    }

    @GetMapping("/utilization-rate")
    public ResponseEntity<Map<RoomType, Double>> getRoomUtilizationRate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(adminStatsService.getRoomUtilizationRate(start, end));
    }

    @GetMapping("/peak-hours")
    public ResponseEntity<Map<LocalTime, Long>> getHourlyBookingDistribution() {
        return ResponseEntity.ok(adminStatsService.getHourlyBookingDistribution());
    }

    @GetMapping("/top-room-types")
    public ResponseEntity<Map<String, Long>> getTopBookedRoomTypes(@RequestParam(defaultValue = "3") int limit) {
        return ResponseEntity.ok(adminStatsService.getTopBookedRoomTypes(limit));
    }

    // ===== USER ACTIVITY & ENGAGEMENT =====

    @GetMapping("/missions/progress")
    public ResponseEntity<Map<ProgressStatus, Long>> getMissionProgressStats() {
        return ResponseEntity.ok(adminStatsService.getMissionProgressStats());
    }

    @GetMapping("/lectures/progress")
    public ResponseEntity<Map<ProgressStatus, Long>> getLectureProgressStats() {
        return ResponseEntity.ok(adminStatsService.getLectureProgressStats());
    }

    @GetMapping("/users/active")
    public ResponseEntity<Map<Role, Long>> getActiveUsersLastNDays(@RequestParam int days) {
        return ResponseEntity.ok(adminStatsService.getActiveUsersLastNDays(days));
    }

    @GetMapping("/users/booking-frequency")
    public ResponseEntity<Map<Role, Long>> getBookingFrequencyByUserType() {
        return ResponseEntity.ok(adminStatsService.getBookingFrequencyByUserType());
    }

    @GetMapping("/users/inactive")
    public ResponseEntity<Map<Role, Long>> getInactiveUsers() {
        return ResponseEntity.ok(adminStatsService.countInactiveUsers());
    }

    @GetMapping("/users/churned")
    public ResponseEntity<Map<Role, Long>> getChurnedUsers(@RequestParam int daysWithoutBooking) {
        return ResponseEntity.ok(adminStatsService.countChurnedUsers(daysWithoutBooking));
    }

    @GetMapping("/users/top")
    public ResponseEntity<LinkedHashMap<String, Long>>getTopUsersByBookings(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(adminStatsService.getTopUsersByBookings(limit));
    }

    // ===== ADMIN DASHBOARD ALERTS =====

    @GetMapping("/alerts/underutilized-rooms")
    public ResponseEntity<List<RoomType>> getUnderutilizedRoomTypes(@RequestParam double thresholdPercentage) {
        return ResponseEntity.ok(adminStatsService.getUnderutilizedRoomTypes(thresholdPercentage));
    }
}
