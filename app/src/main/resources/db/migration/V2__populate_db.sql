INSERT INTO client (name) VALUES
('Ruslan Nepotenko'),
('Pavlo Kulbaba'),
('Martin Nebaba'),
('Petro Karpovich'),
('Vitaliy Strogyi'),
('Dima Demidenko'),
('Yulia Gorlova'),
('Tanay Berezhko'),
('Vasily Dovzhenok'),
('Yuriy Bunak');

INSERT INTO planet (id, name) VALUES
('MER', 'Mercury'),
('EAR', 'Earth'),
('VEN', 'Venus'),
('MARS', 'Mars'),
('JUP', 'Jupiter'),
('SAT', 'Saturn'),
('URAN', 'Uranus'),
('NEP', 'Neptune');

INSERT INTO ticket (created_at, client_id, from_planet_id, to_planet_id) VALUES
('2024-10-23 14:32:01 UTC', 1, 'EAR', 'MARS'),
('2024-09-13 05:01:01 UTC', 2, 'EAR', 'MER'),
('2024-08-05 22:08:55 UTC', 3, 'JUP', 'SAT'),
('2024-08-15 12:45:00 UTC', 4, 'URAN', 'NEP'),
('2024-08-01 21:05:00 UTC', 5, 'URAN', 'EAR'),
('2024-08-01 22:30:00 UTC', 6, 'SAT', 'EAR'),
('2024-07-28 15:21:00 UTC', 7, 'VEN', 'NEP'),
('2024-07-28 16:35:00 UTC', 8, 'JUP', 'MARS'),
('2024-07-27 09:30:00 UTC', 9, 'MARS', 'EAR'),
('2024-07-25 14:19:00 UTC', 10, 'NEP', 'SAT');