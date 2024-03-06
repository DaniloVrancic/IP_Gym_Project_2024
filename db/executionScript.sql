INSERT INTO `db_online_fitness`.`user` (`username`, `password`, `first_name`, `last_name`, `city`, `email`, `activated`, `type`) VALUES ('user1', '0a041b9462caa4a31bac3567e0b6e6fd9100787db2ab433d96f6d178cabfce90', 'Danilo', 'V', 'Banja Luka', 'danilo.vrancic@student.etf.unibl.org', '0', '1');

INSERT INTO `db_online_fitness`.`fitness_program_type` (`name`) VALUES ('Snaga');
INSERT INTO `db_online_fitness`.`fitness_program_type` (`name`) VALUES ('Kardio');

INSERT INTO `db_online_fitness`.`fitness_program` (`name`, `description`, `location_of_event`, `price`, `duration`, `difficulty_level`, `status`, `fitness_program_type_id`, `user_id`) VALUES ('Jogging', 'A nice, regular excercise.', 'Banja Luka', '39.99', '180', '1', '0', '2', '1');
