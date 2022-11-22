package com.domo.lms.service;

import com.domo.lms.entity.Banner;
import com.domo.lms.mapper.BannerMapper;
import com.domo.lms.model.BannerDto;
import com.domo.lms.model.BannerInput;
import com.domo.lms.model.BannerParam;
import com.domo.lms.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    @Override
    public BannerDto getById(long id) {
        return bannerRepository.findById(id)
                .map(BannerDto::of).orElse(null);
    }


    @Override
    public List<BannerDto> list(BannerParam parameter) {
        long totalCount = bannerMapper.selectListCount(parameter);
        List<BannerDto> list = bannerMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (BannerDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public boolean add(BannerInput parameter) {
        Banner banner = Banner.builder()
                .bannerName(parameter.getBannerName())
                .bannerImgUrl(parameter.getFileImgUrl())
                .link(parameter.getLink())
                .sortValue(parameter.getSortValue())
                .openStatus(parameter.getOpenStatus())
                .regDt(LocalDateTime.now())
                .build();
        bannerRepository.save(banner);
        return true;
    }

    @Override
    public boolean set(BannerInput parameter) {

        Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getId());
        if (!optionalBanner.isPresent()) {
            return false;
        }

        Banner banner = optionalBanner.get();
        banner.setBannerName(parameter.getBannerName());
        banner.setBannerImgUrl(parameter.getFileImgUrl());
        banner.setLink(parameter.getLink());
        banner.setSortValue(parameter.getSortValue());
        banner.setOpenStatus(parameter.getOpenStatus());

        bannerRepository.save(banner);
        return true;
    }
}
