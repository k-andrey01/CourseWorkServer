package rssapijava.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rssapijava.entity.Organization;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class OrganizationRepository implements IRestRepository<Organization> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"name\" "
            + "FROM \"organization\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"name\" "
            + "FROM \"organization\" "
            + "WHERE \"id\" = ?";

    private static String insertQuery = "INSERT INTO \"organization\"(\"name\") "
            + "VALUES (?) "
            + "RETURNING \"id\", \"name\"";

    private static String updateQuery = "UPDATE \"organization\" "
            + "SET \"name\" = ?"
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\"";

    private static String deleteQuery = "DELETE FROM \"organization\" "
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\"";

    public OrganizationRepository(JdbcOperations JdbcOperations) {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Organization[] select() {
        ArrayList<Organization> values = new ArrayList<Organization>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Organization(
                    rowSet.getInt(1),
                    rowSet.getString(2)));
        }
        Organization[] result = new Organization[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Organization select(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Organization(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Organization insert(Organization entity) {
        Object[] params = new Object[]{entity.getName()};
        int[] types = new int[]{Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Organization(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Organization update(Integer id, Organization entity) {
        Object[] params = new Object[]{entity.getName(), id};
        int[] types = new int[]{Types.VARCHAR, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Organization(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Organization delete(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Organization(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }
}
