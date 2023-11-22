CREATE TABLE categoria(
id SMALLINT AUTO_INCREMENT,
nome VARCHAR(30) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE curso (
id INT AUTO_INCREMENT,
nome VARCHAR(40) NOT NULL,
categoria_id SMALLINT NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_curso_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE usuario (
id INT AUTO_INCREMENT,
nome VARCHAR(20) NOT NULL,
sobrenome VARCHAR(50) NOT NULL,
senha VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE topico (
id INT AUTO_INCREMENT,
titulo VARCHAR(50) NOT NULL,
mensagem VARCHAR(255) NOT NULL,
data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
estado ENUM('Não respondido','Não solucionado','Solucionado','Fechado'),
usuario_id INT NOT NULL,
curso_id INT NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_topico_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE resposta (
id INT AUTO_INCREMENT,
mensagem VARCHAR(255) NOT NULL,
solucao BIT NOT NULL DEFAULT 0,
data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
usuario_id INT NOT NULL,
topico_id INT NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_resposta_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
CONSTRAINT fk_resposta_topico FOREIGN KEY (topico_id) REFERENCES topico(id)
);