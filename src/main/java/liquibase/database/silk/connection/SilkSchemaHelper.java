package liquibase.database.silk.connection;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.NotImplementedException;

import nl.markv.silk.types.SilkSchema;

public class SilkSchemaHelper {

	//TODO @mark: do something with file-system locking to prevent race conditions

	public static SilkSchema loadAndLock(@Nonnull String path) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}

	public static void saveAndUnlock(@Nonnull SilkSchema silkSchema, @Nonnull String path) {
		saveWithoutUnlock(silkSchema, path);
		unlock(path);
	}

	public static void saveWithoutUnlock(@Nonnull SilkSchema silkSchema, @Nonnull String path) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}

	public static void unlock(@Nonnull String path) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}
}
