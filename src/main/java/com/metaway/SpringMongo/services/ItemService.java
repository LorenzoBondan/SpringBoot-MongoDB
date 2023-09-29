package com.metaway.SpringMongo.services;

import com.metaway.SpringMongo.dto.ItemDTO;
import com.metaway.SpringMongo.dto.PedidoDTO;
import com.metaway.SpringMongo.entities.Item;
import com.metaway.SpringMongo.entities.Pedido;
import com.metaway.SpringMongo.repositories.ItemRepository;
import com.metaway.SpringMongo.repositories.PedidoRepository;
import com.metaway.SpringMongo.services.exceptions.DataBaseException;
import com.metaway.SpringMongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public List<ItemDTO> findAll(){
        return repository.findAll().stream().map(ItemDTO::new).toList();
    }

    @Transactional
    public ItemDTO findById(String id){
        return new ItemDTO(repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item não existe.")));
    }

    @Transactional
    public ItemDTO insert(ItemDTO dto){
        Item entity = new Item();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ItemDTO(entity);
    }

    @Transactional
    public ItemDTO update(String codigo, ItemDTO dto){
        Item entity = repository.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Item não existe."));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ItemDTO(entity);
    }

    @Transactional
    public void delete(String codigo){
        if (!repository.existsById(codigo)) {
            throw new ResourceNotFoundException("Codigo not found: " + codigo);
        }
        try{
            repository.deleteById(codigo);
        } catch(DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ItemDTO dto, Item entity){
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());

        entity.getPedidos().clear();
        for(String pedidoCodigo : dto.getPedidosCodigos()){
            Pedido pedido = pedidoRepository.findById(pedidoCodigo).orElseThrow(() -> new IllegalArgumentException("Pedido não existe."));
            entity.getPedidos().add(pedido);
            // não relacional
            if(!pedido.getItens().contains(entity)){
                pedido.getItens().add(entity);
                pedidoRepository.save(pedido);
            }
        }
    }
}
