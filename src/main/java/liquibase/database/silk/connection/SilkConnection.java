package liquibase.database.silk.connection;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.NotImplementedException;

import liquibase.resource.ResourceAccessor;

/**
 * Implements the java.sql.Connection interface to allow the Silk integration to work with Liquibase.
 */
public class SilkConnection implements Connection {

    private String prefix;
    private String url;

    private String path;
    private ResourceAccessor resourceAccessor;
    private Properties properties;

    public SilkConnection(@Nonnull String url, ResourceAccessor resourceAccessor) {
        this.url = url;

        this.prefix = url.replaceFirst(":[^:]+$", "");

        // Trim the prefix off the URL for the path
        path = url.substring(prefix.length() + 1);
        this.resourceAccessor = resourceAccessor;

        // Check if there is a parameter/query string value.
        properties = new Properties();

        int queryIndex = path.indexOf('?');
        if (queryIndex >= 0) {
            // Convert the query string into properties
            properties.putAll(readProperties(path.substring(queryIndex + 1)));

            if (properties.containsKey("dialect") && !properties.containsKey("hibernate.dialect")) {
                properties.put("hibernate.dialect", properties.getProperty("dialect"));
            }

            // Remove the query string
            path = path.substring(0, queryIndex);
        }
    }

    /**
     * Creates properties to attach to this connection based on the passed query string.
     */
    protected Properties readProperties(String queryString) {
        Properties properties = new Properties();
        queryString = queryString.replaceAll("&", System.getProperty("line.separator"));
        try {
            queryString = URLDecoder.decode(queryString, "UTF-8");
            properties.load(new StringReader(queryString));
        } catch (IOException ioe) {
            throw new IllegalStateException("Failed to read properties from url", ioe);
        }

        return properties;
    }

    /**
     * Returns the entire connection URL
     */
    @Nonnull
    public String getUrl() {
        return url;
    }


    /**
     * Returns the 'protocol' of the URL. For example, "hibernate:classic" or "hibernate:ejb3"
     */
    @Nonnull
    public String getPrefix() {
        return prefix;
    }

    /**
     * The portion of the url between the path and the query string. Normally a filename or a class name.
     */
    @Nonnull
    public String getPath() {
        return path;
    }

    /**
     * The set of properties provided by the URL. Eg:
     * <p/>
     * <code>silkd:/path/to/schema.silk.json?foo=bar</code>
     * <p/>
     * This will have a property called 'foo' with a value of 'bar'.
     */
    @Nonnull
    public Properties getProperties() {
        return properties;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// JDBC METHODS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Statement createStatement() {
        return null;
    }

    public PreparedStatement prepareStatement(String sql) {
        return null;
    }

    public CallableStatement prepareCall(String sql) {
        return null;
    }

    public String nativeSQL(String sql) {
        throw new NotImplementedException("native sql not supported yet");
    }

    public void setAutoCommit(boolean autoCommit) {
        if (autoCommit) {
            throw new NotImplementedException("auto commit mode not supported");
        }
    }

    public boolean getAutoCommit() {
        return false;
    }

    public void commit() {

    }

    public void rollback() {

    }

    public void close() {
        //TODO @mark: only do this if changed
        commit();
    }

    public boolean isClosed() {
        return false;
    }

    public DatabaseMetaData getMetaData() {
        return new SilkConnectionMetadata(url);
    }

    public void setReadOnly(boolean readOnly) {

    }

    public boolean isReadOnly() {
        return true;
    }

    public void setCatalog(String catalog) {

    }

    public String getCatalog() {
        return "HIBERNATE";
    }

    public void setTransactionIsolation(int level) {

    }

    public int getTransactionIsolation() {
        return 0;
    }

    public SQLWarning getWarnings() {
        return null;
    }

    public void clearWarnings() {

    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) {
        return null;
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) {
        return null;
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) {
        return null;
    }

    public Map<String, Class<?>> getTypeMap() {
        return null;
    }

    public void setTypeMap(Map<String, Class<?>> map) {

    }

    public void setHoldability(int holdability) {

    }

    public int getHoldability() {
        return 0;
    }

    public Savepoint setSavepoint() {
        return null;
    }

    public Savepoint setSavepoint(String name) {
        return null;
    }

    public void rollback(Savepoint savepoint) {

    }

    public void releaseSavepoint(Savepoint savepoint) {

    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        return null;
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        return null;
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        return null;
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) {
        return null;
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) {
        return null;
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames) {
        return null;
    }

    public Clob createClob() {
        return null;
    }

    public Blob createBlob() {
        return null;
    }

    public NClob createNClob() {
        return null;
    }

    public SQLXML createSQLXML() {
        return null;
    }

    public boolean isValid(int timeout) {
        return false;
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    public String getClientInfo(String name) {
        return null;
    }

    public Properties getClientInfo() {
        return null;
    }

    public Array createArrayOf(String typeName, Object[] elements) {
        return null;
    }

    public Struct createStruct(String typeName, Object[] attributes) {
        return null;
    }

    public <T> T unwrap(Class<T> iface) {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

    //@Override only in java 1.7
    public void abort(Executor arg0) {
    }

    //@Override only in java 1.7
    public int getNetworkTimeout() {
        return 0;
    }

    //@Override only in java 1.7
    public String getSchema() {
        return "HIBERNATE";
    }

    //@Override only in java 1.7
    public void setNetworkTimeout(Executor arg0, int arg1) {
    }

    //@Override only in java 1.7
    public void setSchema(String arg0) {
    }

    public ResourceAccessor getResourceAccessor() {
        return resourceAccessor;
    }
}
