INSERT IGNORE INTO user (id, email, password, role, username) VALUES 
  (1, 'admin1@vet.cl', '$2a$12$SIkHXo648aSUQcWHC.9ypuMjlo.22ADTAwBEz5kEYV6gkRoYswueC', 'ADMIN', 'admin1'),
  (2, 'vet1@vet.cl', '$2a$12$oomCI21vijjPQrSefTNbNu1hfOzBYAtDPmnUZKUIIemfIJwKwTvcm', 'VET', 'vet1'),
  (3, 'admin2@vet.cl', '$2a$12$IwgZUwUIZCHmT0sIgGzOceMY8Sd9sDbIPRvw5Xh/aVLkI69tVX0JW', 'ADMIN', 'admin2');