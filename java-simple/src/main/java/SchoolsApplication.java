import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SchoolsApplication extends Application<SchoolConfiguration> {
	  
	public static void main(String[] args) throws Exception {
        new SchoolsApplication().run(args);
    }
	
	@Override
    public String getName() {
        return "hello-world";
    }

	    @Override
	    public void initialize(Bootstrap<SchoolConfiguration> bootstrap) {
	        // nothing to do yet
	    }

	    @Override
	    public void run(SchoolConfiguration config, Environment environment) {
	    	
	    	final JdbiFactory factory = new JdbiFactory();
	        final Jdbi jdbi = factory.build(environment, config.getDataSourceFactory(), "postgresql");
	    	final Handle handle = jdbi.open();
	     
	    	environment.jersey().register(new AuthDynamicFeature(
	                new BasicCredentialAuthFilter.Builder<User>()
	                .setAuthenticator(new BasicAuthenticator(handle))
	                .setAuthorizer(new UserAuthorizer())
	                .setRealm("BASIC-AUTH-REALM")
	                .buildAuthFilter()));
	        environment.jersey().register(RolesAllowedDynamicFeature.class);
	        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
	        environment.jersey().register(new SchoolResource(new SchoolDAO(handle)));
	    }
	    
	    

}
