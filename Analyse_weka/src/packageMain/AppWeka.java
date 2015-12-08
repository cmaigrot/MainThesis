package packageMain;

import packageClassifiers.BaggingClassifier;
import packageWeka.Weka;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IB1;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

public class AppWeka {

	public static void main(String[] args) {
		//*
		if(args.length!=3) {
			System.err.println("Mauvais nombre d'arguments :");
			System.out.println("1- Algorithme a appliquer");
			System.out.println("2- Fichier ARFF");
			System.out.println("3- Nombre de plis");
			System.exit(0);
		}
		//*/

		Weka weka = new Weka();

		BaggingClassifier bagging = new BaggingClassifier();

		String classifier = args[0];
		if(classifier.equals("J48")) {
			weka.addClassifier(new J48());
		}
		else if(classifier.equals("SMO")) {
			weka.addClassifier(new SMO());
		}
		else if(classifier.equals("IB1")) {
			weka.addClassifier(new IB1());
		}
		else if(classifier.equals("JRip")) {
			weka.addClassifier(new JRip());
		}
		else if(classifier.equals("NaiveBayes")) {
			weka.addClassifier(new NaiveBayes());
		}
		else if(classifier.equals("RandomForest")) {
			weka.addClassifier(new RandomForest());
		}

		for(Classifier c : bagging.getList())
			weka.addClassifier(c);

		weka.addDataset(args[1]);

		try {
			weka.exect(args[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
