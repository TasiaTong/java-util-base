SET NAMES uft8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `user_info`
(
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `name` varchar(50) NOT NULL DEFAULT '',
      `age` int(11) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `name_index` (`name`)
) ENGINE = InnoDB
DEFAULT CHARSET = uft8mb4;