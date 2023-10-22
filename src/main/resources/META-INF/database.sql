CREATE DATABASE IF NOT EXISTS `project21`;

USE `project21`;
CREATE TABLE IF NOT EXISTS `role`
(
    `id`   int(2)       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(255) NOT NULL
);

INSERT INTO `role` (`name`)
VALUES ('admin'),
       ('client');


CREATE TABLE IF NOT EXISTS `users`
(
    `id`        int(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`  varchar(255) NOT NULL,
    `password`  varchar(255) NOT NULL,
    `email`     varchar(255) NOT NULL,
    `full_name` varchar(255) NOT NULL,
    `address`   varchar(255) NOT NULL,
    `role_id`   INT(2)       NOT NULL,
    `isDeleted` tinyint(1)   NULL DEFAULT 0,
    CONSTRAINT `users_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

INSERT INTO users(username, password, email, full_name, address, role_id)
VALUES ('admin', '1234qwer', 'admin@gmail.com', 'Quản trị viên', 'N/A', 1);


CREATE TABLE IF NOT EXISTS `services`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `destinations`
(
    `id`        int(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`      varchar(255) NOT NULL,
    `image`     VARCHAR(255),
    `isDeleted` tinyint(1)   NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS `packages`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`             varchar(255) NOT NULL,
    `price`            double       NOT NULL,
    `destination_id`   INT(11)      NOT NULL,
    `days`             int(11)      NOT NULL,
    `number_of_people` int(11)      NOT NULL,
    `description`      varchar(255) NOT NULL,
    `image`            VARCHAR(255),
    `isDeleted`        tinyint(1)   NULL DEFAULT 0,
    CONSTRAINT fk_packages FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`id`)
);

CREATE TABLE IF NOT EXISTS `bookings`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`    int(11)      NOT NULL,
    `full_name`  varchar(255) NOT NULL,
    `email`      varchar(255) NOT NULL,
    `tour_date`  DATE         NOT NULL,
    `package_id` int(11)      NOT NULL,
    `request`    VARCHAR(1000),
    `status`     ENUM ('pending', 'accepted', 'rejected', 'cancel') DEFAULT 'pending',
    CONSTRAINT fk_booking_users FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT fk_booking_packages FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
)