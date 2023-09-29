package com.metaway.SpringMongo.services;

import com.metaway.SpringMongo.dto.FuncionarioDTO;
import com.metaway.SpringMongo.dto.PedidoDTO;
import com.metaway.SpringMongo.entities.Funcionario;
import com.metaway.SpringMongo.entities.Pedido;
import com.metaway.SpringMongo.repositories.FuncionarioRepository;
import com.metaway.SpringMongo.repositories.PedidoRepository;
import com.metaway.SpringMongo.services.exceptions.DataBaseException;
import com.metaway.SpringMongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public List<FuncionarioDTO> findAll(){
        return repository.findAll().stream().map(FuncionarioDTO::new).toList();
    }

    @Transactional
    public List<FuncionarioDTO> findAllByIdadeRange(Integer from, Integer to){
        return repository.getFuncionariosByIdadeRange(from, to).stream().map(FuncionarioDTO::new).toList();
    }

    @Transactional
    public FuncionarioDTO findById(String id){
        return new FuncionarioDTO(repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Funcionário não existe.")));
    }

    @Transactional
    public FuncionarioDTO insert(FuncionarioDTO dto){
        Funcionario entity = new Funcionario();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new FuncionarioDTO(entity);
    }

    @Transactional
    public FuncionarioDTO update(String codigo, FuncionarioDTO dto){
        Funcionario entity = repository.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Funcionário não existe."));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new FuncionarioDTO(entity);
    }

    @Transactional
    public void delete(String codigo){
        if (!repository.existsByCodigo(codigo)) {
            throw new ResourceNotFoundException("Codigo not found: " + codigo);
        }
        try{
            repository.deleteByCodigo(codigo);
        } catch(DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(FuncionarioDTO dto, Funcionario entity){
        entity.setNome(dto.getNome());
        entity.setIdade(dto.getIdade());
        entity.setSalario(dto.getSalario());
        if(dto.getChefeCodigo() != null){
            entity.setChefe(repository.findById(dto.getChefeCodigo()).orElseThrow(() -> new IllegalArgumentException("Funcionário não existe.")));
        }

        entity.getPedidos().clear();
        for (PedidoDTO pedidoDto : dto.getPedidos()){
            Pedido pedido = pedidoRepository.findById(pedidoDto.getCodigo()).orElseThrow(() -> new IllegalArgumentException("Pedido não existe."));
            entity.getPedidos().add(pedido);
        }
    }
}
