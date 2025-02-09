package com.tf.eye.reasoning;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.VCARD;


public class RDFDemo {
    // some definitions
    static String personURI = "http://somewhere/JohnSmith";
    static String givenName    = "John";
    static String familyName   = "Smith";
    static String fullName     = givenName + " " + familyName;

    public static void main(String[] args) {
        // create an empty Model
        Model model = ModelFactory.createDefaultModel();

        // create the resource and add the properties cascading style
        Resource johnSmith = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));

        // list the statements in the Model
        StmtIterator iter = model.listStatements();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); // get next statement
            Resource subject = stmt.getSubject(); // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject(); // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            }
            else {
                // object id a literal
                System.out.print("\"" + object.toString() + "\"");
            }

            System.out.println(".");
        }
        model.write(System.out);
    }

}
