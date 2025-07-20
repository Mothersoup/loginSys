package com.example.loginsystem.service.impl;

import com.example.loginsystem.pojo.dto.OfferingCourseDTO;
import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.model.entity.Course;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.pojo.vo.CoursesVO;
import com.example.loginsystem.repo.CoursesRepo;
import com.example.loginsystem.security.LoginPrinciple;
import com.example.loginsystem.service.ICourseModify;
import com.example.loginsystem.service.IUserService;
import com.example.loginsystem.web.ServerCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CourseModifyImp implements ICourseModify {

    private final CoursesRepo coursesRepo;
    private final IUserService userService;

    public CourseModifyImp(CoursesRepo coursesRepo, IUserService userService) {
        this.coursesRepo = coursesRepo;
        this.userService = userService;
    }

    @Override
    public void addCourse(OfferingCourseDTO offeringCourseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginPrinciple loginPrinciple = (LoginPrinciple) authentication.getPrincipal();
        if( coursesRepo.existsByCourseCode( offeringCourseDTO.getCourse_code()) )
            throw new ServiceException(ServerCode.ERR_INSERT, "course has been exist ");
        Course course = new Course();
        course.setCourseCode(offeringCourseDTO.getCourse_code());
        course.setCourseName(offeringCourseDTO.getCourse_name());
        course.setTeacher(userService.findStudentByStudentNum(Integer.parseInt(loginPrinciple.getAccount())));
        course.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        course.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        coursesRepo.save(course);

    }

    /**
     * only admin or teacher self can update the course
     * @param offeringCourseDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCourse(OfferingCourseDTO offeringCourseDTO) {

        String courseCodeToUpdate = offeringCourseDTO.getCourse_code();
        if (courseCodeToUpdate == null || courseCodeToUpdate.isEmpty()) {
            throw new ServiceException(ServerCode.ERR_UPDATE, "Course code is required for update.");
        }

        Optional<Course> existingCourseOpt = coursesRepo.findByCourseCode(courseCodeToUpdate);

        if (existingCourseOpt.isEmpty()) {
            throw new ServiceException(ServerCode.ERR_UPDATE, "Course with code " + courseCodeToUpdate + " not found.");
        }

        Course existingCourse = existingCourseOpt.get();

        if ( (offeringCourseDTO.getCourse_code() != null &&  !offeringCourseDTO.getCourse_code().isEmpty() )) {
            existingCourse.setCourseName(offeringCourseDTO.getCourse_name());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginPrinciple loginPrinciple = (LoginPrinciple) authentication.getPrincipal();


        User newTeacher = userService.findStudentByStudentNum(Integer.parseInt(loginPrinciple.getAccount()));
        if (newTeacher == null)
            throw new ServiceException(ServerCode.ERR_NOT_FOUND, "Teacher not found not found.");
        else if (!Objects.equals(newTeacher.getStudentNumber(), existingCourse.getTeacher().getStudentNumber())
                && authentication.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ServiceException(ServerCode.ERR_PERMISSION_DENIED, "Only the course owner or admin can update the course.");
        }
        existingCourse.setTeacher(newTeacher);
        existingCourse.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        coursesRepo.save(existingCourse);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCourse( String courseCode ) {
        Optional<Course> courseOptional = coursesRepo.findByCourseCode(courseCode);
        if (courseOptional.isEmpty()) {
            throw new ServiceException(ServerCode.ERR_NOT_FOUND, "Course with ID " + courseCode + " not found.");
        }
        Course course = courseOptional.get();
        coursesRepo.delete(course);
    }

    @Override
    public List<CoursesVO> getAllCourses() {
        return coursesRepo.findAll()
                .stream()
                .map(course -> {
                    CoursesVO vo = new CoursesVO();
                    vo.setCourseCode(course.getCourseCode());
                    vo.setCourseName(course.getCourseName());
                    vo.setCreateAt(course.getCreatedAt());
                    vo.setUpdateAt(course.getUpdatedAt());
                    if (course.getTeacher() != null) {
                        vo.setUser_id(course.getTeacher().getStudentNumber());
                    } else {
                        vo.setUser_id(null);
                    }
                    return vo;
                }).collect(Collectors.toList());
    }

    @Override
    public Course getCourseByCourseId(String courseId) {
        return coursesRepo.findByCourseCode(courseId).orElseThrow(() -> new ServiceException(ServerCode.ERR_NOT_FOUND, "Course with ID " + courseId + " not found."));
    }




}
