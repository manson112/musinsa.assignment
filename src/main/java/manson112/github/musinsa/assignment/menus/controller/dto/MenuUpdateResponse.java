package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MenuUpdateResponse {
    private Long menuId;
    private Long parentMenuId;

    public static MenuUpdateResponse of(Long menuId, Long parentMenuId) {
        return MenuUpdateResponse.builder()
                .menuId(menuId)
                .parentMenuId(parentMenuId)
                .build();
    }
}
