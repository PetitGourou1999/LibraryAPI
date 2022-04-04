package fr.library.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import fr.library.api.model.Author;
import fr.library.api.model.Book;

public interface IAuthorRepository extends JpaRepository<Author, Integer> {

	Optional<Author> findById(@Param("id") Integer id);

	Optional<Author> findBySurnameAndFirstnameAndAge(@Param("surname") String surname,
			@Param("firstname") String firstname, @Param("age") int age);
}
