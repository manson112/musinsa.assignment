package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuDeleteRequest {
    @NotNull
    @PositiveOrZero
    private Long menuId;

    public MenuDeleteRequest(Long menuId) {
        this.menuId = menuId;
    }
}
