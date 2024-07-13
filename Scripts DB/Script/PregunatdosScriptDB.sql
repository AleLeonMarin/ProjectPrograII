/*
Created: 5/1/2024
Modified: 5/26/2024
Model: Preguntados
Database: Oracle 11g Release 2
*/


-- Create sequences section -------------------------------------------------

CREATE SEQUENCE PREG_PREGUNTA_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE
;

CREATE SEQUENCE PREG_RESPUESTA_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE
;

CREATE SEQUENCE PREG_CATEGORIA_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE
;

CREATE SEQUENCE PREG_PARTIDA_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE
;

CREATE SEQUENCE PREG_JUGADOR_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE
;

-- Create tables section -------------------------------------------------

-- Table Jugador

CREATE TABLE Jugador(
  jug_id Number NOT NULL,
  jug_nombre Varchar2(30 ) NOT NULL,
  jug_partidasGanadas Number NOT NULL,
  jug_preguntasRespondidas Number NOT NULL,
  pre_res_correctamente Number NOT NULL,
  jug_version Number DEFAULT 1 NOT NULL,
  jug_cont_his Number NOT NULL,
  jug_cont_geo Number NOT NULL,
  jug_cont_dep Number NOT NULL,
  jug_cont_cien Number NOT NULL,
  jug_cont_entre Number NOT NULL,
  jug_cont_arte Number NOT NULL,
  jug_cor_his Number NOT NULL,
  jug_cor_geo Number NOT NULL,
  jug_cor_dep Number NOT NULL,
  jug_cor_cien Number NOT NULL,
  jug_cor_entre Number NOT NULL,
  jug_cor_arte Number NOT NULL
)
;

-- Create indexes for table Jugador

CREATE UNIQUE INDEX jugador_ind01 ON Jugador (jug_nombre)
;

-- Add keys for table Jugador

ALTER TABLE Jugador ADD CONSTRAINT PK_Jugador PRIMARY KEY (jug_id)
;

-- Table and Columns comments section

COMMENT ON COLUMN Jugador.jug_id IS 'Identificador unico para reconocer a un jugador
'
;
COMMENT ON COLUMN Jugador.jug_nombre IS 'Nombre del jugador
'
;
COMMENT ON COLUMN Jugador.jug_partidasGanadas IS 'Contador de las partidas ganadas de un jugador
'
;
COMMENT ON COLUMN Jugador.jug_preguntasRespondidas IS 'Contador general para las preguntas respondidas por el jugador
'
;
COMMENT ON COLUMN Jugador.pre_res_correctamente IS 'Contador general para las preguntas respondidas correctamente por el jugador
'
;
COMMENT ON COLUMN Jugador.jug_version IS 'Version para la entidad Jugador
'
;

-- Table Categoria

CREATE TABLE Categoria(
  cat_nombre Char(20 ) NOT NULL,
  cat_Id Number NOT NULL,
  cat_version Number DEFAULT 1 NOT NULL
)
;

-- Create indexes for table Categoria

CREATE UNIQUE INDEX catergoria_ind01 ON Categoria (cat_nombre)
;

-- Add keys for table Categoria

ALTER TABLE Categoria ADD CONSTRAINT PK_Categoria PRIMARY KEY (cat_Id)
;

-- Table and Columns comments section

COMMENT ON COLUMN Categoria.cat_nombre IS 'Identificador unico para conocer una corona por su nombre';
COMMENT ON COLUMN Categoria.cat_Id IS 'Identificador para reconocer una corona por su ID';
COMMENT ON COLUMN Categoria.cat_version IS 'Version de la entidad categoria';

-- Table Partida

CREATE TABLE Partida(
  par_Id Number NOT NULL,
  par_rondas Number DEFAULT 1 NOT NULL,
  par_dificultad Varchar2(1 ) DEFAULT 'M' NOT NULL,
  par_partida Clob NOT NULL,
  par_version Number NOT NULL,
  CONSTRAINT partida_ck01 CHECK (par_dificultad in ('E','M','H','D'))
)
;

-- Add keys for table Partida

ALTER TABLE Partida ADD CONSTRAINT PK_Partida PRIMARY KEY (par_Id);

-- Table and Columns comments section

COMMENT ON COLUMN Partida.par_Id IS 'Identificador unico para reconocer una partida
'
;
COMMENT ON COLUMN Partida.par_rondas IS 'Contador para la cantidad de rondas transcurridas en la partida'
;
COMMENT ON COLUMN Partida.par_dificultad IS 'Dificultad de la partida del juego(E:Facil,M:Intermedio,H:Dificil,D:Duelo)'
;
COMMENT ON COLUMN Partida.par_partida IS 'Partidas guardas en una archivo de texto'
;
COMMENT ON COLUMN Partida.par_version IS 'Version de la entidad partidad para manejar la concurrencia correctamente
'
;

-- Table Pregunta

CREATE TABLE Pregunta(
  pre_id Number NOT NULL,
  pre_enunciado Varchar2(200 ) NOT NULL,
  pre_estado Varchar2(1 ) DEFAULT 'A' NOT NULL,
  pre_aparicion Number DEFAULT 0 NOT NULL,
  pre_aciertos Number NOT NULL,
  pre_version Number DEFAULT 1 NOT NULL,
  cat_Id Number,
  CONSTRAINT pregunta_ck01 CHECK (pre_estado in ('A','I'))
)
;

-- Create indexes for table Pregunta

CREATE INDEX Pregunta_ind01 ON Pregunta (cat_Id)
;

-- Add keys for table Pregunta

ALTER TABLE Pregunta ADD CONSTRAINT PK_Pregunta PRIMARY KEY (pre_id)
;

-- Table and Columns comments section

COMMENT ON COLUMN Pregunta.pre_id IS 'Identificador unico para reconocer una pregunta'
;
COMMENT ON COLUMN Pregunta.pre_enunciado IS 'Enunciado de la pregunta'
;
COMMENT ON COLUMN Pregunta.pre_estado IS 'Estado actual de la pregunta (A:Activa,I:Inactiva)'
;
COMMENT ON COLUMN Pregunta.pre_aparicion IS 'Cantidad de apariciones de la pregunta en el juego'
;
COMMENT ON COLUMN Pregunta.pre_aciertos IS 'Cantidad de veces que se ha respondido correctamente esta pregunta'
;
COMMENT ON COLUMN Pregunta.pre_version IS 'Version Entidad Pregunta
'
;

-- Table Respuesta

CREATE TABLE Respuesta(
  res_Id Number NOT NULL,
  res_enunciado Varchar2(35 ) NOT NULL,
  res_contador Number DEFAULT 0 NOT NULL,
  res_estado Varchar2(1 ) DEFAULT 'A' NOT NULL,
  res_version Number DEFAULT 1 NOT NULL,
  res_correcta Varchar2(1 ) DEFAULT 'X' NOT NULL,
  pre_Id Number,
  CONSTRAINT respuesta_ck01 CHECK (res_estado in ('A','I')),
  CONSTRAINT respueta_ck02 CHECK (res_correcta in ('C','X'))
)
;

-- Create indexes for table Respuesta

CREATE UNIQUE INDEX Respuesta_ind01 ON Respuesta (pre_Id)
;

-- Add keys for table Respuesta

ALTER TABLE Respuesta ADD CONSTRAINT PK_Respuesta PRIMARY KEY (res_Id)
;


-- Table and Columns comments section

COMMENT ON COLUMN Respuesta.res_Id IS 'Identificador unico de una respuesta
'
;
COMMENT ON COLUMN Respuesta.res_enunciado IS 'Enunciado de la respuesta'
;
COMMENT ON COLUMN Respuesta.res_contador IS 'Contador de veces seleccionada esta respuesta'
;
COMMENT ON COLUMN Respuesta.res_estado IS 'Estado de la respuesta (A:Activa, I:Inactiva)'
;
COMMENT ON COLUMN Respuesta.res_version IS 'Version de la respusta'
;
COMMENT ON COLUMN Respuesta.res_correcta IS 'Estado de si una respuesta es correcta o incorrecta (C:Correcta,X:Incorrecta)'
;


-- Trigger for sequence PREG_JUGADOR_SEQ01 for column jug_id in table Jugador ---------
CREATE OR REPLACE TRIGGER jugador_tgr01 BEFORE INSERT
ON Jugador FOR EACH ROW
BEGIN
  if :new.jug_id is null or :new.jug_id <=0 then
  :new.jug_id :=preg_jugador_seq01.nextval;
  end if;
END;
;
CREATE OR REPLACE TRIGGER jugador_tgr02 AFTER UPDATE OF jug_id
ON Jugador FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column jug_id in table Jugador as it uses sequence.');
END;
;

-- Trigger for sequence PREG_CATEGORIA_SEQ01 for column cat_Id in table Categoria ---------
CREATE OR REPLACE TRIGGER categoria_trg01 BEFORE INSERT
ON Categoria FOR EACH ROW
BEGIN
   if :new.cat_Id is null or :new.cat_Id <= 0 then 
    :new.cat_Id := preg_categoria_seq01.nextval;
    end if;
END;
;
CREATE OR REPLACE TRIGGER categoria_tgr02 AFTER UPDATE OF cat_Id
ON Categoria FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column cat_Id in table Categoria as it uses sequence.');
END;
;

-- Trigger for sequence PREG_PARTIDA_SEQ01 for column par_Id in table Partida ---------
CREATE OR REPLACE TRIGGER partida_tgr01 BEFORE INSERT
ON Partida FOR EACH ROW
BEGIN
   if :new.par_Id is null or :new.par_Id <= 0 then 
   :new.par_Id := preg_partida_seq01.nextval;
   end if;
END;
;
CREATE OR REPLACE TRIGGER partida_tgr02 AFTER UPDATE OF par_Id
ON Partida FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column par_Id in table Partida as it uses sequence.');
END;
;

-- Trigger for sequence PREG_PREGUNTA_SEQ01 for column pre_id in table Pregunta ---------
CREATE OR REPLACE TRIGGER pregunta_tgr01 BEFORE INSERT
ON Pregunta FOR EACH ROW
BEGIN
    if :new.pre_id is null or :new.pre_Id <= 0 then 
    :new.pre_id  := preg_pregunta_seq01.nextval;
    end if;
END;
;
CREATE OR REPLACE TRIGGER pregunta_tgr02 AFTER UPDATE OF pre_id
ON Pregunta FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column pre_id in table Pregunta as it uses sequence.');
END;
;

-- Trigger for sequence PREG_RESPUESTA_SEQ01 for column res_Id in table Respuesta ---------
CREATE OR REPLACE TRIGGER respuesta_tgr01 BEFORE INSERT
ON Respuesta FOR EACH ROW
BEGIN
  if :new.res_Id is null or :new.res_Id <= 0 then 
  :new.res_Id := preg_respuesta_seq01.nextval;
  end if;
END;
;
CREATE OR REPLACE TRIGGER respuesta_tgr02 AFTER UPDATE OF res_Id
ON Respuesta FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column res_Id in table Respuesta as it uses sequence.');
END;
;


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE Pregunta ADD CONSTRAINT PRGTDS_CATPRE_FK01 FOREIGN KEY (cat_Id) REFERENCES Categoria (cat_Id)
;



ALTER TABLE Respuesta ADD CONSTRAINT PRGTDS_PREGRES_FK01 FOREIGN KEY (pre_Id) REFERENCES Pregunta (pre_id)
;



ALTER TABLE partidajugador ADD CONSTRAINT partidajugador_fk02 FOREIGN KEY (exp_idjug) REFERENCES Jugador (jug_id)
;



ALTER TABLE partidajugador ADD CONSTRAINT partidajugador_fk01 FOREIGN KEY (exp_idpar) REFERENCES Partida (par_Id)
;





