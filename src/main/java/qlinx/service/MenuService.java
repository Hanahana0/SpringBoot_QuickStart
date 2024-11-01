package qlinx.service;

import org.springframework.stereotype.Service;
import qlinx.entity.Menu;
import qlinx.repository.MenuRepository;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    // `parentId` 타입을 `String`으로 변경
    public List<Menu> getSubMenus(String parentId) {
        return menuRepository.findByParentId(parentId);
    }
}
