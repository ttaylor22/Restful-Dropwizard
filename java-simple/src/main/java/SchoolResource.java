//import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.auth.Auth;
import java.util.List;
import java.util.Optional;

@RolesAllowed({"ADMIN"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("schools")
public class SchoolResource {
	private SchoolDAO dao;
	
	public SchoolResource(SchoolDAO dao) {
		this.dao = dao;
	}
	
	
	@GET
	public List<School> getSchools(@Auth User user) {
		 return dao.getSchools();
	}
	
	@GET
    @Path("{school_id}")
	public Optional<School> getSchool(@PathParam("school_id") Long school_id, @Auth User user) {
		return dao.getSchool(school_id);
	}
	
	@PUT
	@Path("{school_id}")
	public Optional<School> updateSchool(@PathParam("school_id") Long school_id, School sc, @Auth User user) {
		return dao.updateSchool(school_id, sc);
	}
	
	@GET
	@Path("{school_id}/levels")
	public List<Level> getSchool_levels(@PathParam("school_id") Long school_id, @Auth User user) {
		return dao.getLevels(school_id);
	}
	
	@POST
	@Path("{school_id}/levels")
	public Response postSchool_levels(@PathParam("school_id") Long school_id, Level level, @Auth User user) {
		for(Level l : dao.getLevels(school_id)) {
			if(l.getLevel_name().toUpperCase().equals(level.getLevel_name().toUpperCase())) {
				return Response.status(409).entity(dao.getSchool(school_id)).build();
			}
		}
		return Response.status(201).entity(dao.insertLevel(school_id, level)).build();
	}
	
	@DELETE
	@Path("{school_id}/levels")
	public Response deleteSchool_levels(@PathParam("school_id") Long school_id, Level level, @Auth User user) {
		for(Level l : dao.getLevels(school_id)) {
			if(l.getLevel_name().toUpperCase().equals(level.getLevel_name().toUpperCase())) {
				return Response.status(410).entity(dao.deleteLevel(school_id,level)).build();
			}
		}
		return Response.status(404).entity(dao.getSchool(school_id)).build();
	}
}
