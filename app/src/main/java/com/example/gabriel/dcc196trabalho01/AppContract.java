package com.example.gabriel.dcc196trabalho01;

import android.provider.BaseColumns;

public class AppContract {

    public final class Evento implements BaseColumns {
        public final static String TABLE_NAME = "Evento";
        public final static String COLUMN_NAME_REGISTRO = "registro";
        public final static String COLUMN_NAME_NOME = "nome";
        public final static String COLUMN_NAME_LOCAL = "local";
        public final static String COLUMN_NAME_DATA = "data";
        public final static String COLUMN_NAME_FACILITADOR = "facilitador";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_INSCRITOS = "inscritos";
        public static final String COLUMN_NAME_MAXINSCRITOS = "maxinscritos";
        public final static String CREATE_EVENTO  = "CREATE TABLE "+Evento.TABLE_NAME+" ("
                + Evento.COLUMN_NAME_REGISTRO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Evento.COLUMN_NAME_NOME+ " TEXT, "
                + Evento.COLUMN_NAME_LOCAL+ " TEXT, "
                + Evento.COLUMN_NAME_DATA+ " TEXT, "
                + Evento.COLUMN_NAME_FACILITADOR+ " TEXT, "
                + Evento.COLUMN_NAME_DESCRICAO+ " TEXT, "
                + Evento.COLUMN_NAME_INSCRITOS+ " INTEGER, "
                + Evento.COLUMN_NAME_MAXINSCRITOS+ " INTEGER"
                +")";
        public final static String DROP_EVENTO = "DROP TABLE IF EXISTS "+Evento.TABLE_NAME;
    }

    public final class Participante implements BaseColumns {
        public final static String TABLE_NAME = "Participante";
        public final static String COLUMN_NAME_REGISTRO = "registro";
        public final static String COLUMN_NAME_NOME = "nome";
        public final static String COLUMN_NAME_EMAIL = "email";
        public final static String COLUMN_NAME_CPF = "cpf";
        public final static String CREATE_EVENTO  = "CREATE TABLE "+ Participante.TABLE_NAME+" ("
                + Participante.COLUMN_NAME_REGISTRO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Participante.COLUMN_NAME_NOME+ " TEXT, "
                + Participante.COLUMN_NAME_EMAIL+ " TEXT, "
                + Participante.COLUMN_NAME_CPF+ " TEXT"
                +")";
        public final static String DROP_PARTICIPANTE = "DROP TABLE IF EXISTS "+ Participante.TABLE_NAME;
    };

    public final class ParticipanteEvento implements BaseColumns{
        public final static String TABLE_NAME = "ParticipanteEvento";
        public final static String COLUMN_NAME_REGISTRO = "registro";
        public final static String COLUMN_NAME_PARTICIPANTE = "registroParticipante";
        public final static String COLUMN_NAME_EVENTO = "registroEvento";
        public final static String CREATE_EVENTO  = "CREATE TABLE "+ ParticipanteEvento.TABLE_NAME+" ("
                + ParticipanteEvento.COLUMN_NAME_REGISTRO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ParticipanteEvento.COLUMN_NAME_PARTICIPANTE+ " INTEGER, "
                + ParticipanteEvento.COLUMN_NAME_EVENTO+ " INTEGER"
                +")";
        public final static String DROP_PARTICIPANTEEVENTO = "DROP TABLE IF EXISTS "+ ParticipanteEvento.TABLE_NAME;
    }
}
