ALTER TABLE recipe
DROP COLUMN season_start;

ALTER TABLE recipe
DROP COLUMN season_end;

CREATE TABLE recipe_season (
    recipe_id BIGINT NOT NULL,
    season VARCHAR(255) NOT NULL,
    PRIMARY KEY (recipe_id, season),
    FOREIGN KEY (recipe_id) REFERENCES recipe(id)
);

ALTER TABLE recipe_ingredient
ADD CONSTRAINT unique_ingredient_recipe UNIQUE (ingredient_id, recipe_id);