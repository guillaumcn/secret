CREATE TABLE `user`
(
    `uuid`       VARCHAR(36) PRIMARY KEY,
    `email`      VARCHAR(255) UNIQUE NOT NULL,
    `password`   LONGTEXT            NOT NULL,
    `first_name` VARCHAR(255)        NOT NULL,
    `last_name`  VARCHAR(255)        NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `group`
(
    `uuid`       VARCHAR(36) PRIMARY KEY,
    `name`       VARCHAR(255) NOT NULL,
    `owner_uuid` VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `group_user`
(
    `user_uuid`  VARCHAR(36) NOT NULL,
    `group_uuid` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`user_uuid`, `group_uuid`)
);

CREATE TABLE `assignment`
(
    `uuid`             VARCHAR(36) PRIMARY KEY,
    `source_user_uuid` VARCHAR(36) NOT NULL,
    `target_user_uuid` VARCHAR(36) NOT NULL,
    `group_uuid`       VARCHAR(36) NOT NULL,
    `created_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (`source_user_uuid`, `target_user_uuid`, `group_uuid`)
);

CREATE TABLE `assignment_exception`
(
    `uuid`             VARCHAR(36) PRIMARY KEY,
    `source_user_uuid` VARCHAR(36) NOT NULL,
    `target_user_uuid` VARCHAR(36) NOT NULL,
    `group_uuid`       VARCHAR(36) NOT NULL,
    `created_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (`source_user_uuid`, `target_user_uuid`, `group_uuid`)
);

CREATE TABLE `note`
(
    `uuid`            VARCHAR(36) PRIMARY KEY,
    `assignment_uuid` VARCHAR(36) UNIQUE NOT NULL,
    `value`           LONGTEXT           NOT NULL,
    `created_at`      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `group`
    ADD FOREIGN KEY (`owner_uuid`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `group_user`
    ADD FOREIGN KEY (`user_uuid`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `group_user`
    ADD FOREIGN KEY (`group_uuid`) REFERENCES `group` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `assignment`
    ADD FOREIGN KEY (`source_user_uuid`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `assignment`
    ADD FOREIGN KEY (`target_user_uuid`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `assignment`
    ADD FOREIGN KEY (`group_uuid`) REFERENCES `group` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `assignment_exception`
    ADD FOREIGN KEY (`source_user_uuid`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `assignment_exception`
    ADD FOREIGN KEY (`target_user_uuid`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `assignment_exception`
    ADD FOREIGN KEY (`group_uuid`) REFERENCES `group` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `note`
    ADD FOREIGN KEY (`assignment_uuid`) REFERENCES `assignment` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE;

INSERT INTO `user` (uuid, email, password, first_name, last_name)
VALUES (uuid(), 'admin@admin.admin', '$2a$10$i51P4WuOtnNQyCugZl0ss.6mJufUBlNjVcXDDBa4arHCoNx/oD8DW', 'Admin', 'Admin');
