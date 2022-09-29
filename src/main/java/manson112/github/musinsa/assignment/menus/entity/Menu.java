package manson112.github.musinsa.assignment.menus.entity;

import lombok.*;
import manson112.github.musinsa.assignment.menus.controller.dto.MenuCreateRequest;
import manson112.github.musinsa.assignment.menus.controller.dto.MenuUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
    private Long            menuId;
    private String          menuTitle;
    private String          menuLink;
    private long            sortOrder;
    private Long            parentMenuId;
    private LocalDateTime   createAt;
    private LocalDateTime   updateAt;

    public static Menu from(MenuCreateRequest menuCreateRequest) {
        return Menu.builder()
                .menuTitle(menuCreateRequest.getMenuTitle())
                .menuLink(menuCreateRequest.getMenuLink())
                .sortOrder(menuCreateRequest.getSortOrder())
                .parentMenuId(menuCreateRequest.getParentMenuId())
                .createAt(defaultIfNull(menuCreateRequest.getCreateAt(), LocalDateTime.now()))
                .updateAt(defaultIfNull(menuCreateRequest.getUpdateAt(), LocalDateTime.now()))
                .build();
    }

    public static Menu from(MenuUpdateRequest menuUpdateRequest) {
        return Menu.builder()
                .menuId(menuUpdateRequest.getMenuId())
                .menuTitle(menuUpdateRequest.getMenuTitle())
                .menuLink(menuUpdateRequest.getMenuLink())
                .sortOrder(menuUpdateRequest.getSortOrder())
                .parentMenuId(menuUpdateRequest.getParentMenuId())
                .updateAt(defaultIfNull(menuUpdateRequest.getUpdateAt(), LocalDateTime.now()))
                .build();
    }
}
