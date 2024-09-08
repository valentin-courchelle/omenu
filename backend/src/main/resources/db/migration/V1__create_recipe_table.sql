-- ----------------------------
-- Create hibernate_sequence structure
-- ----------------------------
CREATE SEQUENCE "public"."recipe_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE recipe (
    id int8 NOT NULL,
    name varchar(255) NOT NULL,
    description text,
    duration int,
    rating float,
    nb_people int,
    season_start date,
    season_end date,
    PRIMARY KEY(id)
)