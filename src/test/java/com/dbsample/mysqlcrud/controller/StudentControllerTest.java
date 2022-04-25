package com.dbsample.mysqlcrud.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dbsample.mysqlcrud.entity.Message;
import com.dbsample.mysqlcrud.entity.Student;
import com.dbsample.mysqlcrud.service.MessageService;
import com.dbsample.mysqlcrud.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private StudentService service;

	@MockBean
	private MessageService mService;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	Message mockMessage1 = new Message(3, "Hello World");
	Message mockMessage2 = new Message(4, "World");
	Message mockMessage3 = new Message(33, "Hello World");
	Message mockMessage4 = new Message(34, "I am not typing a message");

	Student mockA = new Student(1, "Bob", "Male", "A", "bob@bob.bob", "1111111111", "address", null);
	Student mockB = new Student(2, "Alice", "Female", "C", "Alice@abc.xyz", "2222223333", "address2",
			new ArrayList<Message>() {
				{
					add(mockMessage1);
					add(mockMessage2);
				}
			});
	Student mockC = new Student(50, "Ash", "Male", "A", "Ash@gmail.com", "8888889999", "earth",
			new ArrayList<Message>() {
				{
					add(mockMessage3);
					add(mockMessage4);
				}
			});

	@Test
	public void findAllStudents_success() throws Exception {
		List<Student> students = new ArrayList<Student>();
		students.add(mockA);
		students.add(mockB);
		students.add(mockC);

		when(service.getStudents()).thenReturn(students);

		mvc.perform(MockMvcRequestBuilders.get("/students").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
				.andExpect(jsonPath("$[1].name").value("Alice"));
	}

	@Test
	public void findStudentById_success() throws Exception {
		when(service.getStudentById(mockA.getId())).thenReturn(Optional.of(mockA).get());

		mvc.perform(MockMvcRequestBuilders.get("/studentById/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.name").value("Bob"));
	}

	@Test
	public void findStudentByName_success() throws Exception {
		when(service.getStudentByName(mockC.getName())).thenReturn(Optional.of(mockC).get());

		mvc.perform(MockMvcRequestBuilders.get("/student/Ash").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.email").value("Ash@gmail.com"));
	}

	@Test
	public void addStudent_success() throws Exception {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);

		// Add Bob
		String content = objectMapper.setFilterProvider(filterProvider).writer().writeValueAsString(mockA);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addStudent")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
//		System.out.println("AddStudent content check Bob: ");
//		System.out.println(content);
		when(service.saveStudent(Mockito.any(Student.class))).thenReturn(mockA);
		mvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.name", Matchers.is("Bob")));

		// Add Alice
		content = objectMapper.setFilterProvider(filterProvider).writer().writeValueAsString(mockB);

		mockRequest = MockMvcRequestBuilders.post("/addStudent").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content);
//		System.out.println("AddStudent content check Alice: ");
//		System.out.println(content);
		when(service.saveStudent(Mockito.any(Student.class))).thenReturn(mockB);
		mvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.name", Matchers.is("Alice")));
	}

	@Test
	public void updateStudent_success() throws Exception {
		Student updatedMockA = Student.builder().id(1).name("Bob").gender("Male").grade("F").address("Jupiter")
				.phone("9999999999").email("bob@bob.bob").messages(new ArrayList<Message>() {
					{
						add(new Message(3, "Hello World"));
						add(new Message(4, "I am bob"));
					}
				}).build();
		when(service.updateStudent(updatedMockA)).thenReturn(updatedMockA);

		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);

		// Update Bob
//		String content = objectMapper.setFilterProvider(filterProvider).writer().writeValueAsString(mockA);
		String updatedContent = objectMapper.setFilterProvider(filterProvider).writer()
				.writeValueAsString(updatedMockA);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/update")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(updatedContent);
//		System.out.println("UpdateStudent content check: ");
//		System.out.println("Old Content: " + content);
//		System.out.println("Updated Content: " + updatedContent);
		mvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.grade", Matchers.is("F")))
				.andExpect(jsonPath("$.phone", Matchers.is("9999999999")))
				.andExpect(jsonPath("$.address", Matchers.is("Jupiter")))
				.andExpect(jsonPath("$.messages", Matchers.notNullValue()));

	}

	@Test
	public void deleteStudent_success() throws Exception {
		when(service.getStudentById(mockA.getId())).thenReturn(Optional.of(mockA).get());

		// Delete Bob
		mvc.perform(MockMvcRequestBuilders.delete("/delete/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void viewMessages_success() throws Exception {
		when(service.getStudentById(mockB.getId())).thenReturn(Optional.of(mockB).get());

		mvc.perform(MockMvcRequestBuilders.get("/student/2/viewMessages").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.messages").isNotEmpty());
	}

	@Test
	public void postMessage_success() throws Exception {
		Message newMessage = new Message(5, "World Hello");
		Student updatedMockB = new Student(2, "Alice", "Female", "C", "Alice@abc.xyz", "2222223333", "address2",
				new ArrayList<Message>() {
					{
						add(new Message(3, "Hello World"));
						add(new Message(4, "World"));
						add(newMessage);
					}
				});
		;

		when(service.getStudentById(mockB.getId())).thenReturn(Optional.of(mockB).get());
		when(service.saveMessage(2, newMessage)).thenReturn(updatedMockB);

		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("id", "gender",
				"grade", "email", "phone", "address");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);

		// Update Alice
		String updatedContent = objectMapper.setFilterProvider(filterProvider).writer()
				.writeValueAsString(updatedMockB);

		System.out.println(updatedContent);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/student/2/postMessage")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(updatedContent);
		mvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.messages", Matchers.hasSize(3)));
	}

	@Test
	public void viewMessagesById_success() throws Exception {
		when(service.getStudentById(mockB.getId())).thenReturn(Optional.of(mockB).get());
		when(mService.getMessageById(mockMessage1.getmId())).thenReturn(Optional.of(mockMessage1).get());
		mvc.perform(MockMvcRequestBuilders.get("/student/2/viewMessageById/3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.mId").value("3"))
				.andExpect(jsonPath("$.message").value("Hello World"));
	}

	@Test
	public void deleteMessageById_success() throws Exception {
		when(service.getStudentById(mockB.getId())).thenReturn(Optional.of(mockB).get());
		when(mService.deleteMessage(mockMessage1.getmId())).thenReturn(Optional.of(mockMessage1).get());

		// Delete Alice's message
		mvc.perform(MockMvcRequestBuilders.delete("/student/2/deleteMessage/3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
