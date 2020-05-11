package liquibase.database.silk.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Triple;

import liquibase.database.LiquibaseExtDriver;
import liquibase.resource.ResourceAccessor;

/**
 * Implements the java.sql.Driver interface to allow the Silk integration to work with Liquibase.
 */
public class SilkDriver implements Driver, LiquibaseExtDriver {

    public static final Triple<Integer, Integer, Integer> VERSION = Triple.of(0, 1, 0);

    private ResourceAccessor resourceAccessor;

    public Connection connect(@Nonnull String url, @Nullable Properties info) {
        return new SilkConnection(url, resourceAccessor);
    }

    public boolean acceptsURL(@Nonnull String url) {
        return url.startsWith("silk:");
    }

    @Nonnull
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) {
        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return VERSION.getLeft();
    }

    public int getMinorVersion() {
        return VERSION.getMiddle();
    }

    public boolean jdbcCompliant() {
        return false;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setResourceAccessor(@Nonnull ResourceAccessor accessor) {
        this.resourceAccessor = accessor;
    }
}
