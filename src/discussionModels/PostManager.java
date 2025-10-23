package discussionModels;

import database.Database;
import entityClasses.Post;
import java.sql.SQLException;
import java.util.List;

public class PostManager {
 private final Database db;

 public PostManager(Database db) { 
	 this.db = db; 
 }
 
 

 public int create(Post p) throws SQLException {
     return db.createPost(p);
 }

 public Post get(int id) throws SQLException {
     return db.getPostById(id);
 }

 public List<Post> listAll() throws SQLException {
     return db.listPosts();
 }
 
 public List<Post> listMyPosts(String author) throws SQLException {
	 return db.listMyPosts(author);
 }

 public boolean update(Post p) throws SQLException {
     return db.updatePost(p);
 }

 public boolean delete(int id) throws SQLException {
     return db.deletePost(id);
 }
 
 public List<Post> searchPosts(String keyword, String thread) throws SQLException{
	 return db.searchPosts(keyword, thread);
 }
 
 public void markRead(int postId, String user) throws SQLException { db.markPostRead(postId, user); }
 public boolean isRead(int postId, String user) throws SQLException { return db.isPostRead(postId, user); }

}


