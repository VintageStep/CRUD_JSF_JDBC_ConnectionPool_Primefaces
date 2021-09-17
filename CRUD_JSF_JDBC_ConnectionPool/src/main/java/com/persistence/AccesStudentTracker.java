package com.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.beans.Student;

public class AccesStudentTracker {
	
	
	private static AccesStudentTracker instance;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public static AccesStudentTracker getInstance() throws Exception {
		
	 if(instance == null) {
		 instance = new AccesStudentTracker();
	 }
	 return instance;
	}
	

	// Methods to access db
	
	public List<Student> getStudentList() throws SQLException{
		// 1. Declaration of variables.
		Connection myConnection = null;
		String sql = "call student_tracker.student_list();";
		CallableStatement query = null;
		ResultSet cursor = null;
		List<Student> result = new ArrayList<>();
		try {
			// 2. Open connection
			myConnection = ConnectionPool.getInstance().getConnection();
			// 3. Get statement from connection.
			query = myConnection.prepareCall(sql);
			// 3b. Set arguments if required
			// 4. execute query
			cursor = query.executeQuery();
			// 5. Retrieve results
			while(cursor.next()) {
				result.add(new Student(cursor.getInt("id"),
									   cursor.getString("first_name"),
									   cursor.getString("last_name"),
									   cursor.getString("email")));
				}
		} finally {
			// 6. Close cursor, statement, connection.
			close(myConnection,query,cursor);
		}	
		// 7. Return results
		return result;
	}

	public Student getStudent(int studentId) throws Exception {
		// 1. Declaration of variables.
		Connection myConnection = null;
		String sql = "call student_tracker.get_one_student(?);";
		CallableStatement query = null;
		ResultSet cursor= null;
		Student result = null;
		try {
			// 2. Open connection
			myConnection = ConnectionPool.getInstance().getConnection();
						
				if(myConnection != null ) {
					// 3. Get statement from connection.
					query = myConnection.prepareCall(sql);
					// 3b. Set arguments if required
			    	query.setInt(1, studentId);
					// 4. execute query
					cursor = query.executeQuery();
					// 5. Retrieve result
					if(cursor.next()) {
						int id = cursor.getInt("id");
						String fName = cursor.getString("first_name");
						String lName = cursor.getString("last_name");
						String email = cursor.getString("email");
						result = new Student(id,fName,lName,email);
						}		
				} else {
					throw new Exception("Could not find student id: " + studentId);
				}
				return result;		
		} finally {
			// 6. Close cursor, statement, connection.
			close(myConnection,query,cursor);
		}
	}
	
	public List<Student> searchStudents(String searchName) throws SQLException{
		// 1. Declaration of variables. (this time around the String for the query has to be defined later)
		Connection myConnection = null;
		String sql;
		CallableStatement query = null;
		ResultSet cursor = null;
		List<Student> result = new ArrayList<>();	
		try {
			// 2. Open connection
			myConnection = ConnectionPool.getInstance().getConnection();
			/*
			* >Only search if the searchName is not empty
			*/
			if (searchName != null && searchName.trim().length() > 0) {
				// 3. Get statement from connection.
				sql = "call student_tracker.search_student_by_name(?);";
				query = myConnection.prepareCall(sql);
				// 3b. Set arguments if required
				query.setString(1, searchName);
				
			} else {
				// 3c. Get alternative statement from connection.
				sql = "call student_tracker.student_list();";
				query = myConnection.prepareCall(sql);
				// 3d. Set arguments if required.
			}
			// 4. execute query
			cursor = query.executeQuery();
			// 5. Retrieve results
			while(cursor.next()) {
				result.add(new Student(cursor.getInt("id"),
									   cursor.getString("first_name"),
									   cursor.getString("last_name"),
									   cursor.getString("email")));}
			// 6. Return results
			return result;
		} finally {
			// 6. Close cursor, statement, connection.
			close(myConnection,query,cursor);
		}
	}
	
	public boolean addStudent(Student student) throws SQLException {
		// 1. Declaration of variables.
		boolean result = false;
		Connection myConnection = null;
		String sql = "call student_tracker.new_student(?,?,?);";
		CallableStatement query = null;
		
		try {
			// 2. Open connection
			myConnection = ConnectionPool.getInstance().getConnection();
			
			if(myConnection != null ) {
				// 3. Get statement from connection.
				query = myConnection.prepareCall(sql);
				// 3b. Set arguments if required
				query.setString(1, student.getfNname());
				query.setString(2, student.getlName());
				query.setString(3, student.getEmail());
				// 4. execute update
				int update = query.executeUpdate();
				// 5. Retrieve result from update
				result = update > 0;
				
				
			} else {
				logger.log(Level.SEVERE, "Connection failure");
			}
			

		} finally {
			// 6. Close cursor, statement, connection.
				close(myConnection,query);
		}
		// 7. Return result
		return result;
	}
	
	public void addStudentSimple(Student student) throws SQLException {
		// 1. Declaration of variables.
		Connection myConnection = null;
		String sql = "call student_tracker.new_student(?,?,?);";
		CallableStatement query = null;
		
		try {
			// 2. Open connection
			myConnection = ConnectionPool.getInstance().getConnection();
			
			if(myConnection != null ) {
				// 3. Get statement from connection.
				query = myConnection.prepareCall(sql);
				// 3b. Set arguments if required
				query.setString(1, student.getfNname());
				query.setString(2, student.getlName());
				query.setString(3, student.getEmail());
				// 4. execute update
				query.executeUpdate();
				// 5. Retrieve data (not in this case, but you can make this method boolean)
				
			} else {
				logger.log(Level.SEVERE, "Connection failure");
			}
			
			
		} finally {
			// 6. Close connection, query and cursor if there's any.
			close(myConnection,query);
		}
	}

	public void updateStudent(Student student) throws SQLException {
		// 1. Declaration of variables.
		Connection myConnection = null;
		String sql = "call student_tracker.update_student(?, ?, ?, ?);";
		CallableStatement query = null;
		try {
			// 2. Open connection
			myConnection = ConnectionPool.getInstance().getConnection();
						
				if(myConnection != null ) {
					// 3. Get statement from connection.
					query = myConnection.prepareCall(sql);
					// 3b. Set arguments if required
			    	query.setInt(1, student.getId());
			    	query.setString(2, student.getfNname());
			    	query.setString(3, student.getlName());
			    	query.setString(4, student.getEmail());
					// 4. execute query
					query.execute();
					// 5. Retrieve data (not in this case, but you can make this method boolean)
	
				} 
		} finally {
			// 6. Close connection, query and cursor if there's any.
			close(myConnection,query);
		}
	}
	
	public void deleteStudent(int studentId) throws SQLException {
		// 1. Declaration of variables.
		Connection myConnection = null;
		String sql = "call student_tracker.delete_student(?);";
		CallableStatement query = null;
		try {
			// 2. Open connection
			myConnection = ConnectionPool.getInstance().getConnection();
						
				if(myConnection != null ) {
					// 3. Get statement from connection.
					query = myConnection.prepareCall(sql);
					// 3b. Set arguments if required
					query.setInt(1, studentId);
					// 4. execute query
					query.execute();
					// 5. Retrieve data (not in this case, but you can make this method boolean)
				} 
		} finally {
			// 6. Close connection, query and cursor if there's any.
			close(myConnection,query);
		}
	}

	// Methods to close the connections and sql objects
	
	private void close(Connection myConnection, Statement myQuery) {

		try {
			if (myConnection != null) {
				ConnectionPool.getInstance().closeConnection(myConnection);
			}

			if (myQuery != null) {
				myQuery.close();
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}		
	}
	
	private void close(Connection myConnection, Statement myQuery, ResultSet myCursor) {

		try {
			if (myConnection != null) {
				ConnectionPool.getInstance().closeConnection(myConnection);
			}

			if (myQuery != null) {
				myQuery.close();
			}

			if (myCursor != null) {
				myCursor.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}	

}	
