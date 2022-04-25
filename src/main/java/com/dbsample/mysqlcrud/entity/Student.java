package com.dbsample.mysqlcrud.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.hateoas.RepresentationModel;

import com.dbsample.mysqlcrud.errors.ErrorMessageCodes;
import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Builder;

@Entity
//@Table(name="STUDENT")
@JsonFilter("studentFilter")
@Builder
public class Student extends RepresentationModel<Student>{

	@Id
	@GeneratedValue
	private int id;
	@NotNull(message = ErrorMessageCodes.NAME_IS_BLANK)
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z ,.'-]+$", message = ErrorMessageCodes.NAME_INVALID)
	private String name;// only alphabets and spaces
	@NotNull(message = ErrorMessageCodes.GENDER_IS_BLANK)
	@Pattern(regexp = "^(?:m|M|male|Male|f|F|female|Female)$")
	private String gender;// male or female
	@NotNull(message = ErrorMessageCodes.GRADE_IS_BLANK)
	@Pattern(regexp = "^(([A-D]|[a-d])[-+]?|(F|f))$", message = ErrorMessageCodes.GRADE_INVALID)
	private String grade;// only letters and +/- (A+ to F-)
	@NotNull(message = ErrorMessageCodes.EMAIL_IS_BLANK)
	@Pattern(regexp = "^(.+)@(.+)$", message = ErrorMessageCodes.EMAIL_INVALID)
	private String email;// alphanumeric@alphanumeric.alpha
	@NotNull(message = ErrorMessageCodes.PHONE_IS_BLANK)
	@Pattern(regexp = "\\d{10}", message = ErrorMessageCodes.PHONE_IS_INVALID)
	private String phone;// 10 numbers
	@NotNull(message = ErrorMessageCodes.ADDRESS_IS_BLANK)
	private String address;// alphanumeric
	@OneToMany(targetEntity = Message.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "sm_fk", referencedColumnName = "id")
	private List<Message> messages;

	public Student() {
		super();
	}
	
	public Student(int id,
			@NotNull(message = "Name cannot be blank. ") @Pattern(regexp = "^[a-zA-Z][a-zA-Z ,.'-]+$", message = "Name must start with a letter. It can contain only letters, Spaces, Commas, Hyphens (-), Periods (.), and Single quotes ('). ") String name,
			@NotNull(message = "Gender cannot be blank. ") @Pattern(regexp = "^(?:m|M|male|Male|f|F|female|Female)$") String gender,
			@NotNull(message = "Grade cannot be blank. ") @Pattern(regexp = "^(([A-D]|[a-d])[-+]?|(F|f))$", message = "Grade must be letters from A+ to D- or F. ") String grade,
			@NotNull(message = "Email cannot be blank. ") @Pattern(regexp = "^(.+)@(.+)$", message = "Email must follow format a@b.c ") String email,
			@NotNull(message = "Phone number cannot be blank. ") @Pattern(regexp = "\\d{10}", message = "Phone number must have 10 digits. ") String phone,
			@NotNull(message = "Address cannot be blank. ") String address,
			List<Message> messages) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.grade = grade;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.messages = messages;
	}

	public Student(int id,
			@NotNull(message = "Name cannot be blank. ") @Pattern(regexp = "^[a-zA-Z][a-zA-Z ,.'-]+$", message = "Name must start with a letter. It can contain only letters, Spaces, Commas, Hyphens (-), Periods (.), and Single quotes ('). ") String name,
			@NotNull(message = "Gender cannot be blank. ") String gender,
			@NotNull(message = "Grade cannot be blank. ") @Pattern(regexp = "^(([A-D]|[a-d])[-+]?|(F|f))$", message = "Grade must be letters from A+ to D- or F. ") String grade,
			@NotNull(message = "Email cannot be blank. ") @Pattern(regexp = "^(.+)@(.+)$", message = "Email must follow format a@b.c ") String email,
			@NotNull(message = "Phone number cannot be blank. ") @Pattern(regexp = "\\d{10}", message = "Phone number must have 10 digits. ") String phone,
			@NotNull(message = "Address cannot be blank. ") String address) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.grade = grade;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", grade=" + grade + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", messages=" + messages + "]";
	}
	
}
