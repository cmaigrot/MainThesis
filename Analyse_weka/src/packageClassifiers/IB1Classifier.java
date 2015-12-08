package packageClassifiers;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IB1;
import weka.classifiers.trees.J48;

public class IB1Classifier {

	ArrayList<Classifier> list;

	public IB1Classifier() {
		this.list = new ArrayList<Classifier>();

		IB1 ib1 = new IB1();
		
		this.list.add(ib1);
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
