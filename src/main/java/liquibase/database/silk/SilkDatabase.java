package liquibase.database.silk;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import liquibase.change.Change;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.silk.connection.SilkDriver;
import liquibase.database.silk.sql.SilkSql;
import liquibase.sql.Sql;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.util.StreamUtil;

/**
 * Not a real database, but a Silk schema file, which can be updated with Liquibase.
 */
public class SilkDatabase extends AbstractJdbcDatabase {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public void saveStatements(final Change change, final List<SqlVisitor> sqlVisitors, final Writer writer) throws
            IOException {
        SqlStatement[] statements = change.generateStatements(this);
        for (SqlStatement statement : statements) {
            for (Sql sql : SqlGeneratorFactory.getInstance().generateSql(statement, this)) {
                if (!(sql instanceof SilkSql)) {
                    throw new IllegalArgumentException("Sql statement of type '" +
                            sql.getClass().getSimpleName() + "' is not supported by Silk");
                }
                writer.append(sql.toSql()).append(sql.getEndDelimiter()).append(StreamUtil.getLineSeparator()).append(StreamUtil.getLineSeparator());
            }
        }
    }

    //TODO @mark: probably handled by connection
//    @Override
//    public void commit() {
//        throw new NotImplementedException("save changes to file");
//    }
//
//    @Override
//    public void rollback() {
//        throw new NotImplementedException("rollback not implemented yet");
//    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Silk";
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection databaseConnection) {
        return databaseConnection.getURL().startsWith("silk:");
    }

    @Override
    public String getDefaultDriver(String url) {
        if (url.startsWith("silk:")) {
            return SilkDriver.class.getName();
        }
        return null;
    }

    @Override
    public String getShortName() {
        return "Silk";
    }

    @Override
    public Integer getDefaultPort() {
        return 0;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public boolean getAutoCommitMode() {
        return false;
    }

    @Override
    public boolean isAutoCommit() {
        return false;
    }
}
