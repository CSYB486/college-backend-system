-- =============================================
-- 学生信息管理系统 建表 + 初始化数据
-- 使用方法：mysql -uroot -p123456 --default-character-set=utf8mb4 < init.sql
-- =============================================
CREATE DATABASE IF NOT EXISTS student_manage DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE student_manage;

-- 系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    username    VARCHAR(50)  NOT NULL COMMENT '用户名',
    password    VARCHAR(100) NOT NULL COMMENT '密码(BCrypt加密)',
    nickname    VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
    create_time DATETIME     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户表';

-- 班级表
CREATE TABLE IF NOT EXISTS clazz (
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(50) NOT NULL COMMENT '班级名称',
    major       VARCHAR(50) DEFAULT NULL COMMENT '专业',
    create_time DATETIME    DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='班级表';

-- 学生表
CREATE TABLE IF NOT EXISTS student (
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    student_no  VARCHAR(20) NOT NULL COMMENT '学号',
    name        VARCHAR(50) NOT NULL COMMENT '姓名',
    gender      TINYINT     NOT NULL DEFAULT 1 COMMENT '性别：1男 2女',
    age         INT         DEFAULT NULL COMMENT '年龄',
    phone       VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    clazz_id    BIGINT      DEFAULT NULL COMMENT '班级id',
    create_time DATETIME    DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_student_no (student_no),
    KEY idx_clazz_id (clazz_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='学生表';

-- 初始账号：admin / 123456
INSERT IGNORE INTO sys_user (username, password, nickname, create_time)
VALUES ('admin', '$2a$10$fkmw8Xj2kPgcFjP.Wf3ep.hiDBhg22DKgT5OasyiVgKnP3mgBcDk2', '管理员', NOW());

-- 初始班级
INSERT IGNORE INTO clazz (id, name, major, create_time) VALUES
(1, '软件工程1班', '软件工程', NOW()),
(2, '计算机科学2班', '计算机科学与技术', NOW()),
(3, '人工智能1班', '人工智能', NOW());

-- 初始学生
INSERT IGNORE INTO student (student_no, name, gender, age, phone, clazz_id, create_time) VALUES
('2024001', '张伟', 1, 20, '13800000001', 1, NOW()),
('2024002', '李娜', 2, 19, '13800000002', 1, NOW()),
('2024003', '王强', 1, 21, '13800000003', 1, NOW()),
('2024004', '赵敏', 2, 20, '13800000004', 2, NOW()),
('2024005', '刘洋', 1, 20, '13800000005', 2, NOW()),
('2024006', '陈静', 2, 19, '13800000006', 2, NOW()),
('2024007', '杨帆', 1, 22, '13800000007', 3, NOW()),
('2024008', '周婷', 2, 20, '13800000008', 3, NOW()),
('2024009', '吴昊', 1, 21, '13800000009', 1, NOW()),
('2024010', '郑爽', 2, 20, '13800000010', 2, NOW()),
('2024011', '孙鹏', 1, 19, '13800000011', 3, NOW()),
('2024012', '黄蕾', 2, 21, '13800000012', 3, NOW());
