package com.example.loginsystem.repo;

import com.example.loginsystem.model.entity.AttendanceRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<AttendanceRecords, Integer> {


}
