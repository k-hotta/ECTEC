package jp.ac.osaka_u.ist.sdl.ectec.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CRDRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CloneGenealogyRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CloneSetLinkRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CloneSetRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CodeFragmentGenealogyRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CodeFragmentLinkRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CodeFragmentRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CommitRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.FileRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.RepositoryRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.RevisionRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.CRDRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.CloneGenealogyRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.CloneSetLinkRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.CloneSetRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.CodeFragmentGenealogyRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.CodeFragmentLinkRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.CodeFragmentRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.CommitRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.FileRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.RepositoryRetriever;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.retriever.RevisionRetriever;

/**
 * A class to manage the connection between the db
 * 
 * @author k-hotta
 * 
 */
public final class DBConnectionManager {

	/**
	 * the connection between the db
	 */
	private Connection connection;

	private final RepositoryRegisterer repositoryRegisterer;

	private final RevisionRegisterer revisionRegisterer;

	private final CommitRegisterer commitRegisterer;

	private final FileRegisterer fileRegisterer;

	private final CodeFragmentRegisterer fragmentRegisterer;

	private final CloneSetRegisterer cloneRegisterer;

	private final CodeFragmentLinkRegisterer fragmentLinkRegisterer;

	private final CloneSetLinkRegisterer cloneLinkRegisterer;

	private final CloneGenealogyRegisterer cloneGenealogyRegisterer;

	private final CodeFragmentGenealogyRegisterer fragmentGenealogyRegisterer;

	private final CRDRegisterer crdRegisterer;

	private final RepositoryRetriever repositoryRetriever;

	private final RevisionRetriever revisionRetriever;

	private final CommitRetriever commitRetriever;

	private final FileRetriever fileRetriever;

	private final CodeFragmentRetriever fragmentRetriever;

	private final CloneSetRetriever cloneRetriever;

	private final CodeFragmentLinkRetriever fragmentLinkRetriever;

	private final CloneSetLinkRetriever cloneLinkRetriever;

	private final CloneGenealogyRetriever cloneGenealogyRetriever;

	private final CodeFragmentGenealogyRetriever fragmentGenealogyRetriever;

	private final CRDRetriever crdRetriever;

	/**
	 * the constructor
	 * 
	 * @param dbPath
	 * @throws Exception
	 */
	public DBConnectionManager(final String dbPath, final int maxBatchCount)
			throws Exception {
		Class.forName("org.sqlite.JDBC");
		this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		this.connection.setAutoCommit(false);
		this.repositoryRegisterer = new RepositoryRegisterer(this,
				maxBatchCount);
		this.revisionRegisterer = new RevisionRegisterer(this, maxBatchCount);
		this.commitRegisterer = new CommitRegisterer(this, maxBatchCount);
		this.fileRegisterer = new FileRegisterer(this, maxBatchCount);
		this.fragmentRegisterer = new CodeFragmentRegisterer(this,
				maxBatchCount);
		this.cloneRegisterer = new CloneSetRegisterer(this, maxBatchCount);
		this.fragmentLinkRegisterer = new CodeFragmentLinkRegisterer(this,
				maxBatchCount);
		this.cloneLinkRegisterer = new CloneSetLinkRegisterer(this,
				maxBatchCount);
		this.cloneGenealogyRegisterer = new CloneGenealogyRegisterer(this,
				maxBatchCount);
		this.fragmentGenealogyRegisterer = new CodeFragmentGenealogyRegisterer(
				this, maxBatchCount);
		this.crdRegisterer = new CRDRegisterer(this, maxBatchCount);
		this.repositoryRetriever = new RepositoryRetriever(this);
		this.revisionRetriever = new RevisionRetriever(this);
		this.commitRetriever = new CommitRetriever(this);
		this.fileRetriever = new FileRetriever(this);
		this.fragmentRetriever = new CodeFragmentRetriever(this);
		this.cloneRetriever = new CloneSetRetriever(this);
		this.fragmentLinkRetriever = new CodeFragmentLinkRetriever(this);
		this.cloneLinkRetriever = new CloneSetLinkRetriever(this);
		this.cloneGenealogyRetriever = new CloneGenealogyRetriever(this);
		this.fragmentGenealogyRetriever = new CodeFragmentGenealogyRetriever(
				this);
		this.crdRetriever = new CRDRetriever(this);
	}

	public final RepositoryRegisterer getRepositoryRegisterer() {
		return repositoryRegisterer;
	}
	
	public final RevisionRegisterer getRevisionRegisterer() {
		return revisionRegisterer;
	}

	public final CommitRegisterer getCommitRegisterer() {
		return commitRegisterer;
	}

	public final FileRegisterer getFileRegisterer() {
		return fileRegisterer;
	}

	public final CodeFragmentRegisterer getFragmentRegisterer() {
		return fragmentRegisterer;
	}

	public final CloneSetRegisterer getCloneRegisterer() {
		return cloneRegisterer;
	}

	public final CodeFragmentLinkRegisterer getFragmentLinkRegisterer() {
		return fragmentLinkRegisterer;
	}

	public final CloneSetLinkRegisterer getCloneLinkRegisterer() {
		return cloneLinkRegisterer;
	}

	public final CloneGenealogyRegisterer getCloneGenealogyRegisterer() {
		return cloneGenealogyRegisterer;
	}

	public final CodeFragmentGenealogyRegisterer getFragmentGenealogyRegisterer() {
		return fragmentGenealogyRegisterer;
	}

	public final CRDRegisterer getCrdRegisterer() {
		return crdRegisterer;
	}
	
	public final RepositoryRetriever getRepositoryRetriever() {
		return repositoryRetriever;
	}

	public final RevisionRetriever getRevisionRetriever() {
		return revisionRetriever;
	}

	public final CommitRetriever getCommitRetriever() {
		return commitRetriever;
	}

	public final FileRetriever getFileRetriever() {
		return fileRetriever;
	}

	public final CodeFragmentRetriever getFragmentRetriever() {
		return fragmentRetriever;
	}

	public final CloneSetRetriever getCloneRetriever() {
		return cloneRetriever;
	}

	public final CodeFragmentLinkRetriever getFragmentLinkRetriever() {
		return fragmentLinkRetriever;
	}

	public final CloneSetLinkRetriever getCloneLinkRetriever() {
		return cloneLinkRetriever;
	}

	public final CloneGenealogyRetriever getCloneGenealogyRetriever() {
		return cloneGenealogyRetriever;
	}

	public final CodeFragmentGenealogyRetriever getFragmentGenealogyRetriever() {
		return fragmentGenealogyRetriever;
	}

	public final CRDRetriever getCrdRetriever() {
		return crdRetriever;
	}

	/**
	 * close the connection
	 */
	public void close() {
		try {
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * create a statement
	 * 
	 * @return
	 */
	public Statement createStatement() {
		Statement result = null;
		try {
			result = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * create a prepared statement with the specified query
	 * 
	 * @param query
	 * @return
	 */
	public PreparedStatement createPreparedStatement(String query) {
		PreparedStatement result = null;
		try {
			result = connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * do commit
	 */
	public synchronized void commit() {
		try {
			connection.commit();
		} catch (SQLException e1) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
	}

	/**
	 * enables/disables auto commit
	 * 
	 * @param autoCommit
	 */
	public void setAutoCommit(boolean autoCommit) {
		try {
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * execute the query
	 * 
	 * @param query
	 * @throws Exception
	 */
	public void executeUpdate(final String query) throws Exception {
		final Statement stmt = createStatement();
		stmt.executeUpdate(query);
		stmt.close();

	}

}
