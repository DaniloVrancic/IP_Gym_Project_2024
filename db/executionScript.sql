INSERT INTO `db_online_fitness`.`user` (`username`, `password`, `first_name`, `last_name`, `city`, `email`, `activated`, `type`) VALUES ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Admin', 'User', 'Beograd', 'admin@hotmail.com', '1', '1');
INSERT INTO `db_online_fitness`.`user` (`username`, `password`, `first_name`, `last_name`, `city`, `email`, `activated`, `type`) VALUES ('advisor', '3ec7e6111cd62b68099dc623aea2cf3c69038529ce07617bfb3c143f2fe9e103', 'Advisor', 'User', 'Banja Luka', 'advisor@mail.com', '1', '2');

INSERT INTO `db_online_fitness`.`fitness_program_type` (`name`) VALUES ('Strength');
INSERT INTO `db_online_fitness`.`fitness_program_type` (`name`) VALUES ('Cardio');
INSERT INTO `db_online_fitness`.`fitness_program_type` (`name`) VALUES ('HIIT');
INSERT INTO `db_online_fitness`.`fitness_program_type` (`name`) VALUES ('Flexibility');

INSERT INTO `db_online_fitness`.`fitness_program` (`name`, `description`, `location_of_event`, `price`, `duration`, `difficulty_level`, `status`, `fitness_program_type_id`, `user_id`) VALUES ('Jogging', 'A nice, regular excercise.', 'Banja Luka', '39.99', '180', '1', '0', '2', '1');
