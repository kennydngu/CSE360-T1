package entityClasses;

public class DiscussionThread {
	private int id;
	private String ThreadTitle;
	private String description;
	
	public DiscussionThread() {}
	
	public DiscussionThread(int id, String ThreadTitle, String description) {
		this.id = id;
		this.ThreadTitle = ThreadTitle;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getThreadTitle() {
		return ThreadTitle;
	}

	public void setThreadTitle(String ThreadTitle) {
		this.ThreadTitle = ThreadTitle;
	}	
	
	public void setNameOfThread(String ThreadTitle) { this.ThreadTitle = ThreadTitle; }
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEmpty() {
		boolean idEmpty = id <= 0;
		boolean titleEmpty = (ThreadTitle == null) || ThreadTitle.trim().isEmpty();
		boolean descEmpty = (description == null) || description.trim().isEmpty();
		return idEmpty && titleEmpty && descEmpty;
	}
}
