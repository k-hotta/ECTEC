package jp.ac.osaka_u.ist.sdl.ectec.db.data;

import java.util.Collections;
import java.util.List;

public abstract class AbstractDBGenealogyInfo<T extends AbstractDBElement, U extends AbstractDBElementLinkInfo>
		extends AbstractDBElement {

	/**
	 * the id of the start combined revision
	 */
	protected final long startCombinedRevisionId;

	/**
	 * the id of the end combined revision
	 */
	protected final long endCombinedRevisionId;

	/**
	 * the list of ids of elements included in this genealogy
	 */
	 protected final List<Long> elements;

	/**
	 * the list of ids of links included in this genealogy
	 */
	protected final List<Long> links;

	public AbstractDBGenealogyInfo(final long id,
			final long startCombinedRevisionId,
			final long endCombinedRevisionId, final List<Long> elements,
			final List<Long> links) {
		super(id);
		this.startCombinedRevisionId = startCombinedRevisionId;
		this.endCombinedRevisionId = endCombinedRevisionId;
		 this.elements = elements;
		this.links = links;
	}

	/**
	 * get the id of the start combined revision
	 * 
	 * @return
	 */
	public final long getStartCombinedRevisionId() {
		return startCombinedRevisionId;
	}

	/**
	 * get the id of the end combined revision
	 * 
	 * @return
	 */
	public final long getEndCombinedRevisionId() {
		return endCombinedRevisionId;
	}

	/**
	 * get the list of ids of elements
	 * 
	 * @return
	 */
	public final List<Long> getElements() {
		return Collections.unmodifiableList(elements);
	}

	/**
	 * get the list of ids of element links
	 * 
	 * @return
	 */
	public final List<Long> getLinks() {
		return Collections.unmodifiableList(links);
	}

}
