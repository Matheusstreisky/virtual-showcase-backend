-- DML
INSERT INTO account(id, login, password) VALUES(1, 'admin', '$2a$12$8oUjHn0DUJ/cuufvnr7zPuA65nGNE/V7INlOxki.wa5IhXOdpSQwq')
    ON CONFLICT (id) DO NOTHING;
