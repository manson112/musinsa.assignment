package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.*;

@Getter
public class MenuDeleteResponse {
    private Long parentMenuId;

    @Builder(access = AccessLevel.PRIVATE)
    public MenuDeleteResponse(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public static MenuDeleteResponse from(Long parentMenuId) {
        return MenuDeleteResponse.builder()
                .parentMenuId(parentMenuId)
                .build();
    }
}
