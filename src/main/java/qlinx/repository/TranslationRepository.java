package qlinx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qlinx.entity.Translation;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    // msg와 lang이 모두 일치하는 데이터 조회
    List<Translation> findByMsgAndLang(String msg, String lang);

    // lang만 일치하는 데이터 조회
    List<Translation> findByLang(String lang);
}
