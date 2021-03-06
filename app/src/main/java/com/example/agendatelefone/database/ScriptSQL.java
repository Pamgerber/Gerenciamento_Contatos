package com.example.agendatelefone.database;

import com.example.agendatelefone.dominio.entidades.Contato;

public class ScriptSQL {


    public static String getCreateContato(){

        StringBuilder sqlBuilder= new StringBuilder();
        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS CONTATO( ");
        sqlBuilder.append(" _id      INTEGER         NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME              VARCHAR (200), ");
        sqlBuilder.append("TELEFONE          VARCHAR (14), ");
        sqlBuilder.append("TIPOTELEFONE      VARCHAR (1), ");
        sqlBuilder.append("EMAIL             VARCHAR (255), ");
        sqlBuilder.append("TIPOEMAIL         VARCHAR (1), ");
        sqlBuilder.append("ENDERECO          VARCHAR (255), ");
        sqlBuilder.append("TIPOENDERECO      VARCHAR (1), ");
        sqlBuilder.append("DATASESPECIAIS    DATE, ");
        sqlBuilder.append("TIPODATAESPECIAL  VARCHAR (1), ");
        sqlBuilder.append("NOTAS             VARCHAR (255) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();


    }
}
