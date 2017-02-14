package edu.msc.stanfordNLP;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by caldama on 1/11/17.
 */
public class TestStanfordNLP {


    public static LinkedHashMap <String,LinkedHashSet<String>> identifyNER(String text, String model)
    {
        LinkedHashMap <String,LinkedHashSet<String>> map=new <String,LinkedHashSet<String>>LinkedHashMap();
        //String serializedClassifier = model;
        //System.out.println(serializedClassifier);
        CRFClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(model);
        List<List<CoreLabel>> classify = classifier.classify(text);
        for (List<CoreLabel> coreLabels : classify)
        {
            for (CoreLabel coreLabel : coreLabels)
            {

                String word = coreLabel.word();
                String category = coreLabel.get(CoreAnnotations.AnswerAnnotation.class);
                if(!"O".equals(category))
                {
                    if(map.containsKey(category))
                    {
                        // key is already their just insert in arraylist
                        map.get(category).add(word);
                    }
                    else
                    {
                        LinkedHashSet<String> temp=new LinkedHashSet<String>();
                        temp.add(word);
                        map.put(category,temp);
                    }
                    System.out.println(word+":"+category);
                }

            }

        }
        return map;
    }

    @Test
    public void testNameRecognition() throws IOException, URISyntaxException {
        //Path path = Paths.get("example.txt");
        String content = readExample();
       // String content =
        System.out.println(identifyNER(content, "english.conll.4class.distsim.crf.ser.gz").toString());

    }

    private String readExample() throws URISyntaxException, IOException {
        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Ameripath_file_sanjeev-1.txt").getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    @Test
    public void testStanfordNLP() throws IOException, URISyntaxException {

        PrintWriter out = new PrintWriter(System.out);


        Properties props = new Properties();
        props.setProperty("annotators","tokenize, ssplit, pos, lemma, ner, parse") ;
        //props.setProperty("sutime.binders","0");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        //StanfordCoreNLP pipeline = new StanfordCoreNLP();
        Annotation annotation;
        String content = readExample();

        annotation = new Annotation(content);

        pipeline.annotate(annotation);
        pipeline.prettyPrint(annotation, out);
        // An Annotation is a Map and you can get and use the various analyses individually.
        // For instance, this gets the parse tree of the first sentence in the text.
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        if (sentences != null && sentences.size() > 0) {
            CoreMap sentence = sentences.get(0);
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            out.println();
            out.println("The first sentence parsed is:");
            tree.pennPrint(out);
        }



    }


}
