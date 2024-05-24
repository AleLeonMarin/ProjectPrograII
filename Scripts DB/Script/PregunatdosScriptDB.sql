/*
Created: 5/1/2024
Modified: 5/23/2024
Model: Preguntados
Database: Oracle 11g Release 2
*/


-- Create sequences section -------------------------------------------------

CREATE SEQUENCE "PREG_PREGUNTA_SEQ01"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

CREATE SEQUENCE "PREG_RESPUESTA_SEQ01"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

CREATE SEQUENCE "PREG_CATEGORIA_SEQ01"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

CREATE SEQUENCE "PREG_PARTIDA_SEQ01"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

CREATE SEQUENCE "PREG_JUGADOR_SEQ01"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

-- Create tables section -------------------------------------------------

-- Table Jugador

CREATE TABLE "Jugador"(
  "jug_id" NUMBER NOT NULL,
  "jug_nombre" VARCHAR2(30 ) NOT NULL,
  "partidasGanadas" NUMBER NOT NULL,
  "jug_preguntasRespondidas" NUMBER NOT NULL,
  "jug_preguntasRespondidasCorrectamente" NUMBER NOT NULL,
  "jug_version" NUMBER DEFAULT 1 NOT NULL
);

-- Add keys for table Jugador

ALTER TABLE "Jugador" ADD CONSTRAINT "PK_Jugador" PRIMARY KEY ("jug_id");

-- Table and Columns comments section

COMMENT ON COLUMN "Jugador"."jug_id" IS 'Identificador unico para reconocer a un jugador
';
COMMENT ON COLUMN "Jugador"."jug_nombre" IS 'Nombre del jugador
';
COMMENT ON COLUMN "Jugador"."partidasGanadas" IS 'Contador de las partidas ganadas de un jugador
';
COMMENT ON COLUMN "Jugador"."jug_preguntasRespondidas" IS 'Contador general para las preguntas respondidas por el jugador
';
COMMENT ON COLUMN "Jugador"."jug_preguntasRespondidasCorrectamente" IS 'Contador general para las preguntas respondidas correctamente';;OMMENT ON COLUMN "Jugador"."jug_version" IS 'Version para la entidad Jugador

';

-- Table Categoria

CREATE TABLE "Categoria"(
  "cat_nombre" CHAR(20 ) NOT NULL,
  "cat_Id" NUMBER NOT NULL,
  "cat_version" NUMBER DEFAULT 1 NOT NULL
);

-- Add keys for table Categoria

ALTER TABLE "Categoria" ADD CONSTRAINT "PK_Categoria" PRIMARY KEY ("cat_nombre");

-- Table and Columns comments section

COMMENT ON COLUMN "Categoria"."cat_nombre" IS 'Identificador unico para conocer una corona por su nombre
';
COMMENT ON COLUMN "Categoria"."cat_Id" IS 'Identificador para reconocer una corona por su ID';
COMMENT ON COLUMN "Categoria"."cat_version" IS 'Version de la entidad categoria';

-- Table Partida

CREATE TABLE "Partida"(
  "par_Id" NUMBER NOT NULL,
  "par_rondas" NUMBER DEFAULT 1 NOT NULL,
  "par_dificultad" VARCHAR2(1 ) DEFAULT 'M' NOT NULL,
  "par_partida" CLOB NOT NULL,
  "par_version" NUMBER NOT NULL
);

-- Add keys for table Partida

ALTER TABLE "Partida" ADD CONSTRAINT "PK_Partida" PRIMARY KEY ("par_Id");

-- Table and Columns comments section

COMMENT ON COLUMN "Partida"."par_Id" IS 'Identificador unico para reconocer una partida
';
COMMENT ON COLUMN "Partida"."par_rondas" IS 'Contador para la cantidad de rondas transcurridas en la partida';
COMMENT ON COLUMN "Partida"."par_dificultad" IS 'Dificultad de la partida del juego(E:Facil,M:Intermedio,H:Dificil,D:Duelo)';
COMMENT ON COLUMN "Partida"."par_partida" IS 'Partidas guardas en una archivo de texto';
COMMENT ON COLUMN "Partida"."par_version" IS 'Version de la entidad partidad para manejar la concurrencia correctamente
';

-- Table Pregunta

CREATE TABLE "Pregunta"(
  "pre_id" NUMBER NOT NULL,
  "pre_enunciado" VARCHAR2(200 ) NOT NULL,
  "pre_estado" VARCHAR2(1 ) DEFAULT 'A' NOT NULL,
  "pre_aparicion" NUMBER DEFAULT 0 NOT NULL,
  "pre_aciertos" NUMBER NOT NULL,
  "pre_version" NUMBER DEFAULT 1 NOT NULL,
  "cat_nombre" CHAR(20 ) NOT NULL
);

-- Create indexes for table Pregunta

CREATE INDEX "Pregunta_ind01" ON "Pregunta" ("cat_nombre");

-- Add keys for table Pregunta

ALTER TABLE "Pregunta" ADD CONSTRAINT "PK_Pregunta" PRIMARY KEY ("pre_id");

-- Table and Columns comments section

COMMENT ON COLUMN "Pregunta"."pre_id" IS 'Identificador unico para reconocer una pregunta';
COMMENT ON COLUMN "Pregunta"."pre_enunciado" IS 'Enunciado de la pregunta';
COMMENT ON COLUMN "Pregunta"."pre_estado" IS 'Estado actual de la pregunta (A:Activa,I:Inactiva)';
COMMENT ON COLUMN "Pregunta"."pre_aparicion" IS 'Cantidad de apariciones de la pregunta en el juego';
COMMENT ON COLUMN "Pregunta"."pre_aciertos" IS 'Cantidad de veces que se ha respondido correctamente esta pregunta';
COMMENT ON COLUMN "Pregunta"."pre_version" IS 'Version Entidad Pregunta
';
COMMENT ON COLUMN "Pregunta"."cat_nombre" IS 'Nombre de la Categoria-Personaje';

-- Table Respuesta

CREATE TABLE "Respuesta"(
  "res_Id" NUMBER NOT NULL,
  "res_enunciado" VARCHAR2(35 ) NOT NULL,
  "res_contador" CHAR(20 ) NOT NULL,
  "res_estado" VARCHAR2(1 ) DEFAULT 'A' NOT NULL,
  "res_version" NUMBER DEFAULT 1 NOT NULL,
  "res_correcta" VARCHAR2(1 ) DEFAULT 'X' NOT NULL,
  "pre_Id" NUMBER
);

-- Create indexes for table Respuesta

CREATE INDEX "Respuesta_ind01" ON "Respuesta" ("pre_Id");

-- Add keys for table Respuesta

ALTER TABLE "Respuesta" ADD CONSTRAINT "PK_Respuesta" PRIMARY KEY ("res_Id");

-- Table and Columns comments section

COMMENT ON COLUMN "Respuesta"."res_Id" IS 'Identificador unico de una respuesta
';
COMMENT ON COLUMN "Respuesta"."res_enunciado" IS 'Enunciado de la respuesta';
COMMENT ON COLUMN "Respuesta"."res_contador" IS 'Contador de veces seleccionada esta respuesta';
COMMENT ON COLUMN "Respuesta"."res_estado" IS 'Estado de la respuesta (A:Activa, I:Inactiva)';
COMMENT ON COLUMN "Respuesta"."res_version" IS 'Version de la respusta';
COMMENT ON COLUMN "Respuesta"."res_correcta" IS 'Estado de si una respuesta es correcta o incorrecta (C:Correcta,X:Incorrecta)';

-- Trigger for sequence PREG_JUGADOR_SEQ01 for column jug_id in table Jugador ---------
CREATE OR REPLACE TRIGGER "ts_Jugador_PREG_JUGADOR_SEQ01" BEFORE
  INSERT ON "Jugador" FOR EACH ROW
BEGIN
  IF :NEW."jug_id" IS NULL OR :NEW."jug_id" <= 0 THEN
     :NEW."jug_id" := "PREG_JUGADOR_SEQ01".NEXTVAL;
  END IF;
END;;
CREATE OR REPLACE TRIGGER "tsu_Jugador_PREG_JUGADOR_SEQ01" AFTER
UPDATE OF "jug_id" ON "Jugador" FOR EACH ROW BEGIN RAISE_APPLICATION_ERROR(
  -20010,
  'Cannot update column "jug_id" in table "Jugador" as it uses sequence.'
);
END;;
 -- Trigger for sequence PREG_CATEGORIA_SEQ01 for column cat_Id in table Categoria ---------
CREATE OR REPLACE TRIGGER "ts_Categoria_PREG_CATEGORIA__1" BEFORE INSERT ON "Categoria" FOR EACH ROW BEGIN :NEW."cat_Id" := "PREG_CATEGORIA_SEQ01".NEXTVAL;
END;;
CREATE OR REPLACE TRIGGER "tsu_Categoria_PREG_CATEGORIA_1" AFTER
UPDATE OF "cat_Id" ON "Categoria" FOR EACH ROW BEGIN RAISE_APPLICATION_ERROR(
  -20010,
  'Cannot update column "cat_Id" in table "Categoria" as it uses sequence.'
);
END;;
 -- Trigger for sequence PREG_PARTIDA_SEQ01 for column par_Id in table Partida ---------
CREATE OR REPLACE TRIGGER "ts_Partida_PREG_PARTIDA_SEQ01" BEFORE INSERT ON "Partida" FOR EACH ROW BEGIN :NEW."par_Id" := "PREG_PARTIDA_SEQ01".NEXTVAL;
END;;
CREATE OR REPLACE TRIGGER "tsu_Partida_PREG_PARTIDA_SEQ01" AFTER
UPDATE OF "par_Id" ON "Partida" FOR EACH ROW BEGIN RAISE_APPLICATION_ERROR(
  -20010,
  'Cannot update column "par_Id" in table "Partida" as it uses sequence.'
);
END;;
 -- Trigger for sequence PREG_PREGUNTA_SEQ01 for column pre_id in table Pregunta ---------
CREATE OR REPLACE TRIGGER "ts_Pregunta_PREG_PREGUNTA_SE_0" BEFORE INSERT ON "Pregunta" FOR EACH ROW BEGIN :NEW."pre_id" := "PREG_PREGUNTA_SEQ01".NEXTVAL;
END;;
CREATE OR REPLACE TRIGGER "tsu_Pregunta_PREG_PREGUNTA_S_0" AFTER
UPDATE OF "pre_id" ON "Pregunta" FOR EACH ROW BEGIN RAISE_APPLICATION_ERROR(
  -20010,
  'Cannot update column "pre_id" in table "Pregunta" as it uses sequence.'
);
END;;
 -- Trigger for sequence PREG_RESPUESTA_SEQ01 for column res_Id in table Respuesta ---------
CREATE OR REPLACE TRIGGER "ts_Respuesta_PREG_RESPUESTA__0" BEFORE INSERT ON "Respuesta" FOR EACH ROW BEGIN :NEW."res_Id" := "PREG_RESPUESTA_SEQ01".NEXTVAL;
END;;
CREATE OR REPLACE TRIGGER "tsu_Respuesta_PREG_RESPUESTA_0" AFTER
UPDATE OF "res_Id" ON "Respuesta" FOR EACH ROW BEGIN RAISE_APPLICATION_ERROR(
  -20010,
  'Cannot update column "res_Id" in table "Respuesta" as it uses sequence.'
);
END;;
 -- Create foreign keys (relationships) section -------------------------------------------------
ALTER TABLE "Pregunta" ADD CONSTRAINT "PRGTDS_CATPRE_FK01" FOREIGN KEY ("cat_nombre") REFERENCES "Categoria" ("cat_nombre");
ALTER TABLE "Respuesta" ADD CONSTRAINT "PRGTDS_PREGRES_FK01" FOREIGN KEY ("pre_Id") REFERENCES "Pregunta" ("pre_id");