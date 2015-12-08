package packageMain;

public class Setting {

	/*----------------------------------
	 -- Selection of methods to apply --
	 ---------------------------------*/
	//	NLP methods
	public static final boolean TFIDF = true;
	public static final boolean LEMMATISATION = true;

	//	Classifiers
	public static final boolean SVM = false;
	public static final boolean J48 = true;

	//-->	StringToWordVector
	public static final boolean USE_TF = true;
	public static final boolean USE_IDF = true;
	public static final boolean USE_TOKENIZER_NGramTokenizer = true;
	public static final int TOKENIZER_NGramTokenizer_NGramMinSize = 1;
	public static final int TOKENIZER_NGramTokenizer_NGramMaxSize = 1;
	public static final int SET_WORDS_TO_KEEP = 1000;
	public static final boolean SET_DO_NOT_OPERATE_ON_PER_CLASS_BASIS = true;
}
