package packageWeka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

import packageMain.Setting;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Weka {

	ArrayList<Classifier> listOfClassifiers;
	ArrayList<String> listOfDatasets;

	public Weka() {
		this.listOfClassifiers = new ArrayList<Classifier>();
		this.listOfDatasets = new ArrayList<String>();
	}

	public void addClassifier(Classifier classif) {
		this.listOfClassifiers.add(classif);
	}

	public void addDataset(String dataset) {
		this.listOfDatasets.add(dataset);
	}

	public String toString() {
		String response = "There are "
				+ this.listOfClassifiers.size()
				+ " classifiers and there are "
				+ this.listOfDatasets.size()
				+ " datasets";
		return response;
	}

	/**
	 * Start the weka process
	 * @param args 
	 * @throws Exception 
	 */
	public void exect(String args) throws Exception {
		BufferedReader datafile;
		for(String dataset : listOfDatasets) {
			System.out.println("*******************************");
			System.out.println("**\t"+dataset);
			System.out.println("*******************************");
			datafile = new BufferedReader(new FileReader(dataset));
			Instances data = new Instances(datafile);
			data.setClassIndex(data.numAttributes() - 1);
			intializationClassifier(data,dataset,args);
		}
	}

	public void intializationClassifier(Instances data, String dataset, String args) throws Exception {
		ArrayList<FilteredClassifier> filteredModels = new ArrayList<FilteredClassifier>();

		for(Classifier classifier : this.listOfClassifiers) {
			FilteredClassifier fc = new FilteredClassifier();
			fc.setClassifier(classifier);
			//fc.setFilter(addStringToWordVectorFilter());
			filteredModels.add(fc);
		}

		int NUMBER_OF_CV = Integer.parseInt(args);
		// 10 cross validation
		Instances[][] splits = crossValidationSplit(data, NUMBER_OF_CV);

		Evaluation evalMax = null;
		Classifier classifMax = null;

		// pour tous les modeles
		for (Classifier classif : this.listOfClassifiers) {
			try{
				Evaluation eval = new Evaluation(data);
				FastVector predictions = new FastVector();

				// apprentissage et test du classifier
				for(int i=0; i<NUMBER_OF_CV; i++) {
					Classifier clsCopy = Classifier.makeCopy(classif);
					clsCopy.buildClassifier(splits[0][i]);
					eval.evaluateModel(clsCopy, splits[1][i]);
				}
				// output evaluation

				if(evalMax==null || evalMax.recall(data.numClasses()-1)<eval.recall(data.numClasses()-1)) {
					evalMax = eval;
					classifMax = classif;
				}
			}catch (Exception e) {System.err.println("Erreur avec "+classif.getClass().getName()+" "+Utils.joinOptions(classif.getOptions()));}

		}


		System.out.println("Nbre classifiers : "+this.listOfClassifiers.size());
		System.out.println("=== Setup ===");
		System.out.println("Classifier: " + classifMax.getClass().getSimpleName());
		System.out.println("Classifier: " + classifMax.getClass().getName() + " " + Utils.joinOptions(classifMax.getOptions()));
		//System.out.println("Dataset: " + data.relationName());
		//System.out.println("Folds: " + NUMBER_OF_CV);
		System.out.println();
		System.out.println(evalMax.toSummaryString("=== " + NUMBER_OF_CV + "-fold Cross-validation ===", false));
		System.out.println(evalMax.toClassDetailsString());
		System.out.println(evalMax.weightedFMeasure());
		System.out.println(evalMax.toMatrixString());

		System.out.println();
		System.out.println();
		System.out.println(classifMax);

	}


	/**
	 * 
	 * @param data
	 * @param numberOfFolds
	 * @return
	 */
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
		return split;
	}
}
