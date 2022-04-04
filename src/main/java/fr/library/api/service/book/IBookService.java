package fr.library.api.service.book;

import java.util.List;

import org.springframework.http.ResponseEntity;

import fr.library.api.dto.BookDTO;
import fr.library.api.utils.BookForm;

public interface IBookService {
	
	public ResponseEntity<List<BookDTO>> getAllBooks();
	
	public ResponseEntity<BookDTO> getBookById(Integer id);
	
	public ResponseEntity<BookDTO> saveBook(BookForm book);
	
	public ResponseEntity<BookDTO> updateBook(Integer id, BookForm book);
	
	public ResponseEntity<BookDTO> deleteBook(Integer id);
}
