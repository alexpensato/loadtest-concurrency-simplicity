CREATE SEQUENCE IF NOT EXISTS category_id_seq START 2;

CREATE TABLE IF NOT EXISTS category (
  id bigint DEFAULT nextval('category_id_seq') PRIMARY KEY,
  name varchar NOT NULL
);

GRANT USAGE, SELECT ON SEQUENCE category_id_seq TO pensato;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE category TO pensato;

CREATE SEQUENCE IF NOT EXISTS company_id_seq START 2;

CREATE TABLE IF NOT EXISTS company (
  id bigint DEFAULT nextval('company_id_seq') PRIMARY KEY,
  title varchar NOT NULL
);

GRANT USAGE, SELECT ON SEQUENCE company_id_seq TO pensato;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE company TO pensato;

CREATE SEQUENCE IF NOT EXISTS product_id_seq START 2;

CREATE TABLE IF NOT EXISTS product (
  id bigint DEFAULT nextval('product_id_seq') PRIMARY KEY,
  description varchar NOT NULL,
  search_pattern varchar NOT NULL,
  price double precision NOT NULL,
  category_rank integer NOT NULL,
  category_id bigint NOT NULL,
  company_id bigint NOT NULL,
  original boolean NOT NULL
);

GRANT USAGE, SELECT ON SEQUENCE product_id_seq TO pensato;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE product TO pensato;

CREATE SEQUENCE IF NOT EXISTS event_config_id_seq START 2;

CREATE TABLE IF NOT EXISTS event_config (
  id bigint DEFAULT nextval('event_config_id_seq') PRIMARY KEY,
  strategy varchar NOT NULL,
  experiment_name varchar NOT NULL,
  config_max_events smallint NOT NULL,
  config_max_time integer NOT NULL,
  config_page_reads smallint NOT NULL,
  config_page_size smallint NOT NULL,
  config_loop_skips smallint NOT NULL,
  executed_events smallint NOT NULL,
  executed_time bigint NOT NULL
);

GRANT USAGE, SELECT ON SEQUENCE event_config_id_seq TO pensato;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE event_config TO pensato;

