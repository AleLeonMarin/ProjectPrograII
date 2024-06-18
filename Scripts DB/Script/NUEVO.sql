/*
Created: 5/1/2024
Modified: 6/15/2024
Model: Preguntados
Database: Oracle 11g Release 2
*/


-- Create sequences section -------------------------------------------------

CREATE SEQUENCE PREG_PREGUNTA_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

CREATE SEQUENCE PREG_RESPUESTA_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

CREATE SEQUENCE PREG_CATEGORIA_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

CREATE SEQUENCE PREG_PARTIDA_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

CREATE SEQUENCE PREG_JUGADOR_SEQ01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE;

-- Create tables section -------------------------------------------------

-- Table Jugador

CREATE TABLE JUGADOR(
  JUG_ID NUMBER NOT NULL,
  JUG_NOMBRE VARCHAR2(30 ) NOT NULL,
  JUG_VERSION NUMBER DEFAULT 1 NOT NULL,
  JUG_PREGUNTASRESPONDIDAS NUMBER DEFAULT 0 NOT NULL,
  PRE_RES_CORRECTAMENTE NUMBER DEFAULT 0 NOT NULL,
  JUG_PARTIDASGANADAS NUMBER DEFAULT 0 NOT NULL,
  JUG_CONT_HIS NUMBER DEFAULT 0 NOT NULL,
  JUG_CONT_GEO NUMBER DEFAULT 0 NOT NULL,
  JUG_CONT_DEP NUMBER DEFAULT 0 NOT NULL,
  JUG_CONT_CIEN NUMBER DEFAULT 0 NOT NULL,
  JUG_CONT_ENTRE NUMBER DEFAULT 0 NOT NULL,
  JUG_CONT_ARTE NUMBER DEFAULT 0 NOT NULL,
  JUG_COR_HIS NUMBER DEFAULT 0 NOT NULL,
  JUG_COR_GEO NUMBER DEFAULT 0 NOT NULL,
  JUG_COR_DEP NUMBER DEFAULT 0 NOT NULL,
  JUG_COR_CIEN NUMBER DEFAULT 0 NOT NULL,
  JUG_COR_ENTRE NUMBER DEFAULT 0 NOT NULL,
  JUG_COR_ARTE NUMBER DEFAULT 0 NOT NULL
);

-- Create indexes for table Jugador

CREATE UNIQUE INDEX JUGADOR_IND01 ON JUGADOR (JUG_NOMBRE);

-- Add keys for table Jugador

ALTER TABLE JUGADOR ADD CONSTRAINT PK_JUGADOR PRIMARY KEY (JUG_ID);

-- Table and Columns comments section

COMMENT ON COLUMN JUGADOR.JUG_ID IS 'Identificador unico para reconocer a un jugador
';
COMMENT ON COLUMN JUGADOR.JUG_NOMBRE IS 'Nombre del jugador
';
COMMENT ON COLUMN JUGADOR.JUG_VERSION IS 'Version para la entidad Jugador
';
COMMENT ON COLUMN JUGADOR.JUG_PREGUNTASRESPONDIDAS IS 'Contador general para las preguntas respondidas por el jugador
';
COMMENT ON COLUMN JUGADOR.PRE_RES_CORRECTAMENTE IS 'Contador general para las preguntas respondidas correctamente por el jugador
';
COMMENT ON COLUMN JUGADOR.JUG_PARTIDASGANADAS IS 'Contador de las partidas ganadas de un jugador
';
COMMENT ON COLUMN JUGADOR.JUG_CONT_HIS IS 'Contador de preguntas respondidas por la categoria de Historia';
COMMENT ON COLUMN JUGADOR.JUG_CONT_GEO IS 'Contador de preguntas respondidas por la categoria de Geografia';
COMMENT ON COLUMN JUGADOR.JUG_CONT_DEP IS 'Contador de preguntas respondidas por la categoria de Deportes';
COMMENT ON COLUMN JUGADOR.JUG_CONT_CIEN IS 'Contador de preguntas respondidas por la categoria de Ciencia';
COMMENT ON COLUMN JUGADOR.JUG_CONT_ENTRE IS 'Contador de preguntas respondidas por la categoria de Entretenimiento';
COMMENT ON COLUMN JUGADOR.JUG_CONT_ARTE IS 'Contador de preguntas respondidas por la categoria de Arte';
COMMENT ON COLUMN JUGADOR.JUG_COR_HIS IS 'Contador de preguntas respondidas correctamente por la categoria de Historia';
COMMENT ON COLUMN JUGADOR.JUG_COR_GEO IS 'Contador de preguntas respondidas correctamente por la categoria de Geografia';
COMMENT ON COLUMN JUGADOR.JUG_COR_DEP IS 'Contador de preguntas respondidas correctamente por la categoria de Deportes';
COMMENT ON COLUMN JUGADOR.JUG_COR_CIEN IS 'Contador de preguntas respondidas correctamente  por la categoria de Ciencia';
COMMENT ON COLUMN JUGADOR.JUG_COR_ENTRE IS 'Contador de preguntas respondidas correctamente por la categoria de Entretenimiento';
COMMENT ON COLUMN JUGADOR.JUG_COR_ARTE IS 'Contador de preguntas respondidas correctamente por la categoria de Arte';

-- Table Categoria

CREATE TABLE CATEGORIA(
  CAT_NOMBRE VARCHAR2(15 ) NOT NULL,
  CAT_ID NUMBER NOT NULL,
  CAT_VERSION NUMBER DEFAULT 1 NOT NULL
);

-- Create indexes for table Categoria

CREATE UNIQUE INDEX CATEGORIA_IND01 ON CATEGORIA (CAT_ID);

-- Add keys for table Categoria

ALTER TABLE CATEGORIA ADD CONSTRAINT PK_CATEGORIA PRIMARY KEY (CAT_NOMBRE);

-- Table and Columns comments section

COMMENT ON COLUMN CATEGORIA.CAT_NOMBRE IS 'Identificador unico para conocer una corona por su nombre
';
COMMENT ON COLUMN CATEGORIA.CAT_ID IS 'Identificador para reconocer una corona por su ID';
COMMENT ON COLUMN CATEGORIA.CAT_VERSION IS 'Version de la entidad categoria';

-- Table Partida

CREATE TABLE PARTIDA(
  PAR_ID NUMBER NOT NULL,
  PAR_PARTIDA CLOB NOT NULL,
  PAR_DUENIO VARCHAR2(50 ),
  PAR_VERSION NUMBER DEFAULT 1 NOT NULL,
  PAR_FECHA DATE,
  PAR_RONDA NUMBER DEFAULT 0 NOT NULL
);

-- Add keys for table Partida

ALTER TABLE PARTIDA ADD CONSTRAINT PK_PARTIDA PRIMARY KEY (PAR_ID);

-- Table and Columns comments section

COMMENT ON COLUMN PARTIDA.PAR_ID IS 'Identificador unico para reconocer una partida
';
COMMENT ON COLUMN PARTIDA.PAR_PARTIDA IS 'Partidas guardas en una archivo de texto';
COMMENT ON COLUMN PARTIDA.PAR_DUENIO IS 'Jugador dueno de la partida creada';
COMMENT ON COLUMN PARTIDA.PAR_VERSION IS 'Version de la entidad partidad para manejar la concurrencia correctamente
';
COMMENT ON COLUMN PARTIDA.PAR_FECHA IS 'Fecha de creación de una partida.';
COMMENT ON COLUMN PARTIDA.PAR_RONDA IS 'Contador para la cantidad de rondas transcurridas en la partida';

-- Table Pregunta

CREATE TABLE PREGUNTA(
  PRE_ID NUMBER NOT NULL,
  PRE_ENUNCIADO VARCHAR2(200 ) NOT NULL,
  PRE_ESTADO VARCHAR2(1 ) DEFAULT 'A' NOT NULL,
  PRE_APARICION NUMBER DEFAULT 0 NOT NULL,
  PRE_ACIERTOS NUMBER DEFAULT 0 NOT NULL,
  PRE_VERSION NUMBER DEFAULT 1 NOT NULL,
  CAT_NOMBRE VARCHAR2(15 ),
  CONSTRAINT PREGUNTA_CK01 CHECK (PRE_ESTADO IN ('A', 'I'))
);

-- Create indexes for table Pregunta

CREATE INDEX PREGUNTA_IND01 ON PREGUNTA (CAT_NOMBRE);

-- Add keys for table Pregunta

ALTER TABLE PREGUNTA ADD CONSTRAINT PK_PREGUNTA PRIMARY KEY (PRE_ID);

-- Table and Columns comments section

COMMENT ON COLUMN PREGUNTA.PRE_ID IS 'Identificador unico para reconocer una pregunta';
COMMENT ON COLUMN PREGUNTA.PRE_ENUNCIADO IS 'Enunciado de la pregunta';
COMMENT ON COLUMN PREGUNTA.PRE_ESTADO IS 'Estado actual de la pregunta (A:Activa,I:Inactiva)';
COMMENT ON COLUMN PREGUNTA.PRE_APARICION IS 'Cantidad de apariciones de la pregunta en el juego';
COMMENT ON COLUMN PREGUNTA.PRE_ACIERTOS IS 'Cantidad de veces que se ha respondido correctamente esta pregunta';
COMMENT ON COLUMN PREGUNTA.PRE_VERSION IS 'Version Entidad Pregunta
';
COMMENT ON COLUMN PREGUNTA.CAT_NOMBRE IS 'Nombre categoria a la cual pertenece la pregunta
';

-- Table Respuesta

CREATE TABLE RESPUESTA(
  RES_ID NUMBER NOT NULL,
  RES_ENUNCIADO VARCHAR2(50 ) NOT NULL,
  RES_CONTADOR NUMBER DEFAULT 0 NOT NULL,
  RES_ESTADO VARCHAR2(1 ) DEFAULT 'A' NOT NULL,
  RES_VERSION NUMBER DEFAULT 1 NOT NULL,
  RES_CORRECTA VARCHAR2(1 ) DEFAULT 'X' NOT NULL,
  PRE_ID NUMBER,
  CONSTRAINT RESPUESTA_CK01 CHECK (RES_ESTADO IN ('A', 'I')),
  CONSTRAINT RESPUESTA_CK02 CHECK (RES_CORRECTA IN ('C', 'X'))
);

-- Create indexes for table Respuesta

CREATE INDEX RESPUESTA_IND01 ON RESPUESTA (PRE_ID);

-- Add keys for table Respuesta

ALTER TABLE RESPUESTA ADD CONSTRAINT PK_RESPUESTA PRIMARY KEY (RES_ID);

-- Table and Columns comments section

COMMENT ON COLUMN RESPUESTA.RES_ID IS 'Identificador unico de una respuesta
';
COMMENT ON COLUMN RESPUESTA.RES_ENUNCIADO IS 'Enunciado de la respuesta';
COMMENT ON COLUMN RESPUESTA.RES_CONTADOR IS 'Contador de veces seleccionada esta respuesta';
COMMENT ON COLUMN RESPUESTA.RES_ESTADO IS 'Estado de la respuesta (A:Activa, I:Inactiva)';
COMMENT ON COLUMN RESPUESTA.RES_VERSION IS 'Version de la respusta';
COMMENT ON COLUMN RESPUESTA.RES_CORRECTA IS 'Estado de si una respuesta es correcta o incorrecta (C:Correcta,X:Incorrecta)';
COMMENT ON COLUMN RESPUESTA.PRE_ID IS 'Identificador de la pregunta a la cual corresponde la respuesta';



-- Trigger for sequence PREG_JUGADOR_SEQ01 for column jug_id in table Jugador ---------
CREATE OR REPLACE TRIGGER JUGADOR_TGR01 BEFORE
  INSERT ON JUGADOR FOR EACH ROW
BEGIN
  IF :NEW.JUG_ID IS NULL OR :NEW.JUG_ID <=0 THEN
    :NEW.JUG_ID :=PREG_JUGADOR_SEQ01.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER JUGADOR_TGR02 AFTER
  UPDATE OF JUG_ID ON JUGADOR FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010, 'Cannot update column jug_id in table Jugador as it uses sequence.');
END;
/

-- Trigger for sequence PREG_CATEGORIA_SEQ01 for column cat_Id in table Categoria ---------
CREATE OR REPLACE TRIGGER CATEGORIA_TRG01 BEFORE
  INSERT ON CATEGORIA FOR EACH ROW
BEGIN
  IF :NEW.CAT_ID IS NULL OR :NEW.CAT_ID <= 0 THEN
    :NEW.CAT_ID := PREG_CATEGORIA_SEQ01.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER CATEGORIA_TGR02 AFTER
  UPDATE OF CAT_ID ON CATEGORIA FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010, 'Cannot update column cat_Id in table Categoria as it uses sequence.');
END;
/

-- Trigger for sequence PREG_PARTIDA_SEQ01 for column par_Id in table Partida ---------
CREATE OR REPLACE TRIGGER PARTIDA_TGR01 BEFORE
  INSERT ON PARTIDA FOR EACH ROW
BEGIN
  IF :NEW.PAR_ID IS NULL OR :NEW.PAR_ID <= 0 THEN
    :NEW.PAR_ID := PREG_PARTIDA_SEQ01.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER PARTIDA_TGR02 AFTER
  UPDATE OF PAR_ID ON PARTIDA FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010, 'Cannot update column par_Id in table Partida as it uses sequence.');
END;
/

CREATE OR REPLACE TRIGGER PARTIDA_TGR03 BEFORE
  INSERT ON PARTIDA FOR EACH ROW
BEGIN
  :NEW.PAR_FECHA := SYSDATE;
END;
/

-- Trigger for sequence PREG_PREGUNTA_SEQ01 for column pre_id in table Pregunta ---------
CREATE OR REPLACE TRIGGER PREGUNTA_TGR01 BEFORE
  INSERT ON PREGUNTA FOR EACH ROW
BEGIN
  IF :NEW.PRE_ID IS NULL OR :NEW.PRE_ID <= 0 THEN
    :NEW.PRE_ID := PREG_PREGUNTA_SEQ01.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER PREGUNTA_TGR02 AFTER
  UPDATE OF PRE_ID ON PREGUNTA FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010, 'Cannot update column pre_id in table Pregunta as it uses sequence.');
END;
/

-- Trigger for sequence PREG_RESPUESTA_SEQ01 for column res_Id in table Respuesta ---------
CREATE OR REPLACE TRIGGER RESPUESTA_TGR01 BEFORE
  INSERT ON RESPUESTA FOR EACH ROW
BEGIN
  IF :NEW.RES_ID IS NULL OR :NEW.RES_ID <= 0 THEN
    :NEW.RES_ID := PREG_RESPUESTA_SEQ01.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER RESPUESTA_TGR02 AFTER
  UPDATE OF RES_ID ON RESPUESTA FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010, 'Cannot update column res_Id in table Respuesta as it uses sequence.');
END;
/

-- Create foreign keys (relationships) section -------------------------------------------------

ALTER TABLE PREGUNTA ADD CONSTRAINT PRGTDS_CATPRE_FK01 FOREIGN KEY (CAT_NOMBRE) REFERENCES CATEGORIA (CAT_NOMBRE);

ALTER TABLE RESPUESTA ADD CONSTRAINT PRGTDS_PREGRES_FK01 FOREIGN KEY (PRE_ID) REFERENCES PREGUNTA (PRE_ID);

INSERT INTO PREGUNTADOS.CATEGORIA (CAT_NOMBRE,CAT_ID,CAT_VERSION) VALUES
	 ('Deportes',3,1),
	 ('Ciencia',4,1),
	 ('Entretenimiento',5,1),
	 ('Arte',6,1),
	 ('Historia',1,1),
	 ('Geografia',2,1);

INSERT INTO PREGUNTADOS.PREGUNTA (PRE_ID,PRE_ENUNCIADO,PRE_ESTADO,PRE_APARICION,PRE_ACIERTOS,PRE_VERSION,CAT_NOMBRE) VALUES
	 (29,'¿A qué estilo artístico pertenece la obra "Las Meninas" de Diego Velázquez?','A',9,4,10,'Arte'),
	 (27,'¿En qué periodo artístico se creó la obra "La Gioconda" de Leonardo da Vinci?
','A',14,11,15,'Arte'),
	 (28,'¿Cuál es el nombre del artista que pintó el cuadro "El Guernica"?','A',11,10,12,'Arte'),
	 (30,'¿Cuál es el nombre de la escultura más famosa de Miguel Ángel?','A',3,3,4,'Arte'),
	 (31,'¿En qué ciudad se encuentra el Museo del Prado?','A',6,5,7,'Arte'),
	 (32,'¿Cuál es el nombre del movimiento artístico que buscaba expresar la realidad de forma objetiva y sin idealizaciones?','A',10,4,11,'Arte'),
	 (33,'¿Qué artista mexicano es conocido por sus obras surrealistas llenas de simbolismo?','A',5,5,6,'Arte'),
	 (34,'¿A qué estilo artístico pertenece la obra "El beso" de Gustav Klimt?','A',10,10,11,'Arte'),
	 (35,'¿Cuál es el nombre del elemento químico con el símbolo Au?','A',10,9,11,'Ciencia'),
	 (36,'¿En qué parte del cuerpo humano se encuentra el ADN?','A',11,10,12,'Ciencia');
INSERT INTO PREGUNTADOS.PREGUNTA (PRE_ID,PRE_ENUNCIADO,PRE_ESTADO,PRE_APARICION,PRE_ACIERTOS,PRE_VERSION,CAT_NOMBRE) VALUES
	 (37,'¿Cuál es la teoría científica que explica el origen y la evolución del universo?','A',9,9,10,'Ciencia'),
	 (38,'¿Cuál es el nombre del científico que descubrió la penicilina?','A',9,8,10,'Ciencia'),
	 (39,'¿Cuál es el planeta más grande del sistema solar?','A',6,5,7,'Ciencia'),
	 (40,'¿En qué rama de la ciencia se estudia la composición, estructura y propiedades de la materia?','A',6,6,7,'Ciencia'),
	 (41,'¿Cuál es el proceso por el cual las plantas producen su propio alimento?','A',8,6,9,'Ciencia'),
	 (42,'¿Cuál es el nombre del hueso más largo del cuerpo humano?','A',8,7,9,'Ciencia'),
	 (43,'¿Cuál es la película más taquillera de todos los tiempos?','A',10,9,11,'Entretenimiento'),
	 (44,'¿En qué serie de televisión interpretó el papel de Rachel Green la actriz Jennifer Aniston?','A',3,3,4,'Entretenimiento'),
	 (45,'¿Cuál es el nombre del cantante que ha vendido más discos en la historia?','A',10,9,11,'Entretenimiento'),
	 (46,'¿Cuál es el libro más vendido de todos los tiempos?','A',6,6,7,'Entretenimiento');
INSERT INTO PREGUNTADOS.PREGUNTA (PRE_ID,PRE_ENUNCIADO,PRE_ESTADO,PRE_APARICION,PRE_ACIERTOS,PRE_VERSION,CAT_NOMBRE) VALUES
	 (47,'¿Cuál es el videojuego más vendido de la historia?','A',7,7,8,'Entretenimiento'),
	 (48,'¿En qué ciudad se encuentra el Museo del Louvre?','A',1,1,2,'Entretenimiento'),
	 (49,'¿Cuál es el nombre del cuadro más famoso de Leonardo da Vinci?','A',11,9,12,'Entretenimiento'),
	 (50,'¿Cuál es el compositor de la ópera "Carmen"?
','A',7,6,8,'Entretenimiento'),
	 (1,'¿En qué año finalizó la Segunda Guerra Mundial?','A',18,14,15,'Historia'),
	 (3,'¿Quién fue el primer presidente de los Estados Unidos de América?','A',12,12,13,'Historia'),
	 (2,'¿Cuál fue la principal causa de la Primera Guerra Mundial?','A',12,12,13,'Historia'),
	 (4,'¿Cuál fue la civilización precolombina que construyó la ciudad de Machu Picchu?','A',9,9,10,'Historia'),
	 (5,'¿En qué año se llevó a cabo la Revolución Francesa?','A',13,8,14,'Historia'),
	 (6,'¿En qué año se derrumbó el Muro de Berlín?','A',9,3,10,'Historia');
INSERT INTO PREGUNTADOS.PREGUNTA (PRE_ID,PRE_ENUNCIADO,PRE_ESTADO,PRE_APARICION,PRE_ACIERTOS,PRE_VERSION,CAT_NOMBRE) VALUES
	 (7,'¿Quién fue el líder del movimiento independentista de la India contra el dominio británico?
','A',7,5,8,'Historia'),
	 (8,'¿En qué año se firmó la Declaración de la Independencia de los Estados Unidos de América?','A',9,6,10,'Historia'),
	 (9,'¿Qué civilización antigua es conocida por haber construido las pirámides de Giza?','A',6,5,7,'Historia'),
	 (10,'¿Cuál fue el objetivo principal del Tratado de Versalles de 1919?','A',8,7,9,'Historia'),
	 (11,'¿Cuál es el país más grande del mundo por superficie terrestre?','A',4,3,5,'Geografia'),
	 (12,'¿Cuál es el río más largo del mundo?','A',6,5,7,'Geografia'),
	 (13,'¿Cuál es el océano más profundo del mundo?','A',7,6,8,'Geografia'),
	 (14,'¿Cuál es la montaña más alta del mundo?','A',5,3,6,'Geografia'),
	 (15,'¿En qué continente se encuentra el desierto del Sahara?','A',7,5,8,'Geografia'),
	 (16,'¿En qué ciudad se encuentra la Estatua de la Libertad?','A',2,2,3,'Geografia');
INSERT INTO PREGUNTADOS.PREGUNTA (PRE_ID,PRE_ENUNCIADO,PRE_ESTADO,PRE_APARICION,PRE_ACIERTOS,PRE_VERSION,CAT_NOMBRE) VALUES
	 (17,'¿En qué país se encuentra la Gran Barrera de Coral?
','A',7,6,8,'Geografia'),
	 (18,'¿En qué país se encuentra el cañón más profundo del mundo?','A',11,10,12,'Geografia'),
	 (19,'¿Cuál es el deporte que se juega con una pelota pequeña y dos raquetas en una cancha rectangular?
','A',11,11,12,'Deportes'),
	 (20,'¿En qué país se originó el fútbol?','A',14,13,15,'Deportes'),
	 (21,'¿Cuántas vueltas tiene una maratón?','A',14,13,15,'Deportes'),
	 (22,'¿Cuál es el equipo de la NBA que ha ganado más campeonatos?','A',16,14,17,'Deportes'),
	 (23,'¿Qué equipo ha ganado más campeonatos de constructores en la historia de la Fórmula 1?','A',15,15,16,'Deportes'),
	 (24,'¿En qué deporte se utiliza un balón ovalado y se enfrentan dos equipos de 15 jugadores?','A',3,3,4,'Deportes'),
	 (25,'¿Cuál es el nombre del golfista español que ha ganado más torneos Masters de Augusta?','A',8,7,9,'Deportes'),
	 (26,'¿Quién es el piloto con más victorias en la historia de la Fórmula 1?','A',16,16,17,'Deportes');
INSERT INTO PREGUNTADOS.PREGUNTA (PRE_ID,PRE_ENUNCIADO,PRE_ESTADO,PRE_APARICION,PRE_ACIERTOS,PRE_VERSION,CAT_NOMBRE) VALUES
	 (53,'¿Cuántos jugadores forman un equipo de baloncesto en la cancha?','A',0,0,1,'Deportes'),
	 (51,'¿Quién pintó "La Última Cena"?','A',0,0,1,'Arte'),
	 (52,'¿Cuál de estos movimientos artísticos pertenece al siglo XX?','A',0,0,1,'Arte'),
	 (54,'¿En qué deporte se utiliza un disco conocido como puck?','A',0,0,1,'Deportes'),
	 (55,'¿Quién es el creador de la serie de televisión "Breaking Bad"?','A',0,0,1,'Entretenimiento'),
	 (56,'¿Cuál es el nombre del planeta natal de Superman?','A',0,0,1,'Entretenimiento'),
	 (57,'¿Cuál es la capital de Australia?','A',0,0,1,'Geografia'),
	 (58,'¿Cuál es el elemento más abundante en la corteza terrestre?','A',0,0,1,'Ciencia'),
	 (59,'¿Qué tipo de célula no tiene núcleo?','A',0,0,1,'Ciencia'),
	 (60,'¿En qué año comenzó la Primera Guerra Mundial?','A',0,0,1,'Historia');
INSERT INTO PREGUNTADOS.PREGUNTA (PRE_ID,PRE_ENUNCIADO,PRE_ESTADO,PRE_APARICION,PRE_ACIERTOS,PRE_VERSION,CAT_NOMBRE) VALUES
	 (61,'¿Qué país tiene la mayor cantidad de islas en el mundo?','A',0,0,1,'Geografia'),
	 (62,'¿Quién fue el líder de la Revolución Rusa de 1917?','A',0,0,1,'Historia');

INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (106,'Barroco',2,'A',3,'X',27),
	 (105,'Renacimiento',11,'A',12,'C',27),
	 (107,'Impresionismo',1,'A',2,'X',27),
	 (108,'Post-impresionismo',0,'A',1,'X',27),
	 (111,'Joan Miró',1,'A',2,'X',28),
	 (110,'Salvador Dalí',0,'A',1,'X',28),
	 (109,'Pablo Picasso',10,'A',11,'C',28),
	 (112,'Francisco Goya',0,'A',1,'X',28),
	 (116,'Realismo',4,'A',9,'C',29),
	 (113,'Rococó',5,'A',10,'X',29);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (115,'Romanticismo',0,'A',1,'X',29),
	 (114,'Neoclásico',0,'A',1,'X',29),
	 (119,'La Piedad',0,'A',1,'X',30),
	 (117,'David',3,'A',4,'C',30),
	 (118,'El Moisés',0,'A',1,'X',30),
	 (120,'El Pensador',0,'A',1,'X',30),
	 (121,'Madrid',5,'A',6,'C',31),
	 (124,'Valencia',0,'A',1,'X',31),
	 (122,'Barcelona',1,'A',2,'X',31),
	 (123,'Sevilla',0,'A',1,'X',31);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (126,'Impresionismo',1,'A',2,'X',32),
	 (128,'Pop Art',3,'A',4,'X',32),
	 (125,'Realismo',4,'A',5,'C',32),
	 (127,'Surrealismo',2,'A',3,'X',32),
	 (132,'Rufino Tamayo',0,'A',1,'X',33),
	 (129,'Frida Kahlo',5,'A',6,'C',33),
	 (130,'Diego Rivera',0,'A',1,'X',33),
	 (131,'David Alfaro Siqueiros',0,'A',1,'X',33),
	 (135,'Expresionismo',0,'A',1,'X',34),
	 (134,'Art Déco',0,'A',1,'X',34);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (136,'Abstracto',0,'A',1,'X',34),
	 (133,'Art Nouveau',10,'A',11,'C',34),
	 (138,'Oro',9,'A',10,'C',35),
	 (140,'Hierro',0,'A',1,'X',35),
	 (139,'Cobre',0,'A',1,'X',35),
	 (137,'Plata',1,'A',2,'X',35),
	 (142,'Huesos',0,'A',1,'X',36),
	 (141,'Sangre',0,'A',1,'X',36),
	 (143,'Núcleo de las células',10,'A',11,'C',36),
	 (144,'Piel',1,'A',2,'X',36);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (148,'Leyes de la termodinámica',0,'A',1,'X',37),
	 (147,'Mecánica cuántica',0,'A',1,'X',37),
	 (146,'Teoría del Big Bang',9,'A',10,'C',37),
	 (145,'Teoría de la relatividad',0,'A',1,'X',37),
	 (150,'Marie Curie',0,'A',1,'X',38),
	 (149,'Albert Einstein',0,'A',1,'X',38),
	 (152,'Isaac Newton',1,'A',2,'X',38),
	 (151,'Alexander Fleming',8,'A',9,'C',38),
	 (153,'Tierra',0,'A',1,'X',39),
	 (156,'Saturno',1,'A',2,'X',39);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (155,'Júpiter',5,'A',6,'C',39),
	 (154,'Marte',0,'A',1,'X',39),
	 (158,'Química',6,'A',7,'C',40),
	 (160,'Astronomía',0,'A',1,'X',40),
	 (157,'Física',0,'A',1,'X',40),
	 (159,'Biología',0,'A',1,'X',40),
	 (164,'Mitosis',1,'A',2,'X',41),
	 (162,'Fotosíntesis',6,'A',7,'C',41),
	 (161,'Respiración celular',1,'A',2,'X',41),
	 (163,'Fermentación',0,'A',1,'X',41);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (167,'Cúbito',1,'A',2,'X',42),
	 (168,'Radio',0,'A',1,'X',42),
	 (165,'Fémur',7,'A',8,'C',42),
	 (166,'Húmero',0,'A',1,'X',42),
	 (172,'Star Wars VII',1,'A',2,'X',43),
	 (171,'Titanic',0,'A',1,'X',43),
	 (169,'Avatar',9,'A',10,'C',43),
	 (170,'Avengers: Endgame',0,'A',1,'X',43),
	 (173,'Friends',3,'A',4,'C',44),
	 (176,'How I Met Your Mother',0,'A',1,'X',44);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (175,'The Big Bang Theory',0,'A',1,'X',44),
	 (174,'Seinfeld',0,'A',1,'X',44),
	 (177,'The Beatles',1,'A',2,'X',45),
	 (178,'Elvis Presley',0,'A',1,'X',45),
	 (180,'Madonna',0,'A',1,'X',45),
	 (179,'Michael Jackson',9,'A',10,'C',45),
	 (183,'Don Quijote de la Mancha',0,'A',1,'X',46),
	 (181,'El Señor de los Anillos',0,'A',1,'X',46),
	 (184,'Cien años de soledad',0,'A',1,'X',46),
	 (182,'Harry Potter',6,'A',7,'C',46);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (188,'Grand Theft Auto V',0,'A',1,'X',47),
	 (186,'Super Mario Bros',0,'A',1,'X',47),
	 (187,'Minecraft',7,'A',8,'C',47),
	 (185,'Tetris',0,'A',1,'X',47),
	 (192,'Madrid',0,'A',1,'X',48),
	 (189,'Roma',0,'A',1,'X',48),
	 (190,'París',1,'A',2,'C',48),
	 (191,'Londres',0,'A',1,'X',48),
	 (194,'El nacimiento de Venus',0,'A',1,'X',49),
	 (195,'La última cena',0,'A',1,'X',49);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (193,'La Gioconda',9,'A',10,'C',49),
	 (196,'La Mona Lisa',2,'A',3,'X',49),
	 (197,'Georges Bizet',6,'A',7,'C',50),
	 (199,'Giacomo Puccini',1,'A',2,'X',50),
	 (200,'Richard Wagner',0,'A',1,'X',50),
	 (198,'Giuseppe Verdi',0,'A',1,'X',50),
	 (8,'La Guerra de Secesión Americana',0,'A',1,'X',2),
	 (5,'La Revolución Francesa',0,'A',1,'X',2),
	 (7,'El asesinato del Archiduque Francisco Fernando',12,'A',13,'C',2),
	 (10,'Thomas Jefferson',0,'A',1,'X',3);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (3,'1945',15,'A',14,'C',1),
	 (1,'1939',2,'A',2,'X',1),
	 (2,'1941',5,'A',1,'X',1),
	 (4,'1950',4,'A',1,'X',1),
	 (6,'La unificación de Italia',0,'A',1,'X',2),
	 (12,'Benjamin Franklin',0,'A',1,'X',3),
	 (11,'George Washington',12,'A',13,'C',3),
	 (9,'John Adams',0,'A',1,'X',3),
	 (15,'Los Mayas',0,'A',1,'X',4),
	 (16,'Los Incas',9,'A',10,'C',4);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (14,'Los Mayas',0,'A',1,'X',4),
	 (13,'Los Aztecas',0,'A',1,'X',4),
	 (17,'1776',4,'A',5,'X',5),
	 (20,'1815',1,'A',2,'X',5),
	 (19,'1804',0,'A',1,'X',5),
	 (18,'1789',8,'A',9,'C',5),
	 (22,'1985',1,'A',2,'X',6),
	 (24,'1991',1,'A',2,'X',6),
	 (21,'1961',4,'A',5,'X',6),
	 (23,'1989',3,'A',4,'C',6);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (27,'Mahatma Gandhi',5,'A',6,'C',7),
	 (26,'Ho Chi Minh',0,'A',1,'X',7),
	 (25,'Mao Zedong',0,'A',1,'X',7),
	 (28,'Nelson Mandela',2,'A',3,'X',7),
	 (29,'1763',0,'A',1,'X',8),
	 (32,'1812',0,'A',1,'X',8),
	 (31,'1789',3,'A',4,'X',8),
	 (30,'1776',6,'A',7,'C',8),
	 (35,'Los Egipcios',5,'A',6,'C',9),
	 (36,'Los Babilonios',0,'A',1,'X',9);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (33,'Los Romanos',0,'A',1,'X',9),
	 (34,'Los Griegos',1,'A',2,'X',9),
	 (39,'Dividir África entre las potencias europeas',0,'A',1,'X',10),
	 (37,'Expandir el Imperio Británico',1,'A',2,'X',10),
	 (40,'Crear la Unión Europea',0,'A',1,'X',10),
	 (38,'Liga de Naciones y fin a I WW',7,'A',8,'C',10),
	 (41,'Rusia',3,'A',4,'C',11),
	 (42,'Canadá',0,'A',1,'X',11),
	 (43,'China',0,'A',1,'X',11),
	 (44,'Estados Unidos',1,'A',2,'X',11);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (47,'Yangtze',0,'A',1,'X',12),
	 (45,'Amazonas',5,'A',6,'C',12),
	 (48,'Mississippi',0,'A',1,'X',12),
	 (46,'Nilo',1,'A',2,'X',12),
	 (49,'Pacífico',6,'A',7,'C',13),
	 (52,'Ártico',0,'A',1,'X',13),
	 (50,'Atlántico',0,'A',1,'X',13),
	 (51,'Índico',1,'A',2,'X',13),
	 (55,'Kangchenjunga',0,'A',1,'X',14),
	 (53,'Monte Everest',3,'A',4,'C',14);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (54,'K2',0,'A',1,'X',14),
	 (56,'Lhotse',2,'A',3,'X',14),
	 (58,'Asia',1,'A',2,'X',15),
	 (57,'África',5,'A',6,'C',15),
	 (59,'América del Norte',0,'A',1,'X',15),
	 (60,'América del Sur',1,'A',2,'X',15),
	 (64,'Río de Janeiro, Brasil',0,'A',1,'X',16),
	 (62,'París, Francia',0,'A',1,'X',16),
	 (61,'Nueva York, Estados Unidos',2,'A',3,'C',16),
	 (63,'Londres, Inglaterra',0,'A',1,'X',16);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (67,'Indonesia',0,'A',1,'X',17),
	 (66,'Nueva Zelanda',1,'A',2,'X',17),
	 (65,'Australia',6,'A',7,'C',17),
	 (68,'Filipinas',0,'A',1,'X',17),
	 (72,'Australia',0,'A',1,'X',18),
	 (69,'Perú',10,'A',11,'C',18),
	 (70,'Estados Unidos',1,'A',2,'X',18),
	 (71,'China',0,'A',1,'X',18),
	 (74,'Bádminton',0,'A',1,'X',19),
	 (76,'Racquetball',0,'A',1,'X',19);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (73,'Tenis',11,'A',12,'C',19),
	 (75,'Squash',0,'A',1,'X',19),
	 (77,'Brasil',0,'A',1,'X',20),
	 (78,'Inglaterra',13,'A',14,'C',20),
	 (80,'Italia',0,'A',1,'X',20),
	 (79,'Argentina',1,'A',2,'X',20),
	 (81,'2',0,'A',1,'X',21),
	 (84,'8',0,'A',1,'X',21),
	 (83,'6',13,'A',14,'C',21),
	 (82,'4',1,'A',2,'X',21);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (86,'Chicago Bulls',0,'A',1,'X',22),
	 (87,'Golden State Warriors',0,'A',1,'X',22),
	 (85,'Boston Celtics',1,'A',2,'X',22),
	 (88,'Los Angeles Lakers',14,'A',15,'C',22),
	 (90,'Mercedes',0,'A',1,'X',23),
	 (89,'Ferrari',15,'A',16,'C',23),
	 (92,'Red Bull',0,'A',1,'X',23),
	 (91,'McLaren',0,'A',1,'X',23),
	 (94,'Fútbol Americano',0,'A',1,'X',24),
	 (93,'Rugby',3,'A',4,'C',24);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (96,'Fútbol Australiano',0,'A',1,'X',24),
	 (95,'Gridiron',0,'A',1,'X',24),
	 (100,'Jon Rahm',0,'A',1,'X',25),
	 (98,'José María Olazábal',7,'A',8,'C',25),
	 (99,'Sergio García',1,'A',2,'X',25),
	 (97,'Severiano Ballesteros',0,'A',1,'X',25),
	 (101,'Michael Schumacher',0,'A',1,'X',26),
	 (102,'Lewis Hamilton',16,'A',17,'C',26),
	 (103,'Juan Manuel Fangio',0,'A',1,'X',26),
	 (104,'Alain Prost',0,'A',1,'X',26);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (204,'Pablo Picasso',0,'A',1,'X',51),
	 (201,'Michelangelo',0,'A',1,'X',51),
	 (202,'Leonardo da Vinci',0,'A',1,'C',51),
	 (203,'Vincent van Gogh',0,'A',1,'X',51),
	 (208,'Romanticismo',0,'A',1,'X',52),
	 (207,'Cubismo',0,'A',1,'C',52),
	 (206,'Renacimiento',0,'A',1,'X',52),
	 (205,'Barroco',0,'A',1,'X',52),
	 (209,'5',0,'A',1,'C',53),
	 (211,'11',0,'A',1,'X',53);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (212,'6',0,'A',1,'X',53),
	 (210,'7',0,'A',1,'X',53),
	 (213,'Béisbol',0,'A',1,'X',54),
	 (216,'Balonmano',0,'A',1,'X',54),
	 (214,'Hockey sobre hielo',0,'A',1,'C',54),
	 (215,'Rugby',0,'A',1,'X',54),
	 (219,'David Benioff',0,'A',1,'X',55),
	 (220,'Steven Spielberg',0,'A',1,'X',55),
	 (218,'J.J. Abrams',0,'A',1,'X',55),
	 (217,'Vince Gilligan',0,'A',1,'C',55);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (223,'Namek',0,'A',1,'X',56),
	 (222,'Kriptón',0,'A',1,'C',56),
	 (221,'Marte',0,'A',1,'X',56),
	 (224,'Tatooine',0,'A',1,'X',56),
	 (225,'Sídney',0,'A',1,'X',57),
	 (226,'Melbourne',0,'A',1,'X',57),
	 (228,'Brisbane',0,'A',1,'X',57),
	 (227,'Canberra',0,'A',1,'C',57),
	 (232,'Carbono',0,'A',1,'X',58),
	 (229,'Hidrógeno',0,'A',1,'X',58);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (231,'Silicio',0,'A',1,'X',58),
	 (230,'Oxígeno',0,'A',1,'C',58),
	 (234,'Célula epitelial',0,'A',1,'X',59),
	 (235,'Glóbulo rojo',0,'A',1,'C',59),
	 (236,'Neurona',0,'A',1,'X',59),
	 (233,'Célula muscular',0,'A',1,'X',59),
	 (240,'1945',0,'A',1,'X',60),
	 (237,'1914',0,'A',1,'C',60),
	 (239,'1920',0,'A',1,'X',60),
	 (238,'1939',0,'A',1,'X',60);
INSERT INTO PREGUNTADOS.RESPUESTA (RES_ID,RES_ENUNCIADO,RES_CONTADOR,RES_ESTADO,RES_VERSION,RES_CORRECTA,PRE_ID) VALUES
	 (242,'Indonesia',0,'A',1,'X',61),
	 (243,'Suecia',0,'A',1,'C',61),
	 (244,'Filipinas',0,'A',1,'X',61),
	 (241,'Canadá',0,'A',1,'X',61),
	 (245,'Josef Stalin',0,'A',1,'X',62),
	 (246,'Vladimir Lenin',0,'A',1,'C',62),
	 (247,'León Trotski',0,'A',1,'X',62),
	 (248,'Nicolás II',0,'A',1,'X',62);
