package manson112.github.musinsa.assignment.menus.repository;

import manson112.github.musinsa.assignment.menus.controller.dto.MenuSelectResponse;
import manson112.github.musinsa.assignment.menus.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MenuRepository {
    Optional<Menu> findById(Long menuId);

    List<MenuSelectResponse> findMenuHierarchy(Long menuId);

    void createMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Long menuId);

    void deleteAll();
}
