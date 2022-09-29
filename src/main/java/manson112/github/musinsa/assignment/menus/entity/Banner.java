package manson112.github.musinsa.assignment.menus.entity;

import lombok.*;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerCreateRequest;

@Getter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner {
    private Long    bannerId;
    private String  bannerLink;
    private String  bannerImageUrl;
    private Long    menuId;

    public static Banner from(BannerCreateRequest bannerCreateRequest) {
        return Banner.builder()
                .bannerLink(bannerCreateRequest.getBannerLink())
                .menuId(bannerCreateRequest.getMenuId())
                .bannerImageUrl(bannerCreateRequest.getBannerImageUrl())
                .build();
    }
}
