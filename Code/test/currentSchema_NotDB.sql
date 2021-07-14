CREATE TABLE logins (
	user_id INTEGER PRIMARY KEY autoincrement,
	username varchar(20) NOT NULL UNIQUE,
	pass_word varchar(20) NOT NULL
);

CREATE TABLE ingredient (
	user_id INTEGER not null,
 	item_num INTEGER NOT NULL check (item_num > 0),
	ingredient_name varchar(20) NOT NULL,
	expiration_date varchar(20),
 	par_amount int NOT NULL,
	quantity int NOT NULL,
	quantity_type varchar(20),
 	foreign key (user_id) references logins(user_id)
);

CREATE TABLE recipes (
	user_id INTEGER not null,
	recipe_num INTEGER NOT NULL check (recipe_num > 0),
	recipe_name varchar(50) NOT NULL,
	cook_time varchar(20),
	prep_time varchar(20),
	executable int NOT NULL,
	foreign key (user_id) references logins(user_id)
);

CREATE TABLE ingredientlist (
  user_id Integer NOT NULL,
  recipe_num INTEGER,
  ingredient_name varchar(20),
  amount int NOT NULL check (amount > 0),
  foreign key (recipe_num) references recipes(recipe_num),
  foreign key (user_id) references logins(user_id)
  );
  
CREATE TABLE recipeSteps (
	user_id Integer NOT NULL,
	recipe_num INTEGER,
 	step_num int NOT NULL,
	step_desc varchar(200),
	foreign key (recipe_num) references recipes(recipe_num),
	foreign key (user_id) references logins(user_id)
  );