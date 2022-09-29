package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuCreateResponse {
    private Long menuId;
    private Long parentMenuId;

    @Builder(access = AccessLevel.PRIVATE)
    public MenuCreateResponse(Long menuId, Long parentMenuId) {
        this.menuId = menuId;
        this.parentMenuId = parentMenuId;
    }

    public static MenuCreateResponse of(Long menuId, Long parentMenuId) {
        return MenuCreateResponse.builder()
                .menuId(menuId)
                .parentMenuId(parentMenuId)
                .build();
    }

}
