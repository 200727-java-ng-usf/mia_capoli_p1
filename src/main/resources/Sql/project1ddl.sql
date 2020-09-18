CREATE TABLE ERS_REIMBURSMENTS(
	REIMB_ID serial PRIMARY key,
	AMOUNT numeric(6,2),
	SUBMITTED timestamp,
	RESOLVED timestamp,
	DESCRIPTION text,
	RECIEPT text,
	AUTHOR_ID int,
	RESOLVER_ID int,
	REIMB_STATUS_ID int,
	REIMB_TYPE_ID int,
			
	
	CONSTRAINT FK_USER_RESOLVER_ID
	FOREIGN KEY(RESOLVER_ID) 
    REFERENCES ERS_USERS(ERS_USER_ID),
    
    CONSTRAINT FK_USER_AUTHOR_ID
	FOREIGN KEY(AUTHOR_ID) 
    REFERENCES ERS_USERS(ERS_USER_ID),
    
    CONSTRAINT FK_REIMB_STATUS_ID
	FOREIGN KEY (REIMB_STATUS_ID) 
    REFERENCES ERS_REIMBURSEMENT_STATUSES(REIMB_STATUS_ID),
    
    CONSTRAINT FK_REIMB_ID
	FOREIGN KEY (REIMB_TYPE_ID) 
    REFERENCES ERS_REIMBURSEMENT_TYPES(REIMB_TYPE_ID)
)

CREATE TABLE ERS_USERS(
	ERS_USER_ID serial PRIMARY key,
	USERNAME varchar(25) UNIQUE,
	PASSWORD varchar(256),
	FIRST_NAME varchar(25),
	LAST_NAME varchar(25),
	EMAIL varchar(256) UNIQUE,
	USER_ROLE_ID int,
	
    CONSTRAINT FK_ROLE_ID
	FOREIGN KEY (USER_ROLE_ID) 
    REFERENCES ERS_USER_ROLES(ROLE_ID)

)

CREATE table ERS_REIMBURSEMENT_STATUSES(
	REIMB_STATUS_ID serial PRIMARY key,
	REIMB_STATUS varchar(10) uniquE
	
)

CREATE table ERS_REIMBURSEMENT_TYPES(
	REIMB_TYPE_ID serial PRIMARY key
	REIMB_TYPE varchar(10)


)

create TABLE ERS_USER_ROLES (
	ROLE_ID serial PRIMARY key,
	ROLE_NAME varchar(10)

)


INSERT INTO ers_users (username, password, first_name, last_name, email, user_role_id )
values 
	('aa', 'pa', 'Alice', 'Anderson', 'aa@revature.com', 1);
	
INSERT INTO ers_users (username, password, first_name, last_name, email, user_role_id )
values 
	('fin', 'pa', 'Fine', 'Man', 'manager@revature.com', 3);

INSERT INTO ers_users (username, password, first_name, last_name, email, user_role_id )
values 
	('who', 'pa', 'Mr.', 'Man', 'man@revature.com', 3);
	
SELECT * FROM project1.ers_users au
            JOIN project1.ers_user_roles ur
            ON au.user_role_id = ur.role_id Where au.is_active = true ORDER by au.ers_user_id;


insert into ers_reimbursement_types values (1, 'Lodging'),
    (2, 'Travel'),
    (3, 'Food'),
    (4, 'Other');
   
   
  insert into ers_reimbursments (amount, submitted, description, author_id, reimb_status_id, reimb_type_id) values 
  (100, '
', 'Dinner for a Client',  6, 1, 3),
  (100, '2004-10-19 10:23:00', 'Hotel Stay',  1, 1, 2),
  (2000, '2019-10-19 10:23:00', 'Moving expenses',  3, 1, 2);
 
SELECT * FROM project1.ers_reimbursments er JOIN project1.ers_reimbursement_types rt ON er.reimb_type_id = rt.reimb_type_id JOIN project1.ers_reimbursement_statuses rs ON er.reimb_status_id = rs.reimb_status_id WHERE author_id = 7
 
 alter table ers_users
add column is_active boolean;

update ers_users set is_active = true;

update ers_users set username = ? password = ? first_name = ? last_name = ? email = ? user_role_id = ? where username = ?;










insert into app_users (first_name , last_name, username, "password", email, "role" ) values 
('Alice', 'Anderson', 'aanderson', 'password', 'aanderson@gmail.com', 'ADMIN');

SELECT id, email, first_name, last_name, "password", "role", username
FROM public.app_users;


commit;



