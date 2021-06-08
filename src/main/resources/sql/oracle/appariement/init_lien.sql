------------------------------------------------------------------------------
-- SCRIPT DE CREATION DES TABLES RELATIVES AUX LIENS ET AUX ENSEMBLES DE LIENS
------------------------------------------------------------------------------

-- DROP TABLE ENSEMBLE_LIENS;
-- DROP TABLE LIEN;
-- DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='LIEN';

CREATE TABLE ENSEMBLE_LIENS (
    COGITID INTEGER PRIMARY KEY,
    NOM VARCHAR(255),
    PARAMETRAGE VARCHAR(255),
    EVALINTERNE VARCHAR(255),
    EVALGLOBALE VARCHAR(255),
    DATEHEURE VARCHAR(100),
    POPULATIONS VARCHAR(1000),
    ROUGE INTEGER,
    VERT INTEGER,
    BLEU INTEGER
    );

CREATE TABLE LIEN (
    COGITID INTEGER PRIMARY KEY,
    GEOM MDSYS.SDO_GEOMETRY,
    OBJETSREF VARCHAR(1000),
    OBJETSCOMP VARCHAR(1000),
    EVALUATION NUMBER,
    INDICATEURS VARCHAR(255),
    COMMENTAIRE VARCHAR(255),
    NOM VARCHAR(255),
    TYPE VARCHAR(255),
    REFERENCE VARCHAR(255),
    COMPARAISON VARCHAR(255),
    ENSLIENSID INTEGER
    );
INSERT INTO USER_SDO_GEOM_METADATA VALUES ('LIEN','GEOM',NULL,NULL);

commit;
