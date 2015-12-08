package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;
import org.apache.log4j.PropertyConfigurator;

public class App {

	public static void main(String[] args) {
		mainFunction();
	}

	private static void mainFunction() {

		PropertyConfigurator.configure("log4j.properties"); 


		// quelques définitions
		String baseURL = "http://somewhere#";
		String something1    = "something1";
		String something2    = "something2";
		String something3    = "something3";
		String typeHoaxString    = "Hoax";
		String categoryTextImageString   = "Text and image";
		String categoryTextString   = "Text";
		String categoryImageString   = "Image";



		// créer un modèle vide
		Model model = ModelFactory.createDefaultModel();

		Property property_category = model.createProperty(baseURL+"category");
		Property property_explanation = model.createProperty(baseURL+"explanation");


		Resource typeHoax = model.createResource(baseURL+"type_"+toNameOfResource(typeHoaxString))
				.addProperty(org.apache.jena.vocabulary.RDF.value, typeHoaxString);

		Resource categoryTextImage = model.createResource(baseURL+"category_"+toNameOfResource(categoryTextImageString))
				.addProperty(org.apache.jena.vocabulary.RDF.value, categoryTextImageString);
		Resource categoryText = model.createResource(baseURL+"category_"+toNameOfResource(categoryTextString))
				.addProperty(org.apache.jena.vocabulary.RDF.value, categoryTextString);
		Resource categoryImage = model.createResource(baseURL+"category_"+toNameOfResource(categoryImageString))
				.addProperty(org.apache.jena.vocabulary.RDF.value, categoryImageString);

		model.createResource(baseURL+something1)
		.addProperty(RDF.type, typeHoax)
		.addProperty(property_category, categoryTextImage)
		.addProperty(DC.date, "05-09-2015")
		.addProperty(property_explanation, model.createResource()
				.addProperty(RDF.value, "Lorem Ipsum"));
		
		model.createResource(baseURL+something2)
		.addProperty(RDF.type, typeHoax)
		.addProperty(property_category, categoryText)
		.addProperty(DC.date, "07-09-2015");
		
		model.createResource(baseURL+something3)
		.addProperty(property_category, categoryImage);


		// maintenant écrit le modèle sous forme XML dans un fichier
		model.write(System.out, "N-TRIPLE");

		String outputFile = "test.rdf", outputFormat = "RDF/XML";
		/* This method is used to serialize the Jena-model to file. */
		System.out.println("Writing model to file: " + outputFile + ".");

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(outputFile);
			model.write(outputStream, outputFormat);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		

		String NL = System.getProperty("line.separator") ;

		ArrayList<String> prologs = new ArrayList<String>();
		prologs.add("PREFIX rdf: <"+RDF.getURI()+">") ;
		prologs.add("PREFIX base: <"+baseURL+">") ;
		prologs.add("PREFIX vCard: <"+VCARD.getURI()+">") ;
		prologs.add("PREFIX dc: <"+DC.getURI()+">") ;

		// Query string.
		String rdq = "";
		for(String s : prologs)
			rdq += (s + NL);
		rdq += "SELECT  * {"
				//+ "?o base:explanation ?p . "
				+ "?p ?r ?s"
				+ "}";

		Query query = QueryFactory.create(rdq);

		// afficher la requete

		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		System.out.println("Les elements du modele : ") ;

		try {
			ResultSet rs = qexec.execSelect() ;
			ResultSetFormatter.out(System.out, rs, query);
		} finally {qexec.close() ;}
	}

	/**
	 * Transform a name value of a node to a normalized name of node
	 * @param text
	 * @return "text" normalized
	 */
	private static String toNameOfResource(String text) {
		return text.replace(" ", "_").toLowerCase();
	}

}
