package liquibase.database.silk.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static liquibase.database.silk.connection.SilkDriver.VERSION;

/**
 * Implements the java.sql.DatabaseMetaData interface to allow the Silk integration to work with Liquibase.
 */
public class SilkConnectionMetadata implements DatabaseMetaData {

    private final String url;

    public SilkConnectionMetadata(@Nonnull String url) {
        this.url = url;
    }

    public boolean allProceduresAreCallable() {
        return false;
    }

    public boolean allTablesAreSelectable() {
        return false;
    }

    @Nonnull 
    public String getURL() {
        return url;
    }

    @Nullable
    public String getUserName() {
        return null;
    }

    public boolean isReadOnly() {
        return false;
    }

    public boolean nullsAreSortedHigh() {
        return false;
    }

    public boolean nullsAreSortedLow() {
        return false;
    }

    public boolean nullsAreSortedAtStart() {
        return false;
    }

    public boolean nullsAreSortedAtEnd() {
        return false;
    }

    @Nonnull
    public String getDatabaseProductName() {
        return "Silk";
    }

    @Nonnull
    public String getDatabaseProductVersion() {
        return VERSION.getLeft() + "." + VERSION.getMiddle() + "." + VERSION.getRight();
    }

    @Nonnull
    public String getDriverName() {
        return SilkDriver.class.getSimpleName();
    }

    @Nonnull
    public String getDriverVersion() {
        return getDatabaseProductVersion();
    }

    public int getDriverMajorVersion() {
        return VERSION.getLeft();
    }

    public int getDriverMinorVersion() {
        return VERSION.getMiddle();
    }

    public boolean usesLocalFiles() {
        return true;
    }

    public boolean usesLocalFilePerTable() {
        return true;
    }

    public boolean supportsMixedCaseIdentifiers() {
        return true;
    }

    public boolean storesUpperCaseIdentifiers() {
        return false;
    }

    public boolean storesLowerCaseIdentifiers() {
        return false;
    }

    public boolean storesMixedCaseIdentifiers() {
        return true;
    }

    public boolean supportsMixedCaseQuotedIdentifiers() {
        return true;
    }

    public boolean storesUpperCaseQuotedIdentifiers() {
        return false;
    }

    public boolean storesLowerCaseQuotedIdentifiers() {
        return false;
    }

    public boolean storesMixedCaseQuotedIdentifiers() {
        return true;
    }

    @Nonnull
    public String getIdentifierQuoteString() {
        return "\"";
    }

    @Nonnull
    public String getSQLKeywords() {
        // Do not return null here due to liquibase.database.jvm.JdbcConnection:30 to avoid NPE's there
        return "";
    }

    @Nullable
    public String getNumericFunctions() {
        return null;
    }

    @Nullable
    public String getStringFunctions() {
        return null;
    }

    @Nullable
    public String getSystemFunctions() {
        return null;
    }

    @Nullable
    public String getTimeDateFunctions() {
        return null;
    }

    @Nullable
    public String getSearchStringEscape() {
        return null;
    }

    @Nullable
    public String getExtraNameCharacters() {
        return null;
    }

    public boolean supportsAlterTableWithAddColumn() {
        return false;
    }

    public boolean supportsAlterTableWithDropColumn() {
        return false;
    }

    public boolean supportsColumnAliasing() {
        return false;
    }

    public boolean nullPlusNonNullIsNull() {
        return false;
    }

    public boolean supportsConvert() {
        return false;
    }

    public boolean supportsConvert(int fromType, int toType) {
        return false;
    }

    public boolean supportsTableCorrelationNames() {
        return false;
    }

    public boolean supportsDifferentTableCorrelationNames() {
        return false;
    }

    public boolean supportsExpressionsInOrderBy() {
        return false;
    }

    public boolean supportsOrderByUnrelated() {
        return false;
    }

    public boolean supportsGroupBy() {
        return false;
    }

    public boolean supportsGroupByUnrelated() {
        return false;
    }

    public boolean supportsGroupByBeyondSelect() {
        return false;
    }

    public boolean supportsLikeEscapeClause() {
        return false;
    }

    public boolean supportsMultipleResultSets() {
        return false;
    }

    public boolean supportsMultipleTransactions() {
        return false;
    }

    public boolean supportsNonNullableColumns() {
        return true;
    }

    public boolean supportsMinimumSQLGrammar() {
        return false;
    }

    public boolean supportsCoreSQLGrammar() {
        return false;
    }

    public boolean supportsExtendedSQLGrammar() {
        return false;
    }

    public boolean supportsANSI92EntryLevelSQL() {
        return false;
    }

    public boolean supportsANSI92IntermediateSQL() {
        return false;
    }

    public boolean supportsANSI92FullSQL() {
        return false;
    }

    public boolean supportsIntegrityEnhancementFacility() {
        return false;
    }

    public boolean supportsOuterJoins() {
        return false;
    }

    public boolean supportsFullOuterJoins() {
        return false;
    }

    public boolean supportsLimitedOuterJoins() {
        return false;
    }

    @Nullable
    public String getSchemaTerm() {
        return null;
    }

    @Nullable
    public String getProcedureTerm() {
        return null;
    }

    @Nullable
    public String getCatalogTerm() {
        return null;
    }

    public boolean isCatalogAtStart() {
        return false;
    }

    @Nullable
    public String getCatalogSeparator() {
        return null;
    }

    public boolean supportsSchemasInDataManipulation() {
        return false;
    }

    public boolean supportsSchemasInProcedureCalls() {
        return false;
    }

    public boolean supportsSchemasInTableDefinitions() {
        return false;
    }

    public boolean supportsSchemasInIndexDefinitions() {
        return false;
    }

    public boolean supportsSchemasInPrivilegeDefinitions() {
        return false;
    }

    public boolean supportsCatalogsInDataManipulation() {
        return false;
    }

    public boolean supportsCatalogsInProcedureCalls() {
        return false;
    }

    public boolean supportsCatalogsInTableDefinitions() {
        return false;
    }

    public boolean supportsCatalogsInIndexDefinitions() {
        return false;
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() {
        return false;
    }

    public boolean supportsPositionedDelete() {
        return false;
    }

    public boolean supportsPositionedUpdate() {
        return false;
    }

    public boolean supportsSelectForUpdate() {
        return false;
    }

    public boolean supportsStoredProcedures() {
        return false;
    }

    public boolean supportsSubqueriesInComparisons() {
        return false;
    }

    public boolean supportsSubqueriesInExists() {
        return false;
    }

    public boolean supportsSubqueriesInIns() {
        return false;
    }

    public boolean supportsSubqueriesInQuantifieds() {
        return false;
    }

    public boolean supportsCorrelatedSubqueries() {
        return false;
    }

    public boolean supportsUnion() {
        return false;
    }

    public boolean supportsUnionAll() {
        return false;
    }

    public boolean supportsOpenCursorsAcrossCommit() {
        return false;
    }

    public boolean supportsOpenCursorsAcrossRollback() {
        return false;
    }

    public boolean supportsOpenStatementsAcrossCommit() {
        return false;
    }

    public boolean supportsOpenStatementsAcrossRollback() {
        return false;
    }

    public int getMaxBinaryLiteralLength() {
        return 0;
    }

    public int getMaxCharLiteralLength() {
        return 0;
    }

    public int getMaxColumnNameLength() {
        return Integer.MAX_VALUE;
    }

    public int getMaxColumnsInGroupBy() {
        return 0;
    }

    public int getMaxColumnsInIndex() {
        return Integer.MAX_VALUE;
    }

    public int getMaxColumnsInOrderBy() {
        return Integer.MAX_VALUE;
    }

    public int getMaxColumnsInSelect() {
        return Integer.MAX_VALUE;
    }

    public int getMaxColumnsInTable() {
        return Integer.MAX_VALUE;
    }

    public int getMaxConnections() {
        return 1;
    }

    public int getMaxCursorNameLength() {
        return 0;
    }

    public int getMaxIndexLength() {
        return Integer.MAX_VALUE;
    }

    public int getMaxSchemaNameLength() {
        return 0;
    }

    public int getMaxProcedureNameLength() {
        return 0;
    }

    public int getMaxCatalogNameLength() {
        return 0;
    }

    public int getMaxRowSize() {
        return Integer.MAX_VALUE;
    }

    public boolean doesMaxRowSizeIncludeBlobs() {
        return false;
    }

    public int getMaxStatementLength() {
        return Integer.MAX_VALUE;
    }

    public int getMaxStatements() {
        return Integer.MAX_VALUE;
    }

    public int getMaxTableNameLength() {
        return Integer.MAX_VALUE;
    }

    public int getMaxTablesInSelect() {
        return Integer.MAX_VALUE;
    }

    public int getMaxUserNameLength() {
        return 0;
    }

    public int getDefaultTransactionIsolation() {
        return 0;
    }

    public boolean supportsTransactions() {
        return false;
    }

    public boolean supportsTransactionIsolationLevel(int level) {
        return false;
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions() {
        return false;
    }

    public boolean supportsDataManipulationTransactionsOnly() {
        return false;
    }

    public boolean dataDefinitionCausesTransactionCommit() {
        return false;
    }

    public boolean dataDefinitionIgnoredInTransactions() {
        return false;
    }

    @Nullable
    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) {
        return null;
    }

    @Nullable
    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) {
        return null;
    }

    @Nullable
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        return null;
    }

    @Nullable
    public ResultSet getSchemas() {
        return null;
    }

    @Nullable
    public ResultSet getCatalogs() {
        return null;
    }

    @Nullable
    public ResultSet getTableTypes() {
        return null;
    }

    @Nullable
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) {
        return null;
    }

    @Nullable
    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) {
        return null;
    }

    @Nullable
    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) {
        return null;
    }

    @Nullable
    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) {
        return null;
    }

    @Nullable
    public ResultSet getVersionColumns(String catalog, String schema, String table) {
        return null;
    }

    @Nullable
    public ResultSet getPrimaryKeys(String catalog, String schema, String table) {
        return null;
    }

    @Nullable
    public ResultSet getImportedKeys(String catalog, String schema, String table) {
        return null;
    }

    @Nullable
    public ResultSet getExportedKeys(String catalog, String schema, String table) {
        return null;
    }

    @Nullable
    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) {
        return null;
    }

    @Nullable
    public ResultSet getTypeInfo() {
        return null;
    }

    @Nullable
    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) {
        return null;
    }

    public boolean supportsResultSetType(int type) {
        return false;
    }

    public boolean supportsResultSetConcurrency(int type, int concurrency) {
        return false;
    }

    public boolean ownUpdatesAreVisible(int type) {
        return true;
    }

    public boolean ownDeletesAreVisible(int type) {
        return true;
    }

    public boolean ownInsertsAreVisible(int type) {
        return true;
    }

    public boolean othersUpdatesAreVisible(int type) {
        return false;
    }

    public boolean othersDeletesAreVisible(int type) {
        return true;
    }

    public boolean othersInsertsAreVisible(int type) {
        return true;
    }

    public boolean updatesAreDetected(int type) {
        return true;
    }

    public boolean deletesAreDetected(int type) {
        return false;
    }

    public boolean insertsAreDetected(int type) {
        return false;
    }

    public boolean supportsBatchUpdates() {
        return true;
    }

    @Nullable
    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) {
        return null;
    }

    @Nullable
    public Connection getConnection() {
        return null;
    }

    public boolean supportsSavepoints() {
        return false;
    }

    public boolean supportsNamedParameters() {
        return false;
    }

    public boolean supportsMultipleOpenResults() {
        return false;
    }

    public boolean supportsGetGeneratedKeys() {
        return false;
    }

    @Nullable
    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) {
        return null;
    }

    @Nullable
    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) {
        return null;
    }

    @Nullable
    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) {
        return null;
    }

    public boolean supportsResultSetHoldability(int holdability) {
        return false;
    }

    public int getResultSetHoldability() {
        return 0;
    }

    public int getDatabaseMajorVersion() {
        return VERSION.getLeft();
    }

    public int getDatabaseMinorVersion() {
        return VERSION.getMiddle();
    }

    public int getJDBCMajorVersion() {
        return 0;
    }

    public int getJDBCMinorVersion() {
        return 0;
    }

    public int getSQLStateType() {
        return 0;
    }

    public boolean locatorsUpdateCopy() {
        return false;
    }

    public boolean supportsStatementPooling() {
        return false;
    }

    @Nullable
    public RowIdLifetime getRowIdLifetime() {
        return null;
    }

    @Nullable
    public ResultSet getSchemas(String catalog, String schemaPattern) {
        return null;
    }

    public boolean supportsStoredFunctionsUsingCallSyntax() {
        return false;
    }

    public boolean autoCommitFailureClosesAllResultSets() {
        return false;
    }

    @Nullable
    public ResultSet getClientInfoProperties() {
        return null;
    }

    @Nullable
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) {
        return null;
    }

    @Nullable
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) {
        return null;
    }

    @Nullable
    public <T> T unwrap(Class<T> iface) {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

    //@Override only override for java 1.7
    public boolean generatedKeyAlwaysReturned() {
        return false;
    }

    @Nullable
    //@Override only override for java 1.7
    public ResultSet getPseudoColumns(String arg0, String arg1, String arg2, String arg3) {
        return null;
    }
}
