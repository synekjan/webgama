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
DROP TABLE observations CASCADE;
DROP TABLE clusters CASCADE;
DROP TABLE points CASCADE;
DROP TABLE covmat_values CASCADE;
DROP TABLE covmats CASCADE;
DROP TABLE networks CASCADE;
DROP TABLE input_privileges CASCADE;
DROP TABLE outputs CASCADE;
DROP TABLE inputs CASCADE;
DROP TABLE calculations CASCADE;

DROP TABLE authorities CASCADE;
DROP TABLE roles CASCADE;
DROP TABLE logins CASCADE;
DROP TABLE confirmations CASCADE;
DROP TABLE activities CASCADE;
DROP TABLE users CASCADE;
DROP TABLE privileges CASCADE;
DROP FUNCTION user_authority_function() CASCADE;



/**************************************************************************
 *************************   U S E R S  P A R T   ************************* 
 **************************************************************************/



-------------------------- USERS table ----------------------------------

CREATE TABLE users (
user_id BIGSERIAL PRIMARY KEY,
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
authority_id BIGSERIAL PRIMARY KEY,
role_id INTEGER NOT NULL REFERENCES roles(role_id),
user_id BIGINT NOT NULL REFERENCES users(user_id)
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
login_id BIGSERIAL PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES users(user_id),
ip_address VARCHAR(15) NOT NULL,
time TIMESTAMP NOT NULL DEFAULT now(),
success BOOLEAN NOT NULL);


--------------- CONFIRMATIONS table ------------------

CREATE TABLE confirmations (
confirmation_id BIGSERIAL PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES users(user_id),
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

CREATE TABLE calculations (
calculation_id	BIGSERIAL PRIMARY KEY,
user_id 		BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
name			VARCHAR(80) NOT NULL,
progress		VARCHAR(20) NOT NULL,
language		VARCHAR(2) NOT NULL,
algorithm		VARCHAR(12),
ang_units		INTEGER,
latitude		DOUBLE PRECISION,
ellipsoid		VARCHAR(20),
time 			TIMESTAMP DEFAULT now()
);


CREATE TABLE inputs (
input_id 		BIGSERIAL PRIMARY KEY,
calculation_id 	BIGINT NOT NULL REFERENCES calculations(calculation_id) ON DELETE CASCADE,
xml_content 	TEXT,
version			VARCHAR(10),
time 			TIMESTAMP DEFAULT now()
);



CREATE TABLE networks (
network_id		BIGSERIAL PRIMARY KEY,
input_id		BIGINT NOT NULL REFERENCES inputs(input_id) ON DELETE CASCADE,
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
point_id		BIGSERIAL PRIMARY KEY,
network_id		BIGINT NOT NULL REFERENCES networks(network_id) ON DELETE CASCADE,
id				VARCHAR(80) NOT NULL,
x				DOUBLE PRECISION,
y				DOUBLE PRECISION,
z				DOUBLE PRECISION,
fix				VARCHAR(3),
adj				VARCHAR(3)
);

CREATE TABLE covmats (
covmat_id 		BIGSERIAL PRIMARY KEY,
dim 			INTEGER NOT NULL,
band 			INTEGER NOT NULL
);

CREATE TABLE covmat_values (
covmat_value_id BIGSERIAL PRIMARY KEY,
covmat_id 		BIGINT NOT NULL REFERENCES covmats(covmat_id) ON DELETE CASCADE,
rind 			INTEGER NOT NULL,
cind 			INTEGER NOT NULL,
val 			DOUBLE PRECISION
);

CREATE TABLE clusters (
cluster_id 		BIGSERIAL PRIMARY KEY,
network_id		BIGINT NOT NULL REFERENCES networks(network_id) ON DELETE CASCADE,
tagname			VARCHAR(20) NOT NULL check (tagname in ('obs', 'coordinates', 'vectors', 'height-differences')),
covmat_id		BIGINT REFERENCES covmats(covmat_id)
);

CREATE TABLE height_differences (
height_difference_id BIGSERIAL PRIMARY KEY,
cluster_id 		BIGINT NOT NULL REFERENCES clusters(cluster_id) ON DELETE CASCADE,
from_id			VARCHAR(80) NOT NULL,
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
dist			DOUBLE PRECISION
);

CREATE TABLE vectors (
vector_id 		BIGSERIAL PRIMARY KEY,
cluster_id 		BIGINT NOT NULL REFERENCES clusters(cluster_id) ON DELETE CASCADE,
from_id			VARCHAR(80) NOT NULL,
to_id			VARCHAR(80) NOT NULL,
dx				DOUBLE PRECISION NOT NULL,
dy				DOUBLE PRECISION NOT NULL,
dz				DOUBLE PRECISION NOT NULL,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);

CREATE TABLE coordinates (
coordinate_id	BIGSERIAL PRIMARY KEY,
cluster_id 		BIGINT NOT NULL REFERENCES clusters(cluster_id) ON DELETE CASCADE,
id				VARCHAR(80) NOT NULL,
x				DOUBLE PRECISION,
y				DOUBLE PRECISION,
z				DOUBLE PRECISION
);

CREATE TABLE observations (
observation_id 	BIGSERIAL PRIMARY KEY,
cluster_id 		BIGINT NOT NULL REFERENCES clusters(cluster_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
orientation		VARCHAR(20),
from_dh			DOUBLE PRECISION,
covmat_id		BIGINT REFERENCES covmats(covmat_id)
);


CREATE TABLE directions (
direction_id	BIGSERIAL PRIMARY KEY,
observation_id	BIGINT NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);

CREATE TABLE distances (
distance_id		BIGSERIAL PRIMARY KEY,
observation_id	BIGINT NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);


CREATE TABLE angles (
angle_id		BIGSERIAL PRIMARY KEY,
observation_id	BIGINT NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
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
slope_distance_id	BIGSERIAL PRIMARY KEY,
observation_id	BIGINT NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);

CREATE TABLE zenith_angles (
zenith_angle_id	BIGSERIAL PRIMARY KEY,
observation_id	BIGINT NOT NULL REFERENCES observations(observation_id) ON DELETE CASCADE,
from_id			VARCHAR(80),
to_id			VARCHAR(80) NOT NULL,
val				DOUBLE PRECISION NOT NULL,
stdev			DOUBLE PRECISION,
from_dh			DOUBLE PRECISION,
to_dh			DOUBLE PRECISION
);

CREATE TABLE privileges (
privilege_id 	INTEGER PRIMARY KEY,
privilege		VARCHAR(10) NOT NULL
);

INSERT INTO privileges (privilege_id, privilege) VALUES (1, 'READ');
INSERT INTO privileges (privilege_id, privilege) VALUES (2, 'CHANGE');
INSERT INTO privileges (privilege_id, privilege) VALUES (3, 'FULL');

/* SHARING inputs */
CREATE TABLE input_privileges (
input_privilege_id BIGSERIAL PRIMARY KEY,
user_id			BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE, 
calculation_id	BIGINT NOT NULL REFERENCES calculations(calculation_id) ON DELETE CASCADE,
privilege_id	INTEGER NOT NULL REFERENCES privileges(privilege_id)
);

-----  OUTPUT PART  -----
CREATE TABLE outputs (
output_id 		BIGSERIAL PRIMARY KEY,
calculation_id 	BIGINT NOT NULL REFERENCES calculations(calculation_id) ON DELETE CASCADE,
xml_content		TEXT,
text_content	TEXT,
html_content	TEXT,
svg_content		TEXT,
running_time	DOUBLE PRECISION,
last_error		TEXT,
time			TIMESTAMP DEFAULT now()
);


----- ACTIVITIES SUPPORT -----
CREATE TABLE activities (
activity_id		BIGSERIAL PRIMARY KEY,
user_id			BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
message			VARCHAR(100) NOT NULL,
time			TIMESTAMP NOT NULL DEFAULT now()
);


/****************
 *    RIGHTS    *
 ****************/
GRANT ALL ON calculations TO synekjan;
GRANT ALL ON calculations_calculation_id_seq TO synekjan;
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
GRANT ALL ON clusters TO synekjan;
GRANT ALL ON clusters_cluster_id_seq TO synekjan;
GRANT ALL ON input_privileges TO synekjan;
GRANT ALL ON input_privileges_input_privilege_id_seq TO synekjan;
GRANT ALL ON privileges TO synekjan;
GRANT ALL ON outputs TO synekjan;
GRANT ALL ON outputs_output_id_seq TO synekjan;
GRANT ALL ON activities TO synekjan;
GRANT ALL ON activities_activity_id_seq TO synekjan;





--COMMIT;


