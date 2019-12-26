package com.kaique.gerenciamentovendas.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaique.gerenciamentovendas.dtos.ClienteDTO;
import com.kaique.gerenciamentovendas.dtos.ClienteNewDTO;
import com.kaique.gerenciamentovendas.model.Cidade;
import com.kaique.gerenciamentovendas.model.Cliente;
import com.kaique.gerenciamentovendas.model.Endereco;
import com.kaique.gerenciamentovendas.model.enums.Perfil;
import com.kaique.gerenciamentovendas.model.enums.TipoCliente;
import com.kaique.gerenciamentovendas.repositorys.CidadeRepository;
import com.kaique.gerenciamentovendas.repositorys.ClienteRepository;
import com.kaique.gerenciamentovendas.repositorys.EnderecoRepository;
import com.kaique.gerenciamentovendas.security.UserSS;
import com.kaique.gerenciamentovendas.services.exceptions.AuthorizationException;
import com.kaique.gerenciamentovendas.services.exceptions.IntegridadeDaInformacaoException;
import com.kaique.gerenciamentovendas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	public Cliente getClienteById(Integer id){
		Cliente categoria = this.clienteRepository.findOne(id);
		
		if(categoria == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return categoria;
	}
	
	public Cliente find(Integer id){
		
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente categoria = this.clienteRepository.findOne(id);
		
		if(categoria == null){
			throw new ObjetoNaoEncontradoException("Objeto não encontrado.");
		}
		
		return categoria;
	}
	
	public List<Cliente> findAll(){
		return this.clienteRepository.findAll();
	}
	
	public Cliente insert(Cliente obj){
		obj.setId(null);
		obj = this.clienteRepository.save(obj);
		this.enderecoRepository.save(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj){
		Cliente newObj = getClienteById(obj.getId());
		updateData(newObj, obj);
		return this.clienteRepository.save(newObj);		
	}
	
	public void delete(Integer id){
		getClienteById(id);
		try {
			this.clienteRepository.delete(id);			
		} catch (Exception e) {
			throw new IntegridadeDaInformacaoException("Não é possível excluir porque há entidades relacionadas.");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO objDto){
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDto(ClienteNewDTO objDto){
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = this.cidadeRepository.findOne(objDto.getCidadeId());
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null) cli.getTelefones().add(objDto.getTelefone2());
		if(objDto.getTelefone3() != null) cli.getTelefones().add(objDto.getTelefone3());
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture (MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();		
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = this.imageService.getJpgImageFromFile(multipartFile);
		String fileName = this.prefix + user.getId() + ".jpg";
		
		return this.s3Service.uploadFile(this.imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
