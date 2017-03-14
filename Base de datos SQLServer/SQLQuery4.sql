use practica7

create table alumno
(
	dni_alumno int primary key,
	nombre varchar(25)not null,
	apellido varchar(25)not null,
	edad int not null,
	sexo char not null,
	divicion int
)

insert into alumno values(39715645,'joel','mora',20,'M','1a')
insert into alumno values(35715645,'franco','fernandez',23,'M','1a')
insert into alumno values(38715645,'javier','troina',22,'M','1a')
insert into alumno values(36715645,'jorge','reinoso',24,'M','1a')
insert into alumno values(36615645,'alan','toledo',24,'M','1b')
insert into alumno values(35415645,'emanuel','miguel',25,'M','1b')
insert into alumno values(37415645,'eduardo','santillan',19,'M','1b')
insert into alumno values(35775645,'eric','schmoll',30,'M','1b')
insert into alumno values(34716645,'ariel','troina',20,'M','1c')
insert into alumno values(33716645,'walter','martinez',20,'M','1c')
insert into alumno values(33715635,'marcos','reinoso',20,'M','1c')

drop table usuario

select * from alumno_v;
create view alumno_v with encryption as
 select nombre,apellido from alumno 
 sp_helptext alumno_v

drop procedure sp_alumnos

create proc sp_alumnas as
select nombre,apellido,divicion from alumno where sexo = 'F'; 

if object_id('sp_alumnas')is not null
	drop proc sp_alumnas
else
	select 'No existe'

create table curso 
(
	divicion varchar(5) primary key,
	turno varchar(35)not null
)

select * from curso

insert into curso values('1a','mañana')
insert into curso values('1b','tarde')
insert into curso values('1c','noche')

create table materia
(
	id_materia int identity primary key,
	dni_profesor int,
	materia varchar(35)not null,
	divicion varchar(5)
)

insert into materia values(29715645,'analisis matematico 1','1a')
insert into materia values(28715645,'programacion 1','1a')
insert into materia values(27715645,'laboratorio 1','1a')
insert into materia values(26715645,'sistemas y procesamientos de datos','1a')
insert into materia values(25715645,'ingles 1','1a')

insert into materia values(27715645,'analisis matematico 1','1b')
insert into materia values(29715645,'programacion 1','1b')
insert into materia values(25715645,'laboratorio 1','1b')
insert into materia values(28715645,'sistemas y procesamientos de datos','1b')
insert into materia values(25715645,'ingles 1','1b')

insert into materia values(25715645,'analisis matematico 1','1c')
insert into materia values(29715645,'programacion 1','1c')
insert into materia values(28715645,'laboratorio 1','1c')
insert into materia values(25715645,'sistemas y procesamientos de datos','1c')
insert into materia values(25715645,'ingles 1','1c')

select * from materia where divicion = '1a'

create table profesores
(
	dni_profesor int primary key,
	nombre varchar(25)not null,
	apellido varchar(25)not null,
	edad int not null,
	id_materia int,
	divicion int 
)

select * from alumno where divicion = '1c'

insert into profesores values(29715645,'ezequiel','oggioni',32,3,'1a')
insert into profesores values(28715645,'franco','octavio',38,4,'1a')
insert into profesores values(27715645,'cristian','baux',35,4,'1a')
insert into profesores values(26715645,'mauricio','davila',34,3,'1a')
insert into profesores values(25715645,'pablo','ferrera',42,2,'1a')
insert into profesores values(23715645,'marcos','peña',42,2,'1b')

select * from alumno join curso on alumno.divicion = curso.divicion
join profesores on curso.divicion = profesores.divicion
 