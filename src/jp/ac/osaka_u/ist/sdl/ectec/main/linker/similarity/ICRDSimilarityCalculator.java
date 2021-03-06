package jp.ac.osaka_u.ist.sdl.ectec.main.linker.similarity;

import jp.ac.osaka_u.ist.sdl.ectec.db.data.DBCodeFragmentInfo;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.DBCrdInfo;

/**
 * An interface for calculating similarities between two crds
 * 
 * @author k-hotta
 * 
 */
public interface ICRDSimilarityCalculator {

	/**
	 * calculate hash values between the given two crds <br>
	 * this function must satisfy the symmetric law (calc(a, b) = calc(b, a))
	 * 
	 * @param crd
	 * @param fragment
	 * @param anotherCrd
	 * @param anotherFragment
	 * @return
	 */
	public double calcSimilarity(final DBCrdInfo crd,
			final DBCodeFragmentInfo fragment, final DBCrdInfo anotherCrd,
			final DBCodeFragmentInfo anotherFragment);

}
