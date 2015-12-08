package packageClassifiers;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;

public class NaiveBayesClassifier {

	ArrayList<Classifier> list;

	public NaiveBayesClassifier() {
		this.list = new ArrayList<Classifier>();
		
			for(int c=0; c<=1;c++)
				for(int d=0; d<=1;d++) {
					NaiveBayes nb = new NaiveBayes();
						nb.setDisplayModelInOldFormat(false);

					if(d==0)
						nb.setUseKernelEstimator(false);
					else
						nb.setUseKernelEstimator(true);

					if(c==0)
						nb.setUseSupervisedDiscretization(false);
					else
						nb.setUseSupervisedDiscretization(true);
					list.add(nb);
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