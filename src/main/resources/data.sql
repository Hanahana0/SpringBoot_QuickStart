INSERT INTO comment (id, content) VALUES (1, '안녕하세요, 첫 번째 댓글입니다!');
INSERT INTO comment (id, content) VALUES (2, '테스트용 두 번째 댓글입니다.');
INSERT INTO comment (id, content) VALUES (3, 'XSS 테스트: <script>alert("XSS")</script>');
INSERT INTO comment (id, content) VALUES (4, 'XSS 테스트: <div><h2>React 좋네</h2></div>');




INSERT INTO users (username, password) VALUES ('alas', '1111');


-- 실제 사용할 만한 조합의 다국어 메시지 예제 데이터 삽입
INSERT INTO TRANSLATION (MSG, LANG, TRANSLATION_TEXT) VALUES
    ('LOGIN_SUCCESS', 'en', 'Login successful'),
    ('LOGIN_SUCCESS', 'ko', '로그인 성공'),
    ('LOGIN_FAILURE', 'en', 'Login failed. Please try again.'),
    ('LOGIN_FAILURE', 'ko', '로그인 실패. 다시 시도해주세요.'),
    ('USER_REGISTRATION_COMPLETE', 'en', 'User registration completed'),
    ('USER_REGISTRATION_COMPLETE', 'ko', '사용자 등록 완료'),
    ('DATA_SAVE_SUCCESS', 'en', 'Data saved successfully'),
    ('DATA_SAVE_SUCCESS', 'ko', '데이터가 성공적으로 저장되었습니다.'),
    ('INVALID_INPUT', 'en', 'Invalid input. Please check your data.'),
    ('INVALID_INPUT', 'ko', '잘못된 입력입니다. 데이터를 확인하세요.'),
    ('ACCESS_DENIED', 'en', 'Access denied'),
    ('ACCESS_DENIED', 'ko', '접근이 거부되었습니다.'),
    ('SESSION_EXPIRED', 'en', 'Session expired. Please log in again.'),
    ('SESSION_EXPIRED', 'ko', '세션이 만료되었습니다. 다시 로그인해주세요.'),
    ('PASSWORD_RESET', 'en', 'Password reset successful'),
    ('PASSWORD_RESET', 'ko', '비밀번호 재설정 성공');

-- 나머지 행은 랜덤 메시지로 채워서 5만 개까지 생성 (테스트 데이터 추가)
INSERT INTO TRANSLATION (MSG, LANG, TRANSLATION_TEXT)
SELECT
    CASE MOD(ROWNUM, 8)
        WHEN 0 THEN 'LOGIN_SUCCESS'
        WHEN 1 THEN 'LOGIN_FAILURE'
        WHEN 2 THEN 'USER_REGISTRATION_COMPLETE'
        WHEN 3 THEN 'DATA_SAVE_SUCCESS'
        WHEN 4 THEN 'INVALID_INPUT'
        WHEN 5 THEN 'ACCESS_DENIED'
        WHEN 6 THEN 'SESSION_EXPIRED'
        WHEN 7 THEN 'PASSWORD_RESET'
    END AS MSG,
    CASE MOD(ROWNUM, 2)
        WHEN 0 THEN 'en'
        WHEN 1 THEN 'ko'
    END AS LANG,
    CASE MOD(ROWNUM, 8)
        WHEN 0 THEN 'Login successful'
        WHEN 1 THEN '로그인 성공'
        WHEN 2 THEN 'Login failed. Please try again.'
        WHEN 3 THEN '로그인 실패. 다시 시도해주세요.'
        WHEN 4 THEN 'User registration completed'
        WHEN 5 THEN '사용자 등록 완료'
        WHEN 6 THEN 'Data saved successfully'
        WHEN 7 THEN '데이터가 성공적으로 저장되었습니다.'
        WHEN 8 THEN 'Invalid input. Please check your data.'
        WHEN 9 THEN '잘못된 입력입니다. 데이터를 확인하세요.'
        WHEN 10 THEN 'Access denied'
        WHEN 11 THEN '접근이 거부되었습니다.'
        WHEN 12 THEN 'Session expired. Please log in again.'
        WHEN 13 THEN '세션이 만료되었습니다. 다시 로그인해주세요.'
        WHEN 14 THEN 'Password reset successful'
        WHEN 15 THEN '비밀번호 재설정 성공'
    END AS TRANSLATION_TEXT
FROM SYSTEM_RANGE(1, 49984);