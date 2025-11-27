package discussionModels;

import java.sql.SQLException;
import java.util.List;

import database.Database;
import entityClasses.DiscussionThread;

public class ThreadManager {
	private final Database db;

	 public ThreadManager(Database db) { 
		 this.db = db; 
	 }
	 

	 public int create(DiscussionThread t) throws SQLException {
	     return db.createThread(t);	 //needs to implement this in database (just copy and modify the Post methods in database)
	 }

	 public DiscussionThread getThread(int id) throws SQLException {
	     return db.getThreadById(id); 	//needs to implement this in database (just copy and modify the Post methods in database)
	 }

	 public List<DiscussionThread> listAllThread() throws SQLException {
	     return db.listThread(); 	//needs to implement this in database (just copy and modify the Post methods in database)
	 }
	 
	 public boolean updateThread(DiscussionThread t) throws SQLException {
	     return db.updateThread(t);	//needs to implement this in database (just copy and modify the Post methods in database)
	 }

	 public boolean deleteThread(int id) throws SQLException {
	     return db.deleteThread(id); //needs to implement this in database (just copy and modify the Post methods in database)
	 }

	}
