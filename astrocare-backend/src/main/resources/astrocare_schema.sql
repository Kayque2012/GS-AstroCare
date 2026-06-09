-- ============================================================
-- AstroCare Remote Health - Script SQL
-- Schema: RM567980
-- ============================================================

-- ========================
-- DROP (ordem por FK)
-- ========================
DROP TABLE T_AC_CONSULTA CASCADE CONSTRAINTS;
DROP TABLE T_AC_PACIENTE CASCADE CONSTRAINTS;
DROP TABLE T_AC_MEDICO CASCADE CONSTRAINTS;

-- ========================
-- CREATE TABLES
-- ========================

CREATE TABLE T_AC_PACIENTE (
    ID_PACIENTE     NUMBER(10)      NOT NULL,
    NOME            VARCHAR2(100)   NOT NULL,
    CPF             VARCHAR2(14)    NOT NULL,
    LOCALIZACAO     VARCHAR2(200)   NOT NULL,
    IDADE           NUMBER(3)       NOT NULL,
    CONDICAO_MEDICA VARCHAR2(200),
    CONSTRAINT PK_PACIENTE PRIMARY KEY (ID_PACIENTE),
    CONSTRAINT UQ_CPF_PACIENTE UNIQUE (CPF),
    CONSTRAINT CK_IDADE_PACIENTE CHECK (IDADE > 0)
);

CREATE TABLE T_AC_MEDICO (
    ID_MEDICO       NUMBER(10)      NOT NULL,
    NOME            VARCHAR2(100)   NOT NULL,
    CRM             VARCHAR2(20)    NOT NULL,
    ESPECIALIDADE   VARCHAR2(100)   NOT NULL,
    DISPONIVEL      CHAR(1)         DEFAULT 'S' NOT NULL,
    CONSTRAINT PK_MEDICO PRIMARY KEY (ID_MEDICO),
    CONSTRAINT UQ_CRM_MEDICO UNIQUE (CRM),
    CONSTRAINT CK_DISPONIVEL CHECK (DISPONIVEL IN ('S', 'N'))
);

CREATE TABLE T_AC_CONSULTA (
    ID_CONSULTA     NUMBER(10)      NOT NULL,
    ID_PACIENTE     NUMBER(10)      NOT NULL,
    ID_MEDICO       NUMBER(10)      NOT NULL,
    DATA_HORA       DATE            NOT NULL,
    STATUS          VARCHAR2(20)    NOT NULL,
    DESCRICAO       VARCHAR2(500),
    CONSTRAINT PK_CONSULTA PRIMARY KEY (ID_CONSULTA),
    CONSTRAINT FK_CONSULTA_PACIENTE FOREIGN KEY (ID_PACIENTE) REFERENCES T_AC_PACIENTE(ID_PACIENTE),
    CONSTRAINT FK_CONSULTA_MEDICO FOREIGN KEY (ID_MEDICO) REFERENCES T_AC_MEDICO(ID_MEDICO),
    CONSTRAINT CK_STATUS_CONSULTA CHECK (STATUS IN ('AGENDADA', 'REALIZADA', 'CANCELADA'))
);

-- ========================
-- INSERT - PACIENTES
-- ========================

INSERT INTO T_AC_PACIENTE VALUES (1, 'Maria Silva', '12345678901', 'Zona Rural - Limoeiro do Norte - CE', 34, 'Hipertensão');
INSERT INTO T_AC_PACIENTE VALUES (2, 'João Ferreira', '98765432100', 'Comunidade Quilombola - Alcântara - MA', 52, 'Diabetes Tipo 2');
INSERT INTO T_AC_PACIENTE VALUES (3, 'Ana Rodrigues', '11122233344', 'Aldeia Indígena - São Gabriel da Cachoeira - AM', 27, 'Anemia');
INSERT INTO T_AC_PACIENTE VALUES (4, 'Carlos Oliveira', '55566677788', 'Assentamento Rural - Vitória da Conquista - BA', 61, 'Artrite');
INSERT INTO T_AC_PACIENTE VALUES (5, 'Francisca Lima', '44455566677', 'Zona Rural - Juazeiro - CE', 19, 'Saudável');

-- ========================
-- INSERT - MEDICOS
-- ========================

INSERT INTO T_AC_MEDICO VALUES (1, 'Dr. Paulo Saúde', 'CRM-SP-123456', 'Clínica Geral', 'S');
INSERT INTO T_AC_MEDICO VALUES (2, 'Dra. Carla Mendes', 'CRM-MG-654321', 'Cardiologia', 'S');
INSERT INTO T_AC_MEDICO VALUES (3, 'Dr. André Costa', 'CRM-CE-111222', 'Endocrinologia', 'N');
INSERT INTO T_AC_MEDICO VALUES (4, 'Dra. Julia Nunes', 'CRM-RJ-333444', 'Clínica Geral', 'S');

-- ========================
-- INSERT - CONSULTAS
-- ========================

INSERT INTO T_AC_CONSULTA VALUES (1, 1, 1, TO_DATE('2025-07-10 09:00', 'YYYY-MM-DD HH24:MI'), 'AGENDADA', 'Consulta de rotina - pressão alta');
INSERT INTO T_AC_CONSULTA VALUES (2, 2, 3, TO_DATE('2025-07-11 14:30', 'YYYY-MM-DD HH24:MI'), 'AGENDADA', 'Controle glicêmico');
INSERT INTO T_AC_CONSULTA VALUES (3, 3, 2, TO_DATE('2025-07-08 10:00', 'YYYY-MM-DD HH24:MI'), 'REALIZADA', 'Avaliação cardíaca preventiva');
INSERT INTO T_AC_CONSULTA VALUES (4, 4, 4, TO_DATE('2025-07-05 16:00', 'YYYY-MM-DD HH24:MI'), 'CANCELADA', 'Consulta cancelada por indisponibilidade do paciente');
INSERT INTO T_AC_CONSULTA VALUES (5, 5, 1, TO_DATE('2025-07-15 08:00', 'YYYY-MM-DD HH24:MI'), 'AGENDADA', 'Primeiro atendimento - triagem geral');

COMMIT;
