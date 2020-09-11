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
	


insert into ers_reimbursement_types values (1, 'Lodging'),
    (2, 'Travel'),
    (3, 'Food'),
    (4, 'Other');
   
   
  insert into ers_reimbursments (amount, submitted, description, author_id, reimb_status_id, reimb_type_id) values 
  (100, '2004-10-19 10:23:00', 'help',  7, 1, 3);

