import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;






public class Gui extends JFrame {

    private SqBuilder Build;
    private SqBuilder Build2;
    private List<List<String>> con ;
    private List<List<String>> con2 ;
    private SqWriter writer;
    private Container contents,contentResult,contentNameFile,contentResultAll;
    private LayoutManager layout,layoutResult,layoutNameFile,layoutResultAll;
    private JButton browseButton, countButton ,clearButton,saveFileButton,addButton,convertButton;
    private JTextField inputSource;
    private JTextField saveNameCSP;
    private JTextArea result,cspFile,infoFile;
    private JLabel label,labelNameSaveFile,nameFile,name,logo;
    private JFileChooser filechooser;
    private JPanel panel1,panel2;
    private JPanel panel3,panel4;
    private File file;
    private JScrollPane scroll1,scroll2,scroll3;
    private static final String NEW_LINE = "\n";



    public Gui(SqBuilder reader) {

        this.Build = reader ;
        this.Build2 = reader ;
        this.setTitle("SequenceTool");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        initController();
        initComponents();
        initActionListener();
        this.pack();
    }



    private void initComponents() {

        contents = new Container();
        getContentPane().setPreferredSize(new Dimension(1500,800));

        contentResult = new Container();

        contentNameFile = new Container();
        contentResultAll = new Container();

        panel4 = new JPanel();
        panel4.setBackground(Color.black);
        panel1 = new JPanel();
        panel1.setBackground(Color.black);
        panel2 = new JPanel();
        panel2.setBackground(Color.black);
        panel3 = new JPanel();
        panel3.setBackground(Color.black);


        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        result = new JTextArea();
        result.setFont(new Font("Courier", Font.PLAIN,20));
        result.setBackground(Color.yellow);

        cspFile = new JTextArea();
        cspFile.setFont(new Font("Courier", Font.PLAIN,20));
        cspFile.setBackground(Color.yellow);
        infoFile = new JTextArea();
        infoFile.setFont(new Font("Courier", Font.PLAIN,20));
        infoFile.setBackground(Color.yellow);
        result.setEditable(false);
        cspFile.setEditable(false);
        infoFile.setEditable(false);


        browseButton = new JButton("Browse");
        browseButton.setFont(new Font("Courier", Font.PLAIN,15));

        countButton = new JButton("        Translate       ");
        countButton.setFont(new Font("Courier", Font.PLAIN,15));

        clearButton = new JButton("         Clear          ");
        clearButton.setFont(new Font("Courier", Font.PLAIN,15));

        saveFileButton = new JButton("Save");
        saveFileButton.setFont(new Font("Courier", Font.PLAIN,15));

        filechooser = new JFileChooser();

        label =new JLabel("File name");
        label.setFont(new Font("Courier", Font.BOLD,15));
        label.setForeground(Color.yellow);

        labelNameSaveFile =new JLabel("  Save File as");
        labelNameSaveFile.setFont(new Font("Courier", Font.BOLD,15));
        labelNameSaveFile.setForeground(Color.yellow);


        logo =new JLabel("Sequence Tool");
        logo.setForeground(Color.yellow);
        logo.setFont(new Font("Courier", Font.BOLD,40));



        inputSource = new JTextField(40);
        inputSource.setFont(new Font("Courier", Font.PLAIN,15));
        inputSource.setBackground(Color.yellow);


        name = new JLabel("File name : ");
        name.setFont(new Font("Courier", Font.PLAIN,25));
        name.setForeground(Color.yellow);

        nameFile = new JLabel(" ");
        nameFile.setFont(new Font("Courier", Font.PLAIN,15));
        nameFile.setForeground(Color.yellow);

        saveNameCSP = new JTextField(40);
        saveNameCSP.setFont(new Font("Courier", Font.PLAIN,15));
        saveNameCSP.setBackground(Color.yellow);

        scroll1 = new JScrollPane(result);
        scroll1.setBackground(Color.yellow);
        scroll2 = new JScrollPane(infoFile);
        scroll2.setBackground(Color.yellow);


        panel4.add(logo);


        panel1.add(label);
        panel1.add(inputSource);
        panel1.add(browseButton);
        panel1.add(labelNameSaveFile);
        panel1.add(saveNameCSP);
        panel1.add(saveFileButton);




        panel2.add(countButton);
        panel2.add(clearButton);

        panel4.setPreferredSize(new Dimension(1500,50));
        panel1.setPreferredSize(new Dimension(1500,40));
        panel2.setPreferredSize(new Dimension(1500,40));


        contentNameFile.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentNameFile.add(name);
        contentNameFile.add(nameFile);
//        contentNameFile.add(addButton);
//        contentNameFile.add(convertButton);


        contentResultAll.setLayout(new GridLayout(1, 2));


        System.out.println("SIZE :"+this.getPreferredSize()+" , "+this.getHeight());


        scroll1.setPreferredSize(new Dimension(700,600));
        scroll2.setPreferredSize(new Dimension(700,600));



        contentResultAll.add(scroll1);
        contentResultAll.add(scroll2);

        contentResult.setLayout(new BoxLayout(contentResult, BoxLayout.Y_AXIS));
        contentResult.add(contentNameFile);
        contentResult.add(contentResultAll);


        panel3.add(contentResult);
        panel3.setPreferredSize(new Dimension(1500,800));

        contents.add(panel4);
        contents.add(panel1);
        contents.add(panel2);
        contents.add(panel3);

        this.add(contents);


    }


    public void initActionListener(){
        ActionListener listener = new ReadDiagramListener();
        countButton.addActionListener(listener);
 //       ActionListener listener2 = new ClearButtonListener();
//        clearButton.addActionListener(listener2);
//        inputSource.addActionListener(listener);
        ActionListener listener3 = new BrowseButtonListener();
        browseButton.addActionListener(listener3);
//        ActionListener listener4 = new AddFileListener();
//        addButton.addActionListener(listener4);
//        ActionListener listener5 = new ConvertToDiagramListener();
//        convertButton.addActionListener(listener5);
//        nameFile.addActionListener(listener5);
//
        ActionListener saveFileListener = new SaveCSPFileListener();
        saveFileButton.addActionListener(saveFileListener);


    }


    public class ReadDiagramListener implements ActionListener {
        /**
         *  method to perform action when the button is pressed
         */
        public void actionPerformed(ActionEvent evt) {

            String url = inputSource.getText();



            try {
                Build = new SqBuilder();
                con = Build.getContents(url);

                System.out.println("Done");


                    if(con == null ){
                        System.out.print("null na ja ");
                    }else{
                        System.out.print("Dont null na ja ");
                        for (List<String> line : con) {

                            StringJoiner joiner = new StringJoiner("","",NEW_LINE);
                            line.stream().forEach(joiner::add);
                            if(line.equals(con.get(0))){
                                result.setText(joiner.toString());
                            }else{
                                result.append(joiner.toString());
                            }

                            System.out.print(joiner.toString());
                        }
                        List<List<String>> data = Build.getInfo(url);
                        for (List<String> line : data) {

                            StringJoiner joiner = new StringJoiner("","",NEW_LINE);
                            line.stream().forEach(joiner::add);
                            if(line.equals(data.get(0))){
                                infoFile.setText(joiner.toString());
                            }else{
                                infoFile.append(joiner.toString());
                            }

                            System.out.print(joiner.toString());
                        }
                        nameFile.setText(url);
                    }



            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println("Failed");

            }

        }
    }



    public class BrowseButtonListener implements ActionListener {
        /** method to perform action when the button is pressed */
        public void actionPerformed(ActionEvent evt) {
            filechooser.showOpenDialog(contents);
            file= filechooser.getSelectedFile();
            inputSource.setText(file.getName().toString());
            System.out.print(file.getName().toString());
        }
    }

    public class SaveCSPFileListener implements ActionListener {
        /**
         *  method to perform action when the button is pressed
         */
        public void actionPerformed(ActionEvent evt) {
            String url = inputSource.getText();

            try {
                Build2 = new SqBuilder();
                con2 = Build.getContents(url);

                writer = new SqWriter(con, saveNameCSP.getText());
                writer.write();
                System.out.println("Done");


            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println("Failed");

            }

        }
    }



    public void run() {
        this.setVisible(true);

    }

    public void refresh() {
        this.revalidate();

    }

}