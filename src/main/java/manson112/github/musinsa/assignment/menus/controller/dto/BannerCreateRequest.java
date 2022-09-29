package manson112.github.musinsa.assignment.menus.controller.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BannerCreateRequest {
    @NotNull(message = "menuId must not be null")
    @Positive
    private Long menuId;
    @URL
    @Length(max = 2083)
    private String bannerLink;
    @URL
    @Length(max = 2083)
    private String bannerImageUrl;

    public BannerCreateRequest(Long menuId, String bannerLink, String bannerImageUrl) {
        this.menuId = menuId;
        this.bannerLink = bannerLink;
        this.bannerImageUrl = bannerImageUrl;
    }
}
