package com.metaway.SpringMongo.controllers;

import com.metaway.SpringMongo.dto.FuncionarioDTO;
import com.metaway.SpringMongo.entities.Funcionario;
import com.metaway.SpringMongo.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> findAll(){
        List<FuncionarioDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/range")
    public ResponseEntity<List<FuncionarioDTO>> findAllByIdadeRange(
            @RequestParam("from") Integer from,
            @RequestParam("to") Integer to){
        List<FuncionarioDTO> list = service.findAllByIdadeRange(from, to);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable String id){
        FuncionarioDTO entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> insert(@RequestBody FuncionarioDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
                .buildAndExpand(dto.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable String id, @RequestBody FuncionarioDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDTO> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
