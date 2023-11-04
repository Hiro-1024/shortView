CREATE TABLE `user` (
                        `id` INT(11) NOT NULL AUTO_INCREMENT,
                        `username` VARCHAR(255) UNIQUE NOT NULL,
                        `password` VARCHAR(255) NOT NULL,
                        PRIMARY KEY (`id`)
);
