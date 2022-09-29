package manson112.github.musinsa.assignment.menus.controller;

import lombok.RequiredArgsConstructor;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerCreateRequest;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerCreateResponse;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerDeleteRequest;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerDeleteResponse;
import manson112.github.musinsa.assignment.menus.service.BannerService;
import manson112.github.musinsa.assignment.utils.ApiUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static manson112.github.musinsa.assignment.utils.ApiUtils.success;


/**
 * 배너관리 컨트롤러
 * 최상위 메뉴에 대한 등록, 삭제요청을 받는다.
 * @version 0.1
 * @author 김관우
 */
@Validated
@RestController
@RequestMapping("api/banners")
@RequiredArgsConstructor
public class BannerRestController {
    private final BannerService bannerService;

    @PostMapping
    public ApiUtils.ApiResult<BannerCreateResponse> createBanner(
            @Valid @RequestBody BannerCreateRequest bannerCreateRequest
    ) {
       return success(bannerService.createBanner(bannerCreateRequest));
    }

    @DeleteMapping
    public ApiUtils.ApiResult<BannerDeleteResponse> deleteBanner(
           @Valid @RequestBody BannerDeleteRequest bannerDeleteRequest
    ) {
        return success(bannerService.deleteBanner(bannerDeleteRequest));
    }
}

