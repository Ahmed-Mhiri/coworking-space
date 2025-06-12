package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.BookingDTO;
import com.coworkingspace.server.DTOs.LectureDTO;

import java.util.List;

public interface InternService {
    LectureDTO createLecture(Long internId, LectureDTO dto);
    LectureDTO updateLecture(Long lectureId, LectureDTO dto);
    void deleteLecture(Long lectureId);
    List<LectureDTO> getLectures(Long internId);

    BookingDTO createBooking(Long internId, BookingDTO bookingDTO);
    List<BookingDTO> getBookings(Long internId);
}
