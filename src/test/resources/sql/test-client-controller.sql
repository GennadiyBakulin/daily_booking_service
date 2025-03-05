insert into apartment
values (1, 'Barnaul', 'Lenina', '46', 'ONLY_ROOM'),
       (2, 'Aleysk', 'Sovetskaya', '102', 'ONLY_ROOM');

insert into advert
values (1, 100, true, 1, 'Наем комнаты Barnaul'),
       (2, 1000, true, 2, 'Наем комнаты Aleysk');

insert into client
values (1, 'Petr', 'mail@mail.ru');

insert into booking
values (1, '2025-01-01', '2025-01-10', 1, 1, 900),
       (2, '2025-02-01', '2025-02-10', 1, 1, 900),
       (3, '2025-03-01', '2025-03-10', 1, 2, 900),
       (4, '2025-04-01', '2025-04-10', 1, 2, 900),
       (5, '2025-05-01', '2025-05-10', 1, 2, 900);