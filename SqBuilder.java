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

	public static String channel = new String("Channel"+" ");
	public static String channelMessage = new String("");
	private static List<String> channelMessageList = new ArrayList<String>();

	public static String channelFrame = new String("");
	private static List<String> channelFrameList = new ArrayList<String>();

	private static int numFrame = 0;

	private static boolean state = true;

	

	private static int erridx = 0;
	private static String[] errmsg = new String[1024];


	private static List<String> lfLineList = new ArrayList<String>();
	private static String lfLine = new String("");

	private static String emptyln = new String("      ");
	private static List<String> emptylnList = new ArrayList<String>();
	
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

    private String inputFile;
    
    /**
     * @param inputFile
     *            File to input
     */
    public SqBuilder(String inputFile) {
        this.inputFile = inputFile;
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
    public List<List<String>> getContents()
            throws LicenseNotFoundException, ProjectNotFoundException, NonCompatibleException,
            IOException, ClassNotFoundException, ProjectLockedException, Throwable {

    	// Open a project. And get the model.
        ProjectAccessor prjAccessor = AstahAPI.getAstahAPI().getProjectAccessor();
        prjAccessor.open(inputFile);
        IModel iModel = prjAccessor.getProject();

		List<List<String>> contents = new ArrayList<List<String>>();
		List<String> con = new ArrayList<String>();
		
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

				
				
        // close project.
        prjAccessor.close();
		contents.add(channelMessageList);
		contents.add(channelFrameList);
		emptylnList.add(emptyln);
		emptylnList.add(emptyln);
		emptylnList.add(emptyln);
		contents.add(emptylnList);
		contents.add(lfLineList);
        return contents;
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
		System.out.println("-----------------------");
	}
	private static void showMiniSeparator() {
		System.out.println("----");
	}

	/**
	 * 
	 * @param interaction
	 * @see http://members.change-vision.com/javadoc/astah-api/6_7_0-43495/api/ja/doc/javadoc/com/change_vision/jude/api/inf/model/IInteraction.html
	 */
	private static void showInteraction(IInteraction interaction) {
		System.out.println("start interaction");
		showSeparator();
		showGates(interaction);
		showSeparator();
		showLifelines(interaction);
		showSeparator();
		showMessages(interaction);
		showSeparator();

		System.out.println("end.");
	}

	/**
	 * 
	 * @param gate
	 */
	private static void showGates(IInteraction interaction) {
		System.out.println("Gate start.");
		IGate[] gates = interaction.getGates();
		for (IGate gate : gates) {
			showGate(gate);
		}
		System.out.println("Gate end.");


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
		System.out.println("Lifeline start.");
		ILifeline[] lifelines = interaction.getLifelines();
		boolean first = true;
		for (ILifeline lifeline : lifelines) {
			lfLine = lifeline.toString() ;
			lfLineList.add(lfLine);
			lfLine = " = ";
			lfLineList.add(lfLine);
			if (!first)
				showMiniSeparator();
			showLifeline(lifeline);
			first = false;
			
		}
		System.out.println("Lifeline end.");
	}


	/**
	 * 
	 * @param lifeline
	 */
	private static void showLifeline(ILifeline lifeline) {
		System.out.println("Lifeline : " + lifeline);
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
			System.out.println("Base : " + base);
		}
	}

	/**
	 * 
	 * @param lifeline
	 */
	private static void showFragments(ILifeline lifeline) {
		showMiniSeparator();
		
		System.out.println("Fragment start.");
		INamedElement[] fragments = lifeline.getFragments();
		for (INamedElement fragment : fragments) {
			if (fragment instanceof ICombinedFragment) {
				ICombinedFragment combinedFragment = (ICombinedFragment) fragment;
				showMiniSeparator();
				showCombinedFragment(combinedFragment);
				showMiniSeparator();
				lfLine = "->f1_b->f1_e->";
				lfLineList.add(lfLine);
				continue;
			}
			
			if (fragment instanceof IStateInvariant) {
				System.out.println("StateInvariant : " + fragment);
				continue;
			}
			System.out.println(fragment);

			if(state == true){
				lfLine = "s_"+fragment.toString() ;
				lfLineList.add(lfLine);
			}else{
				lfLine = "r_"+fragment.toString() ;
				lfLineList.add(lfLine);
			}
			
		}
		System.out.println("Fragment end.");
		lfLine = "->SKIP"+System.lineSeparator();
		lfLineList.add(lfLine);
		
		state = false ;
		showMiniSeparator();
	}
	
	
	/**
	 * @param combinedFragment
	 */
	private static void showCombinedFragment(ICombinedFragment combinedFragment) {
		System.out.println("CombinedFragment");
		
		if (combinedFragment.isAlt())
		{
			System.out.println("[" + zu + "] Figure - [Alt] - [" + life + "] Lifeline: Composite fragment not available");
			errmsg[erridx] = "[" + zu + "] diagram - [Alt] - [" + life + "] lifeline: compound fragment is not available";
	    	erridx++;
			err = "1";
		}
		System.out.println("isAssert() : " + combinedFragment.isAssert());
		if (combinedFragment.isAssert())
		{
			System.out.println("[" + zu + "] Figure - [Assert] - [" + life + "] Lifeline: Composite fragment not available");
			errmsg[erridx] = "[" + zu + "] Figure - [Assert] - [" + life + "] Lifeline: Composite fragment not available";
	    	erridx++;
			err = "1";
		}
		System.out.println("isBreak() : " + combinedFragment.isBreak());
		if (combinedFragment.isBreak())
		{
			System.out.println("[" + zu + "] Figure - [Break] - [" + life + "] lifeline: composite fragment not available");
			errmsg[erridx] = "[" + zu + "] Figure - [Break] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}
		System.out.println("isConsider() : " + combinedFragment.isConsider());
		if (combinedFragment.isConsider())
		{
			System.out.println("[" + zu + "] Figure - [Consider] - [" + life + "] lifeline: composite fragment not available");
			errmsg[erridx] = "[" + zu + "] Figure - [Consider] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}
		System.out.println("isCritical() : " + combinedFragment.isCritical());
		if (combinedFragment.isCritical())
		{
			System.out.println("[" + zu + "] Figure - [Critical] - [" + life + "] lifeline: composite fragment not available");
			errmsg[erridx] = "[" + zu + "] Figure - [Critical] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}
		System.out.println("isIgnore() : " + combinedFragment.isIgnore());
		if (combinedFragment.isIgnore())
		{
			System.out.println("[" + zu + "] Figure - [Ignore] - [" + life + "] lifeline: composite fragment not available");
			errmsg[erridx] = "[" + zu + "] Figure - [Ignore] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}
		System.out.println("isLoop() : " + combinedFragment.isLoop());
		if (combinedFragment.isLoop())
		{
			System.out.println("[" + zu + "] Figure - [Loop] - [" + life + "] lifeline: composite fragment not available");
			errmsg[erridx] = "[" + zu + "] Figure - [Loop] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}
		System.out.println("isNeg() : " + combinedFragment.isNeg());
		if (combinedFragment.isNeg())
		{
			System.out.println("[" + zu + "] Figure - [Neg] - [" + life + "] lifeline: composite fragment not available");
			errmsg[erridx] = "[" + zu + "] Figure - [Neg] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}
		System.out.println("isOpt() : " + combinedFragment.isOpt());
		if (combinedFragment.isOpt())
		{
			System.out.println("[" + zu + "] Figure - [Opt] - [" + life + "] lifeline: composite fragment not available");
			errmsg[erridx] = "[" + zu + "] Figure - [Opt] - [" + life + "] lifeline: composite fragment not available";
	    	erridx++;
			err = "1";
		}

		IInteractionOperand[] interactionOperands = combinedFragment.getInteractionOperands();
		for (IInteractionOperand interactionOperand : interactionOperands) {
			System.out.println("interaction operand guard : '" + interactionOperand.getGuard() + "'");
		}
	}

	/**
	 *
	 * @param interaction
	 */
	private static void showMessages(IInteraction interaction) {
		 
		System.out.println("Message start.");
		IMessage[] messages = interaction.getMessages();
		boolean first = true;
		channelMessageList.add(channel);
		for (IMessage message : messages) {
			
			if (!first){
				showMiniSeparator();
				channelMessage = ", s"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
				channelMessage = ", " + "r"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
			}else{
				channelMessage = "s"+ "_" + message.toString() ;
				channelMessageList.add(channelMessage);
				channelMessage = ", " + "r"+ "_" + message.toString()+" " ;
				channelMessageList.add(channelMessage);
			}

			sequenceZuname[sequenceidx ] = zu;
			sequenceZutype[sequenceidx ] = "Sequence Diagram";
			showMessage(message,interaction);

			
			
				
			
			

			first = false;

			if( message.isCreateMessage())
		    {
		       	System.out.println("[" + zu + "] Figure - [" + message + "]: Treated as asynchronous message");
		    }
			if( message.isDestroyMessage())
		    {
		       	System.out.println("[" + zu + "] Figure - [" + message + "]: Treated as asynchronous message");
		    }
		}
		showMiniSeparator();
		System.out.println("Message end.");
	}



	/**
	 * 
	 * @param message
	 *
	 */
	private static void showMessage(IMessage message, IInteraction interaction) {
        System.out.println("message : " + message);
        INamedElement source = message.getSource();
        sequenceMessage[sequenceidx] = message.toString();
        if (source instanceof IGate) {
            IInteractionUse use = getInteractionUse(interaction, (IGate) source);
            //Add check null -----------
            if (use == null)
            {
            	System.out.println("Sender = Frame");
            }
            else
            {
	            System.out.println("source(1) : " + use); // 「reference」name
	            sequenceSourcename[sequenceidx ] = use.toString();
	            sequenceSourceref[sequenceidx ] = "ref";
	        }
         // check null -----------
        } else {
        	// Add check null -----------
            if (source == null)
            {
            	System.out.println("source=null");
            }
            else
            {
	            System.out.println("source : " + source);
	            sequenceSourcename[sequenceidx ] = source.toString();
            }
        }

        INamedElement target = message.getTarget();
        if (target instanceof IGate) {
            IInteractionUse use = getInteractionUse(interaction, (IGate) target);
            System.out.println("target(1) : " + use); // 「reference」name
            sequenceTargetname[sequenceidx ] = use.toString();
            sequenceTargetref[sequenceidx ] = "ref";
        } else {
            System.out.println("target(2) : " + target);
            sequenceTargetname[sequenceidx ] = target.toString();
        }
        String guard = message.getGuard();
        System.out.println("guard : " + guard);
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
		System.out.println("start show incluceMessages in combined fragment");
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
		numFrame++;

		boolean checkfst = true ;

		if(checkfst == true){
			channelFrame = "f"+ numFrame+"_b, ";
			channelFrameList.add(channelFrame);
			channelFrame = "f"+ numFrame+"_e";
			channelFrameList.add(channelFrame);
		}else{

		channelFrame = ", f"+ numFrame+"_b, ";
		channelFrameList.add(channelFrame);
		channelFrame = "f"+ numFrame+"_e ";
		channelFrameList.add(channelFrame);
		}

		int count = 0 ;
		Rectangle2D combinedFragmentRectangle = combinedFragmentPresentation.getRectangle();
		for (IPresentation presentation : presentations) {
			if (isMessagePresentation(presentation)) {
				ILinkPresentation messagePresentation = (ILinkPresentation) presentation;
				Point2D[] messagePoints = messagePresentation.getPoints();
				if(containsTheMessage(combinedFragmentRectangle, messagePoints)){
					System.out.println("includes message : " + messagePresentation.getLabel());
					count++;
					
					channelFrame = ", f"+numFrame+"_"+"alt"+ count ;
					channelFrameList.add(channelFrame);
					

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
