package manson112.github.musinsa.assignment.menus.repository;

import manson112.github.musinsa.assignment.exceptions.BannerNotFoundException;
import manson112.github.musinsa.assignment.menus.entity.Banner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BannerRepositoryTest {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private BannerRepository bannerRepository;


    @BeforeEach
    public void setUp() {
        bannerRepository.deleteAll();

    }

    private Banner createSampleBanner(Long menuId) {
        Banner banner = Banner.builder()
                .bannerLink("new Banner")
                .bannerImageUrl("image URL")
                .menuId(menuId)
                .build();

        bannerRepository.createBanner(banner);

        return banner;
    }

    @Test
    @DisplayName("배너ID로 조회")
    public void findByIdTest01() throws Exception {
        Long menuId = 1L;
        Banner banner = createSampleBanner(menuId);

        Banner select = bannerRepository.findById(banner.getBannerId()).orElseThrow(Exception::new);

        log.debug("findByIdTest01 :: {}", select);

        assertThat(select.getBannerId()).isEqualTo(banner.getBannerId());
        assertThat(select.getBannerLink()).isEqualTo(banner.getBannerLink());
        assertThat(select.getBannerImageUrl()).isEqualTo(banner.getBannerImageUrl());
        assertThat(select.getMenuId()).isEqualTo(banner.getMenuId());
    }

    @Test
    @DisplayName("메뉴ID로 조회")
    public void findByMenuIdTest01() {
        int count = 5;
        Long menuId = 1L;
        for (int i=0; i<count; ++i) {
            createSampleBanner(menuId);
        }
        List<Banner> banners = bannerRepository.findByMenuId(menuId);

        log.debug("findByMenuIdTest01 :: {}", banners);

        assertThat(banners.size()).isEqualTo(count);
        assertThat(banners.stream().filter(it -> it.getMenuId().equals(menuId)).count()).isEqualTo(count);
    }

    @Test
    @DisplayName("배너 생성")
    public void createBannerTest01() throws Exception {
        Banner banner = createSampleBanner(13L);

        log.debug("createBannerTest01 :: {}", banner);

        Banner select = bannerRepository.findById(banner.getBannerId()).orElseThrow(Exception::new);

        assertThat(banner.getBannerId()).isEqualTo(select.getBannerId());
        assertThat(banner.getBannerLink()).isEqualTo(select.getBannerLink());
        assertThat(banner.getBannerImageUrl()).isEqualTo(select.getBannerImageUrl());
        assertThat(banner.getMenuId()).isEqualTo(select.getMenuId());
    }

    @Test
    @DisplayName("배너 삭제")
    public void deleteBannerTest01() {
        Banner banner = createSampleBanner(13L);

        assertThat(bannerRepository.findById(banner.getBannerId())).isNotEmpty();

        bannerRepository.deleteBanner(banner.getBannerId());

        assertThat(bannerRepository.findById(banner.getBannerId())).isEmpty();
    }


}