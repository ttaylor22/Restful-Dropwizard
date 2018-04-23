import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class SchoolConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }
		
		@JsonProperty("database")
	    public void setDataSourceFactory(DataSourceFactory factory) {
	        this.database = factory;
	    }
		
	 	@JsonProperty("database")
	    public DataSourceFactory getDataSourceFactory() {
	         return database;
	    }
	 	
	 	//@NotEmpty
	    //private String dateFormat;
	 	
	 //	@JsonProperty("dataFormat")
	   // public String getDateFormat() {
	   //     return dateFormat;
	   // }
	 	
	 	// @NotNull
	    // private MessageQueueFactory messageQueue = new MessageQueueFactory();

	    //// @JsonProperty("messageQueue")
	    // public MessageQueueFactory getMessageQueueFactory() {
	    //     return messageQueue;
	    // }

	    // @JsonProperty("messageQueue")
	    // public void setMessageQueueFactory(MessageQueueFactory factory) {
	    //     this.messageQueue = factory;
	    // }

	
}
