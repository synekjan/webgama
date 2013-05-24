/****************
 * EXAMPLE FEED *
 ****************/

INSERT INTO users (username,password,enabled) VALUES ('admin','c4737b4b5e1d23be81810b2e1346e8d2b6c9bdaa8122c5d9545cc8be4e8edf133a0fa313d187b6ef',TRUE);
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('pepa','e700f2307e1687f9f18056952efa7af739b3564268345382612c5c5dc5f62d5fafe5390f64a0e64e',TRUE,'Josef','Vonásek','josef@vonasek.cz','777111222','Lipská', 80,'Prague','51401','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('radek','cdce3501cb2760a09bfa35de996aa3779c8e20617efadccd94e35e24621bc5771d5a8dbefd3f2761',TRUE,'Radek','Pomalý','radek@pomaly.cz','776890543','Mostecká', 30,'Pětipsy','22098','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('ivona','89c4d3e6c065d247f5a2ed858a7c6fd0536c772c1abc92a13c11dcbba3ec92887683df293095873e',TRUE,'Ivona','Lichá','ivona@licha.cz','603764290','Luční', 55,'Ostrava','42921','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('alois','3b369b77bfbfb03dac769acf086764e70d5ac01067b9dc201cb83bf4ed5c686448284a2f47f75c09',TRUE,'Alois','Pilka','alois@pilka.cz','773528124','Větrná', 1245,'Liberec','24398','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('gita','9e470fa6d9ef691beecde07d5b9ecaa8a4da480c09bef7fdc4e8380135dcd060efad973fc7dbf4ab',TRUE,'Gita','Myslivečková','gita@email.cz','605429189','Národní', 97,'Libochovice','38219','Czech Republic');

INSERT INTO authorities (role_id,user_id) VALUES (1,1);
INSERT INTO authorities (role_id,user_id) VALUES (2,1);

INSERT INTO logins (user_id,ip_address,success) VALUES (1,'172.16.98.48',TRUE);
INSERT INTO logins (user_id,ip_address,success) VALUES (1,'172.16.98.47',TRUE);
INSERT INTO logins (user_id,ip_address,success) VALUES (2,'172.16.98.45',TRUE);
