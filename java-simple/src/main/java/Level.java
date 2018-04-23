import com.fasterxml.jackson.annotation.JsonProperty;

public class Level {
	
	private String level_name;
	
	public Level() {
		
	}
	
	public Level(String level_name) {
		this.level_name = level_name;
	}
	
	public void setLevel_name(String level) {
		level_name = level;
	}
	
	
	@JsonProperty
	public String getLevel_name() {
		return level_name;
	}
}
