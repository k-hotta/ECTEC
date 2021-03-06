package jp.ac.osaka_u.ist.sdl.ectec.main.fragmentdetector.crd;

import jp.ac.osaka_u.ist.sdl.ectec.db.data.BlockType;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.DBCrdInfo;
import jp.ac.osaka_u.ist.sdl.ectec.main.fragmentdetector.normalizer.StringCreateVisitor;

import org.eclipse.jdt.core.dom.CatchClause;

/**
 * A crd creator for catch clauses
 * 
 * @author k-hotta
 * 
 */
public class CatchClauseCRDCreator extends AbstractBlockAnalyzer<CatchClause> {

	public CatchClauseCRDCreator(CatchClause node, DBCrdInfo parent,
			StringCreateVisitor visitor) {
		super(node, parent, BlockType.CATCH, visitor);
	}

	/**
	 * get the anchor (the type of the exception caught by this clause)
	 */
	@Override
	protected String getAnchor() {
		return getAnchor(node);
	}

	public static String getAnchor(final CatchClause node) {
		return node.getException().getType().toString();
	}

	@Override
	protected String getNormalizedAnchor() {
		return getAnchor(node);
	}

}
