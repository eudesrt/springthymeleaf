DROP TABLE IF EXISTS tb_permissao_x_grupo;
DROP TABLE IF EXISTS tb_usuario_x_grupo;
DROP TABLE IF EXISTS tb_permissao;
DROP TABLE IF EXISTS tb_usuario;
DROP TABLE IF EXISTS tb_grupo;


/*CRIANDO A TABELA DE GRUPOS*/

CREATE TABLE tb_grupo
( 
  id_grupo     serial         PRIMARY KEY NOT NULL,
  ds_nome      VARCHAR(50)   NOT NULL,
  ds_descricao VARCHAR(200)  NOT NULL
);


/*CRIANDO A TABELA DE PERMISSÕES*/
CREATE TABLE tb_permissao
(
   id_permissao serial PRIMARY KEY NOT NULL,
   ds_permissao VARCHAR(50)    NOT NULL,
   ds_descricao VARCHAR(200)   NOT NULL   
);

CREATE TABLE tb_usuario
(
  id_usuario SERIAL PRIMARY KEY NOT NULL,
  ds_nome    VARCHAR(60)      NOT NULL,
  ds_login   VARCHAR(60)      NOT NULL,
  ds_senha   VARCHAR(400)     NOT NULL,
  fl_ativo   BOOLEAN        NOT NULL
);

/*CRIANDO A TABELA DE USUÁRIO X GRUPO*/
CREATE TABLE tb_usuario_x_grupo
(
  id_usuario INT NOT NULL,  
  id_grupo   INT NOT NULL,
  CONSTRAINT PK_USU_GRUP   PRIMARY KEY(id_usuario,id_grupo),
  FOREIGN KEY(id_usuario) REFERENCES tb_usuario(id_usuario), 
  FOREIGN KEY(id_grupo)  REFERENCES tb_grupo(id_grupo)
);

CREATE TABLE tb_permissao_x_grupo
(
  id_permissao INT NOT NULL,  
  id_grupo     INT NOT NULL,
  CONSTRAINT PK_PER_GRUP   PRIMARY KEY(id_permissao,id_grupo),
  CONSTRAINT FK_PERM_1 FOREIGN KEY(id_permissao) REFERENCES tb_permissao(id_permissao), 
  CONSTRAINT FK_GRUP_1 FOREIGN KEY(id_grupo)  REFERENCES tb_grupo(id_grupo)
);

INSERT INTO tb_grupo(ds_nome, ds_descricao) VALUES('ADMINISTRADORES', 'Adminitrador');
 
INSERT INTO tb_grupo(ds_nome,ds_descricao) VALUES('USUARIOS', 'Usuários Comum');
 
INSERT INTO tb_grupo(ds_nome, ds_descricao) VALUES('BACKOFFICE', 'Backoffice - Cadastros');

							  
INSERT INTO tb_usuario (ds_nome,ds_login, ds_senha, fl_ativo)
VALUES('Rafael Eudes','eudes','$2a$10$YYe9VtFGZoWvrNSZNV/AeuVSTOMQLxcGia4IQEl/yVaxrfAnPDcuO',true);

								 
INSERT INTO tb_permissao(ds_permissao, ds_descricao)
 VALUES('ROLE_CADASTROUSUARIO','CADASTRO DE NOVOS USUÁRIOS');
								   
INSERT INTO tb_permissao(ds_permissao, ds_descricao)
VALUES('ROLE_CONSULTAUSUARIO','CONSULTA DE USUÁRIOS'); 
								   
INSERT INTO tb_permissao(ds_permissao, ds_descricao) 
VALUES('ROLE_ADMIN','ADMINISTRAÇÂO COMPLETA');
								   

INSERT INTO tb_usuario_x_grupo(id_usuario,id_grupo)VALUES(1,1);

/*ROLE_CADASTROUSUARIO x BACKOFFICE*/
INSERT INTO tb_permissao_x_grupo(id_permissao,id_grupo)VALUES(1,3); 
 
/*ROLE_CONSULTAUSUARIO x USUARIOS*/
INSERT INTO tb_permissao_x_grupo(id_permissao,id_grupo)VALUES(2,2);
 
/*ROLE_ADMIN x ADMINISTRADORES*/
INSERT INTO tb_permissao_x_grupo(id_permissao,id_grupo)VALUES(3,1);

select * from tb_usuario


SELECT
  TB_PERMISSAO_X_GRUPO.ID_PERMISSAO,
  TB_PERMISSAO.DS_PERMISSAO,
  TB_PERMISSAO.DS_DESCRICAO,
  TB_PERMISSAO_X_GRUPO.ID_GRUPO,
  TB_GRUPO.DS_NOME
FROM
  TB_PERMISSAO_X_GRUPO TB_PERMISSAO_X_GRUPO
INNER JOIN  TB_GRUPO TB_GRUPO ON  TB_GRUPO.ID_GRUPO = TB_PERMISSAO_X_GRUPO.ID_GRUPO 
INNER JOIN  TB_PERMISSAO TB_PERMISSAO ON TB_PERMISSAO.ID_PERMISSAO  = TB_PERMISSAO_X_GRUPO.ID_PERMISSAO;



								   
								   
								   
								 

