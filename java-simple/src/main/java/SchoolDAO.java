
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jdbi.v3.core.Handle;



public class SchoolDAO {

	private Handle handle;
	
	public SchoolDAO(Handle handle) {
		this.handle = handle;
	}

	static final String SELECT_ALL = "SELECT schools.school_id s_id, school_name s_name, school_website s_website, school_gender s_gender, "
			+ "levelsgrail.level_id l_id, level_name l_name "
			+ "FROM schools LEFT JOIN levelsgrail ON (schools.school_id = levelsgrail.school_id) "; 
	static final String SELECT_ALL_TWO = "select level_id, level_name from levelsgrail where school_id = :school_id";
	static final String SELECT_ONE = SELECT_ALL + "WHERE schools.school_id = :school_id";
	static final String UPDATE_ONE = "UPDATE schools SET school_name = :school_name, school_website = :school_website, school_gender = :school_gender WHERE school_id = :school_id";
	static final String INSERT_ONE = "INSERT INTO levelsgrail (school_id, level_name) VALUES (:school_id,:level_name)";
	static final String DELETE_ONE = "DELETE FROM levelsgrail where school_id = :school_id AND level_name = :level_name";

	List<School> getSchools() {
		
		return handle.createQuery(SELECT_ALL)
				.reduceResultSet(new HashMap<Long, School>(), (acc, resultSet, ctx) -> {
					long schoolId = resultSet.getLong("s_id");
					School school;
					if (acc.containsKey(schoolId)) {
						school = acc.get(schoolId);
					} else {
						school = new School();
						school.setSchool_id(schoolId);
						school.setSchool_name(resultSet.getString("s_name"));
						school.setSchool_website(resultSet.getString("s_website"));
						school.setSchool_gender(resultSet.getString("s_gender"));
					}

					if (!resultSet.wasNull()) {
						Level level = new Level();
						level.setLevel_name(resultSet.getString("l_name"));
						school.addLevel(level);
					}
					acc.put(schoolId, school);
					return acc;
				}).values().stream().collect(Collectors.toList());
	 }

	Optional<School> getSchool(long school_id) {
		return handle.createQuery(SELECT_ONE)
				.bind("school_id", school_id)
				.reduceResultSet(new HashMap<Long, School>(), (acc, resultSet, ctx) -> {
					long schoolId = resultSet.getLong("s_id");
					School school;
					if (acc.containsKey(schoolId)) {
						school = acc.get(schoolId);
					} else {
						school = new School();
						school.setSchool_id(schoolId);
						school.setSchool_name(resultSet.getString("s_name"));
						school.setSchool_website(resultSet.getString("s_website"));
						school.setSchool_gender(resultSet.getString("s_gender"));
					}

					if (!resultSet.wasNull()) {
						Level level = new Level();
						level.setLevel_name(resultSet.getString("l_name"));
						school.addLevel(level);
					}
					acc.put(schoolId, school);
					return acc;
				}).values().stream().findFirst();
	}

	Optional<School> updateSchool(long school_id, School sc) {
		handle.createUpdate(UPDATE_ONE)
		.bind("school_name", sc.getSchool_name()).bind("school_website", sc.getSchool_website())
		.bind("school_gender", sc.getSchool_gender())
		.bind("school_id", school_id).execute();
		return getSchool(school_id);
	}

	List<Level> getLevels(long school_id) {
		return handle.createQuery(SELECT_ALL_TWO)
				.bind("school_id", school_id)
				.reduceResultSet(new HashMap<Long, Level>(), (acc, resultSet, ctx) -> {
						Level level = new Level();
						level.setLevel_name(resultSet.getString("level_name"));
					
					acc.put(resultSet.getLong("level_id"), level);
					return acc;
				}).values().stream().collect(Collectors.toList());

	}

	Optional<School> insertLevel(long school_id, Level l) {
		handle.createUpdate(INSERT_ONE)
		.bind("school_id", school_id)
		.bind("level_name", l.getLevel_name()).execute();
		return getSchool(school_id);
	}

	Optional<School> deleteLevel(long school_id, Level l) {
		handle.createUpdate(DELETE_ONE)
		.bind("school_id", school_id)
		.bind("level_name", l.getLevel_name()).execute();
		return getSchool(school_id);
	}

	
}
