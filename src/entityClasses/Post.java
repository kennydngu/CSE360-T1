package entityClasses;

import java.sql.Timestamp;

// This class defines a single discussion post with an id of the user, the authors name, title, the content of the post
// and timestamps of when it was created and updated
public class Post {
	
	private int id;
	private String author;
	private String title;
	private String content;
	private String thread;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	// Constructor class
	public Post() {}

	
	public Post (int id, String author, String title, String content, String thread, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.thread = thread;
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
	
	public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    
    public String getThread() {
    	return thread;
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
    
    public void setThread(String thread) {
    	this.thread = thread;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
    	this.updatedAt = updatedAt;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public void setAuthor(String author) {
    	this.author = author;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
    	this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
    	return "[" + id + "]" + title + " created by " + author + " - " + content;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
