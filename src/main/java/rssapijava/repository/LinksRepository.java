package rssapijava.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rssapijava.entity.Links;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class LinksRepository implements IRestRepository<Links>
{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"org_name\", \"type_id\", \"text\" "
            + "FROM \"links\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"org_name\", \"type_id\", \"text\" "
            + "FROM \"links\" "
            + "WHERE \"id\"=?";

    private static String selectByOrgNameQuery = "SELECT \"id\", \"org_name\", \"type_id\", \"text\" "
            + "FROM \"links\" "
            + "WHERE \"org_name\"=?";

    private static String selectByTypeIdQuery = "SELECT \"id\", \"org_name\", \"type_id\", \"text\" "
            + "FROM \"links\" "
            + "WHERE \"type_id\"=?";

    private static String insertQuery = "INSERT INTO \"links\"(\"org_name\", \"type_id\", \"text\") " +
            "VALUES (?, ?, ?) " +
            "RETURNING \"id\", \"org_name\", \"type_id\", \"text\"";

    private static String updateQuery = "UPDATE \"links\" " +
            "SET \"org_name\" = ?, \"type_id\" = ?, \"text\"=? " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"org_name\", \"type_id\", \"text\"";

    private static String deleteQuery = "DELETE FROM \"links\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"org_name\", \"type_id\", \"text\"";

    public LinksRepository(JdbcOperations JdbcOperations)
    {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Links[] select()
    {
        ArrayList<Links> values = new ArrayList<Links>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while(rowSet.next())
        {
            values.add(new Links(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getString(4)));
        }
        Links[] result = new Links[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Links select(Integer id)
    {
        Object[] params = new Object[] {id};
        int[] types = new int[] {Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if(!rowSet.next())
        {
            return null;
        }
        return new Links(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getString(4)
        );
    }

    public Links[] selectByTypeId(Integer typeId)
    {
        ArrayList<Links> values = new ArrayList<Links>();
        Object[] params = new Object[] { typeId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByTypeIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Links(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getString(4)
            ));
        }
        Links[] result = new Links[values.size()];
        result = values.toArray(result);
        return result;
    }

    public Links[] selectByOrgName(Integer org_name)
    {
        ArrayList<Links> values = new ArrayList<Links>();
        Object[] params = new Object[] { org_name };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByOrgNameQuery, params, types);
        while (rowSet.next()) {
            values.add(new Links(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getString(4)
            ));
        }
        Links[] result = new Links[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Links insert(Links entity)
    {
        Object[] params = new Object[] { entity.getOrgName(), entity.getTypeId(), entity.getText()};
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next())
        {
            return null;
        }
        return new Links(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Links update(Integer id, Links entity)
    {
        Object[] params = new Object[] { entity.getOrgName(), entity.getTypeId(), entity.getText(), id };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next())
        {
            return null;
        }
        return new Links(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Links delete(Integer id)
    {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Links(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getString(4)
        );
    }
}
