import java.util.List;

/**
 * Class that exports its information in CSV format.
 */
public class SqExporter {

    public static void main(String[] args) {


//        if (args.length != 2) {
//            System.err.println("Usage: java SqExporter inputFile.asta outputFile.csp");
//            return;
//        }



        try {
            Gui a = new Gui(new SqBuilder());
            a.run();

            System.out.println("Done");

        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("Failed");
            
        }
    }
}
