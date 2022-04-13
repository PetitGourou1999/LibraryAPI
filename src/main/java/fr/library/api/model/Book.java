package fr.library.api.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "title", length = 500, nullable = false)
	private String title;

	@Column(name = "nbexemplaires", nullable = false)
	private Integer nbexemplaires;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "authorid", nullable = false)
	private Author author;

	public Book() {
		super();
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", nbexemplaires=" + nbexemplaires + "]";
	}
	
	

}
