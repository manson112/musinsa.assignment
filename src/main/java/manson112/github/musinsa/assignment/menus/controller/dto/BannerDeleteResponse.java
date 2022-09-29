package manson112.github.musinsa.assignment.menus.controller.dto;


import lombok.*;

@Getter
public class BannerDeleteResponse {
    private Long menuId;

    @Builder(access = AccessLevel.PRIVATE)
    public BannerDeleteResponse(Long menuId) {
        this.menuId = menuId;
    }

    public static BannerDeleteResponse from(Long menuId) {
        return BannerDeleteResponse.builder().menuId(menuId).build();
    }
}
