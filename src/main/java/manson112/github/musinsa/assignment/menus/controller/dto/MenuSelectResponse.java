package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.*;
import manson112.github.musinsa.assignment.menus.entity.Menu;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuSelectResponse {
    private Long    menuId;
    private String  menuTitle;
    private String  menuLink;
    private Long    parentMenuId;
    private LocalDateTime updateAt;
    private Integer depth;
    private String  path;
    private String  sortOrder;
    private List<BannerSelectResponse> banners;

    public MenuSelectResponse() {
    }

    public static MenuSelectResponse from(Menu menu) {
        return MenuSelectResponse.builder()
                .menuId(menu.getMenuId())
                .menuTitle(menu.getMenuTitle())
                .menuLink(menu.getMenuLink())
                .parentMenuId(menu.getParentMenuId())
                .sortOrder(Long.toString(menu.getSortOrder()))
                .build();
    }

    public void setBanners(List<BannerSelectResponse> banners) {
        this.banners = banners;
    }
}
