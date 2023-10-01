CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ds_address` varchar(255) DEFAULT NULL,
  `nm_first_name` varchar(255) NOT NULL,
  `sg_gender` varchar(255) DEFAULT NULL,
  `nm_last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) 
