package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuSelectRequest {
    @NotNull
    private Long menuId;

    public MenuSelectRequest(Long menuId) {
        this.menuId = menuId;
    }
}
