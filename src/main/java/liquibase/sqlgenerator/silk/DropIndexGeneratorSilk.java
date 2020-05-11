package liquibase.sqlgenerator.silk;

import java.util.List;

import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DropIndexStatement;
import liquibase.structure.core.Index;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class DropIndexGeneratorSilk extends AbstractSqlGenerator<DropIndexStatement> {

    @Override
    public ValidationErrors validate(DropIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("indexName", statement.getIndexName());

        if ((database instanceof MySQLDatabase) || (database instanceof MSSQLDatabase) || database instanceof SybaseASADatabase) {
            validationErrors.checkRequiredField("tableName", statement.getTableName());
        }

        return validationErrors;
    }

    @Override
    public Sql[] generateSql(DropIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        List<String> associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",");
        if (associatedWith != null) {
            if (associatedWith.contains(Index.MARK_PRIMARY_KEY) || associatedWith.contains(Index.MARK_UNIQUE_CONSTRAINT)) {
                return new Sql[0];
            } else if (associatedWith.contains(Index.MARK_FOREIGN_KEY)) {
                if (!((database instanceof OracleDatabase) || (database instanceof MSSQLDatabase))) {
                    return new Sql[0];
                }
            }
        }

        String schemaName = statement.getTableSchemaName();

        if (database instanceof MySQLDatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeIndexName(null, null, statement.getIndexName()) + " ON " + database.escapeTableName(statement.getTableCatalogName(), schemaName, statement.getTableName()), getAffectedIndex(statement))};
        } else if (database instanceof MSSQLDatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeIndexName(null, null, statement.getIndexName()) + " ON " + database.escapeTableName(statement.getTableCatalogName(), schemaName, statement.getTableName()), getAffectedIndex(statement))};
        } else if (database instanceof SybaseDatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + statement.getTableName() + "." + statement.getIndexName(), getAffectedIndex(statement))};
        } else if (database instanceof SybaseASADatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeTableName(statement.getTableCatalogName(), schemaName, statement.getTableName()) + "." + database.escapeIndexName(statement.getTableCatalogName(), schemaName, statement.getIndexName()), getAffectedIndex(statement))};
        } else if (database instanceof PostgresDatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeIndexName(statement.getTableCatalogName(), schemaName, statement.getIndexName()), getAffectedIndex(statement))};
        }

        return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeIndexName(statement.getTableCatalogName(), schemaName, statement.getIndexName()), getAffectedIndex(statement))};
    }

    protected Index getAffectedIndex(DropIndexStatement statement) {
        Table table = null;
        if (statement.getTableName() != null) {
            table = (Table) new Table().setName(statement.getTableName()).setSchema(statement.getTableCatalogName(), statement.getTableSchemaName());
        }
        return new Index().setName(statement.getIndexName()).setRelation(table);
    }
}
