import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class BasicAuthenticator implements Authenticator<BasicCredentials, User>{
	private Handle handle;
	public BasicAuthenticator(Handle handle) {
		this.handle = handle;
	}
	static final String SELECT_ALL_USERS = "select username, password "
		    + "from auth";
	private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of(
	        "admin", ImmutableSet.of("ADMIN")
	    );
	@Override
	
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		handle.registerRowMapper(BeanMapper.factory(Authen.class));
		List<Authen> authens = handle.select(SELECT_ALL_USERS)
				.reduceResultSet(new HashMap<String, Authen>(), (acc, resultSet, ctx) -> {
					Authen authen;
						authen = new Authen();
						authen.setUsername(resultSet.getString("username"));
						authen.setPassword(resultSet.getString("password"));
					//Temporary Bad Practice
					acc.put(authen.getUsername(),authen);
					return acc;
				}).values().stream().collect(Collectors.toList());
		
		for (Authen authen: authens) {
		 if (authen.getUsername().equals(credentials.getUsername())  && authen.getPassword().equals(credentials.getPassword())) {
	            return Optional.of(new User(credentials.getUsername(),VALID_USERS.get("admin")));
	        }
	    }
		return Optional.empty();
}
}
