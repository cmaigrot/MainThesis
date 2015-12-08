package packageClassifiers;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.meta.Bagging;

public class BaggingClassifier extends Bagging {

	public ArrayList<Classifier> list;

	public BaggingClassifier() {
		this.list = new ArrayList<Classifier>();
	}

	public BaggingClassifier(Classifier classif) {
		this.list = new ArrayList<Classifier>();

		for(int z=7; z<10; z++) {
			Bagging bagging = new Bagging();
			bagging.setNumIterations(z);
			//bagging.setBagSizePercent(y); 	
			bagging.setCalcOutOfBag(false);
			bagging.setClassifier(classif);
			list.add(bagging);
		}
	}


	/**
	 * @return the list
	 */
	public ArrayList<Classifier> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(ArrayList<Classifier> list) {
		this.list = list;
	}

}
