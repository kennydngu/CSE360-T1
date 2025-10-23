package entityClasses;

import java.sql.Timestamp;

// This class defines a single discussion post with an id of the user, the authors name, title, the content of the post
// and timestamps of when it was created and updated
public class Reply {
	
	private int id;
	private String author;
	private String title;
	private String content;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private int postId;
	
	// Constructor class
	public Reply (int id, String author, String content, Timestamp createdAt, Timestamp updatedAt, int postId) {
		this.id = id;
		this.postId = postId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
	}
	
	// getter and setter functions 
	
	public int getId() {
		return id;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public int getPostId() {
		return postId;
	}
	

    public String getContent() {
        return content;
    }
    

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
    
    
    public void setUpdatedAt(Timestamp updatedAt) {
    	this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
    	return "[" + id + "]" + title + " created by " + author + " - " + content;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
