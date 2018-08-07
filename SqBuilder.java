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

    // keep Channel
	public static String channel = new String("channel"+" ");
	public static String channelMessage = new String("");
	private static List<String> channelMessageList = new ArrayList<String>();

    // Channel in Frame's part
    public static String channelFrame = new String("");
	private static List<String> channelFrameList = new ArrayList<String>();
	private static int numFrame = 0;

    // check state sent or receive
	private static boolean state = true;

	// check alt
	private static boolean alt = false ;

   

	private static int erridx = 0;
	private static String[] errmsg = new String[1024];

    //lifeline outside frame
	private static List<String> lifelineList = new ArrayList<String>();
	private static String lifeLineStr = new String("");

	//keep lifeline for use
	private static List<String> line = new ArrayList<String>();
	private static String checkLifelineStr = new String("") ;


    // empty line in CSP
	private static String emptyln = new String("      ");
	private static List<String> emptylnList = new ArrayList<String>();

    //lifeline inside frame
    private static List<String> insideFrameList = new ArrayList<String>();
    private static String insideFrameStr = new String("");


    // SQ total process list
    private static List<String> sqTotalprocessList = new ArrayList<String>();
    private static String sqTotalprocessStr = new String("") ;

	// SQ_Lifeline total process list
	private static List<String> sumSQLifelineframeList = new ArrayList<String>();
	private static String sumSQLifelineframeStr = new String("") ;

	// Frame_Lifeline total process list
	private static List<String> frameLifelineprocessList = new ArrayList<String>();
	private static String frameLifelineprocessStr = new String("") ;
	// keep variable frame without "chanel" work for use
	private static List<String> variableframeList = new ArrayList<String>();

	// keep lifeline list for use
    private static List<String> uselifelineList = new ArrayList<String>();
    private static String uselifelineStr = new String("") ;

    // conclude line in csp (last line in csp file)
    private static List<String> conclusionList = new ArrayList<String>();
    private static String conclusionStr = new String("") ;

	// sum variable for conclude line in csp (last line in csp file)
    private static List<String> variableforconclusionList = new ArrayList<String>();
    private static String variableforconclusionStr = new String("") ;

    // Message's total process (MSG)
    private static List<String> msgList = new ArrayList<String>();
    private static String msgStr = new String("") ;

	// Keep Message for use in total process (MSG)
    private static List<String> keepForMSGList = new ArrayList<String>();
    private static String keepForMSGStr = new String("") ;

	// keep detail for information text area (UI)
	private static List<String> detailForInfo = new ArrayList<String>();
	private static String detailStr = new String("") ;

	// keep Message
	private static List<String> keepMessageList = new ArrayList<String>();
	private static String keepMessageStr = new String("") ;

	// keep total of SQI for frame
	private static List<String> totalSQforFrameList = new ArrayList<String>();
	private static String totalSQforFrameStr = new String("") ;

	// count when combinedFragment.isAlt()
    private static int numAlt = 0 ;

	// count when combinedFragment.isAlt()
	private static int numFrameList = 0 ;

	// check when get combinedFragment for count numframe
	private static boolean checkcount = false ;

	// check variable start
	private static boolean checkVariable = false ;

	// set 0 for start the frame list line
	private static int numCheckStart = 0 ;
	//
	private static int checkStart = 0 ;

	// count guard in case alt
	private static int guardCount = 0 ;


	private static String temp = new String("")  ;

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
		
		lifelineList = new ArrayList<String>();
		line = new ArrayList<String>();
		variableframeList = new ArrayList<String>();
		totalSQforFrameList = new ArrayList<String>();
		emptylnList = new ArrayList<String>();
		insideFrameList = new ArrayList<String>();

		temp = new String("")  ;
		sqTotalprocessList = new ArrayList<String>();

		uselifelineList = new ArrayList<String>();
		frameLifelineprocessList = new ArrayList<String>();
		sumSQLifelineframeList = new ArrayList<String>();
		conclusionList = new ArrayList<String>();
		variableforconclusionList = new ArrayList<String>();
		msgList = new ArrayList<String>();
		keepForMSGList = new ArrayList<String>();
		keepMessageList = new ArrayList<String>();

		detailForInfo = new ArrayList<String>();
		numAlt = 0 ;
		numCheckStart = 0 ;
		guardCount = 0 ;
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

							checkLifelineStr = lifeline.toString() ;
							line.add(checkLifelineStr);
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
            contents.add(lifelineList);
            contents.add(emptylnList);
            contents.add(insideFrameList);
			contents.add(emptylnList);
			contents.add(keepMessageList);
			contents.add(msgList);
			contents.add(emptylnList);
			contents.add(sumSQLifelineframeList);
			contents.add(frameLifelineprocessList);
			contents.add(totalSQforFrameList);
			contents.add(emptylnList);
			conclusionStr = zu + " = "+ zu+"I[|{" ;
			conclusionList.add(conclusionStr);
			for (String sum : variableforconclusionList) {
				if(sum.equals(variableforconclusionList.get(0))){
					conclusionStr = sum ;
					conclusionList.add(conclusionStr);
				}else{
					conclusionStr = ",";
					conclusionList.add(conclusionStr);
					conclusionStr = sum ;
					conclusionList.add(conclusionStr);
				}


			}
			conclusionStr = "}|]MSG" ;
			conclusionList.add(conclusionStr);
			contents.add(conclusionList);



        }else {
            emptylnList.add(emptyln);
            emptylnList.add(emptyln);
            emptylnList.add(emptyln);
            contents.add(emptylnList);
            contents.add(keepMessageList);
            contents.add(msgList);
            contents.add(emptylnList);
            contents.add(lifelineList);
            contents.add(sqTotalprocessList);
            contents.add(emptylnList);
            conclusionStr = zu + " = "+ zu+"I[|{" ;
            conclusionList.add(conclusionStr);
                for (String sum : variableforconclusionList) {
                        if(sum.equals(variableforconclusionList.get(0))){
                            conclusionStr = sum ;
                            conclusionList.add(conclusionStr);
                        }else{
                            conclusionStr = ",";
                            conclusionList.add(conclusionStr);
                            conclusionStr = sum ;
                            conclusionList.add(conclusionStr);
                        }


                }
            conclusionStr = "}|]MSG" ;
            conclusionList.add(conclusionStr);
            contents.add(conclusionList);

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
		keepMessageList = new ArrayList<String>();
		List<List<String>> informations = new ArrayList<List<String>>();
		List<String> con = new ArrayList<String>();
		channelMessageList = new ArrayList<String>();
		channelFrameList = new ArrayList<String>();
		numFrame = 0;
		state = true;
		alt = false ;

		lifelineList = new ArrayList<String>();
		line = new ArrayList<String>();
		variableframeList = new ArrayList<String>();
		emptylnList = new ArrayList<String>();
		insideFrameList = new ArrayList<String>();
		temp = new String("")  ;
		totalSQforFrameList = new ArrayList<String>();
		sqTotalprocessList = new ArrayList<String>();
		uselifelineList = new ArrayList<String>();
		frameLifelineprocessList = new ArrayList<String>();
		sumSQLifelineframeList = new ArrayList<String>();
		conclusionList = new ArrayList<String>();
		variableforconclusionList = new ArrayList<String>();
		msgList = new ArrayList<String>();
		keepForMSGList = new ArrayList<String>();


		detailForInfo = new ArrayList<String>();

		numAlt = 0 ;
		numCheckStart = 0 ;
		guardCount = 0 ;
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
		informations.add(detailForInfo);

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
		detailStr = "-----------------------" + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


	}
	private static void showMiniSeparator() {

		detailStr = "----" + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);

	}

	/**
	 * 
	 * @param interaction
	 * @see http://members.change-vision.com/javadoc/astah-api/6_7_0-43495/api/ja/doc/javadoc/com/change_vision/jude/api/inf/model/IInteraction.html
	 */
	private static void showInteraction(IInteraction interaction) {

		detailStr = "start interaction" + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		showSeparator();
		showGates(interaction);
		showSeparator();
		showLifelines(interaction);
		showSeparator();
		showMessages(interaction);
		showSeparator();


		detailStr = "end." + System.lineSeparator() ;
		System.out.println(detailStr);
		detailForInfo.add(detailStr);
		showInsideFrame(interaction);
	}

	/**
	 * 
	 * @param gate
	 */
	private static void showGates(IInteraction interaction) {

		detailStr = "Gate start."+ System.lineSeparator() ;
		detailForInfo.add(detailStr);
		System.out.println(detailStr);

		IGate[] gates = interaction.getGates();
		for (IGate gate : gates) {
			showGate(gate);
		}

		detailStr = "Gate end." + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


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

		detailStr = "Lifeline start." + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);

		ILifeline[] lifelines = interaction.getLifelines();
		boolean first = true;
		for (ILifeline lifeline : lifelines) {

//			chcklifeline = lifeline.toString() ;
//			line.add(chcklifeline);
//			System.out.print(lifeline.toString() + " nameeeeee na ja na aj");
//			System.out.print(line.size()+ "checklinesize");

		    uselifelineList.add(lifeline.toString());
			lifeLineStr = lifeline.toString() ;

			lifelineList.add(lifeLineStr);
			lifeLineStr = " = ";
			lifelineList.add(lifeLineStr);
			if (!first)
				showMiniSeparator();
			showLifeline(lifeline);
			first = false;
			
		}
		detailStr = "Lifeline end." + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


        sqTotalprocessStr = zu +"I = " ;
        sqTotalprocessList.add(sqTotalprocessStr);
        if(uselifelineList.size() > 1){
            for (String lf : uselifelineList) {
                if(lf.equals(uselifelineList.get(uselifelineList.size()-1))){
                    sqTotalprocessStr = lf;
                    sqTotalprocessList.add(sqTotalprocessStr);
					for(int i=2 ; i<uselifelineList.size() ; i++ ){
						sqTotalprocessStr = ")" ;
						sqTotalprocessList.add(sqTotalprocessStr);
					}


                }else if(lf.equals(uselifelineList.get(0))){
                    sqTotalprocessStr = lf;
                    sqTotalprocessList.add(sqTotalprocessStr);
                    sqTotalprocessStr = " ||| ";
                    sqTotalprocessList.add(sqTotalprocessStr);
                }else{
                    sqTotalprocessStr = "("+ lf;
                    sqTotalprocessList.add(sqTotalprocessStr);
                    sqTotalprocessStr = " ||| ";
                    sqTotalprocessList.add(sqTotalprocessStr);
                }
            }
        }else{
            sqTotalprocessStr = uselifelineList.get(0).toString() + " ||| "+ uselifelineList.get(1).toString();
            sqTotalprocessList.add(sqTotalprocessStr);
        }









	}


	/**
	 * 
	 * @param lifeline
	 */
	private static void showLifeline(ILifeline lifeline) {



		detailStr = "Lifeline : " + lifeline + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);



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

			detailStr = "Base : " + base + System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);

		}
	}

	/**
	 * 
	 * @param lifeline
	 */
	private static void showFragments(ILifeline lifeline) {
		showMiniSeparator();

		detailStr = "Fragment start."+ System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);
		boolean end = false ;

		INamedElement[] fragments = lifeline.getFragments();
		for (INamedElement fragment : fragments) {
			if (fragment instanceof ICombinedFragment) {

				ICombinedFragment combinedFragment = (ICombinedFragment) fragment;


				showMiniSeparator();

				if(checkcount == false){
					numFrame++;
					if(lifelineList != null) {
						lifeLineStr = "f"+ numFrame +"_b->f"+ numFrame +"_e->";
						lifelineList.add(lifeLineStr);
					}else if(lifelineList == null){
						lifeLineStr = "f"+ numFrame +"_b->f"+ numFrame +"_e";
						lifelineList.add(lifeLineStr);
					}
				}else{

					if(end == false){
						for(int i=1 ; i < numFrame+1 ; i++){
							if(lifelineList != null) {
								lifeLineStr = "f"+ i +"_b->f"+ i +"_e->";
								lifelineList.add(lifeLineStr);
							}else if(lifelineList == null){
								lifeLineStr = "f"+ i +"_b->f"+ i +"_e";
								lifelineList.add(lifeLineStr);
							}
						}
					}

					end = true ;

				}

				showCombinedFragment(combinedFragment);
				showMiniSeparator();


				continue;
			}

			
			if (fragment instanceof IStateInvariant) {

				detailStr = "StateInvariant : " + fragment +System.lineSeparator();
				detailForInfo.add(detailStr);
				System.out.println(detailStr);

				continue;
			}


			detailStr = fragment +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);




            for (String lfstr : lifelineList) {

                    if (lfstr.equals("s_"+fragment.toString()+"->")) {
                            state = false ;

                    }
            }

            if(state == true){
				lifeLineStr = "s_"+fragment.toString()+"->" ;
				lifelineList.add(lifeLineStr);
			}else{
				lifeLineStr = "r_"+fragment.toString()+"->" ;
				lifelineList.add(lifeLineStr);
                state = true;
			}
			
		}

		detailStr = "Fragment end." +System.lineSeparator();

		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		lifeLineStr = "SKIP"+System.lineSeparator();
		lifelineList.add(lifeLineStr);


		showMiniSeparator();
		checkcount = true ;
	}



	/**
	 *
	 * @param lifeline
	 */
	private static void showInsideFrame(IInteraction interaction) {
		ILifeline[] lifelines = interaction.getLifelines();
		int countFrame = 0 ;
		int gCount = 0 ;
		checkStart = 0 ;
		for (ILifeline lifeline : lifelines) {
			countFrame = 0;
			INamedElement[] fragments = lifeline.getFragments();
			for (INamedElement fragment : fragments) {
				if (fragment instanceof ICombinedFragment) {
					ICombinedFragment combinedFragment = (ICombinedFragment) fragment;
					 gCount = 0 ;
					if (combinedFragment.isAlt()){
						IInteractionOperand[] interactionOperands = combinedFragment.getInteractionOperands();
						IMessage[] messages = null ;
							countFrame ++;
							insideFrameStr = "F" + countFrame + "_" + lifeline.toString() + " = f" + countFrame + "_b->F" + countFrame + "_" + lifeline.toString() + "_ALT" + System.lineSeparator()+ "F" + countFrame + "_" + lifeline.toString() + "_ALT" + " = ";
							insideFrameList.add(insideFrameStr);

						for (IInteractionOperand interactionOperand : interactionOperands) {
							if(interactionOperand.getGuard() != null){
								gCount++;
							}


							messages = interactionOperand.getMessages();

							if (checkStart == 0) {
								insideFrameStr = "(f" + countFrame + "_alt" + gCount ;
								insideFrameList.add(insideFrameStr);
								for (IMessage message : messages) {
									if(message.getSource().toString().equals(lifeline.toString())){
										insideFrameStr =  "->" ;
										insideFrameList.add(insideFrameStr);
										insideFrameStr =  "s_"+ message ;
										insideFrameList.add(insideFrameStr);

									}else if(message.getTarget().toString().equals(lifeline.toString())){
										insideFrameStr =  "->" ;
										insideFrameList.add(insideFrameStr);
										insideFrameStr =  "r_"+ message ;
										insideFrameList.add(insideFrameStr);

									}
									insideFrameStr = "->f" + countFrame + "_e->SKIP)" ;
									insideFrameList.add(insideFrameStr);
								}
								checkStart++;
							}else if (checkStart == interactionOperands.length-1) {
								insideFrameStr = "[]" ;
								insideFrameList.add(insideFrameStr);
								insideFrameStr = "(f" + countFrame + "_alt" + gCount ;
								insideFrameList.add(insideFrameStr);
								for (IMessage message : messages) {
									if(message.getSource().toString().equals(lifeline.toString())){
										insideFrameStr =  "->" ;
										insideFrameList.add(insideFrameStr);
										insideFrameStr =  "s_"+ message ;
										insideFrameList.add(insideFrameStr);

									}else if(message.getTarget().toString().equals(lifeline.toString())){
										insideFrameStr =  "->" ;
										insideFrameList.add(insideFrameStr);
										insideFrameStr =  "r_"+ message ;
										insideFrameList.add(insideFrameStr);

									}
									insideFrameStr = "->f" + countFrame + "_e->SKIP)" +System.lineSeparator(); ;
									insideFrameList.add(insideFrameStr);
								}
								checkStart = 0 ;

							}else{
								insideFrameStr = "(f" + countFrame + "_alt" + gCount ;
								insideFrameList.add(insideFrameStr);
								for (IMessage message : messages) {
									if(message.getSource().toString().equals(lifeline.toString())){
										insideFrameStr =  "->" ;
										insideFrameList.add(insideFrameStr);
										insideFrameStr =  "s_"+ message ;
										insideFrameList.add(insideFrameStr);

									}else if(message.getTarget().toString().equals(lifeline.toString())){
										insideFrameStr =  "->" ;
										insideFrameList.add(insideFrameStr);
										insideFrameStr =  "r_"+ message ;
										insideFrameList.add(insideFrameStr);

									}
									insideFrameStr = "->f" + countFrame + "_e->SKIP)" ;
									insideFrameList.add(insideFrameStr);
								}
								checkStart++;
							}





						}



					}

				}
			}

		}

	}




	/**
	 * @param combinedFragment
	 */
	private static void showCombinedFragment(ICombinedFragment combinedFragment) {

		detailStr = "CombinedFragment" +System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);






		
		if (combinedFragment.isAlt())
		{

			detailStr = "[" + zu + "] Figure - [Alt] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] diagram - [Alt] - [" + life + "] lifeline: compound fragment is not available";
	    	erridx++;
			err = "1";
			alt = true ;

					numAlt++ ;










		}
		detailStr = "isAssert() : " + combinedFragment.isAssert() + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		if (combinedFragment.isAssert())
		{

			detailStr = "[" + zu + "] Figure - [Assert] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] Figure - [Assert] - [" + life + "] Lifeline: Composite fragment not available";
	    	erridx++;
			err = "1";
		}

		detailStr = "isBreak() : " + combinedFragment.isBreak() + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		if (combinedFragment.isBreak())
		{


			detailStr = "[" + zu + "] Figure - [Break] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] Figure - [Break] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		detailStr = "isConsider() : " + combinedFragment.isConsider() + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);



		if (combinedFragment.isConsider())
		{
			detailStr = "[" + zu + "] Figure - [Consider] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] Figure - [Consider] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		detailStr = "isCritical() : " + combinedFragment.isCritical() + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		if (combinedFragment.isCritical())
		{

			detailStr = "[" + zu + "] Figure - [Critical] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] Figure - [Critical] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		detailStr = "isIgnore() : " + combinedFragment.isIgnore() + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		if (combinedFragment.isIgnore())
		{
			detailStr = "[" + zu + "] Figure - [Ignore] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] Figure - [Ignore] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		detailStr = "isLoop() : " + combinedFragment.isLoop() + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		if (combinedFragment.isLoop())
		{

			detailStr = "[" + zu + "] Figure - [Loop] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] Figure - [Loop] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		detailStr = "isNeg() : " + combinedFragment.isNeg() + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);



		if (combinedFragment.isNeg())
		{
			detailStr = "[" + zu + "] Figure - [Neg] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] Figure - [Neg] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		detailStr = "isOpt() : " + combinedFragment.isOpt() + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		if (combinedFragment.isOpt())
		{
			detailStr = "[" + zu + "] Figure - [Opt] - [" + life + "] Lifeline: Composite fragment not available" +System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


			errmsg[erridx] = "[" + zu + "] Figure - [Opt] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}



		IInteractionOperand[] interactionOperands = combinedFragment.getInteractionOperands();
		IMessage[] messages = null ;
		ILifeline[] lifelinesCom = null ;
		


		for (IInteractionOperand interactionOperand : interactionOperands) {
			if(alt == true && numAlt > 1 && numAlt == line.size()){
				if(interactionOperand.getGuard() != null){
					guardCount++;
				}
			}


			if(interactionOperands == null){

				detailStr = "null" + System.lineSeparator();
				detailForInfo.add(detailStr);
				System.out.println(detailStr);

			}else{

				detailStr = "interaction operand guard : '" + interactionOperand.getGuard() + "'" + System.lineSeparator();
				detailForInfo.add(detailStr);
				System.out.println(detailStr);



				messages = interactionOperand.getMessages();
				for (IMessage message : messages) {
					detailStr = "Ms : '" + message.toString() + "' Source : " + message.getSource()+  ", Target : "  + message.getTarget() + System.lineSeparator();
					detailForInfo.add(detailStr);
					System.out.println(detailStr);


				}


//				lifelinesCom = interactionOperand.getLifelines();
//				for (ILifeline life : lifelinesCom) {
//
//					s1 = "Lifeline : '" + life.toString() + "'" + System.lineSeparator();
//					detail.add(s1);
//					System.out.println(s1);
//
//
//
//
//
//				}
			}

		}

		numCheckStart = 0 ;

		int ck = 0;
		System.out.print(line.size()+ "checklinesize");




		boolean checkVariable = true ;
		if(alt == true && numAlt > 1 && numAlt == line.size()){
			for(int k=1 ; k<numFrame+1 ; k++){
				if( checkVariable == true){
					channelFrame = "f"+ k+"_b, ";
					variableframeList.add(channelFrame);
					channelFrame = "f"+ k+"_e";
					variableframeList.add(channelFrame);
				}else{

					channelFrame = ", f"+ k+"_b, ";
					variableframeList.add(channelFrame);
					channelFrame = "f"+ k+"_e ";
					variableframeList.add(channelFrame);
				}
			}

			for(int k=1 ; k<numFrame+1 ; k++){
				for(int i=1 ; i< guardCount+1 ; i++){
					channelFrame = ", f"+k+"_"+"alt"+ i ;
					variableframeList.add(channelFrame);

				}
			}

			for(int k=1 ; k<numFrame+1 ; k++){
				frameLifelineprocessStr = "F"+k+"_" ;
				frameLifelineprocessList.add(frameLifelineprocessStr);
				for (String lf : line) {
					frameLifelineprocessStr = lf;
					frameLifelineprocessList.add(frameLifelineprocessStr);

				}
				frameLifelineprocessStr = " = " ;
				frameLifelineprocessList.add(frameLifelineprocessStr);
				for (String lf : line) {



						if(lf.equals(line.get(line.size()-1))){
							frameLifelineprocessStr = "F"+k+"_" + lf;
							frameLifelineprocessList.add(frameLifelineprocessStr);
							frameLifelineprocessStr = ")"  + System.lineSeparator();
							frameLifelineprocessList.add(frameLifelineprocessStr);

						}else if(lf.equals(line.get(0))){
							frameLifelineprocessStr = "F"+k+"_" + lf;
							frameLifelineprocessList.add(frameLifelineprocessStr);
							frameLifelineprocessStr = "[|{";
							frameLifelineprocessList.add(frameLifelineprocessStr);
							frameLifelineprocessStr = "f"+k+ "_b, f"+k+"_e" ;
							frameLifelineprocessList.add(frameLifelineprocessStr);
							for(int i=1 ; i< guardCount+1 ; i++){
								frameLifelineprocessStr =  ", f"+k+"_alt"+i ;
								frameLifelineprocessList.add(frameLifelineprocessStr);
							}


//						for(int i=0 ; i< variableframeList.size() ; i++){
//
//							frameLifelineprocessStr = variableframeList.get(i) ;
//							frameLifelineprocessList.add(frameLifelineprocessStr);
//
//						}




							frameLifelineprocessStr = "}|]";
							frameLifelineprocessList.add(frameLifelineprocessStr);


						}else{
							frameLifelineprocessStr = "(" ;
							frameLifelineprocessList.add(frameLifelineprocessStr);
							frameLifelineprocessStr =  "F"+k+"_" + lf;
							frameLifelineprocessList.add(frameLifelineprocessStr);
							frameLifelineprocessStr = "[|{";
							frameLifelineprocessList.add(frameLifelineprocessStr);
							frameLifelineprocessStr = "f"+k+ "_b, f"+k+"_e" ;
							frameLifelineprocessList.add(frameLifelineprocessStr);

							for(int i=1 ; i< guardCount+1 ; i++){
								frameLifelineprocessStr =  ", f"+k+"_alt"+i ;
								frameLifelineprocessList.add(frameLifelineprocessStr);
							}




							frameLifelineprocessStr = "}|]" ;
							frameLifelineprocessList.add(frameLifelineprocessStr);

						}





				}
			}


			sumSQLifelineframeStr = zu + "I_" ;
			sumSQLifelineframeList.add(sumSQLifelineframeStr);
			for (String lf : line) {
				sumSQLifelineframeStr = lf;
				sumSQLifelineframeList.add(sumSQLifelineframeStr);

			}
			sumSQLifelineframeStr = " = " ;
			sumSQLifelineframeList.add(sumSQLifelineframeStr);
			for (String lf : line) {
				if(lf.equals(line.get(line.size()-1))){
					sumSQLifelineframeStr = lf;
					sumSQLifelineframeList.add(sumSQLifelineframeStr);
					sumSQLifelineframeStr = ")" ;
					sumSQLifelineframeList.add(sumSQLifelineframeStr);

				}else if(lf.equals(line.get(0))){
					sumSQLifelineframeStr = lf;
					sumSQLifelineframeList.add(sumSQLifelineframeStr);
					sumSQLifelineframeStr = "[|{";
					sumSQLifelineframeList.add(sumSQLifelineframeStr);

					for(int k=1 ; k<numFrame+1 ; k++){

						if(k == 1){
							sumSQLifelineframeStr = "f"+k+"_b, f"+k+"_e" ;
							sumSQLifelineframeList.add(sumSQLifelineframeStr);
						}else{
							sumSQLifelineframeStr = ", f"+k+"_b, f"+k+"_e" ;
							sumSQLifelineframeList.add(sumSQLifelineframeStr);
						}


					}
					sumSQLifelineframeStr = "}|]";
					sumSQLifelineframeList.add(sumSQLifelineframeStr);

				}else{
					sumSQLifelineframeStr = "(" ;
					sumSQLifelineframeList.add(sumSQLifelineframeStr);
					sumSQLifelineframeStr = lf;
					sumSQLifelineframeList.add(sumSQLifelineframeStr);
					sumSQLifelineframeStr = "[|{";
					sumSQLifelineframeList.add(sumSQLifelineframeStr);

					for(int k=1 ; k<numFrame+1 ; k++){
						if(k == 1){
							sumSQLifelineframeStr = "f"+k+"_b, f"+k+"_e" ;
							sumSQLifelineframeList.add(sumSQLifelineframeStr);
						}else{
							sumSQLifelineframeStr = ", f"+k+"_b, f"+k+"_e" ;
							sumSQLifelineframeList.add(sumSQLifelineframeStr);
						}

					}
					sumSQLifelineframeStr = "}|]";
					sumSQLifelineframeList.add(sumSQLifelineframeStr);

				}
			}

			totalSQforFrameStr = zu +"I = "+ zu + "I_" ;
			totalSQforFrameList.add(totalSQforFrameStr);
			for (String lf : line) {
				totalSQforFrameStr = lf;
				totalSQforFrameList.add(totalSQforFrameStr);

			}

			totalSQforFrameStr = "[|{" ;
			totalSQforFrameList.add(totalSQforFrameStr);

			for(int k=1 ; k<numFrame+1 ; k++){

				if(k == 1){
					totalSQforFrameStr = "f"+k+"_b, f"+k+"_e" ;
					totalSQforFrameList.add(totalSQforFrameStr);
				}else{
					totalSQforFrameStr = ", f"+k+"_b, f"+k+"_e" ;
					totalSQforFrameList.add(totalSQforFrameStr);
				}


			}

			totalSQforFrameStr = "}|]" ;
			totalSQforFrameList.add(totalSQforFrameStr);

			for(int k=1 ; k<numFrame+1 ; k++){

				if(numFrame < 2){
					totalSQforFrameStr = "F"+k+"_" ;
					totalSQforFrameList.add(totalSQforFrameStr);
					for (String lf : line) {
						totalSQforFrameStr = lf;
						totalSQforFrameList.add(totalSQforFrameStr);

					}
				}else{

					if(k == 1){
						totalSQforFrameStr = "(F"+k+"_" ;
						totalSQforFrameList.add(totalSQforFrameStr);
						for (String lf : line) {
							totalSQforFrameStr = lf;
							totalSQforFrameList.add(totalSQforFrameStr);

						}
					}else if(k == numFrame){
						totalSQforFrameStr = "|||"+"F"+k+"_" ;
						totalSQforFrameList.add(totalSQforFrameStr);
						for (String lf : line) {
							totalSQforFrameStr = lf;
							totalSQforFrameList.add(totalSQforFrameStr);

						}

						for(int j=1 ; j<numFrame ; j++){
							totalSQforFrameStr = ")" ;
							totalSQforFrameList.add(totalSQforFrameStr);
						}

					}else{

						totalSQforFrameStr = "||| ("+k+"_" ;
						totalSQforFrameList.add(totalSQforFrameStr);
						for (String lf : line) {
							totalSQforFrameStr = lf;
							totalSQforFrameList.add(totalSQforFrameStr);

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

		detailStr = "Message start." + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


		IMessage[] messages = interaction.getMessages();
		boolean first = true;
		channelMessageList.add(channel);
		for (IMessage message : messages) {

            keepMessageStr = message.toString().toUpperCase() + " = ";
            keepMessageList.add(keepMessageStr);
            keepMessageStr = "s_"+ message.toString() +"->r_"+ message.toString() + "->"+message.toString().toUpperCase()+ System.lineSeparator()   ;
            keepMessageList.add(keepMessageStr);

			if (!first){
				showMiniSeparator();
				channelMessage = ", s"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
                variableforconclusionStr = "s"+ "_" + message.toString() ;
                variableforconclusionList.add(variableforconclusionStr);
				channelMessage = ", " + "r"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
                variableforconclusionStr = "r"+ "_" + message.toString() ;
                variableforconclusionList.add(variableforconclusionStr);
			}else{
                variableforconclusionStr = "s"+ "_" + message.toString() ;
                variableforconclusionList.add(variableforconclusionStr);
				channelMessage = "s"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
				channelMessage = ", " + "r"+ "_" + message.toString()+" " ;
				channelMessageList.add(channelMessage);
                variableforconclusionStr = "r"+ "_" + message.toString() ;
                variableforconclusionList.add(variableforconclusionStr);
			}

			sequenceZuname[sequenceidx ] = zu;
			sequenceZutype[sequenceidx ] = "Sequence Diagram";
			showMessage(message,interaction);

            keepForMSGStr = message.toString();
            keepForMSGList.add(keepForMSGStr);





				
			
			

			first = false;

			if( message.isCreateMessage())
		    {
				detailStr = "[" + zu + "] Figure - [" + message + "]: Treated as asynchronous message" + System.lineSeparator();
				detailForInfo.add(detailStr);
				System.out.println(detailStr);

		    }
			if( message.isDestroyMessage())
		    {
				detailStr = "[" + zu + "] Figure - [" + message + "]: Treated as asynchronous message" + System.lineSeparator();
				detailForInfo.add(detailStr);
				System.out.println(detailStr);


		    }
		}

        msgStr = "MSG = " ;
        msgList.add(msgStr);
        if(keepForMSGList.size() > 2){
            for (String lf : keepForMSGList) {
                if(lf.equals(keepForMSGList.get(keepForMSGList.size()-1))){
                    msgStr = lf.toUpperCase();
                    msgList.add(msgStr);

					for(int i=1 ; i < keepForMSGList.size()-1 ; i++){
						msgStr = ")" ;
						msgList.add(msgStr);
					}

                }else if(lf.equals(keepForMSGList.get(0))){
                    msgStr = lf.toUpperCase();
                    msgList.add(msgStr);
                    msgStr = " ||| ";
                    msgList.add(msgStr);
                }else{
                    msgStr = "("+ lf.toUpperCase();
                    msgList.add(msgStr);
                    msgStr = " ||| ";
                    msgList.add(msgStr);
                }
            }
        }else{
            for (String lf : keepForMSGList) {
                if(lf.equals(keepForMSGList.get(keepForMSGList.size()-1))){
                    msgStr = lf.toUpperCase();
                    msgList.add(msgStr);
                }else if(lf.equals(keepForMSGList.get(0))){
                    msgStr = lf.toUpperCase();
                    msgList.add(msgStr);
                    msgStr = " ||| ";
                    msgList.add(msgStr);
                }
            }
        }
		showMiniSeparator();

		detailStr = "Message end." + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);




	}



	/**
	 * 
	 * @param message
	 *
	 */
	private static void showMessage(IMessage message, IInteraction interaction) {

		detailStr = "message : " + message  + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


        INamedElement source = message.getSource();
        sequenceMessage[sequenceidx] = message.toString();
        if (source instanceof IGate) {
            IInteractionUse use = getInteractionUse(interaction, (IGate) source);
            //Add check null -----------
            if (use == null)
            {
				detailStr = "Sender = Frame"+ System.lineSeparator();
				detailForInfo.add(detailStr);
				System.out.println(detailStr);


            }
            else
            {

				detailStr = "source(1) : " + use + System.lineSeparator(); // 「reference」name
				detailForInfo.add(detailStr);
				System.out.println(detailStr);

	            sequenceSourcename[sequenceidx ] = use.toString();
	            sequenceSourceref[sequenceidx ] = "ref";
	        }
         // check null -----------
        } else {
        	// Add check null -----------
            if (source == null)
            {

				detailStr = "source=null" + System.lineSeparator();
				detailForInfo.add(detailStr);
				System.out.println(detailStr);


            }
            else
            {

				detailStr = "source : " + source + System.lineSeparator();
				detailForInfo.add(detailStr);
				System.out.println(detailStr);


	            sequenceSourcename[sequenceidx ] = source.toString();
            }
        }

        INamedElement target = message.getTarget();
        if (target instanceof IGate) {
            IInteractionUse use = getInteractionUse(interaction, (IGate) target);

			detailStr = "target(1) : " + use + System.lineSeparator(); // 「reference」name
			detailForInfo.add(detailStr);
			System.out.println(detailStr);

            sequenceTargetname[sequenceidx ] = use.toString();
            sequenceTargetref[sequenceidx ] = "ref";

        } else {

			detailStr = "target(2) : " + target+ System.lineSeparator();
			detailForInfo.add(detailStr);
			System.out.println(detailStr);


            sequenceTargetname[sequenceidx ] = target.toString();
        }
        String guard = message.getGuard();

		detailStr = "guard : " + guard + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


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
		detailStr = "start show incluceMessages in combined fragment" + System.lineSeparator();
		detailForInfo.add(detailStr);
		System.out.println(detailStr);


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
				channelFrame ="f"+ k+"_e";
				channelFrameList.add(channelFrame);
				checkfst = false ;
			}else{

				channelFrame =  ", f"+ k+"_b, ";
				channelFrameList.add(channelFrame);
				channelFrame =  "f"+ k+"_e ";
				channelFrameList.add(channelFrame);
			}
		}

		for(int k=1 ; k<numFrame+1 ; k++){
			for(int i=1 ; i< guardCount+1 ; i++){
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
					detailStr = "includes message : " + messagePresentation.getLabel() + System.lineSeparator();
					detailForInfo.add(detailStr);
					System.out.println(detailStr);





					

					

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
