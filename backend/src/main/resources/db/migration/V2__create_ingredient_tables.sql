-- ----------------------------
-- Create ingredient_sequence structure
-- ----------------------------
CREATE SEQUENCE "public"."ingredient_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE ingredient (
    id int8 NOT NULL,
    name varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    PRIMARY KEY(id)
);

-- ----------------------------
-- Create recip_ing_sequence structure
-- ----------------------------
CREATE SEQUENCE "public"."recipe_ing_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE recipe_ingredient (
    id int8 NOT NULL,
    ingredient_id int8 NOT NULL REFERENCES ingredient(id),
    quantity float,
    unit varchar(255),
    recipe_id int8 NOT NULL REFERENCES recipe(id),
    PRIMARY KEY(id)
);