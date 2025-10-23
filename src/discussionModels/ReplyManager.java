package discussionModels;

import database.Database;
import java.sql.SQLException;

public class ReplyManager {
	
	
		  private final Database db;
		  public ReplyManager(Database db){ this.db = db; }
		  public int create(entityClasses.Reply r) throws SQLException { return db.createReply(r); }
		  public java.util.List<entityClasses.Reply> listForPost(int postId) throws SQLException { return db.listRepliesForPost(postId); }
		  public boolean update(entityClasses.Reply r) throws SQLException { return db.updateReply(r); }
		  public boolean delete(int id) throws SQLException { return db.deleteReply(id); }
		  public int countForPost(int postId) throws SQLException {return db.countReplies(postId); }
		  public int countUnreadForUser(int postId, String userName) throws SQLException {return db.countUnreadReplies(postId, userName);}
		  public void markAllReadForUser(int postId, String userName) throws SQLException {db.markRepliesReadForPost(postId, userName);}
		  public void markOneRead(int replyId, String userName) throws SQLException {db.markReplyRead(replyId, userName);}
		  public boolean isRead(int replyId, String userName) throws SQLException {return db.isReplyRead(replyId, userName);}
		  public java.util.List<entityClasses.Reply> listUnreadForPost(int postId, String userName) throws SQLException {return db.listUnreadRepliesForPost(postId, userName);}
    
}
