package manson112.github.musinsa.assignment.menus.service;

import lombok.RequiredArgsConstructor;
import manson112.github.musinsa.assignment.exceptions.BannerNotFoundException;
import manson112.github.musinsa.assignment.exceptions.MenuNotFoundException;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerCreateRequest;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerCreateResponse;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerDeleteRequest;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerDeleteResponse;
import manson112.github.musinsa.assignment.menus.entity.Banner;
import manson112.github.musinsa.assignment.menus.entity.Menu;
import manson112.github.musinsa.assignment.menus.repository.BannerRepository;
import manson112.github.musinsa.assignment.menus.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 배너를 등록, 수정, 삭제, 조회하기 위한 서비스
 * @version 0.1
 * @author 김관우
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    private final MenuRepository menuRepository;

    /**
     * 배너를 생성한다.
     * 배너는 최상위 메뉴일 때만 등록 가능하고,
     * 등록된 배너의 ID를 반환한다.
     * @param bannerCreateRequest 배너생성요청
     * @return 생성된 배너ID
     */
    public BannerCreateResponse createBanner(BannerCreateRequest bannerCreateRequest) {
        /* 1. 메뉴 존재여부 확인 */
        Menu menu = menuRepository.findById(bannerCreateRequest.getMenuId())
                .orElseThrow(() -> new MenuNotFoundException(bannerCreateRequest.getMenuId()));

        /* 2. 최상위 메뉴여부 확인 */
        if (menu.getParentMenuId() != 0L) {
            throw new IllegalArgumentException("Banner can't be registered for non-top tier menu");
        }

        /* 3. 등록 */
        Banner banner = Banner.from(bannerCreateRequest);
        bannerRepository.createBanner(banner);

        return BannerCreateResponse.of(banner.getBannerId(), menu.getMenuId());
    }


    /**
     * 배너를 삭제한다.
     * @param bannerDeleteRequest 배너삭제요청
     * @return 배너의 메뉴ID
     */
    public BannerDeleteResponse deleteBanner(BannerDeleteRequest bannerDeleteRequest) {
        Long bannerId = bannerDeleteRequest.getBannerId();
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new BannerNotFoundException(bannerId));

        bannerRepository.deleteBanner(bannerId);

        return BannerDeleteResponse.from(banner.getMenuId());
    }

}
