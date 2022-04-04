package fr.library.api.service.book;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.library.api.dto.BookDTO;
import fr.library.api.mapper.IAuthorMapper;
import fr.library.api.mapper.IBookMapper;
import fr.library.api.model.Author;
import fr.library.api.model.Book;
import fr.library.api.repository.IAuthorRepository;
import fr.library.api.repository.IBookRepository;
import fr.library.api.utils.BookForm;

@Service
public class BookService implements IBookService {

	private IBookMapper mapper;
	private IAuthorMapper mapperAuthor;
	private IBookRepository repository;
	private IAuthorRepository repositoryAuthor;

	public BookService(IBookMapper mapper, IAuthorMapper mapperAuthor, IBookRepository repository,
			IAuthorRepository repositoryAuthor) {
		super();
		this.mapper = mapper;
		this.mapperAuthor = mapperAuthor;
		this.repository = repository;
		this.repositoryAuthor = repositoryAuthor;
	}

	@Override
	public ResponseEntity<List<BookDTO>> getAllBooks() {
		return new ResponseEntity(mapper.booksToBooksAllDTO(repository.findAll()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<BookDTO> saveBook(BookForm book) {
		Book bookFormToBook = mapper.bookFormToBook(book);
		bookFormToBook = checkAuthor(book, bookFormToBook);
		return new ResponseEntity(mapper.bookToBookDTO(repository.save(bookFormToBook)), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<BookDTO> updateBook(Integer id, BookForm book) {
		Optional<Book> optionalBook = repository.findById(id);
		if (optionalBook.isPresent()) {
			Book foundBook = optionalBook.get();
			foundBook.setTitle(book.getTitle());
			foundBook.setAuthor(mapperAuthor.authorFormToAuthor(book.getAuthor()));
			foundBook = checkAuthor(book, foundBook);
			return new ResponseEntity(mapper.bookToBookDTO(repository.save(foundBook)), HttpStatus.OK);
		} else {
			return new ResponseEntity<BookDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<BookDTO> deleteBook(Integer id) {
		Optional<Book> optionalBook = repository.findById(id);
		if (optionalBook.isPresent()) {
			Book foundBook = optionalBook.get();
			repository.delete(foundBook);
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity<BookDTO>(HttpStatus.NOT_FOUND);
		}
	}

	private Book checkAuthor(BookForm form, Book book) {
		Optional<Author> optional = repositoryAuthor.findBySurnameAndFirstnameAndAge(form.getAuthor().getSurname(),
				form.getAuthor().getFirstname(), form.getAuthor().getAge());

		if (optional.isPresent()) {
			book.setAuthor(optional.get());
		}

		return book;
	}

	@Override
	public ResponseEntity<BookDTO> getBookById(Integer id) {
		Optional<Book> optionalBook = repository.findById(id);
		if (optionalBook.isPresent()) {
			Book foundBook = optionalBook.get();
			BookDTO bookToBookDTO = mapper.bookToBookDTO(foundBook);
			return new ResponseEntity(bookToBookDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<BookDTO>(HttpStatus.NOT_FOUND);
		}
	}

}
