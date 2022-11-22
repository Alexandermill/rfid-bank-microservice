INSERT INTO bank_client (id, email, first_name, identification_number, last_name)
VALUES ('1', 'sam@gmail.com', 'Sam', '808829932V', 'Silva');
INSERT INTO bank_client (id, email, first_name, identification_number, last_name)
VALUES ('2', 'guru@gmail.com', 'Guru', '901830556V', 'Darmaraj');
INSERT INTO bank_client (id, email, first_name, identification_number, last_name)
VALUES ('3', 'ragu@gmail.com', 'Ragu', '348829932V', 'Sivaraj');
INSERT INTO bank_client (id, email, first_name, identification_number, last_name)
VALUES ('4', 'randor@gmail.com', 'Randor', '842829932V', 'Manoon');


INSERT INTO account_ref (number, client_id)
VALUES (100015003000, '1'),
       (100015003001, '1'),
       (100015003002, '2'),
       (100015003003, '2'),
       (100015003004, '2'),
       (100015003005, '3'),
       (100015003006, '3'),
       (100015003007, '3'),
       (100015003008, '3'),
       (100015003009, '3'),
       (100015003010, '4'),
       (100015003011, '4'),
       (100015003012,'4'),
       (100015003013,'4');