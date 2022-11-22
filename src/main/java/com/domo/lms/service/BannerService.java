package com.domo.lms.service;

import com.domo.lms.model.BannerDto;
import com.domo.lms.model.BannerInput;
import com.domo.lms.model.BannerParam;

import java.util.List;

public interface BannerService {
    BannerDto getById(long id) ;

    List<BannerDto> list(BannerParam parameter);

    boolean add(BannerInput parameter);

    boolean set(BannerInput parameter);
}
