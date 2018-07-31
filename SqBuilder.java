import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.exception.LicenseNotFoundException;
import com.change_vision.jude.api.inf.exception.NonCompatibleException;
import com.change_vision.jude.api.inf.exception.ProjectLockedException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IAttribute;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IConstraint;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.ILifeline;
import com.change_vision.jude.api.inf.model.IInteraction;
import com.change_vision.jude.api.inf.model.ICombinedFragment;
import com.change_vision.jude.api.inf.model.IMessage;
import com.change_vision.jude.api.inf.model.IGeneralization;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IOperation;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IParameter;
import com.change_vision.jude.api.inf.model.IRealization;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.model.IGate;
import com.change_vision.jude.api.inf.model.IInteractionOperand;
import com.change_vision.jude.api.inf.model.IInteractionUse;
import com.change_vision.jude.api.inf.model.ILifeline;
import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import com.change_vision.jude.api.inf.presentation.INodePresentation;
import com.change_vision.jude.api.inf.presentation.IPresentation;
import com.change_vision.jude.api.inf.model.IPseudostate;
import com.change_vision.jude.api.inf.model.ISequenceDiagram;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.model.IStateInvariant;

/**
 * Class to build class definition from selected project.
 */
public class SqBuilder {


	private static String zu = new String("");    
    private static String err = new String("");
	public static String life = new String("");

    // Channel
	public static String channel = new String("channel"+" ");
	public static String channelMessage = new String("");
	private static List<String> channelMessageList = new ArrayList<String>();

    // Channel Frame
    public static String channelFrame = new String("");
	private static List<String> channelFrameList = new ArrayList<String>();
	private static int numFrame = 0;

    // state s or r
	private static boolean state = true;
	private static boolean alt = false ;

    private static int count = 0 ;

	private static int erridx = 0;
	private static String[] errmsg = new String[1024];

    //lifeline outside frame
	private static List<String> lfLineList = new ArrayList<String>();
	private static String lfLine = new String("");

	//lifeline
	private static List<String> line = new ArrayList<String>();


    // empty line
	private static String emptyln = new String("      ");
	private static List<String> emptylnList = new ArrayList<String>();

    //lifeline inside frame
    private static List<String> fragmentList = new ArrayList<String>();
    private static String fragmentStr = new String("");

    // keep include message
    private static List<String> IncludeMsList = new ArrayList<String>();
    private static String IncludeMsStr ;

    // keep include message
    private static List<String> MsList = new ArrayList<String>();
    private static String MsStr = new String("") ;

    // total list
    private static List<String> totalList = new ArrayList<String>();
    private static String totalStr = new String("") ;
    private static List<String> lifeList = new ArrayList<String>();
    private static String lifeStr = new String("") ;

    // total list2
    private static List<String> conList = new ArrayList<String>();
    private static String conStr = new String("") ;
    private static List<String> sumList = new ArrayList<String>();
    private static String sumStr = new String("") ;

    //total Message
    private static List<String> mTotalList = new ArrayList<String>();
    private static String mTotalStr = new String("") ;
    private static List<String> mtList = new ArrayList<String>();
    private static String mtStr = new String("") ;


    // Ms list2
    private static List<String> showmList = new ArrayList<String>();
    private static String showmStr = new String("") ;

	private static List<String> TestList = new ArrayList<String>();
	private static String test = new String("") ;


	private static List<String> detail = new ArrayList<String>();
	private static String s1 = new String("") ;

    private static int num = 0 ;
	private static boolean checkcount = false ;

	private static int numk = 0 ;
	private static int numG = 0 ;
	private static int numLine = 0 ;
	private static String chcklifeline = new String("") ;

    private static int Ssequenceidx = 0;
	private static String[] SequenceZuname = new String[1000];      
	private static String[] SequenceZutype = new String[1000];      
	private static String[] SequenceMessage = new String[1000];     
	private static String[] SequenceSourcename = new String[1000];  
	private static String[] SequenceSourceref = new String[1000];   
	private static String[] SequenceGuard = new String[1000];       
	private static String[] SequenceTargetname = new String[1000];  
	private static String[] SequenceTargetref = new String[1000];   

	//attr
	private static int sequenceidx = 0;
	private static String[] sequenceZuname = new String[1000];      
	private static String[] sequenceZutype = new String[1000];      
	private static String[] sequenceMessage = new String[1000];     
	private static String[] sequenceSourcename = new String[1000];  
	private static String[] sequenceSourceref = new String[1000];   
	private static String[] sequenceGuard = new String[1000];       
	private static String[] sequenceTargetname = new String[1000];  
	private static String[] sequenceTargetref = new String[1000];
	  
	

    private static final String EMPTY_COLUMN = "";

    private String input;
    
    /**
     * @param inputFile
     *            File to input
     */
    public SqBuilder() {
//        this.inputFile = inputFile;
    }

    /**
     * Get class information.
     * 
     * @return Class information (String List stored in the List)
     * @throws LicenseNotFoundException
     *             License cannot be found 
     * @throws ProjectNotFoundException
     *             Project cannot be found
     * @throws NonCompatibleException
     *             Old Model Version (The version of API is older than the version of Astah that the project has been last edited with)
     * @throws ClassNotFoundException
     *             Cannot read some models
     * @throws IOException
     *             Input/Output error
     * @throws ProjectLockedException
     *             Project file has been locked
     */
    public List<List<String>> getContents(String inputFile)
            throws LicenseNotFoundException, ProjectNotFoundException, NonCompatibleException,
            IOException, ClassNotFoundException, ProjectLockedException, Throwable {

    	// Open a project. And get the model.
        ProjectAccessor prjAccessor = AstahAPI.getAstahAPI().getProjectAccessor();
        prjAccessor.open(inputFile);
        IModel iModel = prjAccessor.getProject();

		List<List<String>> contents = new ArrayList<List<String>>();
		List<String> con = new ArrayList<String>();
		channelMessageList = new ArrayList<String>();
		channelFrameList = new ArrayList<String>();
		numFrame = 0;
		state = true;
		alt = false ;
		count = 0 ;
		lfLineList = new ArrayList<String>();
		line = new ArrayList<String>();

		emptylnList = new ArrayList<String>();
		fragmentList = new ArrayList<String>();
		IncludeMsList = new ArrayList<String>();
		MsList = new ArrayList<String>();
		totalList = new ArrayList<String>();
		lifeList = new ArrayList<String>();

		conList = new ArrayList<String>();
		sumList = new ArrayList<String>();
		mTotalList = new ArrayList<String>();
		mtList = new ArrayList<String>();
		showmList = new ArrayList<String>();
		TestList = new ArrayList<String>();
		detail = new ArrayList<String>();
		num = 0 ;
		numk = 0 ;
		numG = 0 ;
		checkcount = false ;

		channelFrameList.add(channel);
        INamedElement[] foundElementsS = findSequence(prjAccessor);
				for (INamedElement elementS : foundElementsS)
				{
					// Initial
					zu = "";
					sequenceidx = 0;
					for(int k2 = 0;k2 < 1000;k2++)
					{
						sequenceZuname[k2] = "";
						sequenceZutype[k2] = "";
						sequenceMessage[k2] = "";
						sequenceSourcename[k2] = "";
						sequenceSourceref[k2] = "";
						sequenceGuard[k2] = "";
						sequenceTargetname[k2] = "";
						sequenceTargetref[k2] = "";
					}

					ISequenceDiagram sequence = castSequence(elementS);
					if (sequence != null)
					{
						zu = sequence.getName();
						System.out.println(zu);
						// con.add(zu);
						IInteraction interaction = sequence.getInteraction();

						ILifeline[] lifelines = interaction.getLifelines();
						boolean first = true;
						for (ILifeline lifeline : lifelines) {

							chcklifeline = lifeline.toString() ;
							line.add(chcklifeline);
							System.out.print(lifeline.toString() + " nameeeeee na ja na aj");


						}

						showInteraction(interaction);
						showIncludeMessagesInCombinedFragment(sequence);


						if (err != "1")
						{
							for(int k2 = 0;k2 < sequenceidx ;k2++)
							{
								if (SequenceMessage[k2] != "")
								{
									SequenceZuname[Ssequenceidx ] = sequenceZuname[k2];
									SequenceZutype[Ssequenceidx ] = sequenceZutype[k2];
									SequenceMessage[Ssequenceidx ] = sequenceMessage[k2];
									SequenceSourcename[Ssequenceidx ] = sequenceSourcename[k2];
									SequenceSourceref[Ssequenceidx ] = sequenceSourceref[k2];
									SequenceGuard[Ssequenceidx ] = sequenceGuard[k2];
									SequenceTargetname[Ssequenceidx ] = sequenceTargetname[k2];
									SequenceTargetref[Ssequenceidx ] = sequenceTargetref[k2];
									Ssequenceidx ++;
								}
							}
						}
					}
				}

				
				
        // close project.
        prjAccessor.close();
		contents.add(channelMessageList);
        if(channelFrameList.size() > 1 ){
		    contents.add(channelFrameList);
            emptylnList.add(emptyln);
            emptylnList.add(emptyln);
            emptylnList.add(emptyln);
            contents.add(emptylnList);
            contents.add(lfLineList);
            contents.add(emptylnList);
            contents.add(fragmentList);
			contents.add(TestList);


        }else {
            emptylnList.add(emptyln);
            emptylnList.add(emptyln);
            emptylnList.add(emptyln);
            contents.add(emptylnList);
            contents.add(showmList);
            contents.add(mTotalList);
            contents.add(emptylnList);
            contents.add(lfLineList);
            contents.add(totalList);
            contents.add(emptylnList);
            conStr = zu + " = "+ zu+"I[|{" ;
            conList.add(conStr);
                for (String sum : sumList) {
                        if(sum.equals(sumList.get(0))){
                            conStr = sum ;
                            conList.add(conStr);
                        }else{
                            conStr = ",";
                            conList.add(conStr);
                            conStr = sum ;
                            conList.add(conStr);
                        }


                }
            conStr = "}|]MSG" ;
            conList.add(conStr);
            contents.add(conList);

        }



        System.out.print("AC_____");
        return contents;
	}



	public List<List<String>> getInfo(String inputFile)
			throws LicenseNotFoundException, ProjectNotFoundException, NonCompatibleException,
			IOException, ClassNotFoundException, ProjectLockedException, Throwable {

		// Open a project. And get the model.
		ProjectAccessor prjAccessor = AstahAPI.getAstahAPI().getProjectAccessor();
		prjAccessor.open(inputFile);
		IModel iModel = prjAccessor.getProject();

		List<List<String>> informations = new ArrayList<List<String>>();
		List<String> con = new ArrayList<String>();
		channelMessageList = new ArrayList<String>();
		channelFrameList = new ArrayList<String>();
		numFrame = 0;
		state = true;
		alt = false ;
		count = 0 ;
		lfLineList = new ArrayList<String>();
		line = new ArrayList<String>();

		emptylnList = new ArrayList<String>();
		fragmentList = new ArrayList<String>();
		IncludeMsList = new ArrayList<String>();
		MsList = new ArrayList<String>();
		totalList = new ArrayList<String>();
		lifeList = new ArrayList<String>();

		conList = new ArrayList<String>();
		sumList = new ArrayList<String>();
		mTotalList = new ArrayList<String>();
		mtList = new ArrayList<String>();
		showmList = new ArrayList<String>();
		TestList = new ArrayList<String>();
		detail = new ArrayList<String>();
		num = 0 ;
		numk = 0 ;
		numG = 0 ;
		checkcount = false ;

		channelFrameList.add(channel);
		INamedElement[] foundElementsS = findSequence(prjAccessor);
		for (INamedElement elementS : foundElementsS)
		{
			// Initial
			zu = "";
			sequenceidx = 0;
			for(int k2 = 0;k2 < 1000;k2++)
			{
				sequenceZuname[k2] = "";
				sequenceZutype[k2] = "";
				sequenceMessage[k2] = "";
				sequenceSourcename[k2] = "";
				sequenceSourceref[k2] = "";
				sequenceGuard[k2] = "";
				sequenceTargetname[k2] = "";
				sequenceTargetref[k2] = "";
			}

			ISequenceDiagram sequence = castSequence(elementS);
			if (sequence != null)
			{
				zu = sequence.getName();
				System.out.println(zu);
				// con.add(zu);
				IInteraction interaction = sequence.getInteraction();

				showInteraction(interaction);
				showIncludeMessagesInCombinedFragment(sequence);


				if (err != "1")
				{
					for(int k2 = 0;k2 < sequenceidx ;k2++)
					{
						if (SequenceMessage[k2] != "")
						{
							SequenceZuname[Ssequenceidx ] = sequenceZuname[k2];
							SequenceZutype[Ssequenceidx ] = sequenceZutype[k2];
							SequenceMessage[Ssequenceidx ] = sequenceMessage[k2];
							SequenceSourcename[Ssequenceidx ] = sequenceSourcename[k2];
							SequenceSourceref[Ssequenceidx ] = sequenceSourceref[k2];
							SequenceGuard[Ssequenceidx ] = sequenceGuard[k2];
							SequenceTargetname[Ssequenceidx ] = sequenceTargetname[k2];
							SequenceTargetref[Ssequenceidx ] = sequenceTargetref[k2];
							Ssequenceidx ++;
						}
					}
				}
			}
		}

		System.out.print("ADD_____");
		informations.add(detail);

		// close project.
		prjAccessor.close();

		return informations ;
	}
	
	/**
	 *
	 * @param projectAccessor
	 * @return 
	 * @throws Exception
	 */
	private static INamedElement[] findSequence(ProjectAccessor projectAccessor)
			throws Exception {
		INamedElement[] foundElementsS = projectAccessor
				.findElements(new ModelFinder() {
					public boolean isTarget(INamedElement namedElement) {
						return namedElement instanceof ISequenceDiagram;
					}
				});
		return foundElementsS;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	private static ISequenceDiagram castSequence(INamedElement element) {
		ISequenceDiagram sequenceDiagram = null;
		if (element instanceof ISequenceDiagram) {
			sequenceDiagram = (ISequenceDiagram) element;
		}
		return sequenceDiagram;
	}

	private static void showSeparator() {
		s1 = "-----------------------" + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


	}
	private static void showMiniSeparator() {

		s1 = "----" + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);

	}

	/**
	 * 
	 * @param interaction
	 * @see http://members.change-vision.com/javadoc/astah-api/6_7_0-43495/api/ja/doc/javadoc/com/change_vision/jude/api/inf/model/IInteraction.html
	 */
	private static void showInteraction(IInteraction interaction) {

		s1 = "start interaction" + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		showSeparator();
		showGates(interaction);
		showSeparator();
		showLifelines(interaction);
		showSeparator();
		showMessages(interaction);
		showSeparator();


		s1 = "end." + System.lineSeparator() ;
		System.out.println(s1);
		detail.add(s1);
	}

	/**
	 * 
	 * @param gate
	 */
	private static void showGates(IInteraction interaction) {

		s1 = "Gate start."+ System.lineSeparator() ;
		detail.add(s1);
		System.out.println(s1);

		IGate[] gates = interaction.getGates();
		for (IGate gate : gates) {
			showGate(gate);
		}

		s1 = "Gate end." + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


	}


	/**
	 * 
	 * @param gate
	 */
	private static void showGate(IGate gate) {

		System.out.println(gate);
	}

	/**
	 * 
	 * @param lifeline
	 */
	private static void showLifelines(IInteraction interaction) {

		s1 = "Lifeline start." + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);

		ILifeline[] lifelines = interaction.getLifelines();
		boolean first = true;
		for (ILifeline lifeline : lifelines) {

//			chcklifeline = lifeline.toString() ;
//			line.add(chcklifeline);
//			System.out.print(lifeline.toString() + " nameeeeee na ja na aj");
//			System.out.print(line.size()+ "checklinesize");

		    lifeList.add(lifeline.toString());
			lfLine = lifeline.toString() ;

			lfLineList.add(lfLine);
			lfLine = " = ";
			lfLineList.add(lfLine);
			if (!first)
				showMiniSeparator();
			showLifeline(lifeline);
			first = false;
			
		}
		s1 = "Lifeline end." + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


        totalStr = zu +"I = " ;
        totalList.add(totalStr);
        if(lifeList.size() > 1){
            for (String lf : lifeList) {
                if(lf.equals(lifeList.get(lifeList.size()-1))){
                    totalStr = lf;
                    totalList.add(totalStr);
					for(int i=2 ; i<lifeList.size() ; i++ ){
						totalStr = ")" ;
						totalList.add(totalStr);
					}


                }else if(lf.equals(lifeList.get(0))){
                    totalStr = lf;
                    totalList.add(totalStr);
                    totalStr = " ||| ";
                    totalList.add(totalStr);
                }else{
                    totalStr = "("+ lf;
                    totalList.add(totalStr);
                    totalStr = " ||| ";
                    totalList.add(totalStr);
                }
            }
        }else{
            totalStr = lifeList.get(0).toString() + " ||| "+ lifeList.get(1).toString();
            totalList.add(totalStr);
        }
	}


	/**
	 * 
	 * @param lifeline
	 */
	private static void showLifeline(ILifeline lifeline) {



		s1 = "Lifeline : " + lifeline + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);



		life = lifeline.toString();

		showBase(lifeline);
		showFragments(lifeline);
	}

	/**
	 * 
	 * @param lifeline
	 */
	private static void showBase(ILifeline lifeline) {
		IClass base = lifeline.getBase();
		if (base != null) {

			s1 = "Base : " + base + System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);

		}
	}

	/**
	 * 
	 * @param lifeline
	 */
	private static void showFragments(ILifeline lifeline) {
		showMiniSeparator();

		s1 = "Fragment start."+ System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);

		INamedElement[] fragments = lifeline.getFragments();
		for (INamedElement fragment : fragments) {
			if (fragment instanceof ICombinedFragment) {

				ICombinedFragment combinedFragment = (ICombinedFragment) fragment;


				showMiniSeparator();

				if(checkcount == false){
					numFrame++;
				}

				showCombinedFragment(combinedFragment);
				showMiniSeparator();

                if(lfLineList != null) {
                    lfLine = "f1_b->f1_e->";
                    lfLineList.add(lfLine);
                }else if(lfLineList == null){
                    lfLine = "f1_b->f1_e";
                    lfLineList.add(lfLine);
                }
				continue;
			}

			
			if (fragment instanceof IStateInvariant) {

				s1 = "StateInvariant : " + fragment +System.lineSeparator();
				detail.add(s1);
				System.out.println(s1);

				continue;
			}


			s1 = fragment +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);




            for (String lfstr : lfLineList) {

                    if (lfstr.equals("s_"+fragment.toString()+"->")) {
                            state = false ;

                    }
            }

            if(state == true){
				lfLine = "s_"+fragment.toString()+"->" ;
				lfLineList.add(lfLine);
			}else{
				lfLine = "r_"+fragment.toString()+"->" ;
				lfLineList.add(lfLine);
                state = true;
			}
			
		}

		s1 = "Fragment end." +System.lineSeparator();

		detail.add(s1);
		System.out.println(s1);


		lfLine = "SKIP"+System.lineSeparator();
		lfLineList.add(lfLine);


		showMiniSeparator();
		checkcount = true ;
	}
	
	
	/**
	 * @param combinedFragment
	 */
	private static void showCombinedFragment(ICombinedFragment combinedFragment) {

		s1 = "CombinedFragment" +System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);





		
		if (combinedFragment.isAlt())
		{

			s1 = "[" + zu + "] Figure - [Alt] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] diagram - [Alt] - [" + life + "] lifeline: compound fragment is not available";
	    	erridx++;
			err = "1";
			alt = true ;


			num++ ;


		}
		s1 = "isAssert() : " + combinedFragment.isAssert() + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		if (combinedFragment.isAssert())
		{

			s1 = "[" + zu + "] Figure - [Assert] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] Figure - [Assert] - [" + life + "] Lifeline: Composite fragment not available";
	    	erridx++;
			err = "1";
		}

		s1 = "isBreak() : " + combinedFragment.isBreak() + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		if (combinedFragment.isBreak())
		{


			s1 = "[" + zu + "] Figure - [Break] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] Figure - [Break] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		s1 = "isConsider() : " + combinedFragment.isConsider() + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);



		if (combinedFragment.isConsider())
		{
			s1 = "[" + zu + "] Figure - [Consider] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] Figure - [Consider] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		s1 = "isCritical() : " + combinedFragment.isCritical() + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		if (combinedFragment.isCritical())
		{

			s1 = "[" + zu + "] Figure - [Critical] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] Figure - [Critical] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		s1 = "isIgnore() : " + combinedFragment.isIgnore() + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		if (combinedFragment.isIgnore())
		{
			s1 = "[" + zu + "] Figure - [Ignore] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] Figure - [Ignore] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		s1 = "isLoop() : " + combinedFragment.isLoop() + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		if (combinedFragment.isLoop())
		{

			s1 = "[" + zu + "] Figure - [Loop] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] Figure - [Loop] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		s1 = "isNeg() : " + combinedFragment.isNeg() + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);



		if (combinedFragment.isNeg())
		{
			s1 = "[" + zu + "] Figure - [Neg] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] Figure - [Neg] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		s1 = "isOpt() : " + combinedFragment.isOpt() + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		if (combinedFragment.isOpt())
		{
			s1 = "[" + zu + "] Figure - [Opt] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


			errmsg[erridx] = "[" + zu + "] Figure - [Opt] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		IInteractionOperand[] interactionOperands = combinedFragment.getInteractionOperands();
		IMessage[] messages = null ;
		ILifeline[] lifelinesCom = null ;
		count = 0 ;


		for (IInteractionOperand interactionOperand : interactionOperands) {

			count++ ;

			if(interactionOperands == null){

				s1 = "null" + System.lineSeparator();
				detail.add(s1);
				System.out.println(s1);

			}else{

				s1 = "interaction operand guard : '" + interactionOperand.getGuard() + "'" + System.lineSeparator();
				detail.add(s1);
				System.out.println(s1);



				messages = interactionOperand.getMessages();
				for (IMessage message : messages) {
					s1 = "Ms : '" + message.toString() + "' Source : " + message.getSource()+  ", Target : "  + message.getTarget() + System.lineSeparator();
					detail.add(s1);
					System.out.println(s1);


				}


				lifelinesCom = interactionOperand.getLifelines();
				for (ILifeline life : lifelinesCom) {

					s1 = "Lifeline : '" + life.toString() + "'" + System.lineSeparator();
					detail.add(s1);
					System.out.println(s1);





				}
			}

		}

		numk = 0 ;
		numG = 0 ;
		int ck = 0;
		System.out.print(line.size()+ "checklinesize");

		if(alt == true && num > 1 && num == line.size()){
			for(int k=1 ; k< numFrame+1 ; k++){

				for (String lf : line) {

					ck++ ;

					fragmentStr = "F" + k + "_" + lf + " = f" + k + "_b->F" + k + "_" + lf + "_ALT" + System.lineSeparator()+ "F" + k + "_" + lf + "_ALT" + " = ";
					fragmentList.add(fragmentStr);

					numk = 0 ;
					numG = 0 ;



					for (IInteractionOperand interactionOperand : interactionOperands) {

							if(interactionOperand.getGuard() != null){
								numG++ ;
							}


							messages = interactionOperand.getMessages();

						if (numk == 0) {



							fragmentStr = "(f" + k + "_alt" + numG ;
							fragmentList.add(fragmentStr);

							for (IMessage message : messages) {

								if(message.getSource().toString().equals(lf)){
									fragmentStr =  "->" ;
									fragmentList.add(fragmentStr);
									fragmentStr = "s_"+ message ;
									fragmentList.add(fragmentStr);
								}else if(message.getTarget().toString().equals(lf)){
									fragmentStr =  "->" ;
									fragmentList.add(fragmentStr);
									fragmentStr = "r_"+message ;
									fragmentList.add(fragmentStr);
								}

								fragmentStr = "->f" + k + "_e->SKIP)"  ;
								fragmentList.add(fragmentStr);


							}

							numk++ ;


						} else if (numk == interactionOperands.length-1) {

							fragmentStr = "[]";
							fragmentList.add(fragmentStr);
							fragmentStr = "(f" + k + "_alt" + numG  ;
							fragmentList.add(fragmentStr);

							for (IMessage message : messages) {

								if(message.getSource().toString().equals(lf)){
									fragmentStr =  "->" ;
									fragmentList.add(fragmentStr);
									fragmentStr = "s_"+message ;
									fragmentList.add(fragmentStr);
								}else if(message.getTarget().toString().equals(lf)){
									fragmentStr =  "->" ;
									fragmentList.add(fragmentStr);
									fragmentStr = "r_"+message ;
									fragmentList.add(fragmentStr);
								}

								fragmentStr = "->f" + k + "_e->SKIP)"  + System.lineSeparator();
								fragmentList.add(fragmentStr);


							}

							numk++ ;

						} else {

							fragmentStr = "[]";
							fragmentList.add(fragmentStr);
							fragmentStr = "(f" + k + "_alt" + numG ;
							fragmentList.add(fragmentStr);

							for (IMessage message : messages) {

								if(message.getSource().toString().equals(lf)){
									fragmentStr =  "->" ;
									fragmentList.add(fragmentStr);
									fragmentStr = "s_"+message ;
									fragmentList.add(fragmentStr);
								}else if(message.getTarget().toString().equals(lf)){
									fragmentStr =  "->" ;
									fragmentList.add(fragmentStr);
									fragmentStr = "r_"+message ;
									fragmentList.add(fragmentStr);
								}

								fragmentStr = "->f" + k + "_e->SKIP)" ;
								fragmentList.add(fragmentStr);


							}

							numk++ ;
						}




					}
				}
			}


		}



	}

	/**
	 *
	 * @param interaction
	 */
	private static void showMessages(IInteraction interaction) {

		s1 = "Message start." + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		IMessage[] messages = interaction.getMessages();
		boolean first = true;
		channelMessageList.add(channel);
		for (IMessage message : messages) {

            showmStr = message.toString().toUpperCase() + " = ";
            showmList.add(showmStr);
            showmStr = "s_"+ message.toString() +"->r_"+ message.toString() + "->"+message.toString().toUpperCase()+ System.lineSeparator()   ;
            showmList.add(showmStr);

			if (!first){
				showMiniSeparator();
				channelMessage = ", s"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
                sumStr = "s"+ "_" + message.toString() ;
                sumList.add(sumStr);
				channelMessage = ", " + "r"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
                sumStr = "r"+ "_" + message.toString() ;
                sumList.add(sumStr);
			}else{
                sumStr = "s"+ "_" + message.toString() ;
                sumList.add(sumStr);
				channelMessage = "s"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
				channelMessage = ", " + "r"+ "_" + message.toString()+" " ;
				channelMessageList.add(channelMessage);
                sumStr = "r"+ "_" + message.toString() ;
                sumList.add(sumStr);
			}

			sequenceZuname[sequenceidx ] = zu;
			sequenceZutype[sequenceidx ] = "Sequence Diagram";
			showMessage(message,interaction);

            mtStr = message.toString();
            mtList.add(mtStr);




			if(message.toString() == IncludeMsStr){
			    if(message.getSource().toString() == life){
                     MsStr = "s_"+message.toString() ;
                }else{
                    MsStr = "r_"+message.toString() ;
                }

                MsList.add(MsStr);

            }
				
			
			

			first = false;

			if( message.isCreateMessage())
		    {
				s1 = "[" + zu + "] Figure - [" + message + "]: Treated as asynchronous message" + System.lineSeparator();
				detail.add(s1);
				System.out.println(s1);

		    }
			if( message.isDestroyMessage())
		    {
				s1 = "[" + zu + "] Figure - [" + message + "]: Treated as asynchronous message" + System.lineSeparator();
				detail.add(s1);
				System.out.println(s1);


		    }
		}

        mTotalStr = "MSG = " ;
        mTotalList.add(mTotalStr);
        if(mtList.size() > 2){
            for (String lf : mtList) {
                if(lf.equals(mtList.get(mtList.size()-1))){
                    mTotalStr = lf.toUpperCase();
                    mTotalList.add(mTotalStr);
                    mTotalStr = ")" ;
                    mTotalList.add(mTotalStr);
                }else if(lf.equals(mtList.get(0))){
                    mTotalStr = lf.toUpperCase();
                    mTotalList.add(mTotalStr);
                    mTotalStr = " ||| ";
                    mTotalList.add(mTotalStr);
                }else{
                    mTotalStr = "("+ lf.toUpperCase();
                    mTotalList.add(mTotalStr);
                    mTotalStr = " ||| ";
                    mTotalList.add(mTotalStr);
                }
            }
        }else{
            for (String lf : mtList) {
                if(lf.equals(mtList.get(mtList.size()-1))){
                    mTotalStr = lf.toUpperCase();
                    mTotalList.add(mTotalStr);
                }else if(lf.equals(mtList.get(0))){
                    mTotalStr = lf.toUpperCase();
                    mTotalList.add(mTotalStr);
                    mTotalStr = " ||| ";
                    mTotalList.add(mTotalStr);
                }
            }
        }
		showMiniSeparator();

		s1 = "Message end." + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);




	}



	/**
	 * 
	 * @param message
	 *
	 */
	private static void showMessage(IMessage message, IInteraction interaction) {

		s1 = "message : " + message  + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


        INamedElement source = message.getSource();
        sequenceMessage[sequenceidx] = message.toString();
        if (source instanceof IGate) {
            IInteractionUse use = getInteractionUse(interaction, (IGate) source);
            //Add check null -----------
            if (use == null)
            {
				s1 = "Sender = Frame"+ System.lineSeparator();
				detail.add(s1);
				System.out.println(s1);


            }
            else
            {

				s1 = "source(1) : " + use + System.lineSeparator(); // 「reference」name
				detail.add(s1);
				System.out.println(s1);

	            sequenceSourcename[sequenceidx ] = use.toString();
	            sequenceSourceref[sequenceidx ] = "ref";
	        }
         // check null -----------
        } else {
        	// Add check null -----------
            if (source == null)
            {

				s1 = "source=null" + System.lineSeparator();
				detail.add(s1);
				System.out.println(s1);


            }
            else
            {

				s1 = "source : " + source + System.lineSeparator();
				detail.add(s1);
				System.out.println(s1);


	            sequenceSourcename[sequenceidx ] = source.toString();
            }
        }

        INamedElement target = message.getTarget();
        if (target instanceof IGate) {
            IInteractionUse use = getInteractionUse(interaction, (IGate) target);

			s1 = "target(1) : " + use + System.lineSeparator(); // 「reference」name
			detail.add(s1);
			System.out.println(s1);

            sequenceTargetname[sequenceidx ] = use.toString();
            sequenceTargetref[sequenceidx ] = "ref";

        } else {

			s1 = "target(2) : " + target+ System.lineSeparator();
			detail.add(s1);
			System.out.println(s1);


            sequenceTargetname[sequenceidx ] = target.toString();
        }
        String guard = message.getGuard();

		s1 = "guard : " + guard + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


        sequenceGuard[sequenceidx ] = guard.toString();
        sequenceidx ++;




	}
	
	 /**
     * @param interaction
     * @param gate
     * @return IntaractionUse
     */
    private static IInteractionUse getInteractionUse(IInteraction interaction, IGate gate) {
        ILifeline[] lifelines = interaction.getLifelines();
        for (ILifeline lifeline : lifelines) {
            INamedElement[] fragments = lifeline.getFragments();
            for (INamedElement fragment : fragments) {
                if (fragment instanceof IInteractionUse) {
                    IInteractionUse use = (IInteractionUse) fragment;
                    IGate[] gates = use.getGates();
                    for (IGate aGate : gates) {
                        if (aGate == gate) return use;
                    }
                }
            }
        }
        return null;
    }


	/**
	 * 
	 * @param sequence
	 * @throws InvalidUsingException
	 */
	private static void showIncludeMessagesInCombinedFragment(
			ISequenceDiagram sequence) throws InvalidUsingException {
		showSeparator();
		s1 = "start show incluceMessages in combined fragment" + System.lineSeparator();
		detail.add(s1);
		System.out.println(s1);


		showMiniSeparator();
		IPresentation[] presentations = sequence.getPresentations();
		INodePresentation combinedFragmentPresentation = getCombinedFragmentPresentation(presentations);
		if (combinedFragmentPresentation != null) {
			showIncludeMessages(presentations,combinedFragmentPresentation);
		}
	}

	/**
	 * 
	 */
	private static void showIncludeMessages(IPresentation[] presentations,
			INodePresentation combinedFragmentPresentation) {



		boolean checkfst = true ;
		for(int k=1 ; k<numFrame+1 ; k++){
			if(checkfst == true){
				channelFrame = "f"+ k+"_b, ";
				channelFrameList.add(channelFrame);
				channelFrame = "f"+ k+"_e";
				channelFrameList.add(channelFrame);
			}else{

				channelFrame = ", f"+ k+"_b, ";
				channelFrameList.add(channelFrame);
				channelFrame = "f"+ k+"_e ";
				channelFrameList.add(channelFrame);
			}
		}

		for(int k=1 ; k<numFrame+1 ; k++){
			for(int i=1 ; i< numG+1 ; i++){
				channelFrame = ", f"+k+"_"+"alt"+ i ;
				channelFrameList.add(channelFrame);

			}
		}




		Rectangle2D combinedFragmentRectangle = combinedFragmentPresentation.getRectangle();
		for (IPresentation presentation : presentations) {
			if (isMessagePresentation(presentation)) {
				ILinkPresentation messagePresentation = (ILinkPresentation) presentation;
				Point2D[] messagePoints = messagePresentation.getPoints();
				if(containsTheMessage(combinedFragmentRectangle, messagePoints)){
					s1 = "includes message : " + messagePresentation.getLabel() + System.lineSeparator();
					detail.add(s1);
					System.out.println(s1);


					IncludeMsStr = messagePresentation.toString() ;
                    IncludeMsList.add(IncludeMsStr);


					

					

				}
			}
		}

	}

	/**
	 * 
	 * @param combinedFragmentRectangle
	 * @param messagePoints
	 * @return
	 */
	private static boolean containsTheMessage(
			Rectangle2D combinedFragmentRectangle, Point2D[] messagePoints) {
		return combinedFragmentRectangle.contains(messagePoints[0]) && combinedFragmentRectangle.contains(messagePoints[1]);
	}

	/**
	 * 
	 * @param presentation
	 * @return
	 */
	private static boolean isMessagePresentation(IPresentation presentation) {
		return presentation.getType().equals("Message") && presentation instanceof ILinkPresentation;
	}

	/**
	 * 
	 * @param presentations
	 * @return
	 */
	private static INodePresentation getCombinedFragmentPresentation(
			IPresentation[] presentations) {
		INodePresentation combinedFragmentPresentation = null;
		for (IPresentation presentation : presentations) {
			if (presentation.getType().equals("CombinedFragment")) {
				if (presentation instanceof INodePresentation) {
					combinedFragmentPresentation = (INodePresentation) presentation;
				}
			}
		}
		return combinedFragmentPresentation;
	}

    
}
