package com.example.gabriel.dcc196trabalho01;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Evento implements Serializable {

    private Integer registro;

    public Integer getRegistro() {
        return registro;
    }

    public void setRegistro(Integer registro) {
        this.registro = registro;
    }

    private String nome;
    private String local;
    private Calendar dataEvento;
    private String facilitador;
    private String descricao;
    private List<Participante> participanteList;
    private Integer numMaximoInscritos;
    private Integer numInscritos;

    public Evento() {
    }

    public Evento(String nome, String local, Calendar dataEvento, String facilitador, String descricao, List<Participante> participanteList, Integer numMaximoInscritos, Integer numInscritos) {
        this.nome = nome;
        this.local = local;
        this.dataEvento = dataEvento;
        this.facilitador = facilitador;
        this.descricao = descricao;
        this.participanteList = new ArrayList<>();
        this.numMaximoInscritos = numMaximoInscritos;
        this.numInscritos = numInscritos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Participante> getParticipanteList() {
        return participanteList;
    }

    public void setParticipanteList(List<Participante> participanteList) {
        this.participanteList = participanteList;
    }

    public Integer getNumMaximoInscritos() {
        return numMaximoInscritos;
    }

    public void setNumMaximoInscritos(Integer numMaximoInscritos) {
        this.numMaximoInscritos = numMaximoInscritos;
    }

    public Integer getNumInscritos() {
        return numInscritos;
    }

    public void setNumInscritos(Integer numInscritos) {
        this.numInscritos = numInscritos;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Calendar getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Calendar dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
