package liquibase.database.silk.connection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.apache.commons.lang3.NotImplementedException;

import nl.markv.silk.types.SilkSchema;

//TODO @mark: also add tsChanged checks (for the non-locking variant)
public class SilkSchemaHelper {

	public static class JsonSchemaFile {
		@Nonnull
		public final SilkSchema schema;
		@Nonnull
		final Path schemaPath;
		boolean locked;

		public JsonSchemaFile(@Nonnull SilkSchema schema, @Nonnull Path schemaPath, boolean locked) {
			this.schema = schema;
			this.schemaPath = schemaPath;
			this.locked = locked;
		}

		/**
		 * Save the changes, and unlock the schema (if it was locked).
		 */
		public void saveAndUnlock() {
			if (!locked) {
				// Wait for locks by other processes to release.
				waitForLockFileOrFail(lockFilePath(schemaPath));
			}
			try {
				directlySave(schema, schemaPath);
			} finally {
				if (locked) {
					locked = false;
					unlock(schemaPath);
				}
			}
		}

		/**
		 * Save the changed, hanging on to the lock (if any) for possible further changes.
		 */
		public void saveWithoutUnlock() {
			if (!locked) {
				// Wait for locks by other processes to release.
				waitForLockFileOrFail(lockFilePath(schemaPath));
			}
			directlySave(schema, schemaPath);
		}
	}

	/**
	 * Load the Silk schema. If it is locked, waits a while, and fails if lock cannot be obtained.
	 */
	@Nonnull
	@CheckReturnValue
	public static JsonSchemaFile loadWithoutLock(@Nonnull Path path) {
		return new JsonSchemaFile(directlyLoad(path), path, false);
	}

	/**
	 * Load the Silk schema. If it is locked, waits a while, and fails if lock cannot be obtained.
	 */
	@Nonnull
	@CheckReturnValue
	public static JsonSchemaFile lockAndLoad(@Nonnull Path path) {
		lock(path);
		SilkSchema schema;
		try {
			schema = directlyLoad(path);
		} catch (Exception ex) {
			unlock(path);
			throw ex;
		}
		return new JsonSchemaFile(schema, path, true);
	}

	@Nonnull
	@CheckReturnValue
	private static SilkSchema directlyLoad(@Nonnull Path schemaPath) {
		throw new NotImplementedException("todo");  //TODO @mark: implement
	}

	private static void directlySave(@Nonnull SilkSchema silkSchema, @Nonnull Path schemaPath) {
		throw new NotImplementedException("todo");  //TODO @mark: implement
	}

	public static void lock(@Nonnull Path schemaPath) {
		if (!Files.exists(schemaPath)) {
			throw new IllegalStateException("Could not lock schema at '" + schemaPath + "' because it does not exist");
		}
		Path lockFile = lockFilePath(schemaPath);
		waitForLockFileOrFail(lockFile);
		try {
			Files.createFile(lockFile);
		} catch (IOException ex) {
			throw new IllegalStateException("Failed to obtain lock", ex);
		}
	}

	private static void unlock(@Nonnull Path schemaPath) {
		Path lockFile = lockFilePath(schemaPath);
		if (!Files.exists(lockFile)) {
			throw new IllegalStateException("Tried to unlock file '" + schemaPath +
					"', but the file was not locked");
		}
		try {
			Files.delete(lockFile);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * Wait for a file to disappear for a specified maximum time.
	 *
	 * @return Whether the file has disappeared before the time expired.
	 */
	private static boolean waitForLockFile(@Nonnull Path lockFilePath, int timeoutMs) {
		if (!Files.exists(lockFilePath)) {
			return true;
		}
		System.out.println("waiting for lock file to disappear (" + lockFilePath +
				") for at most " + (timeoutMs / 1000) + "s");
		int waitTimeMs = 0;
		while (waitTimeMs < timeoutMs) {
			waitTimeMs += 50;
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				// Someone wants us to wake up; we'll just skip this tick.
			}
			if (!Files.exists(lockFilePath)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Like {@link #waitForLockFile(Path, int)}, but throws if the lock is not obtained in 30s.
	 */
	private static void waitForLockFileOrFail(@Nonnull Path schemaPath) {
		Path lockPath = lockFilePath(schemaPath);
		if (!waitForLockFile(lockPath, 30_000)) {
			throw new IllegalStateException("Could not lock Silk schema at '" + schemaPath +
					"' because it is already locked, and was not released quickly enough " +
					"(if you are sure that no other program uses the schema, delete the " +
					"lock file at '" + lockPath + "')");
		}
	}

	@Nonnull
	private static Path lockFilePath(@Nonnull Path schemaPath) {
		return schemaPath.resolveSibling(schemaPath.getFileName() + ".lock~");
	}
}
