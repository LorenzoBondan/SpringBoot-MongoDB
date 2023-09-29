package com.metaway.SpringMongo.services;

import com.metaway.SpringMongo.dto.FuncionarioDTO;
import com.metaway.SpringMongo.dto.ItemDTO;
import com.metaway.SpringMongo.dto.PedidoDTO;
import com.metaway.SpringMongo.entities.Funcionario;
import com.metaway.SpringMongo.entities.Item;
import com.metaway.SpringMongo.entities.Pedido;
import com.metaway.SpringMongo.repositories.FuncionarioRepository;
import com.metaway.SpringMongo.repositories.ItemRepository;
import com.metaway.SpringMongo.repositories.PedidoRepository;
import com.metaway.SpringMongo.services.exceptions.DataBaseException;
import com.metaway.SpringMongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public List<PedidoDTO> findAll(){
        return repository.findAll().stream().map(PedidoDTO::new).toList();
    }

    @Transactional
    public PedidoDTO findById(String id){
        return new PedidoDTO(repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido não existe.")));
    }

    @Transactional
    public PedidoDTO insert(PedidoDTO dto){
        Pedido entity = new Pedido();
        copyDtoToEntity(dto, entity);
        entity.setDataCriacao(LocalDate.now());
        entity = repository.save(entity);
        return new PedidoDTO(entity);
    }

    @Transactional
    public PedidoDTO update(String codigo, PedidoDTO dto){
        Pedido entity = repository.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Pedido não existe."));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PedidoDTO(entity);
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

    private void copyDtoToEntity(PedidoDTO dto, Pedido entity){
        entity.setDescricao(dto.getDescricao());

        // remover pedido da lista do funcionário antigo (se for update)
        Funcionario funcionarioAntigo = entity.getFuncionario();
        if(funcionarioAntigo != null && !Objects.equals(dto.getFuncionarioCodigo(), entity.getFuncionario().getCodigo())){
            funcionarioAntigo.getPedidos().remove(entity);
            funcionarioRepository.save(funcionarioAntigo);
        }

        // adicionar pedido a lista do funcionário novo
        Funcionario funcionarioNovo = funcionarioRepository.findById(dto.getFuncionarioCodigo()).orElseThrow(() -> new IllegalArgumentException("Funcionário não existe."));
        entity.setFuncionario(funcionarioNovo);
        if(!funcionarioNovo.getPedidos().contains(entity)){
            FuncionarioDTO funcionarioNovoDto = new FuncionarioDTO(funcionarioNovo); // para não gerar stackoverflow
            funcionarioNovoDto.getPedidos().add(new PedidoDTO(entity));
        }
        funcionarioNovo = funcionarioRepository.findById(funcionarioNovo.getCodigo()).orElseThrow(() -> new IllegalArgumentException("Funcionário não existe."));
        funcionarioRepository.save(funcionarioNovo);

        entity.getItens().clear();
        for(ItemDTO itemDTO : dto.getItens()){
            Item item = itemRepository.findById(itemDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Item não existe."));
            entity.getItens().add(item);
            // não relacional
            if(!item.getPedidos().contains(entity)){
                item.getPedidos().add(entity);
                itemRepository.save(item);
            }
        }
    }
}
