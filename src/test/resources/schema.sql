CREATE TABLE IF NOT EXISTS category (
  id IDENTITY NOT NULL PRIMARY KEY,
  name varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS company (
  id IDENTITY NOT NULL PRIMARY KEY,
  title varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
  id IDENTITY NOT NULL PRIMARY KEY,
  description varchar NOT NULL,
  search_pattern varchar NOT NULL,
  price double NOT NULL,
  category_rank integer NOT NULL,
  category_id bigint NOT NULL,
  company_id bigint NOT NULL,
  original boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS event_config (
  id IDENTITY NOT NULL PRIMARY KEY,
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

