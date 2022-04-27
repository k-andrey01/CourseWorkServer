package rssapijava.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rssapijava.entity.Place;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class PlaceRepository implements IRestRepository<Place> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"name\", \"address\", \"phone\" "
            + "FROM \"place\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"name\", \"address\", \"phone\" "
            + "FROM \"place\" "
            + "WHERE \"id\" = ?";

    private static String insertQuery = "INSERT INTO \"place\"(\"name\", \"address\", \"phone\") "
            + "VALUES (?,?,?) "
            + "RETURNING \"id\", \"name\", \"address\", \"phone\"";

    private static String updateQuery = "UPDATE \"place\" "
            + "SET \"name\" = ?, \"address\" = ?, \"phone\" = ?"
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\", \"address\", \"phone\"";

    private static String deleteQuery = "DELETE FROM \"place\" "
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\", \"address\", \"phone\"";

    public PlaceRepository(JdbcOperations JdbcOperations) {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Place[] select() {
        ArrayList<Place> values = new ArrayList<Place>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Place(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3),
                    rowSet.getString(4)));
        }
        Place[] result = new Place[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Place select(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Place(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Place insert(Place entity) {
        Object[] params = new Object[]{entity.getName(), entity.getAddress(), entity.getPhone()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Place(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Place update(Integer id, Place entity) {
        Object[] params = new Object[]{entity.getName(), entity.getAddress(), entity.getPhone(), id};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Place(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Place delete(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Place(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }
}
