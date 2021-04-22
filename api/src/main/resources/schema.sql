CREATE EXTENSION IF NOT EXISTS 'uuid-ossp';

CREATE TYPE IF NOT EXISTS runtime_type AS ENUM('Java');
CREATE TYPE IF NOT EXISTS status_type AS ENUM('Ready', 'Done', 'Error');

CREATE INDEX IF NOT EXISTS lambda_name_index ON Lambda (name);
CREATE INDEX IF NOT EXISTS execution_lambda_id_index ON Execution (lambda_id);

CREATE TABLE IF NOT EXISTS Lambda (
  id uuid DEFAULT uuid_generate_v4(),
  name text NOT NULL,
  description text NOT NULL,
  runtime runtime_type NOT NULL,
  source text NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT lambda_pk_constraint PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Execution (
  id uuid DEFAULT uuid_generate_v4(),
  lambda_id uuid NOT NULL,
  arguments jsonb[] NOT NULL,
  result jsonb NOT NULL,
  log text NOT NULL,
  status status_type NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  CONSTRAINT execution_pk_constraint PRIMARY KEY (id),
  CONSTRAINT execution_lambda_id_fk_constraint
    FOREIGN KEY (lambda_id)
    REFERENCES Lambda(id)
    ON DELETE CASCADE
);
