package liquibase.database.silk.sql;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import liquibase.sql.Sql;
import liquibase.structure.DatabaseObject;
import nl.markv.silk.pojos.v0_1_0.SilkSchema;

public class SilkSql implements Sql {

	@Nonnull
	private final String tableName;
	@Nonnull
	public final Consumer<SilkSchema> schemaChangeOperation;

	public SilkSql(@Nonnull String tableName, @Nonnull Consumer<SilkSchema> schemaChangeOperation) {
		this.tableName = tableName;
		this.schemaChangeOperation = schemaChangeOperation;
	}

	@Nonnull
	@Override
	public String toSql() {
		return "";
	}

	@Nonnull
	@Override
	public String getEndDelimiter() {
		return "";
	}

	@Nonnull
	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singletonList(
				new liquibase.structure.core.Table()
						.setName(tableName));
	}
}
