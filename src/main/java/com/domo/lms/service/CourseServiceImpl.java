package com.domo.lms.service;

import com.domo.lms.entity.Course;
import com.domo.lms.mapper.CourseMapper;
import com.domo.lms.model.CourseDto;
import com.domo.lms.model.CourseInput;
import com.domo.lms.model.CourseParam;
import com.domo.lms.model.MemberDto;
import com.domo.lms.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public boolean add(CourseInput courseInput) {
        Course course = Course.builder()
                .subject(courseInput.getSubject())
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);
        return true;
    }

    @Override
    public boolean set(CourseInput parameter) {
        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if (!optionalCourse.isPresent()) {
            return false;
        }

        Course course = optionalCourse.get();
        course.setSubject(parameter.getSubject());;
        course.setUpDt(LocalDateTime.now());

        courseRepository.save(course);
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


}
