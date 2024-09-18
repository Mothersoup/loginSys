package com.example.loginsystem.service;

import com.example.loginsystem.model.entity.AttendanceRecords;
import com.example.loginsystem.repo.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {


    private final AttendanceRepo attendanceRepo;

    @Autowired
    public AttendanceService(AttendanceRepo attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    public AttendanceRecords saveAttendanceRecord(AttendanceRecords record) {
        return attendanceRepo.save(record);
    }

    // Example method to find an attendance record by ID
    public Optional<AttendanceRecords> getAttendanceRecordById(int id) {
        return attendanceRepo.findById(id);
    }

    // Example method to retrieve all attendance records
    public List<AttendanceRecords> getAllAttendanceRecords() {
        return attendanceRepo.findAll();
    }

    // Example method to delete an attendance record by ID
    public void deleteAttendanceRecord(int id) {
        attendanceRepo.deleteById(id);
    }

}
