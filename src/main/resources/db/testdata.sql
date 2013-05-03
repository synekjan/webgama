/****************
 * EXAMPLE FEED *
 ****************/

INSERT INTO users (username,password,enabled) VALUES ('admin','c4737b4b5e1d23be81810b2e1346e8d2b6c9bdaa8122c5d9545cc8be4e8edf133a0fa313d187b6ef',TRUE);
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('pepa','ac906a053c63114a477bc10d5c20ca36265a5c4650a2a347edf91d00203b5b0ede46e48178219a10',TRUE,'Josef','Vonásek','josef@vonasek.cz','777111222','Lipská', 80,'Prague','51401','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('radek','0b0479e3f5bdb4897ac1877f5618f7252807bf0973d9ae8a7690b485f1e8770acddce677b271674b',TRUE,'Radek','Pomalý','radek@pomaly.cz','776890543','Mostecká', 30,'Pětipsy','22098','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('ivona','3dc00fe010448c891c2662c7635d7a46df2bfee364a554e65e50bf3841e07e3bdce5ee43a9930143',TRUE,'Ivona','Lichá','ivona@licha.cz','603764290','Luční', 55,'Ostrava','42921','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('alois','b5b40b04b2b7202ed6bc604b386cc9f7c36685775f5692dcd99b828ad1aef4d8d6a79ae2313bc697',TRUE,'Alois','Pilka','alois@pilka.cz','773528124','Větrná', 1245,'Liberec','24398','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('gita','cf7b4e0f80352719f2b6deb587c65f6539657e41b91bc410f216e0bf95a839481f96795abf7bbe53',TRUE,'Gita','Myslivečková','gita@email.cz','605429189','Národní', 97,'Libochovice','38219','Czech Republic');

INSERT INTO authorities (role_id,user_id) VALUES (1,1);
INSERT INTO authorities (role_id,user_id) VALUES (2,1);

INSERT INTO logins (user_id,ip_address,success) VALUES (1,'172.16.98.48',TRUE);
INSERT INTO logins (user_id,ip_address,success) VALUES (1,'172.16.98.47',TRUE);
INSERT INTO logins (user_id,ip_address,success) VALUES (2,'172.16.98.45',TRUE);
