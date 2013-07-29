INSERT INTO `birjan`.`users` (`id`, `created`, `createdDate`, `enabled`, `password`, `username`, `commisionRate`) VALUES (NULL, NOW(), NOW(), '1', '', 'ad', '0.2');
INSERT INTO `birjan`.`users` (`id`, `created`, `createdDate`, `enabled`, `password`, `username`, `commisionRate`) VALUES (NULL, NOW(), NOW(), '1', '', 'ma', '0.2');
INSERT INTO `birjan`.`users` (`id`, `created`, `createdDate`, `enabled`, `password`, `username`, `commisionRate`) VALUES (NULL, NOW(), NOW(), '1', '', 'u1', '0.2');
INSERT INTO `birjan`.`users` (`id`, `created`, `createdDate`, `enabled`, `password`, `username`, `commisionRate`) VALUES (NULL, NOW(), NOW(), '1', '', 'u2', '0.2');
INSERT INTO `birjan`.`users` (`id`, `created`, `createdDate`, `enabled`, `password`, `username`, `commisionRate`) VALUES (NULL, NOW(), NOW(), '1', '', 'u3', '0.2');
INSERT INTO `birjan`.`users` (`id`, `created`, `createdDate`, `enabled`, `password`, `username`, `commisionRate`) VALUES (NULL, NOW(), NOW(), '1', '', '1491378438', '0.2');
INSERT INTO `birjan`.`agency` (`id`, `created`, `createdDate`, `enabled`, `principal_id`, `email`, `commisionRate`) VALUES (NULL, NOW(), NOW(), '1', '1', 'csobrero@gmail.com', '0.2');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_ADMIN', 'ad');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_MANAGER', 'ad');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_USER', 'ad');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_MANAGER', 'ma');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_USER', 'ma');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_USER', 'u1');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_USER', 'u2');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_USER', 'u3');
INSERT INTO `birjan`.`authorities` (`id`, `created`, `createdDate`, `authority`, `username`) VALUES (NULL, NOW(), NOW(), 'ROLE_USER', '1491378438');
INSERT INTO `birjan`.`BALANCE` (`id`, `created`, `createdDate`, `cash`, `clearance`, `commission`, `date`, `income`, `payments`, `prizes`, `state`, `user_id`) VALUES (NULL, NOW(), NOW(), '0', '0', '0', NOW(), '0', '0', '0', 'ACTIVE', '1');
INSERT INTO `birjan`.`BALANCE` (`id`, `created`, `createdDate`, `cash`, `clearance`, `commission`, `date`, `income`, `payments`, `prizes`, `state`, `user_id`) VALUES (NULL, NOW(), NOW(), '0', '0', '0', NOW(), '0', '0', '0', 'ACTIVE', '2');
INSERT INTO `birjan`.`BALANCE` (`id`, `created`, `createdDate`, `cash`, `clearance`, `commission`, `date`, `income`, `payments`, `prizes`, `state`, `user_id`) VALUES (NULL, NOW(), NOW(), '0', '0', '0', NOW(), '0', '0', '0', 'ACTIVE', '3');
INSERT INTO `birjan`.`BALANCE` (`id`, `created`, `createdDate`, `cash`, `clearance`, `commission`, `date`, `income`, `payments`, `prizes`, `state`, `user_id`) VALUES (NULL, NOW(), NOW(), '0', '0', '0', NOW(), '0', '0', '0', 'ACTIVE', '4');
INSERT INTO `birjan`.`BALANCE` (`id`, `created`, `createdDate`, `cash`, `clearance`, `commission`, `date`, `income`, `payments`, `prizes`, `state`, `user_id`) VALUES (NULL, NOW(), NOW(), '0', '0', '0', DATE_ADD(NOW(), INTERVAL -1 DAY), '0', '0', '0', 'ACTIVE', '5');
INSERT INTO `birjan`.`BALANCE` (`id`, `created`, `createdDate`, `cash`, `clearance`, `commission`, `date`, `income`, `payments`, `prizes`, `state`, `user_id`) VALUES (NULL, NOW(), NOW(), '0', '0', '0', NOW(), '0', '0', '0', 'ACTIVE', '6');