package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.*;
import manson112.github.musinsa.assignment.menus.entity.Banner;

@Getter
@ToString
public class BannerSelectResponse {
    private Long    bannerId;
    private String  bannerLink;
    private String  bannerImageUrl;
    private Long    menuId;

    @Builder(access = AccessLevel.PRIVATE)
    private BannerSelectResponse(Long bannerId, String bannerLink, String bannerImageUrl, Long menuId) {
        this.bannerId = bannerId;
        this.bannerLink = bannerLink;
        this.bannerImageUrl = bannerImageUrl;
        this.menuId = menuId;
    }

    public static BannerSelectResponse from(Banner banner) {
        return BannerSelectResponse.builder()
                .bannerId(banner.getBannerId())
                .bannerLink(banner.getBannerLink())
                .bannerImageUrl(banner.getBannerImageUrl())
                .menuId(banner.getMenuId())
                .build();
    }
}
