CREATE TABLE `user_video_mapping` (
                                      `id` INT(11) NOT NULL AUTO_INCREMENT,
                                      `user_id` INT(11) NOT NULL,
                                      `video_id` INT(11) NOT NULL,
                                      PRIMARY KEY (`id`),
);
