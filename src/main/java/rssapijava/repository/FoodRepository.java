package rssapijava.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rssapijava.entity.Food;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class FoodRepository implements IRestRepository<Food> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"name\", \"address\", \"site\" "
            + "FROM \"food\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"name\", \"address\", \"site\" "
            + "FROM \"food\" "
            + "WHERE \"id\" = ?";

    private static String insertQuery = "INSERT INTO \"food\"(\"name\", \"address\", \"site\") "
            + "VALUES (?,?,?) "
            + "RETURNING \"id\", \"name\", \"address\", \"site\"";

    private static String updateQuery = "UPDATE \"food\" "
            + "SET \"name\" = ?, \"address\" = ?, \"site\" = ?"
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\", \"address\", \"site\"";

    private static String deleteQuery = "DELETE FROM \"food\" "
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\", \"address\", \"site\"";

    public FoodRepository(JdbcOperations JdbcOperations) {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Food[] select() {
        ArrayList<Food> values = new ArrayList<Food>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Food(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3),
                    rowSet.getString(4)));
        }
        Food[] result = new Food[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Food select(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Food(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Food insert(Food entity) {
        Object[] params = new Object[]{entity.getName(), entity.getAddress(), entity.getSite()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Food(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Food update(Integer id, Food entity) {
        Object[] params = new Object[]{entity.getName(), entity.getAddress(), entity.getSite(), id};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Food(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Food delete(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Food(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }
}