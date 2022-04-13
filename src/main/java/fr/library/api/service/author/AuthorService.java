package fr.library.api.service.author;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.library.api.dto.AuthorDTO;
import fr.library.api.mapper.IAuthorMapper;
import fr.library.api.model.Author;
import fr.library.api.model.Book;
import fr.library.api.repository.IAuthorRepository;
import fr.library.api.repository.IBookRepository;
import fr.library.api.utils.AuthorForm;

@Service
public class AuthorService implements IAuthorService {

	private static final Logger LOG = Logger.getLogger(AuthorService.class.getName());

	private IAuthorMapper mapper;
	private IAuthorRepository repository;
	private IBookRepository repositoryBook;

	public AuthorService(IAuthorMapper mapper, IAuthorRepository repository, IBookRepository repositoryBook) {
		super();
		this.mapper = mapper;
		this.repository = repository;
		this.repositoryBook = repositoryBook;
	}

	@Override
	public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
		return new ResponseEntity<List<AuthorDTO>>(mapper.authorsToAuthorsAllDTO(repository.findAll()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<AuthorDTO> saveAuthor(AuthorForm author) {
		return new ResponseEntity<AuthorDTO>(
				mapper.authorToAuthorDTO(repository.save(mapper.authorFormToAuthor(author))), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<AuthorDTO> updateAuthor(Integer id, AuthorForm author) {
		Optional<Author> optional = repository.findById(id);
		if (optional.isPresent()) {
			Author foundAuthor = optional.get();
			foundAuthor.setAge(author.getAge());
			foundAuthor.setFirstname(author.getFirstname());
			foundAuthor.setSurname(author.getSurname());
			LOG.log(Level.INFO, "UpdateAuthor : Author updated : " + foundAuthor);
			return new ResponseEntity<AuthorDTO>(mapper.authorToAuthorDTO(repository.save(foundAuthor)), HttpStatus.OK);
		} else {
			LOG.log(Level.SEVERE, "UpdateAuthor : Author does not exist");
			return new ResponseEntity<AuthorDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<AuthorDTO> deleteAuthor(Integer id) {
		Optional<Author> optional = repository.findById(id);
		if (optional.isPresent()) {
			Author foundAuthor = optional.get();
			Optional<List<Book>> booksForAuthor = repositoryBook.findByAuthor(foundAuthor);
			if (booksForAuthor.isPresent()) {
				for (Book book : booksForAuthor.get()) {
					repositoryBook.delete(book);
				}
			}
			LOG.log(Level.INFO, "DeleteAuthor : Author deleted : " + foundAuthor);
			repository.delete(foundAuthor);
			return new ResponseEntity<AuthorDTO>(HttpStatus.OK);
		} else {
			LOG.log(Level.SEVERE, "DeleteAuthor : Author does not exist");
			return new ResponseEntity<AuthorDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<AuthorDTO> getAuthorById(Integer id) {
		Optional<Author> optional = repository.findById(id);
		if (optional.isPresent()) {
			Author foundAuthor = optional.get();
			LOG.log(Level.INFO, "getAuthorById : Author found : " + foundAuthor);
			return new ResponseEntity<AuthorDTO>(mapper.authorToAuthorDTO(foundAuthor), HttpStatus.OK);
		} else {
			LOG.log(Level.SEVERE, "DeleteAuthor : Author does not exist");
			return new ResponseEntity<AuthorDTO>(HttpStatus.NOT_FOUND);
		}
	}

}
