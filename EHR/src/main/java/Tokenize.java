
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Tokenize {
    public static List<String> getTokenList(String txtFilePath) throws IOException {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        ReadTxt readTxt = new ReadTxt();
        String text = readTxt.readFile(txtFilePath, Charset.defaultCharset());

        CoreDocument coreDocument = new CoreDocument(text);

        stanfordCoreNLP.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens();
        List<String> tokenList = new ArrayList<>();

        for(CoreLabel coreLabel : coreLabelList) {
            tokenList.add(coreLabel.originalText());
            //System.out.println(coreLabel.originalText());

        }
        return tokenList;
    }
}

