ALTER TABLE ingredient ADD CONSTRAINT unique_name_type UNIQUE(name, type);
ALTER TABLE recipe ADD CONSTRAINT unique_name UNIQUE(name);