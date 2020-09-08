CREATE USER keycloak WITH PASSWORD 'p@sserKeycloak' ;
CREATE DATABASE  keycloakdb ;
ALTER DATABASE keycloakdb OWNER TO keycloak;
