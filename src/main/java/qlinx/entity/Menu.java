package qlinx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @Column(name = "id", length = 20)
    private String id;  // 메뉴 코드

    @Column(name = "app_code", nullable = false, length = 10)
    private String appCode;  // 애플리케이션 코드

    @Column(name = "title", nullable = false, length = 50)
    private String title;  // 메뉴 이름

    @Column(name = "description", length = 100)
    private String description;  // 메뉴 설명

    @Column(name = "path", length = 100)
    private String path;  // 메뉴 경로

    @Column(name = "component_path", length = 100)  // 컴포넌트 파일 경로
    private String componentPath;

    @Column(name = "menu_level")
    private Integer menuLevel;  // 메뉴 레벨

    @Column(name = "parent_id", length = 20)
    private String parentId;  // 상위 메뉴 코드

    @Column(name = "use_yn", length = 1)
    private String useYn = "Y";  // 사용 여부 (기본값 'Y')

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;  // 생성 일자

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;  // 수정 일자
}
