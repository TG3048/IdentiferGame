package Entities;

public class Profile {

	private String id;
	private String type;
	private String slug;
	private String jobTitle;
	private String firstName;
	private String lastName;
	private Object headshot;
	
	public String getFirstName() {
		return firstName;
	}

	public Object getHeadshot() {
		return headshot;
	}

	public String getId() {
		return id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSlug() {
		return slug;
	}

	public String getType() {
		return type;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setHeadshot(Object headshot) {
		this.headshot = headshot;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Person = " + String.valueOf(this.getId()) + this.getFirstName() + " "
				+ this.getLastName();
	}

	@Override
	public boolean equals(Object profileObject) {

		if (profileObject == null) {
			return false;
		}

		if (!(profileObject instanceof Profile)) {
			return false;
		}	

		Profile profile = (Profile) profileObject;
		return this.id == profile.id;
	}

	@Override
	public int hashCode() {
		return this.getLastName().length() * this.getJobTitle().length() * 31;
	}
	
}