package fr.library.api.utils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public class BookForm {

	@NotNull
	@NotBlank
	@Size(min = 1, max = 500)
	private String title;

	@NotNull
	private AuthorForm author;

	public BookForm() {
		super();
	}

	public BookForm(@NotNull @NotBlank @Size(min = 1, max = 500) String title, @NotNull AuthorForm author) {
		super();
		this.title = title;
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AuthorForm getAuthor() {
		return author;
	}

	public void setAuthor(AuthorForm author) {
		this.author = author;
	}

}
