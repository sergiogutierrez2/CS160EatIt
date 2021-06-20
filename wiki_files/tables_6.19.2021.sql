create table logins (
	user_id INTEGER PRIMARY KEY autoincrement,
	username varchar(20) NOT NULL UNIQUE,
  pass_word varchar(20) NOT NULL
);

insert into logins (user_id, username, pass_word) values (0, 'zuber', 'password');
insert into logins (username, pass_word) values ('zuber1', 'password');

///

user_id  username  pass_word
-------  --------  ---------
0        zuber     password
1        zuber1    password

////

create table ingredient (
	user_id varchar(20),
  item_num int NOT NULL UNIQUE check (item_num > 0) AUTOINCREMENT = 1,
	ingredient_name varchar(20) NOT NULL UNIQUE,
  expiration_date varchar(20),
  par_amount int NOT NULL,
  quantity int NOT NULL,
  quantity_type varchar(20),
	primary key(item_num),
  foreign key (user_id) references logins(user_id)
);

//
insert into ingredient (user_id, ingredient_name, expiration_date, par_amount, quantity, quantity_type) values (1, 'apples', '7-19-2021', 2, 1, 'dozen');

create table recipes (
	user_id varchar(20),
  recipe_num integer NOT NULL UNIQUE check (recipe_num > 0) AUTOINCREMENT,
  recipe_name varchar(50) NOT NULL UNIQUE,
	cook_time varchar(20),
  prep_time varchar(20),
  executable int NOT NULL,
  primary key(recipe_num),
  foreign key (user_id) references logins(user_id)
);

create table ingredientlist (
  recipe_num varchar(50),
  ingredient_name varchar(20),
  amount int NOT NULL check (amount > 0),
  foreign key (recipe_num) references recipes(recipe_num)
  )
  
create table recipeSteps (
  recipe_num varchar(50),
  step_num varchar(20),
  step_desc varchar(200),
  foreign key (recipe_num) references recipes(recipe_num)
  )









CREATE TABLE ingredient (
name varchar(20),
item_num	integer NOT NULL UNIQUE check (item_num > 0),
expiration_date	varchar(20),
quantity	numeric,
primary key (item_num)
);



**********************************************************************
**********************************************************************
**********************************************************************
**********************************************************************
**********************************************************************
**********************************************************************
**********************************************************************



--
-- db2 -td"@" -f p2_sample.sql
--
CONNECT TO CS157A@
--
--
DROP PROCEDURE P2.CUST_CRT@
DROP PROCEDURE P2.CUST_LOGIN@
DROP PROCEDURE P2.ACCT_OPN@
DROP PROCEDURE P2.ACCT_CLS@
DROP PROCEDURE P2.ACCT_DEP@
DROP PROCEDURE P2.ACCT_WTH@
DROP PROCEDURE P2.ACCT_TRX@
DROP PROCEDURE P2.ADD_INTEREST@
--
--
CREATE PROCEDURE P2.CUST_CRT
(IN p_name CHAR(15), IN p_gender CHAR(1), IN p_age INTEGER, IN p_pin INTEGER, OUT id INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
LANGUAGE SQL
  BEGIN
    IF p_gender != 'M' AND p_gender != 'F' THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid gender';
    ELSEIF p_age <= 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid age';
    ELSEIF p_pin < 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid pin';
    ELSE
        INSERT INTO p2.customer (NAME, GENDER, AGE, PIN)
        VALUES(p_name, p_gender, p_age, p2.encrypt(p_pin));
        SET id = (SELECT MAX(ID) FROM p2.customer);
        SET err_msg = '-';
        SET sql_code = 0;
    END IF;
END@

CREATE PROCEDURE P2.CUST_LOGIN
(IN p_id INTEGER, IN p_pin INTEGER, OUT valid INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
LANGUAGE SQL
  BEGIN
    DECLARE comparison int;
    SET comparison = (SELECT count(*) FROM p2.customer WHERE ID = p_id and p2.decrypt(Pin) = p_pin);

    IF NOT EXISTS(SELECT * FROM p2.customer WHERE ID = p_id) THEN 
      SET valid = 0;
      SET sql_code = -100;
      SET err_msg = 'Invalid id';
    ELSEIF comparison != 1 THEN
      SET valid = 0;
      SET sql_code = -100;
      SET err_msg = 'Incorrect id or pin';
    ELSE
      SET valid = 1;
      SET sql_code = 0;
      SET err_msg = '-';
    END IF;
END@

CREATE PROCEDURE P2.ACCT_OPN
(IN p_id INTEGER, IN p_balance INTEGER, IN p_type CHAR(1), OUT acc_num INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
LANGUAGE SQL
  BEGIN
    IF NOT EXISTS(SELECT * FROM p2.customer WHERE ID = p_id) THEN 
      SET sql_code = -100;
      SET err_msg = 'Invalid id';
    ELSEIF p_balance < 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid balance';
    ELSEIF p_type != 'C' AND p_type != 'S' THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid type';
    ELSE
      INSERT INTO p2.account (ID, Balance, Type, Status)
      VALUES(p_id, p_balance, p_type, 'A');
      SET acc_num = (select Number from P2.account where id = ID limit 1);
      SET err_msg = '-';
          SET sql_code = 0;
    END IF; 
END@

CREATE PROCEDURE P2.ACCT_CLS
(IN p_acc_num INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
LANGUAGE SQL
  BEGIN
    IF NOT EXISTS(SELECT * FROM p2.account WHERE Number = p_acc_num) THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid account number';
    ELSE 
      UPDATE p2.account SET Status = 'I' WHERE Number = p_acc_num;
      UPDATE p2.account SET Balance = 0 WHERE Number = p_acc_num;
      SET err_msg = '-';
          SET sql_code = 0;
    END IF;
END@

CREATE PROCEDURE P2.ACCT_DEP
(IN p_acc_num INTEGER, IN p_amount INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
LANGUAGE SQL
  BEGIN
    IF NOT EXISTS(SELECT * FROM p2.account WHERE Number = p_acc_num) THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid account number';
    ELSEIF p_amount <= 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid amount';
    ELSEIF ((SELECT Status FROM p2.account WHERE Number = p_acc_num) = 'I') THEN
      SET sql_code = -100;
      SET err_msg = 'Inactive account';
    ELSE 
      UPDATE p2.account SET Balance = Balance + p_amount WHERE Number = p_acc_num;
      SET err_msg = '-';
          SET sql_code = 0;
    END IF;
END@

CREATE PROCEDURE P2.ACCT_WTH
(IN p_acc_num INTEGER, IN p_amount INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
LANGUAGE SQL
  BEGIN
    IF p_amount <= 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid amount';
    ELSEIF p_amount > (SELECT Balance FROM p2.account WHERE Number = p_acc_num) THEN
      SET sql_code = -100;
      SET err_msg = 'Not enough funds';
    ELSEIF ((SELECT Status FROM p2.account WHERE Number = p_acc_num) = 'I') THEN
      SET sql_code = -100;
      SET err_msg = 'Inactive account';
    ELSE
      UPDATE p2.account SET Balance = Balance - p_amount WHERE Number = p_acc_num;
      SET err_msg = '-';
          SET sql_code = 0;
    END IF;
END@

CREATE PROCEDURE P2.ACCT_TRX
(IN p_src_acct INTEGER, IN p_dest_acct INTEGER, IN p_amount INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
LANGUAGE SQL
  BEGIN
    CALL P2.ACCT_WTH(p_src_acct, p_amount, sql_code, err_msg);
    CALL P2.ACCT_DEP(p_dest_acct, p_amount, sql_code, err_msg);
END@

CREATE PROCEDURE P2.ADD_INTEREST
(IN p_savings_rate float, IN p_checking_rate float, OUT sql_code INTEGER, OUT err_msg CHAR(100))
LANGUAGE SQL
  BEGIN
    IF p_savings_rate < 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid savings interest rate';
    ELSEIF p_checking_rate < 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid checking interest rate';
    ELSE
      UPDATE p2.account SET Balance = Balance + Balance * p_savings_rate WHERE Type = 'S' AND Status = 'A';
      UPDATE p2.account SET Balance = Balance + Balance * p_checking_rate WHERE Type = 'C' AND Status = 'A';
      SET err_msg = '-';
          SET sql_code = 0;
        END IF;
END@
--
TERMINATE@
--

