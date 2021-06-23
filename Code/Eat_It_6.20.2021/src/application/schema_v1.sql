create table logins (
    user_id INTEGER PRIMARY KEY autoincrement,
    username varchar(20) NOT NULL UNIQUE,
  pass_word varchar(20) NOT NULL
);

create table ingredient (
  user_id varchar(20),
  item_num INTEGER PRIMARY KEY autoincrement NOT NULL UNIQUE check (item_num > 0),
  ingredient_name varchar(20) NOT NULL UNIQUE,
  expiration_date varchar(20),
  par_amount int NOT NULL,
  quantity int NOT NULL,
  quantity_type varchar(20),
  foreign key (user_id) references logins(user_id)
);


create table recipes (
    user_id varchar(20),
  recipe_num INTEGER PRIMARY KEY autoincrement NOT NULL UNIQUE check (recipe_num > 0),
  recipe_name varchar(50) NOT NULL UNIQUE,
    cook_time varchar(20),
  prep_time varchar(20),
  executable int NOT NULL,
  foreign key (user_id) references logins(user_id)
);

create table ingredientlist (
  recipe_num varchar(50),
  ingredient_name varchar(20),
  amount int NOT NULL check (amount > 0),
  foreign key (recipe_num) references recipes(recipe_num)
  );

create table recipeSteps (
  recipe_num varchar(50),
  step_num varchar(20),
  step_desc varchar(200),
  foreign key (recipe_num) references recipes(recipe_num)
  );