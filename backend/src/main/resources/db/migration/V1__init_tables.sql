CREATE TABLE recipe (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    rating FLOAT,
    nb_people INT,
    duration INT,
    UNIQUE (name)
);

CREATE TABLE recipe_season (
    recipe_id BIGINT NOT NULL,
    season VARCHAR(255) NOT NULL,
    PRIMARY KEY (recipe_id, season),
    FOREIGN KEY (recipe_id) REFERENCES recipe(id)
);

CREATE TABLE ingredient (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    UNIQUE (name, type)
);

CREATE TABLE recipe_ingredient (
    id BIGSERIAL PRIMARY KEY,
    quantity FLOAT NOT NULL,
    unit VARCHAR(50) NOT NULL,
    recipe_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipe(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(id) ON DELETE CASCADE,
    UNIQUE (ingredient_id, recipe_id)
);

