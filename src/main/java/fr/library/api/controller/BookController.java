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

import fr.library.api.dto.BookDTO;
import fr.library.api.service.book.IBookService;
import fr.library.api.utils.BookForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = " SwaggerRESTController", description = "REST API BookController")
public class BookController {
	
	private static final Logger LOG = Logger.getLogger(BookController.class.getName());

	private IBookService service;

	public BookController(IBookService service) {
		super();
		this.service = service;
	}

	@ApiOperation(value = "Récupération de tous les livres")
	@GetMapping("/books")
	public ResponseEntity<List<BookDTO>> getAllBooks() {
		return service.getAllBooks();
	}

	@ApiOperation(value = "Sauvegarde d'un livre", response = BookDTO.class)
	@PostMapping("/books")
	public ResponseEntity<BookDTO> saveBook(@RequestBody @Valid BookForm book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			LOG.log(Level.WARNING, "Binding Errors SaveBook");
			return new ResponseEntity<BookDTO>(new BookDTO(), HttpStatus.CONFLICT);
		} else {
			LOG.log(Level.INFO, "Creating Book");
			return service.saveBook(book);
		}

	}

	@ApiOperation(value = "Mise à jour d'un livre", response = BookDTO.class)
	@PatchMapping("/books/{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, @RequestBody @Valid BookForm book,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			LOG.log(Level.WARNING, "Binding Errors UpdateBook");
			return new ResponseEntity<BookDTO>(new BookDTO(), HttpStatus.CONFLICT);
		} else {
			LOG.log(Level.INFO, "Updating Book");
			return service.updateBook(id, book);
		}

	}

	@ApiOperation(value = "Suppression d'un livre")
	@DeleteMapping("/books/{id}")
	public ResponseEntity<BookDTO> deleteBook(@PathVariable Integer id) {
		LOG.log(Level.INFO, "Trying deletion of Book : " + id);
		return service.deleteBook(id);
	}

	@ApiOperation(value = "Récupération d'un livre par son ID")
	@GetMapping("/books/{id}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Integer id) {
		LOG.log(Level.INFO, "Trying to retireve Book : " + id);
		return service.getBookById(id);
	}

}
