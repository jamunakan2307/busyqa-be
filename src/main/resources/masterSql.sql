INSERT INTO teams(name) VALUES('TEAM_SALES');
INSERT INTO teams(name) VALUES('TEAM_ACCOUNTS');
INSERT INTO teams(name) VALUES('TEAM_ADMIN');
INSERT INTO teams(name) VALUES('TEAM_UNASSIGNED');
INSERT INTO teams(name) VALUES('TEAM_CLIENT');
INSERT INTO teams(NAME) VALUES('TEAM_TECH');
INSERT INTO teams(NAME) VALUES('TEAM_TRAINER');

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_PM');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_CLIENT');

INSERT INTO client_status(name) VALUES('LEADS');
INSERT INTO client_status(name) VALUES('INTERN');
INSERT INTO client_status(name) VALUES('ALUMINI');
INSERT INTO client_status(name) VALUES('STUDENT');
INSERT INTO client_status(name) VALUES('MOCK');
INSERT INTO client_status(name) VALUES('RESUME');



INSERT INTO payment_status(name) VALUES('ON_SCHEDULE');
INSERT INTO payment_status(name) VALUES('DUE_FOR_A_WEEK');
INSERT INTO payment_status(name) VALUES('SERIOUS_OVER_DUE');
INSERT INTO payment_status(name) VALUES('UNSCHEDULED');


INSERT INTO payment_plan(name) VALUES('One_Time_Credit_Card');
INSERT INTO payment_plan(name) VALUES('One_Time_Debit_Card_Cash');
INSERT INTO payment_plan(name) VALUES('One_Time_Email_transfer,Automated_Weekly');
INSERT INTO payment_plan(name) VALUES('Automated_Weekly');
INSERT INTO payment_plan(name) VALUES('Automated_Bi_Weekly');
INSERT INTO payment_plan(name) VALUES('UNSCHEDULED');
INSERT INTO payment_plan(name) VALUES('Automated_Monthly');

INSERT into users(email, first_name, last_name, password, roles, status, status_as_of_day,teams,username) VALUES ('administrator@busyqa.com', 'administratorbusyqa_trainer', 'busyqa', '$2a$10$R8eL2njsXCpCc/ez8pUpPeJReiAe7UYeXpooENPD7trY5Wu/.OYXG', NULL,'YES',NULL,NULL,'administrator');
INSERT into user_team_role(roles_id,teams_id,user_id) VALUES(3,3,1);

INSERT into users(email, first_name, last_name, password, roles, status, status_as_of_day,teams,username) VALUES ('salesmanagerbusyqacrm@busyqa.com', 'salesmanager', 'busyqa', '$2a$10$R8eL2njsXCpCc/ez8pUpPeJReiAe7UYeXpooENPD7trY5Wu/.OYXG', NULL,'YES',NULL,NULL,'salesmanager');
INSERT into user_team_role(roles_id,teams_id,user_id) VALUES(3,1,2);


INSERT into users(email, first_name, last_name, password, roles, status, status_as_of_day,teams,username) VALUES ('accountsmanager@busyqa.com', 'accountsmanager', 'busyqa', '$2a$10$R8eL2njsXCpCc/ez8pUpPeJReiAe7UYeXpooENPD7trY5Wu/.OYXG', NULL,'YES',NULL,NULL,'accountsmanager');
INSERT into user_team_role(roles_id,teams_id,user_id) VALUES(3,2,3);


INSERT into users(email, first_name, last_name, password, roles, status, status_as_of_day,teams,username) VALUES ('techlead@busyqa.com', 'techlead', 'busyqa', '$2a$10$R8eL2njsXCpCc/ez8pUpPeJReiAe7UYeXpooENPD7trY5Wu/.OYXG', NULL,'YES',NULL,NULL,'techlead');
INSERT into user_team_role(roles_id,teams_id,user_id) VALUES(3,6,4);