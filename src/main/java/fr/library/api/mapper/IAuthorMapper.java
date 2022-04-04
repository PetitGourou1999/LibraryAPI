package fr.library.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import fr.library.api.dto.AuthorDTO;
import fr.library.api.model.Author;
import fr.library.api.utils.AuthorForm;

@Mapper
public interface IAuthorMapper {

	public Author authorFormToAuthor(AuthorForm author);

	public AuthorDTO authorToAuthorDTO(Author author);

	public List<AuthorDTO> authorsToAuthorsAllDTO(List<Author> authors);
}
