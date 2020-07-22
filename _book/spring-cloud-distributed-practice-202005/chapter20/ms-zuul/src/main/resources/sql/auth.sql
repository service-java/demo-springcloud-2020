# 创建2个数据库实例
create database sc_chapter20_product_1;
create database sc_chapter20_product_2;

# 使用sc_chapter20_product_1数据库实例
use sc_chapter20_product_1;
# 建产品表
create table t_product (
id bigint primary key,
product_name varchar(60) not null,
stock int(12) not null default 0,
note varchar(512) null
);

# 建产品销售表
create table t_product_sales_details(
id bigint primary key,
xid bigint, # 业务流水交易序号
product_id bigint not null,
user_name varchar(60) not null,
quantity int(12) not null,
sale_date date not null ,
note varchar(256) null
);

# 创建索引
create index product_id_sales_idex on t_product_sales_details(product_id);
create index product_user_sales_idex on t_product_sales_details(user_name);

# 测试数据
insert into t_product(id, product_name, stock, note)
    values(68119486682775552, 'product_name_abc', 10, '测试产品1');

# 使用sc_chapter20_product_2数据库实例
use sc_chapter20_product_2;
create table t_product (
id bigint primary key,
product_name varchar(60) not null,
stock int(12) not null default 0,
note varchar(512) null
);

# 建产品销售表
create table t_product_sales_details(
id bigint primary key,
xid bigint , # 业务流水交易序号
product_id bigint not null,
user_name varchar(60) not null,
quantity int(12) not null,
sale_date date not null,
note varchar(256) null
);

# 创建索引
create index product_id_sales_idex on t_product_sales_details(product_id);
create index product_user_sales_idex on t_product_sales_details(user_name);

# 测试数据
insert into t_product(id, product_name, stock, note)
values(68119486682775551, 'product_name_def', 20, '测试产品2');


