package com.kaique.gerenciamentovendas.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaique.gerenciamentovendas.dtos.ClienteDTO;
import com.kaique.gerenciamentovendas.dtos.ClienteNewDTO;
import com.kaique.gerenciamentovendas.model.Cliente;
import com.kaique.gerenciamentovendas.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value = "{id}")
	public ResponseEntity<?> getClienteById(@PathVariable Integer id){
		Cliente cliente = this.clienteService.find(id);
		return ResponseEntity.ok(cliente);
	}
	
	@GetMapping(value = "/email")
	public ResponseEntity<Cliente> find (@RequestParam(value="value") String email) {
		Cliente obj = this.clienteService.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = this.clienteService.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok(listDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		Page<Cliente> list = this.clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok(listDto);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
		Cliente obj = this.clienteService.fromDto(objDto);
		obj = this.clienteService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = this.clienteService.fromDto(objDto);
		obj.setId(id);
		obj = this.clienteService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		this.clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/picture")
	public ResponseEntity<Void> uploadProfilePicture (@RequestParam(name="file") MultipartFile file) {
		URI uri = this.clienteService.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}	
}
