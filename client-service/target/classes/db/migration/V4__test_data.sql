INSERT INTO bank_client (id, email, first_name, identification_number, last_name)
VALUES ('1', 'sap@gmail.com', 'Алексей', '808829932V', 'Сапожников');
INSERT INTO bank_client (id, email, first_name, identification_number, last_name)
VALUES ('2', 'umel@gmail.com', 'Юлия', '901830556V', 'Мелконян');
INSERT INTO bank_client (id, email, first_name, identification_number, last_name)
VALUES ('3', 'silentbob@gmail.com', 'Bob', '348829932V', 'Silent');
INSERT INTO bank_client (id, email, first_name, identification_number, last_name)
VALUES ('4', 'pamuk@gmail.com', 'Омар', '842829932V', 'Памук');


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