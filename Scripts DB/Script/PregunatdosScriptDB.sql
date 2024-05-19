/*
Created: 5/1/2024
Modified: 5/18/2024
Model: Preguntados
Database: Oracle 11g Release 2
*/


-- Create tables section -------------------------------------------------

-- Table Jugador

CREATE TABLE "Jugador"(
  "jug_id" Number NOT NULL,
  "jug_nombre" Varchar2(30 ) NOT NULL,
  "partidasGanadas" Number NOT NULL,
  "jug_preguntasRespondidas" Number NOT NULL,
  "jug_preguntasRespondidasCorrectamente" Number NOT NULL,
  "jug_version" Number DEFAULT 1 NOT NULL
)
/

-- Add keys for table Jugador

ALTER TABLE "Jugador" ADD CONSTRAINT "PK_Jugador" PRIMARY KEY ("jug_id")
/

-- Table and Columns comments section

COMMENT ON COLUMN "Jugador"."jug_id" IS 'Identificador unico para reconocer a un jugador'
;
COMMENT ON COLUMN "Jugador"."jug_nombre" IS 'Nombre del jugador'
;
COMMENT ON COLUMN "Jugador"."partidasGanadas" IS 'Contador de las partidas ganadas de un jugador'
;
COMMENT ON COLUMN "Jugador"."jug_preguntasRespondidas" IS 'Contador general para las preguntas respondidas por el jugador'
;
COMMENT ON COLUMN "Jugador"."jug_preguntasRespondidasCorrectamente" IS 'Contador general para las preguntas respondidas correctamente'
;
COMMENT ON COLUMN "Jugador"."jug_version" IS 'Version para la entidad Jugador'
;

-- Table Categoria

CREATE TABLE "Categoria"(
  "cat_nombre" Char(20 ) NOT NULL,
  "cat_Id" Number NOT NULL,
  "cat_version" Number DEFAULT 1 NOT NULL
)
/

-- Add keys for table Categoria

ALTER TABLE "Categoria" ADD CONSTRAINT "PK_Categoria" PRIMARY KEY ("cat_nombre")
/

-- Table and Columns comments section

COMMENT ON COLUMN "Categoria"."cat_nombre" IS 'Identificador unico para conocer una corona por su nombre
'
/
COMMENT ON COLUMN "Categoria"."cat_Id" IS 'Identificador para reconocer una corona por su ID'
/
    COMMENT ON COLUMN "Categoria"."cat_version" IS 'Version de la entidad categoria'
/

-- Table Partida

CREATE TABLE "Partida"(
  "par_Id" Number NOT NULL,
  "par_rondas" Number DEFAULT 1 NOT NULL,
  "par_dificultad" Varchar2(1 ) DEFAULT 'M' NOT NULL,
  "par_partida" Clob NOT NULL,
  "par_version" Number NOT NULL
)
/

-- Add keys for table Partida

ALTER TABLE "Partida" ADD CONSTRAINT "PK_Partida" PRIMARY KEY ("par_Id")
/

-- Table and Columns comments section

COMMENT ON COLUMN "Partida"."par_Id" IS 'Identificador unico para reconocer una partida
'
/
COMMENT ON COLUMN "Partida"."par_rondas" IS 'Contador para la cantidad de rondas transcurridas en la partida'
/
COMMENT ON COLUMN "Partida"."par_dificultad" IS 'Dificultad de la partida del juego(E:Facil,M:Intermedio,H:Dificil,D:Duelo)'
/
COMMENT ON COLUMN "Partida"."par_partida" IS 'Partidas guardas en una archivo de texto'
/
COMMENT ON COLUMN "Partida"."par_version" IS 'Version de la entidad partidad para manejar la concurrencia correctamente
'
/

-- Table Pregunta

CREATE TABLE "Pregunta"(
  "pre_id" Number NOT NULL,
  "pre_enunciado" Varchar2(200 ) NOT NULL,
  "pre_estado" Varchar2(1 ) DEFAULT 'A' NOT NULL,
  "pre_aparicion" Number DEFAULT 0 NOT NULL,
  "pre_aciertos" Number NOT NULL,
  "pre_version" Number DEFAULT 1 NOT NULL,
  "cat_nombre" Char(20 ) NOT NULL
)
/

-- Create indexes for table Pregunta

CREATE INDEX "Pregunta_ind01" ON "Pregunta" ("cat_nombre")
/

-- Add keys for table Pregunta

ALTER TABLE "Pregunta" ADD CONSTRAINT "PK_Pregunta" PRIMARY KEY ("pre_id")
/

-- Table and Columns comments section

COMMENT ON COLUMN "Pregunta"."pre_id" IS 'Identificador unico para reconocer una pregunta'
/
COMMENT ON COLUMN "Pregunta"."pre_enunciado" IS 'Enunciado de la pregunta'
/
COMMENT ON COLUMN "Pregunta"."pre_estado" IS 'Estado actual de la pregunta (A:Activa,I:Inactiva)'
/
COMMENT ON COLUMN "Pregunta"."pre_aparicion" IS 'Cantidad de apariciones de la pregunta en el juego'
/
COMMENT ON COLUMN "Pregunta"."pre_aciertos" IS 'Cantidad de veces que se ha respondido correctamente esta pregunta'
/
COMMENT ON COLUMN "Pregunta"."pre_version" IS 'Version Entidad Pregunta
'
/
COMMENT ON COLUMN "Pregunta"."cat_nombre" IS 'Nombre de la Categoria-Personaje'
/

-- Table Respuesta

CREATE TABLE "Respuesta"(
  "res_Id" Number NOT NULL,
  "res_enunciado" Varchar2(35 ) NOT NULL,
  "res_contador" Char(20 ) NOT NULL,
  "res_estado" Varchar2(1 ) DEFAULT 'A' NOT NULL,
  "res_version" Number DEFAULT 1 NOT NULL,
  "res_correcta" Varchar2(1 ) DEFAULT 'X' NOT NULL,
  "pre_Id" Number
)
/

-- Create indexes for table Respuesta

CREATE INDEX "Respuesta_ind01" ON "Respuesta" ("pre_Id")
/

-- Add keys for table Respuesta

ALTER TABLE "Respuesta" ADD CONSTRAINT "PK_Respuesta" PRIMARY KEY ("res_Id")
/

-- Table and Columns comments section

COMMENT ON COLUMN "Respuesta"."res_Id" IS 'Identificador unico de una respuesta
'
/
COMMENT ON COLUMN "Respuesta"."res_enunciado" IS 'Enunciado de la respuesta'
/
COMMENT ON COLUMN "Respuesta"."res_contador" IS 'Contador de veces seleccionada esta respuesta'
/
COMMENT ON COLUMN "Respuesta"."res_estado" IS 'Estado de la respuesta (A:Activa, I:Inactiva)'
/
COMMENT ON COLUMN "Respuesta"."res_version" IS 'Version de la respusta'
/
COMMENT ON COLUMN "Respuesta"."res_correcta" IS 'Estado de si una respuesta es correcta o incorrecta (C:Correcta,X:Incorrecta)'
/


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE "Pregunta" ADD CONSTRAINT "PRGTDS_CATPRE_FK01" FOREIGN KEY ("cat_nombre") REFERENCES "Categoria" ("cat_nombre")
/



ALTER TABLE "Respuesta" ADD CONSTRAINT "PRGTDS_PREGRES_FK01" FOREIGN KEY ("pre_Id") REFERENCES "Pregunta" ("pre_id")
/





