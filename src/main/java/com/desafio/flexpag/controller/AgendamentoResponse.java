package com.desafio.flexpag.controller;

import com.desafio.flexpag.entity.Agendamento;

public class AgendamentoResponse {

    public Long id;

    public AgendamentoResponse(Agendamento agendamento) {
        this.id = agendamento.getId();
    }

}
