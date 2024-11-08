package qlinx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qlinx.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // createdAt 필드를 기준으로 날짜 범위 검색
    List<User> findByUsernameContainingAndCreatedAtBetween(String name, LocalDateTime startDate, LocalDateTime endDate);

    List<User> findByUsernameContaining(String username);
}
