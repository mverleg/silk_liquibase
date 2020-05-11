package liquibase.database.silk;

import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.silk.connection.SilkDriver;

/**
 * Not a real database, but a Silk schema file, which can be updated with Liquibase.
 */
public class SilkDatabase extends AbstractJdbcDatabase {

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Silk";
    }

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT;
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
}
