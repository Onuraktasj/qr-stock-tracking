-- temel tablolar

create table product_type(
  id serial primary key,
  name text not null,
  quality text,
  length_mm int not null,
  standard text,
  created_at timestamptz default now()
);

create table hall(
  id serial primary key,
  code text unique not null
);

create table machine(
  id serial primary key,
  name text not null
);

create table app_user(
  id serial primary key,
  username text unique not null,
  password_hash text not null,
  role text not null check (role in ('OPERATOR','WAREHOUSE','SHIPMENT','SUPERVISOR','ADMIN'))
);

create table package(
  id uuid primary key,
  short_code text unique not null,
  product_type_id int references product_type(id),
  machine_id int references machine(id),
  heat_no text,
  lot_no text,
  length_mm int,
  pieces int,
  paket_no int,
  created_at timestamptz default now()
);

create table stock_ledger(
  id bigserial primary key,
  package_id uuid references package(id),
  txn_type text not null check (txn_type in ('IN','MOVE','OUT','COUNT','ADJUST','ALLOCATE','DEALLOCATE')),
  from_hall_id int references hall(id),
  to_hall_id int references hall(id),
  qty_pcs int,
  ref_doc text,
  actor_user_id int references app_user(id),
  notes text,
  created_at timestamptz default now()
);
create index idx_ledger_pkg on stock_ledger(package_id);

create table orders(
  id serial primary key,
  customer text not null,
  tax_no text,
  country text,
  order_date date default current_date,
  status text default 'OPEN'
);

create table order_line(
  id serial primary key,
  order_id int references orders(id) on delete cascade,
  product_type_id int references product_type(id),
  length_mm int,
  qty_pcs int not null
);

create table allocation(
  id serial primary key,
  order_line_id int references order_line(id) on delete cascade,
  package_id uuid references package(id),
  qty_pcs int not null,
  created_at timestamptz default now()
);

-- seed: 10 hol + 11 makine
insert into hall(code)
select 'Hol-'||lpad(i::text,2,'0') from generate_series(1,10) as s(i);

insert into machine(name)
select 'Hatt-'||lpad(i::text,2,'0') from generate_series(1,11) as s(i);

-- örnek kullanıcı (admin / admin123) -- prod'da değiştir
-- bcrypt hash: admin123 (örnek)
insert into app_user(username,password_hash,role)
values ('admin','$2a$10$3u9cL6wZq1LZkWj0lM2vNe2xgkV9V5Uzk1e0xQK1wAqTt9cGvYcT2','ADMIN');

