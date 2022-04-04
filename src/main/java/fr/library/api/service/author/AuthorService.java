package fr.library.api.service.author;

import java.util.List;
import java.util.Optional;

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
			return new ResponseEntity<AuthorDTO>(mapper.authorToAuthorDTO(repository.save(foundAuthor)), HttpStatus.OK);
		} else {
			return new ResponseEntity<AuthorDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<AuthorDTO> deleteAuthor(Integer id) {
		Optional<Author> optional = repository.findById(id);
		if (optional.isPresent()) {
			Author foundAuthor = optional.get();
			Optional<List<Book>> booksForAuthor = repositoryBook.findByAuthor(foundAuthor);
			if(booksForAuthor.isPresent()) {
				for (Book book : booksForAuthor.get()) {
					repositoryBook.delete(book);
				}
			}
			repository.delete(foundAuthor);
			return new ResponseEntity<AuthorDTO>(HttpStatus.OK);
		} else {
			return new ResponseEntity<AuthorDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<AuthorDTO> getAuthorById(Integer id) {
		Optional<Author> optional = repository.findById(id);
		if (optional.isPresent()) {
			Author foundAuthor = optional.get();
			return new ResponseEntity<AuthorDTO>(mapper.authorToAuthorDTO(foundAuthor), HttpStatus.OK);
		} else {
			return new ResponseEntity<AuthorDTO>(HttpStatus.NOT_FOUND);
		}
	}

}
