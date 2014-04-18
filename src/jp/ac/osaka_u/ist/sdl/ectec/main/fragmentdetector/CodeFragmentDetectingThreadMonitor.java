package jp.ac.osaka_u.ist.sdl.ectec.main.fragmentdetector;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentMap;

import jp.ac.osaka_u.ist.sdl.ectec.db.data.DBCodeFragmentInfo;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.DBCrdInfo;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CRDRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.db.data.registerer.CodeFragmentRegisterer;
import jp.ac.osaka_u.ist.sdl.ectec.settings.Constants;
import jp.ac.osaka_u.ist.sdl.ectec.settings.MessagePrinter;

/**
 * A monitor class for code fragment detecting threads
 * 
 * @author k-hotta
 * 
 */
public class CodeFragmentDetectingThreadMonitor {

	/**
	 * a map having detected crds
	 */
	private final ConcurrentMap<Long, DBCrdInfo> detectedCrds;

	/**
	 * a map having detected fragments
	 */
	private final ConcurrentMap<Long, DBCodeFragmentInfo> detectedFragments;

	/**
	 * the threshold for elements <br>
	 * if the number of stored elements exceeds this threshold, then this
	 * monitor interrupts the other threads and register elements into db with
	 * the registered elements removed from the map
	 */
	private final int maxElementsCount;

	/**
	 * the registerer for crds
	 */
	private final CRDRegisterer crdRegisterer;

	/**
	 * the registerer for code fragments
	 */
	private final CodeFragmentRegisterer fragmentRegisterer;

	public CodeFragmentDetectingThreadMonitor(
			final ConcurrentMap<Long, DBCrdInfo> detectedCrds,
			final ConcurrentMap<Long, DBCodeFragmentInfo> detectedFragments,
			final int maximumElementsCount, final CRDRegisterer crdRegisterer,
			final CodeFragmentRegisterer fragmentRegisterer) {
		this.detectedCrds = detectedCrds;
		this.detectedFragments = detectedFragments;
		this.maxElementsCount = maximumElementsCount;
		this.crdRegisterer = crdRegisterer;
		this.fragmentRegisterer = fragmentRegisterer;
	}

	public void monitor() throws Exception {
		long numberOfCrds = 0;
		long numberOfFragments = 0;

		while (true) {

			try {
				Thread.sleep(Constants.MONITORING_INTERVAL);

				if (detectedCrds.size() >= maxElementsCount) {
					synchronized (detectedCrds) {
						final Set<DBCrdInfo> currentElements = new TreeSet<DBCrdInfo>();
						currentElements.addAll(detectedCrds.values());
						crdRegisterer.register(currentElements);
						MessagePrinter.println("\t" + currentElements.size()
								+ " CRDs have been registered into db");
						numberOfCrds += currentElements.size();

						for (final DBCrdInfo crd : currentElements) {
							detectedCrds.remove(crd.getId());
						}
					}
				}

				if (detectedFragments.size() >= maxElementsCount) {
					synchronized (detectedFragments) {
						final Set<DBCodeFragmentInfo> currentElements = new TreeSet<DBCodeFragmentInfo>();
						currentElements.addAll(detectedFragments.values());
						fragmentRegisterer.register(currentElements);
						MessagePrinter.println("\t" + currentElements.size()
								+ " fragments have been registered into db");
						numberOfFragments += currentElements.size();

						for (final DBCodeFragmentInfo fragment : currentElements) {
							detectedFragments.remove(fragment.getId());
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			// break this loop if all the other threads have died
			if (Thread.activeCount() == 2) {
				break;
			}
		}

		MessagePrinter.println();

		MessagePrinter.println("\tall threads have finished their work");
		MessagePrinter
				.println("\tregistering all the remaining elements into db ");
		crdRegisterer.register(detectedCrds.values());
		fragmentRegisterer.register(detectedFragments.values());

		numberOfCrds += detectedCrds.size();
		numberOfFragments += detectedFragments.size();

		MessagePrinter.println("\t\tOK");

		MessagePrinter.println();

		MessagePrinter.println("the numbers of detected elements are ... ");
		MessagePrinter.println("\tCRD: " + numberOfCrds);
		MessagePrinter.println("\tFragment: " + numberOfFragments);

	}

}