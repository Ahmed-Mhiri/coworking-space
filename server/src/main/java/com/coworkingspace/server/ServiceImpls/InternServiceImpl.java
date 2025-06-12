package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.DTOs.BookingDTO;
import com.coworkingspace.server.DTOs.LectureDTO;
import com.coworkingspace.server.services.BookingService;
import com.coworkingspace.server.services.InternService;
import com.coworkingspace.server.services.LectureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternServiceImpl implements InternService {

    private final LectureService lectureService; // reuse LectureService if exists
    private final BookingService bookingService; // reuse BookingService if exists

    public InternServiceImpl(LectureService lectureService, BookingService bookingService) {
        this.lectureService = lectureService;
        this.bookingService = bookingService;
    }

    @Override
    public LectureDTO createLecture(Long internId, LectureDTO dto) {
        return lectureService.createLecture(dto);
    }

    @Override
    public LectureDTO updateLecture(Long lectureId, LectureDTO dto) {
        return lectureService.updateLecture(lectureId, dto);
    }

    @Override
    public void deleteLecture(Long lectureId) {
        lectureService.deleteLecture(lectureId);
    }

    @Override
    public List<LectureDTO> getLectures(Long internId) {
        return lectureService.getLecturesForIntern();
    }

    @Override
    public BookingDTO createBooking(Long internId, BookingDTO bookingDTO) {
        return bookingService.createBooking(internId, false, bookingDTO);
    }

    @Override
    public List<BookingDTO> getBookings(Long internId) {
        return bookingService.getBookingsForUser(internId);
    }
}