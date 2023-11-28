ALTER TABLE topico ADD COLUMN ativo BIT NOT NULL;
UPDATE topico SET ativo = 1;
