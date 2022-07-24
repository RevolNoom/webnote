package webnote.beans;

public class Note {
	// properties
	private int id;
	private String description;
	private String dateCreated;
	
	// Parameterized constructor without id
	public Note(String description) {
		super();
		this.description = description;
	}
	
	// Parameterized constructor without date created
	public Note(int id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	// Parameterized constructor
	public Note(int id, String description, String dateCreated) {
		super();
		this.id = id;
		this.description = description;
		this.dateCreated = dateCreated;
	}

	// Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
}
