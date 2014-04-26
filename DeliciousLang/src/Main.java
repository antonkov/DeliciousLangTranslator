import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

/**
 * Created by antonkov on 4/21/14.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(new File("input")));
        DeliciousLangLexer lexer = new DeliciousLangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DeliciousLangParser parser = new DeliciousLangParser(tokens);
        ParseTree tree = parser.prog();
        ParseTreeWalker walker = new ParseTreeWalker();
        DeliciousLangTranslator extractor = new DeliciousLangTranslator("main.c");
        extractor.visit(tree);
    }
}
