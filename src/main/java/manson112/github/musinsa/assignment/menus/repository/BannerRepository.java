package manson112.github.musinsa.assignment.menus.repository;

import manson112.github.musinsa.assignment.menus.controller.dto.BannerSelectResponse;
import manson112.github.musinsa.assignment.menus.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BannerRepository {
    void createBanner(Banner banner);
    void deleteBanner(Long bannerId);

    void deleteAll();

    Optional<Banner> findById(Long bannerId);

    List<Banner> findByMenuId(Long menuId);
}
