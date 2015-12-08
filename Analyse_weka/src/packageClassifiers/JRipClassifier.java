package packageClassifiers;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.rules.JRip;

public class JRipClassifier {

	ArrayList<Classifier> list;

	public JRipClassifier() {
		this.list = new ArrayList<Classifier>();

		for(int e=0; e<=1;e++)
			for(float b=((float) 0.5);b<=4.;b+=.5)
				for(int c=1; c<=3;c++){
					JRip jrip = new JRip();
					jrip.setCheckErrorRate(false);
					jrip.setMinNo(b);
					if(e==0)
						jrip.setUsePruning(false);
					else
						jrip.setUsePruning(true);
					list.add(jrip);
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
