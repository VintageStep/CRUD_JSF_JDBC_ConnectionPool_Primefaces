package com.beans;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.persistence.AccesStudentTracker;

@ManagedBean (name="studentController")
@ViewScoped
public class StudentController implements Serializable{
	
	// Attributes
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 257744571441207216L;
	private List<Student> students;
	AccesStudentTracker db;
	private Logger logger = Logger.getLogger(getClass().getName());
	private String searchName;
	private List<SortMeta> sortBy;
	
	// Constructor
	
	public StudentController() throws Exception {
		students = new ArrayList<>();
		
		db = AccesStudentTracker.getInstance();
		
        sortBy = new ArrayList<>();
        sortBy.add(SortMeta.builder()
                .field("fName")
                .order(SortOrder.ASCENDING)
                .build());

        sortBy.add(SortMeta.builder()
                .field("lName")
                .order(SortOrder.ASCENDING)
                .build());
        sortBy.add(SortMeta.builder()
                .field("email")
                .order(SortOrder.ASCENDING)
                .build());
        sortBy.add(SortMeta.builder()
                .field("id")
                .order(SortOrder.ASCENDING)
                .build());
	}
	
	// Methods
	
	public void loadStudents() {
		logger.info("loading students");
		logger.info("searchName = " + searchName);
		
		students.clear();
		
		try {
			
			// Validation to use the search feature
			if(searchName != null && searchName.trim().length() >0) {
				// Search students by name
				students = db.searchStudents(searchName);
			} else {
				// Load all students from database
				students = db.getStudentList();
			}

		} catch (Exception e) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading students", e);
			
			// add error message to JSF page
			addErrorMessage(e);
		} finally {
			// reset the search info
			searchName = null;
		}
	}
	
	public String loadStudent(int studentId) {
		
		logger.info("loading student: " + studentId);
		
		try {
			// Get student from database
			Student student = db.getStudent(studentId);
			
			// Put in the request attribute, making this information available for the JSF form
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("student", student); 
			/* String on the left is the name of the bean that should store the object, one might think
			 * that is just a label to call later in the JSF page(similar to how information is mapped on JSP) 
			 * but it needs to refer the bean of the corresponding item.
			 */
		} catch (Exception e) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading student id: " + studentId + e);
			// add error message for the JSF page
			addErrorMessage(e);
			
			return null;
		}
		
		return "update-student-form.xhtml";
	}
	
	public String addStudentMultiple(Student student) {
		
		logger.info("Adding stduent: " + student);
		
		boolean result = false;
				
		try {
			
			//add Student to the database
			result = db.addStudent(student);
			
			updateResult(result);
			
		} catch (Exception e) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding student", e);
			
			// add error message for JSF page
			addErrorMessage(e);
			
			return null;
		}
		
		return "add-student-form?faces-redirect=false";
	}
	
	public String addStudentSimple(Student student) {
		
		logger.info("Adding stduent: " + student);
		
		try {
			
			//add Student to the database
			db.addStudent(student);

		} catch (Exception e) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding student", e);
			
			// add error message for JSF page
			addErrorMessage(e);
			
			return null;
		}
		
		return "students-list?faces-redirect=true";
	}

	public String updateStudent(Student student) {
		
		logger.info("updating student: " + student);
		
		try {
			// Update student in the db
			db.updateStudent(student);
			
		} catch (Exception e) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating student", e);
			
			// add error message for JSF page
			addErrorMessage(e);
			
			return null;
		}
		
		return "students-list?faces-redirect=true";
	}
	
	public String deleteStudent(int studentId) {
		logger.info("Deleting student id: " + studentId);
		
		try {
			// Update student in the db
			db.deleteStudent(studentId);
			
		} catch (Exception e) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting student id: " + studentId, e);
			
			// add error message for JSF page
			addErrorMessage(e);
			
			return null;
		}
		
		return "students-list";		
	}
	
	private void addErrorMessage(Exception e) {
		FacesMessage message = new FacesMessage("Error: " + e.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
		
	}
	
	private void updateResult(Boolean result) {
		if(Boolean.TRUE.equals(result)) {
			FacesMessage message = new FacesMessage("Entry done correctly");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage("Entry couldn't be done");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	// Getters & Setters
	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
