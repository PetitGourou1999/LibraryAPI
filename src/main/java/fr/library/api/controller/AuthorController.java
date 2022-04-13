package fr.library.api.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.library.api.dto.AuthorDTO;
import fr.library.api.service.author.IAuthorService;
import fr.library.api.utils.AuthorForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = " SwaggerRESTController", description = "REST API AuthorController")
public class AuthorController {
	
	private static final Logger LOG = Logger.getLogger(AuthorController.class.getName());

	private IAuthorService service;

	public AuthorController(IAuthorService service) {
		super();
		this.service = service;
	}

	@ApiOperation(value = "Récupération de tous les auteurs")
	@GetMapping("/authors")
	public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
		return service.getAllAuthors();
	}

	@ApiOperation(value = "Sauvegarde d'un auteur", response = AuthorDTO.class)
	@PostMapping("/authors")
	public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody @Valid AuthorForm author, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			LOG.log(Level.WARNING, "Binding Errors SaveAuthor");
			return new ResponseEntity<AuthorDTO>(new AuthorDTO(), HttpStatus.CONFLICT);
		} else {
			LOG.log(Level.INFO, "Saving Author");
			return service.saveAuthor(author);
		}

	}

	@ApiOperation(value = "Mise à jour d'un auteur", response = AuthorDTO.class)
	@PatchMapping("/authors/{id}")
	public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Integer id, @RequestBody @Valid AuthorForm author,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			LOG.log(Level.WARNING, "Binding Errors UpdateAuthor");
			return new ResponseEntity<AuthorDTO>(new AuthorDTO(), HttpStatus.CONFLICT);
		} else {
			LOG.log(Level.INFO, "Updating Author");
			return service.updateAuthor(id, author);
		}

	}

	@ApiOperation(value = "Suppression d'un auteur")
	@DeleteMapping("/authors/{id}")
	public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable Integer id) {
		LOG.log(Level.INFO, "Trying deletion of Author : " + id);
		return service.deleteAuthor(id);
	}
	
	@ApiOperation(value = "Récupération d'un auteur par son ID")
	@GetMapping("/authors/{id}")
	public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Integer id) {
		LOG.log(Level.INFO, "Trying to retrieve Author : " + id);
		return service.getAuthorById(id);
	}

}
