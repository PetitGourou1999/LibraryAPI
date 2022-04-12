package fr.library.api.dto;

public class BookDTO {

	private Integer id;

	private String title;

	private Integer nbexemplaires;

	private AuthorDTO author;

	public BookDTO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

}
