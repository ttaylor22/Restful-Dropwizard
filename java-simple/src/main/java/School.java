import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


public class School {
	
	private Long school_id;
	private String school_name;
	private String school_website;
    private String school_gender;
    private List<Level> levels = new ArrayList<Level>();
    
    public School() {
    	
    }
    
    public School(Long school_id, String school_name, String school_website, String school_gender, List<Level> levels) {	
    	this.school_id = school_id;
    	this.school_name = school_name;
    	this.school_website = school_website;
    	this.school_gender= school_gender;
    	this.levels =levels;
    }
    public void setSchool_id(Long i) {
    	school_id = i;
    }
    
    public void setSchool_name(String string){
        school_name = string;
    }
    
    public void setSchool_website(String string){
        school_website = string;
    }
    
    public void addSchool_level(Level l) {
		levels.add(l);
		
	}
    
    public void addLevel (Level l){
        levels.add(l);
    }
    
    public void setSchool_gender(String string){
        school_gender = string;
    }
    
    @JsonProperty
    public Long getSchool_id(){
        return school_id;
    }
    
    @JsonProperty
    public String getSchool_name(){
        return school_name;
    }
    
    @JsonProperty
    public String getSchool_website(){
        return school_website;
    }
    
    @JsonProperty
    public List<Level> getSchool_levels(){
        return levels;
    }
    
    @JsonProperty
    public String getSchool_gender(){
        return school_gender;
    }

}
