package packageClassifiers;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.NormalizedPolyKernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.PrecomputedKernelMatrixKernel;
import weka.classifiers.functions.supportVector.Puk;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.classifiers.functions.supportVector.StringKernel;

public class SMOClassifier extends SMO {

	ArrayList<Classifier> list;


	public SMOClassifier(){
		this.list = new ArrayList<Classifier>();

		for(float c=(float) 0.5; c<=1.;c+=0.5)
			for(float e=(float) 0.0001; e<=1.;e*=10) {
				SMO smo = new SMO();

				smo.setC(c);

				PolyKernel pk = new PolyKernel();
				smo.setKernel(pk);

				smo.setToleranceParameter(e);

				list.add(smo);
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
