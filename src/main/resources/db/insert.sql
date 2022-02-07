INSERT INTO CATEGORIES(ID, NAME) VALUES 
	(1, 'TERROR'), 
	(2, 'COMEDIA'), 
	(3, 'ACCIÓN');
    
INSERT INTO TV_SHOWS(ID, NAME, SHORT_DESC, LONG_DESC, YEAR, RECOMMENDED_AGE) VALUES 
	(1, 'Juego de tronos', 'Descripción corta', 'Descripción larga', '2012', 16), 
	(2, 'American horror Story', NULL, NULL, '2010', 16), 
	(3, 'Big Bang', NULL, NULL, '2008', 7);
    
INSERT INTO SEASONS(ID, NUMBER, NAME, TV_SHOW_ID) VALUES 
	(1, 1, 'One', 1), 
	(2, 2, 'Two', 1), 
	(3, 1, 'One', 2), 
	(4, 2, 'Two', 2), 
	(5, 3, 'Three', 2), 
	(6, 1, 'One', 3);

INSERT INTO CHAPTERS(ID, NUMBER, NAME, DURATION, SEASON_ID) VALUES 
	(1, 1, 'Chapter 1', 43, 1), 
	(2, 2, 'Chapter 2', 45, 1), 
	(3, 3, 'Chapter 3', 44, 1),
	(4, 1, 'Chapter 0', 50, 2),
	(5, 3, 'Chapter 3', 44, 6);

INSERT INTO CATEGORY_TV_SHOW(CATEGORY_ID, TV_SHOW_ID) VALUES 
	(1, 2), 
	(1, 1),
	(2, 3),
	(3, 3);    

INSERT INTO ACTORS(ID, NAME, SURNAME, DATE_BIRTH) VALUES 
	(1, 'Will', 'Smith' , '1968/09/25'), 
	(2, 'Robert', 'De Niro' , '1943/08/17'), 
	(3, 'Morgan', 'Freeman' , '1937/06/01'),
	(4, 'Denzel', 'Washington' , '1954/12/28');

INSERT INTO ACTOR_CHAPTER(ACTOR_ID, CHAPTER_ID) VALUES 
	(1, 2), 
	(1, 1),
	(3, 2), 
	(4, 3),
	(4, 5);  

INSERT INTO AWARDS(ID, NAME, DESCRIPTION, DATE, TV_SHOW_ID) VALUES 
	(1, 'Mejor serie de Drama', 'Descripcion' , '2021/09/25', 1), 
	(2, 'Mejor serie de Suspense', 'Descripcion' , '2021/08/17', 1), 
	(3, 'Mejor serie de Comedia', 'Descripcion' , '2021/06/01',3),
	(4, 'Mejor serie de Terror', 'Descripcion' , '2021/12/28',2);


INSERT INTO USERS(ID, NAME, SURNAME, USERNAME, PASSWORD) VALUES /* Administrador/es */
	(1, 'Adam', 'Yacobi' , 'admin', 'admin');

INSERT INTO USERS(ID, NAME, SURNAME, USERNAME, PASSWORD) VALUES  /*Usuario/s*/
	(2, 'Usuario1', 'Avatar' , 'usuario1', 'usuario1');


INSERT INTO ROLES(ID, NAME) VALUES
	(1, 'ADMIN'),
	(2, 'USER');

INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES
	(1, 1),
	(1, 2),
	(2, 2);

