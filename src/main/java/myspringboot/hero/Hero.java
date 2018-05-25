package myspringboot.hero;

public class Hero {

	private Long id;
	private String name;
	
	public Hero() {
		
	}
	
	public Hero(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Hero [id=" + id + ", name=" + name + "]";
	}
	
}
