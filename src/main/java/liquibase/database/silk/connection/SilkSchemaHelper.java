package liquibase.database.silk.connection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.apache.commons.lang3.NotImplementedException;

import nl.markv.silk.types.SilkSchema;

import static org.apache.commons.lang3.Validate.isTrue;

public class SilkSchemaHelper {

	//TODO @mark: do something with file-system locking to prevent race conditions

	@Nonnull
	@CheckReturnValue
	public static SilkSchema loadWithoutLock(@Nonnull Path path) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}

	/**
	 * Load the Silk schema. If it is locked, waits a while, and fails if lock cannot be obtained.
	 */
	@Nonnull
	@CheckReturnValue
	public static SilkSchema loadAndLock(@Nonnull Path path) {
		lock(path);
		try {
			return loadWithoutLock(path);
		} catch (Exception ex) {
			unlock(path);
			throw ex;
		}
	}

	public static void saveAndUnlock(@Nonnull SilkSchema silkSchema, @Nonnull Path path) {
		saveWithoutUnlock(silkSchema, path);
		unlock(path);
	}

	public static void saveWithoutUnlock(@Nonnull SilkSchema silkSchema, @Nonnull Path path) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}

	public static void lock(@Nonnull Path schemaPath) {
		if (!Files.exists(schemaPath)) {
			throw new IllegalStateException("Could not lock schema at '" + schemaPath + "' because it does not exist");
		}
		Path lockFile = lockFilePath(schemaPath);
		if (!waitForLockFile(lockFile, 30_000)) {
			throw new IllegalStateException("Could not lock Silk schema at '" + schemaPath +
					"' because it is already locked, and was not released quickly enough " +
					"(if you are sure that no other program uses the schema, delete the " +
					"lock file at '" + lockFilePath(schemaPath) + "')");
		}
		try {
			Files.createFile(lockFile);
		} catch (IOException ex) {
			throw new IllegalStateException("Failed to obtain lock", ex);
		}
	}

	public static void unlock(@Nonnull Path path) {
		throw new NotImplementedException("todo: ");  //TODO @mark: implement
	}

	/**
	 * Wait for a file to disappear for a specified maximum time.
	 *
	 * @return Whether the file has disappeared before the time expired.
	 */
	private static boolean waitForLockFile(@Nonnull Path path, int timeoutMs) {
		if (!Files.exists(path)) {
			return true;
		}
		System.out.println("waiting for lock file to disappear (" + path +
				") for at most " + (timeoutMs / 1000) + "s";
		int waitTimeMs = 0;
		while (waitTimeMs < timeoutMs) {
			waitTimeMs += 50;
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				// Someone wants us to wake up; we'll just skip this tick.
			}
			if (!Files.exists(path)) {
				return true;
			}
		}
		return false;
	}

	private static Path lockFilePath(@Nonnull Path schemaPath) {
		return schemaPath.resolveSibling(schemaPath.getFileName() + ".lock~");
	}
}
