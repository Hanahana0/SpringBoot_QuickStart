package qlinx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qlinx.entity.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findByParentId(String parentId); // `parentId`의 타입을 String으로 수정
}