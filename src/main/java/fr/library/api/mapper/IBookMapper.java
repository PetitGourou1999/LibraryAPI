package fr.library.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import fr.library.api.dto.BookDTO;
import fr.library.api.model.Book;
import fr.library.api.utils.BookForm;

@Mapper
public interface IBookMapper {
	
	public Book bookFormToBook(BookForm book);
	
	public BookDTO bookToBookDTO(Book book);
	
	public List<BookDTO> booksToBooksAllDTO(List<Book> books);

}
