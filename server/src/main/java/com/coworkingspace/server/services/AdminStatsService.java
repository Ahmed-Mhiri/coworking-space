package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.RoomDTO;
import com.coworkingspace.server.models.ProgressStatus;
import com.coworkingspace.server.models.Role;
import com.coworkingspace.server.models.RoomType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface AdminStatsService {

    // ===== ROOM & USER METRICS =====

    Map<RoomType, Long> countPeoplePerRoomType();
    Map<String, Long> countUserTypesInRoom(Long roomId);
    Map<RoomType, Long> countRoomsByType();

    // ===== FINANCIAL METRICS =====

    double getTotalRevenueOnDate(LocalDate date);
    double getTotalRevenueBetween(LocalDate start, LocalDate end);
    Map<YearMonth, Double> getMonthlyRevenueTrend();
    Map<RoomType, Double> getRevenuePerRoomType();
    Map<LocalDate, Double> getDailyRevenueTrend(int days);

    // NEW: Revenue breakdown by user type
    Map<Role, Double> getRevenueByUserType();

    // ===== BOOKING & USAGE PATTERNS =====

    long countBookingsOnDate(LocalDate date);
    Map<LocalDate, Long> getDailyBookingsTrend(int days);
    double getAverageBookingDurationInHours();
    Map<RoomType, Double> getAverageSessionLengthPerRoomType();
    Map<RoomType, Double> getOccupancyRatePerRoomTypeForMonth(YearMonth month);
    Map<RoomType, Double> getRoomUtilizationRate(LocalDate start, LocalDate end);

    // NEW: Peak hours across all bookings
    Map<LocalTime, Long> getHourlyBookingDistribution();

    // NEW: Most booked room types
    Map<String, Long> getTopBookedRoomTypes(int limit);

    // ===== USER ACTIVITY & ENGAGEMENT =====

    Map<ProgressStatus, Long> getMissionProgressStats();
    Map<ProgressStatus, Long> getLectureProgressStats();
    Map<Role, Long> getActiveUsersLastNDays(int days);
    Map<Role, Long> getBookingFrequencyByUserType();

    // NEW: Inactive users who have never booked
    Map<Role, Long> countInactiveUsers();

    // NEW: User churn (users who were active but stopped booking in last N days)
    Map<Role, Long> countChurnedUsers(int daysWithoutBooking);

    // NEW: Most active users (by number of bookings)
    LinkedHashMap<String, Long> getTopUsersByBookings(int limit);

    // ===== ADMIN DASHBOARD ALERTS / FLAGS =====

    // NEW: Detect underutilized room types (e.g. < 30% utilization past month)
    List<RoomType> getUnderutilizedRoomTypes(double thresholdPercentage);

}

