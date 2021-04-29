create extension if not exists "uuid-ossp";;

do $$
begin
  create type runtime_type as enum('Java');
  create type status_type as enum('Ready', 'Done', 'Error');
exception
  when duplicate_object then
    raise notice 'types "runtime_type" and "status_type" already exist, skipping';
end $$;;

create table if not exists lambda (
  id uuid default uuid_generate_v4(),
  name text unique not null,
  description text not null,
  runtime runtime_type not null,
  source text not null,
  created_at timestamp not null,
  updated_at timestamp not null,
  constraint lambda_pk_constraint primary key (id)
);;

create table if not exists execution (
  id uuid default uuid_generate_v4(),
  lambda_id uuid not null,
  arguments json not null,
  result json not null,
  log text not null,
  status status_type not null,
  created_at timestamp not null,
  updated_at timestamp not null,
  constraint execution_pk_constraint primary key (id),
  constraint execution_lambda_id_fk_constraint
    foreign key (lambda_id)
    references lambda (id)
    on delete cascade
);;

create index if not exists lambda_name_index on lambda (name);;
create index if not exists execution_lambda_id_index on execution (lambda_id);;
