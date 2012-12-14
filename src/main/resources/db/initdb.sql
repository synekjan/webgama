--BEGIN;

-- DROPS --
DROP TABLE directions CASCADE;
DROP TABLE distances CASCADE;
DROP TABLE angles CASCADE;
DROP TABLE slope_distances CASCADE;
DROP TABLE zenith_angles CASCADE;
DROP TABLE height_differences CASCADE;
DROP TABLE vectors CASCADE;
DROP TABLE coordinates CASCADE;
DROP TABLE alternative_observations CASCADE;
DROP TABLE observations CASCADE;
DROP TABLE points CASCADE;
DROP TABLE covmat_values CASCADE;
DROP TABLE covmats CASCADE;
DROP TABLE networks CASCADE;
DROP TABLE inputs CASCADE;


DROP TABLE outputs CASCADE;


DROP TABLE authorities CASCADE;
DROP TABLE roles CASCADE;
DROP TABLE logins CASCADE;
DROP TABLE confirmations CASCADE;
DROP TABLE users CASCADE;
DROP FUNCTION user_authority_function() CASCADE;



/**************************************************************************
 *************************   U S E R S  P A R T   ************************* 
 **************************************************************************/



-------------------------- USERS table ----------------------------------

CREATE TABLE users (
user_id SERIAL PRIMARY KEY,
username VARCHAR(30) NOT NULL UNIQUE,
password VARCHAR(80) NOT NULL,
enabled BOOLEAN DEFAULT FALSE,
firstname VARCHAR(30),
lastname VARCHAR(50),
email VARCHAR(50),
telephone VARCHAR(15),
street VARCHAR(50),
number VARCHAR(20),
city VARCHAR(50),
zipcode VARCHAR(30),
state VARCHAR(50),
date_created TIMESTAMP DEFAULT now(),
date_modified TIMESTAMP DEFAULT now());


---------------------- ROLES AND AUTHORITIES ---------------------------------
CREATE TABLE roles (
role_id INTEGER PRIMARY KEY,
role VARCHAR(25) NOT NULL
);

INSERT INTO roles VALUES (1,'ROLE_ADMIN');
INSERT INTO roles VALUES (2,'ROLE_PRIVILEGED_USER');
INSERT INTO roles VALUES (3,'ROLE_USER');


CREATE TABLE authorities (
authority_id SERIAL PRIMARY KEY,
role_id INTEGER NOT NULL REFERENCES roles(role_id),
user_id INTEGER NOT NULL REFERENCES users(user_id)
);

--trigger function for automatic insert default user permissions
CREATE OR REPLACE FUNCTION user_authority_function()
RETURNS TRIGGER AS $$
BEGIN
	
	IF (TG_OP = 'INSERT') THEN
		INSERT INTO authorities (role_id,user_id) VALUES (3,NEW.user_id);
		RETURN NEW;
	ELSEIF (TG_OP = 'DELETE') THEN
		DELETE FROM authorities WHERE user_id = OLD.user_id;
		DELETE FROM logins WHERE user_id = OLD.user_id;
		DELETE FROM confirmations WHERE user_id = OLD.user_id;
		RETURN OLD;
	END IF;
	RETURN NULL; --result is ignored since this is an AFTER trigger
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER user_insert_trigger
AFTER INSERT ON users FOR EACH ROW
EXECUTE PROCEDURE user_authority_function();

CREATE TRIGGER user_delete_trigger
BEFORE DELETE ON users FOR EACH ROW
EXECUTE PROCEDURE user_authority_function();


--------------- LOGINS table -------------------

CREATE TABLE logins (
login_id SERIAL PRIMARY KEY,
user_id INTEGER NOT NULL REFERENCES users(user_id),
ip_address VARCHAR(15) NOT NULL,
time TIMESTAMP NOT NULL DEFAULT now(),
success BOOLEAN NOT NULL);


--------------- CONFIRMATIONS table ------------------

CREATE TABLE confirmations (
confirmation_id SERIAL PRIMARY KEY,
user_id INTEGER NOT NULL REFERENCES users(user_id),
uuid VARCHAR(36) NOT NULL UNIQUE,
time TIMESTAMP DEFAULT now()
);


/****************
 *    RIGHTS    *
 ****************/
GRANT ALL ON users TO synekjan;
GRANT ALL ON users_user_id_seq TO synekjan;
GRANT ALL ON authorities TO synekjan;
GRANT ALL ON authorities_authority_id_seq TO synekjan;
GRANT ALL ON roles TO synekjan;
GRANT ALL ON logins TO synekjan;
GRANT ALL ON logins_login_id_seq TO synekjan;
GRANT ALL ON confirmations TO synekjan;
GRANT ALL ON confirmations_confirmation_id_seq TO synekjan;






/**************************************************************************
 ********************  P A R S I N G   X M L   P A R T  ******************* 
 **************************************************************************/

-----  INPUT PART  -----


CREATE TABLE inputs (
input_id 		SERIAL PRIMARY KEY,
user_id 		INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
filename 		VARCHAR(255) NOT NULL,
file_content 	TEXT,
algorithm		VARCHAR(12) NOT NULL,
ang_units		INTEGER NOT NULL,
latitude		DOUBLE PRECISION NOT NULL,
ellipsoid		VARCHAR(20),
version			VARCHAR(10),
time 			TIMESTAMP DEFAULT now()
);



CREATE TABLE networks (
network_id		SERIAL PRIMARY KEY,
input_id		INTEGER NOT NULL REFERENCES inputs(input_id) ON DELETE CASCADE,
axes_xy			VARCHAR(2),
angles			VARCHAR(12),
epoch			DOUBLE PRECISION,
description		TEXT,
sigma_apr		DOUBLE PRECISION,
conf_pr			DOUBLE PRECISION,
tol_abs			DOUBLE PRECISION,
sigma_act		VARCHAR(11),
update_cc		VARCHAR(3),
direction_stdev	VARCHAR(80),
angle_stdev		VARCHAR(80),
zenith_angle_stdev VARCHAR(80),
distance_stdev	VARCHAR(80)
);

CREATE TABLE points (
point_id		SERIAL PRIMARY KEY,
network_id		INTEGER NOT NULL REFERENCES networks(network_id) ON DELETE CASCADE,
id				VARCHAR(80) NOT NULL,
x				DOUBLE PRECISION,
y				DOUBLE PRECISION,
z				DOUBLE PRECISION,
fix				VARCHAR(3),
adj				VARCHAR(3)
);

CREATE TABLE covmats (
covmat_id 		SERIAL PRIMARY KEY,
dim 			INTEGER NOT NULL,
band 			INTEGER NOT NULL
);

CREATE TABLE covmat_values (
covmat_value_id SERIAL PRIMARY KEY,
covmat_id 		INTEGER NOT NULL REFERENCES covmats(covmat_id) ON DELETE CASCADE,
rind 			INTEGER NOT NULL,
cind 			INTEGER NOT NULL,
val 			DOUBLE PRECISION
);

CREATE TABLE alternative_observations (
alternative_observation_id SERIAL PRIMARY KEY,
network_id		INTEGER NOT NULL REFERENCES networks(network_id) ON DELETE CASCADE,
tagname			VARCHAR(20) NOT NULL check (tagname in ('coordinates', 'vectors', 'height-differences')),
covmat_id		INTEGER REFERENCES covmats(covmat_id)
);

CREATE TABLE height_differences (
height_difference_id SERIAL PRIMARY KEY,
alternative_observation_id INTEGER NOT NULL REFERENCES alternative_observations(alternative_observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80) NOT NULL,
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
dist			DOUBLE PRECISION
);

CREATE TABLE vectors (
vector_id 		SERIAL PRIMARY KEY,
alternative_observation_id INTEGER NOT NULL REFERENCES alternative_observations(alternative_observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80) NOT NULL,
to_id			VARCHAR(80) NOT NULL,
dx				DOUBLE PRECISION NOT NULL,
dy				DOUBLE PRECISION NOT NULL,
dz				DOUBLE PRECISION NOT NULL,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);

CREATE TABLE coordinates (
coordinate_id	SERIAL PRIMARY KEY,
alternative_observation_id INTEGER NOT NULL REFERENCES alternative_observations(alternative_observation_id) ON DELETE CASCADE,
id				VARCHAR(80) NOT NULL,
x				DOUBLE PRECISION,
y				DOUBLE PRECISION,
z				DOUBLE PRECISION
);



CREATE TABLE observations (
observation_id 	SERIAL PRIMARY KEY,
network_id		INTEGER NOT NULL REFERENCES networks(network_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
orientation		VARCHAR(20),
from_dh			DOUBLE PRECISION,
covmat_id		INTEGER REFERENCES covmats(covmat_id)
);


CREATE TABLE directions (
direction_id	SERIAL PRIMARY KEY,
observation_id	INTEGER NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);

CREATE TABLE distances (
distance_id		SERIAL PRIMARY KEY,
observation_id	INTEGER NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);


CREATE TABLE angles (
angle_id		SERIAL PRIMARY KEY,
observation_id	INTEGER NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
bs				VARCHAR(80) NOT NULL,
fs				VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
bs_dh			DOUBLE PRECISION,
fs_dh			DOUBLE PRECISION
);


CREATE TABLE slope_distances (
slope_distance_id	SERIAL PRIMARY KEY,
observation_id	INTEGER NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);

CREATE TABLE zenith_angles (
zenith_angle_id	SERIAL PRIMARY KEY,
observation_id	INTEGER NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);




/****************
 *    RIGHTS    *
 ****************/
GRANT ALL ON inputs TO synekjan;
GRANT ALL ON inputs_input_id_seq TO synekjan;
GRANT ALL ON networks TO synekjan;
GRANT ALL ON networks_network_id_seq TO synekjan;
GRANT ALL ON observations TO synekjan;
GRANT ALL ON observations_observation_id_seq TO synekjan;
GRANT ALL ON points TO synekjan;
GRANT ALL ON points_point_id_seq TO synekjan;
GRANT ALL ON directions TO synekjan;
GRANT ALL ON directions_direction_id_seq TO synekjan;
GRANT ALL ON distances TO synekjan;
GRANT ALL ON distances_distance_id_seq TO synekjan;
GRANT ALL ON angles TO synekjan;
GRANT ALL ON angles_angle_id_seq TO synekjan;
GRANT ALL ON slope_distances TO synekjan;
GRANT ALL ON slope_distances_slope_distance_id_seq TO synekjan;
GRANT ALL ON zenith_angles TO synekjan;
GRANT ALL ON zenith_angles_zenith_angle_id_seq TO synekjan;
GRANT ALL ON height_differences TO synekjan;
GRANT ALL ON height_differences_height_difference_id_seq TO synekjan;
GRANT ALL ON coordinates TO synekjan;
GRANT ALL ON coordinates_coordinate_id_seq TO synekjan;
GRANT ALL ON vectors TO synekjan;
GRANT ALL ON vectors_vector_id_seq TO synekjan;
GRANT ALL ON covmats TO synekjan;
GRANT ALL ON covmats_covmat_id_seq TO synekjan;
GRANT ALL ON covmat_values TO synekjan;
GRANT ALL ON covmat_values_covmat_value_id_seq TO synekjan;
GRANT ALL ON alternative_observations TO synekjan;
GRANT ALL ON alternative_observations_alternative_observation_id_seq TO synekjan;





-----  OUTPUT PART  -----


CREATE TABLE outputs (
output_id 		SERIAL PRIMARY KEY,
user_id 		INTEGER NOT NULL REFERENCES users(user_id)

);



/****************
 *    RIGHTS    *
 ****************/
GRANT ALL ON outputs TO synekjan;
GRANT ALL ON outputs_output_id_seq TO synekjan;







/****************
 * EXAMPLE FEED *
 ****************/

INSERT INTO users (username,password,enabled) VALUES ('admin','e8023e46442e3deb62882d8e60a3bf644ec5e6b0d42d39612884399a5ebda0b143a15b9eb779e1fe',TRUE);
INSERT INTO users (username,password,enabled) VALUES ('user','e65e74111ce740e7c8d7128f8613f8b4097134e4a1effe22599df7a9d9925cd27326df67894022d0',TRUE);
INSERT INTO users (username,password,enabled) VALUES ('landa','bb5d0c4fbacde508900302c9b8060dc74ea4e861980dfc5ca011b4456999f2636d4827b82c3715ea',TRUE);
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('pepa','ac906a053c63114a477bc10d5c20ca36265a5c4650a2a347edf91d00203b5b0ede46e48178219a10',TRUE,'Josef','Vonásek','josef@vonasek.cz','777111222','Lipská', 80,'Prague','51401','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('radek','0b0479e3f5bdb4897ac1877f5618f7252807bf0973d9ae8a7690b485f1e8770acddce677b271674b',TRUE,'Radek','Pomalý','radek@pomaly.cz','776890543','Mostecká', 30,'Pětipsy','22098','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('ivona','3dc00fe010448c891c2662c7635d7a46df2bfee364a554e65e50bf3841e07e3bdce5ee43a9930143',TRUE,'Ivona','Lichá','ivona@licha.cz','603764290','Luční', 55,'Ostrava','42921','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('alois','b5b40b04b2b7202ed6bc604b386cc9f7c36685775f5692dcd99b828ad1aef4d8d6a79ae2313bc697',TRUE,'Alois','Pilka','alois@pilka.cz','773528124','Větrná', 1245,'Liberec','24398','Czech Republic');
INSERT INTO users (username,password,enabled,firstname,lastname,email,telephone,street,number,city,zipcode,state) VALUES ('gita','cf7b4e0f80352719f2b6deb587c65f6539657e41b91bc410f216e0bf95a839481f96795abf7bbe53',TRUE,'Gita','Myslivečková','gita@email.cz','605429189','Národní', 97,'Libochovice','38219','Czech Republic');
INSERT INTO users (username,password,enabled) VALUES ('cepek','65b0c1af77dc3486accab0abdb5f0fdb3cf7bdc5003dd9e7594568d732ed72d976de5ff51a25d4e8',TRUE);

INSERT INTO authorities (role_id,user_id) VALUES (1,1);
INSERT INTO authorities (role_id,user_id) VALUES (2,1);

INSERT INTO logins (user_id,ip_address,success) VALUES (1,'172.16.98.48',TRUE);
INSERT INTO logins (user_id,ip_address,success) VALUES (1,'172.16.98.47',TRUE);
INSERT INTO logins (user_id,ip_address,success) VALUES (2,'172.16.98.45',TRUE);



--COMMIT;


