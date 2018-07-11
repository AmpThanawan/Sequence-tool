import java.util.List;

/**
 * Class that exports its information in CSV format.
 */
public class SqExporter {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java SqExporter inputFile.asta outputFile.csp");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];

        try {
            SqBuilder converter = new SqBuilder(inputFile);
            List<List<String>> contents = converter.getContents();
            SqWriter writer = new SqWriter(contents, outputFile);
            writer.write();
            System.out.println("Done");

        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("Failed");
            
        }
    }
}
