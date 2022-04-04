package fr.library.api.utils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public class AuthorForm {

	@NotNull
	@NotBlank
	@Size(min = 1, max = 200)
	private String firstname;

	@NotNull
	@NotBlank
	@Size(min = 1, max = 200)
	private String surname;

	@NotNull
	@Min(value = 1)
	@Max(value = 150)
	private Integer age;

	public AuthorForm() {
		super();
	}

	public AuthorForm(@NotNull @NotBlank @Size(min = 1, max = 200) String firstname,
			@NotNull @NotBlank @Size(min = 1, max = 200) String surname, @NotNull @Min(1) @Max(150) Integer age) {
		super();
		this.firstname = firstname;
		this.surname = surname;
		this.age = age;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
