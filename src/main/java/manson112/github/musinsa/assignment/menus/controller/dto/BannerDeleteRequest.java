package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BannerDeleteRequest {
    @NotNull
    @Positive
    private Long bannerId;

    public BannerDeleteRequest(Long bannerId) {
        this.bannerId = bannerId;
    }
}
