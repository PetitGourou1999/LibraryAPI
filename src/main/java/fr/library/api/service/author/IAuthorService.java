package fr.library.api.service.author;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.library.api.dto.AuthorDTO;
import fr.library.api.utils.AuthorForm;

public interface IAuthorService {
	
	public ResponseEntity<List<AuthorDTO>> getAllAuthors();
	
	public ResponseEntity<AuthorDTO> getAuthorById(Integer id);

	public ResponseEntity<AuthorDTO> saveAuthor(AuthorForm author);

	public ResponseEntity<AuthorDTO> updateAuthor(Integer id, AuthorForm author);

	public ResponseEntity<AuthorDTO> deleteAuthor(Integer id);
}
