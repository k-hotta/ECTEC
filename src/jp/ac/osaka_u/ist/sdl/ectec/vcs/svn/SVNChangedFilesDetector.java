package jp.ac.osaka_u.ist.sdl.ectec.vcs.svn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.ac.osaka_u.ist.sdl.ectec.db.data.DBCommitInfo;
import jp.ac.osaka_u.ist.sdl.ectec.settings.Language;
import jp.ac.osaka_u.ist.sdl.ectec.vcs.IChangedFilesDetector;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.io.SVNRepository;

/**
 * A class that detects added/changed/deleted files from the given svn
 * repository
 * 
 * @author k-hotta
 * 
 */
public class SVNChangedFilesDetector implements IChangedFilesDetector {

	/**
	 * the manager of the target repository
	 */
	private final SVNRepositoryManager manager;

	public SVNChangedFilesDetector(final SVNRepositoryManager manager) {
		this.manager = manager;
	}

	@Override
	public Map<String, Character> detectChangedFiles(final DBCommitInfo commit,
			final Language language) throws Exception {
		final long revision = Long.parseLong(commit
				.getAfterRevisionIdentifier());

		// a special treat for the initial commit
		if (commit.getBeforeRevisionId() == -1) {
			final Map<String, Character> result = new HashMap<String, Character>();
			final List<String> allFiles = manager.getListOfSourceFiles(
					revision, language);
			for (final String file : allFiles) {
				result.put(file, 'A');
				System.out.println(file);
			}
			return Collections.unmodifiableMap(result);
		}

		final Map<String, Character> result = new HashMap<String, Character>();

		final List<String> deleted = new ArrayList<String>();

		final SVNRepository repository = manager.getRepository();

		repository.log(null, revision, revision, true, false,
				new ISVNLogEntryHandler() {
					public void handleLogEntry(SVNLogEntry logEntry)
							throws SVNException {

						for (final Map.Entry<String, SVNLogEntryPath> entry : logEntry
								.getChangedPaths().entrySet()) {

							// in the case that source files are updated
							if (language.isTarget(entry.getKey())) {
								result.put(entry.getKey().substring(1), entry
										.getValue().getType());
								continue;
							}

							// in the case that directories are deleted
							else if (('D' == entry.getValue().getType())
									|| ('R' == entry.getValue().getType())) {
								deleted.add(entry.getKey().substring(1));
								continue;
							}
						}
					}
				});

		if (!deleted.isEmpty()) {
			final List<String> sourceFilesInDeletedDir = manager
					.getListOfSourceFiles(commit.getBeforeRevisionIdentifier(),
							language, deleted);

			for (final String deletedFile : sourceFilesInDeletedDir) {
				result.put(deletedFile, 'D');
			}
		}

		return Collections.unmodifiableMap(result);
	}

}
