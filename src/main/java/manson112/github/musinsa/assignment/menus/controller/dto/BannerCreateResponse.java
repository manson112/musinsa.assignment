package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.*;

@Getter
@ToString
public class BannerCreateResponse {
    private Long bannerId;
    private Long menuId;

    @Builder(access = AccessLevel.PRIVATE)
    private BannerCreateResponse(Long bannerId, Long menuId) {
        this.bannerId = bannerId;
        this.menuId = menuId;
    }

    public static BannerCreateResponse of(Long bannerId, Long menuId) {
        return BannerCreateResponse.builder()
                .bannerId(bannerId)
                .menuId(menuId)
                .build();
    }
}
