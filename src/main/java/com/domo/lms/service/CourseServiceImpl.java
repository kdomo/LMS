package com.domo.lms.service;

import com.domo.lms.entity.Course;
import com.domo.lms.entity.TakeCourse;
import com.domo.lms.mapper.CourseMapper;
import com.domo.lms.model.*;
import com.domo.lms.repository.CourseRepository;
import com.domo.lms.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final TakeCourseRepository takeCourseRepository;

    private LocalDate getLocalDate(String value) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(CourseInput courseInput) {
        LocalDate seleEndDt = getLocalDate(courseInput.getSaleEndDtText());

        Course course = Course.builder()
                .categoryId(courseInput.getCategoryId())
                .subject(courseInput.getSubject())
                .keyword(courseInput.getKeyword())
                .summary(courseInput.getSummary())
                .contents(courseInput.getContents())
                .price(courseInput.getPrice())
                .salePrice(courseInput.getSalePrice())
                .saleEndDt(seleEndDt)
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);
        return true;
    }

    @Override
    public boolean set(CourseInput parameter) {
        LocalDate seleEndDt = getLocalDate(parameter.getSaleEndDtText());

        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if (!optionalCourse.isPresent()) {
            return false;
        }

        Course course = optionalCourse.get();
        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setSaleEndDt(seleEndDt);
        course.setUpDt(LocalDateTime.now());

        courseRepository.save(course);
        return true;
    }

    @Override
    public boolean delete(String idList) {
        if(idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                    return false;
                }

                if(id > 0) {
                    courseRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam parameter) {
        long totalCount = courseMapper.selectListCount(parameter);
        List<CourseDto> list = courseMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (CourseDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public CourseDto getById(long id) {
        return courseRepository.findById(id)
                .map(CourseDto::of).orElse(null);


    }

    @Override
    public List<CourseDto> frontList(CourseParam parameter) {
        if (parameter.getCategoryId() < 1) {
            List<Course> list = courseRepository.findAll();
            return CourseDto.of(list);
        }

        Optional<List<Course>> optionalList = courseRepository.findByCategoryId(parameter.getCategoryId());
        if (optionalList.isPresent()) {
            return CourseDto.of(optionalList.get());
        }

        return new ArrayList<>();
    }

    @Override
    public CourseDto frontDetail(long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if(optionalCourse.isPresent()) {
            return CourseDto.of(optionalCourse.get());
        }

        return CourseDto.builder().build();
    }

    @Override
    public ServiceReslut req(TakeCourseInput parameter) {
        ServiceReslut reslut = new ServiceReslut();
        Optional<Course> optionalCourse = courseRepository.findById(parameter.getCourseId());
        if (!optionalCourse.isPresent()) {
            reslut.setReslut(false);
            reslut.setMessage("강좌 정보가 존재하지 않습니다.");
            return reslut;
        }

        Course course = optionalCourse.get();

        String[] statusList = {TakeCourse.STATUS_REQ, TakeCourse.STATUS_COMPLETE};
        long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(course.getId(), parameter.getUserId(), Arrays.asList(statusList));
        if (count > 0) {
            reslut.setReslut(false);
            reslut.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return reslut;
        }
        TakeCourse takeCourse = TakeCourse.builder()
                .courseId(course.getId())
                .userId(parameter.getUserId())
                .payPrice(course.getSalePrice())
                .status(TakeCourse.STATUS_REQ)
                .regDt(LocalDateTime.now())
                .build();
        takeCourseRepository.save(takeCourse);

        reslut.setReslut(true);
        return reslut;
    }

}