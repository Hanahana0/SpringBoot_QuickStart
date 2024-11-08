INSERT INTO users (username, password, email)
VALUES ('alas', '1111','hy.lee@oneqic.co.kr');


-- 영어 번역 데이터
INSERT INTO translation (msg, lang, translation_text)
VALUES ('SECURITY_MGMT', 'en', 'Security Management'),
       ('ADMIN_MENU', 'en', 'Admin management'),
       ('WN_MENU', 'en', 'Wharhouse management'),
       ('ROLE_MGMT', 'en', 'Role Management'),
       ('SYSTEM_MGMT', 'en', 'System Management'),
       ('IF_MNG', 'en', 'IF Management'),
       ('IF_SEND_REC_HIST_SEARCH', 'en', 'IF Send/Receive History Search'),
       ('SCHEDULER_MGMT', 'en', 'Scheduler Management'),
       ('USER_MGMT', 'en', 'User Management'),
       ('USER_GRP_MGMT', 'en', 'User Group Setting'),
       ('SCR_ROLE_MGMT', 'en', 'Role Setting'),
       ('SYSTEM_CONFIG', 'en', 'System Configuration'),
       ('USE_HIST', 'en', 'Usage History'),
       ('CONN_HIST', 'en', 'Connection History'),
       ('PRGM_MGMT', 'en', 'Program Management'),
       ('CD_MGMT', 'en', 'Code Management'),
       ('MENU_MGMT', 'en', 'Menu Management'),
       ('LANG_MGMT', 'en', 'Language Management'),
       ('EXCEL_UPLOAD_SET', 'en', 'Excel Upload Setting'),
       ('EXCEL_UPLOAD_FORM_MGMT', 'en', 'Excel Upload Form Management'),
       ('IF_JOB_LIST', 'en', 'IF Send/Receive List'),
       ('MSGSEND_MASTER', 'en', 'Mail, SMS Send Master'),
       ('DRIVER_USER_MGMT', 'en', 'Driver User Management'),
       ('MSGSEND_DATA', 'en', 'Mail, SMS Send Data'),
       ('DATE', 'en', 'Date'),
       ('NAME', 'en', 'Name'),
       ('ERROR_LOG', 'en', 'Error Log');

-- 한국어 번역 데이터
INSERT INTO translation (msg, lang, translation_text)
VALUES ('SECURITY_MGMT', 'ko', '보안관리'),
       ('ADMIN_MENU', 'ko', '설정관리'),
       ('WN_MENU', 'ko', '창고관리'),
       ('ROLE_MGMT', 'ko', '권한관리'),
       ('SYSTEM_MGMT', 'ko', '시스템관리'),
       ('IF_MNG', 'ko', 'IF관리'),
       ('IF_SEND_REC_HIST_SEARCH', 'ko', 'IF송수신이력조회'),
       ('SCHEDULER_MGMT', 'ko', '스케줄 관리'),
       ('USER_MGMT', 'ko', '사용자관리'),
       ('USER_GRP_MGMT', 'ko', '사용자그룹설정'),
       ('SCR_ROLE_MGMT', 'ko', '권한설정'),
       ('SYSTEM_CONFIG', 'ko', '시스템설정'),
       ('USE_HIST', 'ko', '사용이력'),
       ('CONN_HIST', 'ko', '접속이력'),
       ('PRGM_MGMT', 'ko', '프로그램관리'),
       ('CD_MGMT', 'ko', '코드관리'),
       ('MENU_MGMT', 'ko', '메뉴관리'),
       ('LANG_MGMT', 'ko', '언어관리'),
       ('EXCEL_UPLOAD_SET', 'ko', '엑셀업로드 설정'),
       ('EXCEL_UPLOAD_FORM_MGMT', 'ko', '엑셀업로드양식관리'),
       ('IF_JOB_LIST', 'ko', 'IF송/수신 리스트'),
       ('MSGSEND_MASTER', 'ko', 'Mail,SMS전송 Master'),
       ('DRIVER_USER_MGMT', 'ko', '운전원사용자관리'),
       ('MSGSEND_DATA', 'ko', 'Mail,SMS전송정보'),
       ('DATE', 'ko', '날짜'),
       ('NAME', 'ko', '이름'),
       ('ERROR_LOG', 'ko', '에러로그');


-- 최상위 루트 메뉴 (parent_id가 NULL인 경우)
INSERT INTO menu (id, app_code, title, description, menu_level, parent_id)
VALUES ('ADM', 'ADM', 'ADMIN_MENU', '관리자 메뉴', 0, NULL),
       ('WM', 'WM', 'WN_MENU', '창고관리', 0, NULL);

-- 그 다음, 기존 최상위 메뉴들을 모두 'ADM'을 parent_id로 갖는 하위 메뉴로 변경
INSERT INTO menu (id, app_code, title, description, menu_level, parent_id)
VALUES ('ADM101010', 'ADM', 'SECURITY_MGMT', '보안관리', 1, 'ADM'),
       ('ADM101210', 'ADM', 'ROLE_MGMT', '권한관리', 1, 'ADM'),
       ('ADM111010', 'ADM', 'SYSTEM_MGMT', '시스템관리', 1, 'ADM'),
       ('WM101010', 'WM', 'WM_MGMT', '창고마스터', 1, 'WM');

-- 기존 하위 메뉴들은 최상위 메뉴의 ID를 parent_id로 유지
INSERT INTO menu (id, app_code, title, description, menu_level, parent_id, path, component_path)
VALUES
    ('ADM112110', 'ADM', 'IF_MNG', 'IF관리', 2, 'ADM111010', '/adm/ifManagement', '../pages/adm/ifManagement/IfManagement'),
    ('ADM112120', 'ADM', 'IF_SEND_REC_HIST_SEARCH', 'IF송수신이력조회', 2, 'ADM111010', '/adm/ifSendReceiveHistory', '../pages/adm/ifSendReceiveHistory/IfSendReceiveHistory'),
    ('ADM111910', 'ADM', 'SCHEDULER_MGMT', '스케줄 관리', 2, 'ADM111010', '/adm/schedulerManagement', '../pages/adm/schedulerManagement/SchedulerManagement'),
    ('ADM101110', 'ADM', 'USER_MGMT', '사용자관리', 2, 'ADM101010', '/adm/userManagement', '../pages/adm/userManagement/UserManagement'),
    ('ADM101211', 'ADM', 'USER_GRP_MGMT', '사용자그룹설정', 2, 'ADM101210', '/adm/userGroupManagement', '../pages/adm/userGroupManagement/UserGroupManagement'),
    ('ADM101213', 'ADM', 'SCR_ROLE_MGMT', '권한설정', 2, 'ADM101210', '/adm/roleManagement', '../pages/adm/roleManagement/RoleManagement'),
    ('ADM101311', 'ADM', 'SYSTEM_CONFIG', '시스템설정', 2, 'ADM111010', '/adm/systemConfig', '../pages/adm/systemConfig/SystemConfig'),
    ('ADM101410', 'ADM', 'USE_HIST', '사용이력', 2, 'ADM101010', '/adm/useHistory', '../pages/adm/useHistory/UseHistory'),
    ('ADM101710', 'ADM', 'CONN_HIST', '접속이력', 2, 'ADM101010', '/adm/connectionHistory', '../pages/adm/connectionHistory/ConnectionHistory'),
    ('ADM101810', 'ADM', 'PRGM_MGMT', '프로그램관리', 2, 'ADM101010', '/adm/programManagement', '../pages/adm/programManagement/ProgramManagement'),
    ('ADM111110', 'ADM', 'CD_MGMT', '코드관리', 2, 'ADM111010', '/adm/codeManagement', '../pages/adm/codeManagement/CodeManagement'),
    ('ADM111210', 'ADM', 'MENU_MGMT', '메뉴관리', 2, 'ADM111010', '/adm/menuManagement', '../pages/adm/menuManagement/MenuManagement'),
    ('ADM111410', 'ADM', 'LANG_MGMT', '언어관리', 2, 'ADM111010', '/adm/languageManagement', '../pages/adm/languageManagement/LanguageManagement'),
    ('ADM111510', 'ADM', 'EXCEL_UPLOAD_SET', '엑셀업로드 설정', 2, 'ADM111010', '/adm/excelUploadSettings', '../pages/adm/excelUploadSettings/ExcelUploadSettings'),
    ('ADM111610', 'ADM', 'EXCEL_UPLOAD_FORM_MGMT', '엑셀업로드양식관리', 2, 'ADM111010', '/adm/excelUploadFormManagement', '../pages/adm/excelUploadFormManagement/ExcelUploadFormManagement'),
    ('ADM112010', 'ADM', 'IF_JOB_LIST', 'IF송/수신 리스트', 2, 'ADM111010', '/adm/ifJobList', '../pages/adm/ifJobList/IfJobList'),
    ('ADM112130', 'ADM', 'MSGSEND_MASTER', 'Mail,SMS전송 Master', 2, 'ADM111010', '/adm/msgSendMaster', '../pages/adm/msgSendMaster/MsgSendMaster'),
    ('ADM101112', 'ADM', 'DRIVER_USER_MGMT', '운전원사용자관리', 2, 'ADM101010', '/adm/driverUserManagement', '../pages/adm/driverUserManagement/DriverUserManagement'),
    ('ADM112131', 'ADM', 'MSGSEND_DATA', 'Mail,SMS전송정보', 2, 'ADM111010', '/adm/msgSendData', '../pages/adm/msgSendData/MsgSendData'),
    ('ADM102110', 'ADM', 'ERROR_LOG', '에러로그', 2, 'ADM101010', '/adm/errorLog', '../pages/adm/errorLog/ErrorLog'),
    ('WM101110', 'WM', 'MASTER', '시스템구성', 2, 'WM101010', '/wm/master', '../pages/wm/master/Master');
