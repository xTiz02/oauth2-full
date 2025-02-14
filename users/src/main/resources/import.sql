-- CREACIÓN DE OPERACIONES
INSERT INTO auth_permissions (name, description) VALUES ('READ_ALL_USERS','');
INSERT INTO auth_permissions (name, description)VALUES ('READ_ONE_USER','');
INSERT INTO auth_permissions (name, description) VALUES ('CREATE_ONE_USER','');
INSERT INTO auth_permissions (name, description) VALUES ('UPDATE_ONE_USER','');
INSERT INTO auth_permissions (name, description) VALUES ('DISABLE_ONE_USER','');
INSERT INTO auth_permissions (name, description) VALUES ('CREATE_ONE_ROL','');
INSERT INTO auth_permissions (name, description) VALUES ('VIEW_PROFILE','');
INSERT INTO auth_permissions (name, description) VALUES ('CHANGE_ALL_DATA','');


-- CREACIÓN DE ROLES
INSERT INTO auth_roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO auth_roles (name) VALUES ('ROLE_USER');

-- CREACIÓN DE PERMISOS
INSERT INTO auth_granted_permissions (role_id, permission_id) VALUES (1, 8);
INSERT INTO auth_granted_permissions (role_id, permission_id) VALUES (2, 7);


-- CREACIÓN DE USUARIOS
INSERT INTO auth_users (username, email, password,account_expired,account_locked,credentials_expired,enabled,role_id) VALUES ('admin1', 'admin1@gmail.com','$2a$10$yUYjPg1OY6AZX8LEGaBoJeo1ErAN0uaPwjRjs1M3o79FOaQn/NrkW',false,false,false,true,1);
INSERT INTO auth_users (username, email, password,account_expired,account_locked,credentials_expired,enabled,role_id) VALUES ('user1', 'user1@gmail.com','$2a$10$yUYjPg1OY6AZX8LEGaBoJeo1ErAN0uaPwjRjs1M3o79FOaQn/NrkW',false,false,false,true,2);