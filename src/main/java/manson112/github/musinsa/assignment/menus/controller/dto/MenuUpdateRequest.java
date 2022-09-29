package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuUpdateRequest {
    @NotNull
    @Positive
    private Long menuId;

    @NotEmpty
    @Length(min = 1, max = 20)
    private String menuTitle;

    @URL
    @NotEmpty
    @Length(min = 1, max = 2083)
    private String menuLink;

    @PositiveOrZero
    private long sortOrder;
    @PositiveOrZero
    private long parentMenuId;

    private LocalDateTime updateAt;

    @Builder
    public MenuUpdateRequest(Long menuId, String menuTitle, String menuLink, long sortOrder, long parentMenuId, LocalDateTime updateAt) {
        this.menuId = menuId;
        this.menuTitle = menuTitle;
        this.menuLink = menuLink;
        this.sortOrder = sortOrder;
        this.parentMenuId = parentMenuId;
        this.updateAt = defaultIfNull(updateAt, LocalDateTime.now());
    }
}
