package com.domo.lms.service;

import com.domo.lms.entity.TakeCourse;
import com.domo.lms.mapper.TakeCourseMapper;
import com.domo.lms.model.ServiceResult;
import com.domo.lms.model.TakeCourseDto;
import com.domo.lms.model.TakeCourseParam;
import com.domo.lms.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TakeCourseServiceImpl implements TakeCourseService {
    private final TakeCourseRepository takeCourseRepository;
    private final TakeCourseMapper takeCourseMapper;

    @Override
    public List<TakeCourseDto> list(TakeCourseParam parameter) {
        long totalCount = takeCourseMapper.selectListCount(parameter);
        List<TakeCourseDto> list = takeCourseMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (TakeCourseDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public ServiceResult updateStatus(long id, String status) {
        Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
        if (!optionalTakeCourse.isPresent()) {
            return new ServiceResult(false, "수강 정보가 존재하지 않습니다.");
        }
        TakeCourse takeCourse = optionalTakeCourse.get();

        takeCourse.setStatus(status);
        takeCourseRepository.save(takeCourse);
        return new ServiceResult(true);
    }
}