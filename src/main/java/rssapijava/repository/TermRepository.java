package rssapijava.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rssapijava.entity.Links;
import rssapijava.entity.Term;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class TermRepository implements IRestRepository<Term> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"name\", \"text\" "
            + "FROM \"term\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"name\", \"text\" "
            + "FROM \"term\" "
            + "WHERE \"id\" = ?";

    private static String selectByNameQuery = "SELECT \"id\", \"name\", \"text\" "
            + "FROM \"term\" "
            + "WHERE \"name\" = ?";

    private static String insertQuery = "INSERT INTO \"term\"(\"name\", \"text\") "
            + "VALUES (?,?) "
            + "RETURNING \"id\", \"name\", \"text\"";

    private static String updateQuery = "UPDATE \"term\" "
            + "SET \"name\" = ?, \"text\" = ?"
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\", \"text\"";

    private static String deleteQuery = "DELETE FROM \"term\" "
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\", \"text\"";

    public TermRepository(JdbcOperations JdbcOperations) {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Term[] select() {
        ArrayList<Term> values = new ArrayList<Term>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Term(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)));
        }
        Term[] result = new Term[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Term select(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Term(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }

    public Term[] selectByName(String name)
    {
        ArrayList<Term> values = new ArrayList<Term>();
        Object[] params = new Object[] { name };
        int[] types = new int[] { Types.VARCHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByNameQuery, params, types);
        while (rowSet.next()) {
            values.add(new Term(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            ));
        }
        Term[] result = new Term[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Term insert(Term entity) {
        Object[] params = new Object[]{entity.getName(), entity.getText()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Term(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }

    @Override
    public Term update(Integer id, Term entity) {
        Object[] params = new Object[]{entity.getName(), entity.getText(), id};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Term(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }

    @Override
    public Term delete(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Term(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }
}
