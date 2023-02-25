insert into roles (id, role_name) values (1,'ADMIN'),(2,'USER');
insert into users (id, first_name, last_name , password, tckn) values (1, 'Admin', 'User', '$2a$10$CNC2/e5PC.e2cPsNIfyaTeggwcqeIkkY4ciWdDNWG44a2ubBQqCRa', '10000000000');
insert into user_roles (user_id, role_id) values (0x31000000000000000000000000000000, 1);
