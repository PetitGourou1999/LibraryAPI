package fr.library.api.utils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;

@Builder
public class BookForm {

	@NotNull
	@NotBlank
	@Size(min = 1, max = 500)
	private String title;

	@NotNull
	private AuthorForm author;

	@NotNull
	@Min(value = 0)
	private Integer nbexemplaires;

	public BookForm() {
		super();
	}

	public BookForm(@NotNull @NotBlank @Size(min = 1, max = 500) String title, @NotNull AuthorForm author,
			@NotNull @Min(value = 0) Integer nbexemplaires) {
		super();
		this.title = title;
		this.author = author;
		this.nbexemplaires = nbexemplaires;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNbexemplaires() {
		return nbexemplaires;
	}

	public void setNbexemplaires(Integer nbexemplaires) {
		this.nbexemplaires = nbexemplaires;
	}

	public AuthorForm getAuthor() {
		return author;
	}

	public void setAuthor(AuthorForm author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "BookForm [title=" + title + ", author=" + author + ", nbexemplaires=" + nbexemplaires + "]";
	}
	
	

}
