package fr.library.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import fr.library.api.model.Author;
import fr.library.api.model.Book;

public interface IBookRepository extends JpaRepository<Book, Integer> {

	Optional<Book> findById(@Param("id") Integer id);
	
	Optional<List<Book>> findByAuthor(@Param("author") Author author);

}
