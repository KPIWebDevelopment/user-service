-- V2__insert_sample_users.sql
INSERT INTO users (id, name, email, password, created_at) VALUES
  (gen_random_uuid(), 'Alice', 'alice@example.com', '$2a$10$Ayo4/1tPrYwrAw2sIbqdHOuJ6zo.x9EhU0T3ADCqivhaGoSi73zVy', now()),
  (gen_random_uuid(), 'Bob', 'bob@example.com', 'password2', now());
