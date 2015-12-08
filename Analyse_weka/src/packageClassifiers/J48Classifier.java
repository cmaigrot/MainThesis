package packageClassifiers;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

public class J48Classifier extends J48{

	ArrayList<Classifier> list;

	public J48Classifier() {
		this.list = new ArrayList<Classifier>();
		for(float confidenceFactor=((float) 0.1);confidenceFactor<=.3; confidenceFactor+=.1) 
			for(int minNumObj=1; minNumObj<=2; minNumObj++) 
				for(int numFolds=1; numFolds<=2; numFolds++){

					J48 j48 = new J48();
					j48.setBinarySplits(false);
					j48.setConfidenceFactor(confidenceFactor);
					j48.setDebug(false);
					j48.setMinNumObj(minNumObj);
					j48.setNumFolds(numFolds);
					j48.setReducedErrorPruning(false);
					j48.setSaveInstanceData(false);
					j48.setSeed(1);
					j48.setSubtreeRaising(true);
					j48.setUnpruned(true);
					j48.setUseLaplace(false);
					list.add(j48);

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
