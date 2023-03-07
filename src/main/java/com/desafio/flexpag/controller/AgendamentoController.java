package com.desafio.flexpag.controller;

import com.desafio.flexpag.entity.Agendamento;
import com.desafio.flexpag.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping
    public List<Agendamento> listaDeAgendamentos(){
        return agendamentoRepository.findAll();
    }

    @PostMapping(value = "/{id}")
    public AgendamentoResponse agendar(@RequestBody AgendamentoRequest agendamentoRequest){
        Agendamento agendamento = new Agendamento(agendamentoRequest.nome,agendamentoRequest.cpf,agendamentoRequest.data,
                agendamentoRequest.hora);
        var createdAgendamento = agendamentoRepository.save(agendamento);
        AgendamentoResponse response =  new AgendamentoResponse(createdAgendamento);
        return response;
    }
    @PutMapping(value = "/{id}")
    public void alterarAgendamento(@RequestBody AgendamentoRequest agendamentoRequest,@PathVariable Long id){
        Optional<Agendamento> agendamentoOptional = agendamentoRepository.findById(id);
        if (agendamentoOptional.get().getStatus() == "pending") {
            agendamentoOptional.get().setData(agendamentoRequest.data);
            agendamentoOptional.get().setHora(agendamentoRequest.hora);
        }
        else {
            System.out.println("Cliente já realizou o pagamento e não pode alterar o agendamento!");
        }

    }
    @DeleteMapping
    public void removerAgendamento(Long id){
        Optional<Agendamento> agendamentoOptional = agendamentoRepository.findById(id);
        if (agendamentoOptional.isPresent() && agendamentoOptional.get().getStatus() == "pending"){
            agendamentoRepository.deleteById(id);
        }
        else {
            System.out.println("Cliente não existe ou já realizou o pagamento");
        }

    }

}
