package researchprojectnet;

/**
 *
 * @author Kevin
 */
  
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Random;
 
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TargetTest
{
  //enables configuration of the test to customer's wants
  private static final int LABEL_COUNT = 100;
  private static final int TEST_TARGET_LOCATION = 42;
  private static final int TEST_TARGET_APPERANCE = 50;
  private static final int TEST_TARGET_DISAPPERANCE = 56;
  private static final int DELAY = 800;
  private static final int TARGET_TOTAL = 80;
  private static final int ACTIVE_RED_TARGET_TOTAL = 3;
  private static final int ACTIVE_BLUE_TARGET_TOTAL = 6;
  private static final int ACTIVE_GREEN_TARGET_TOTAL = 6;
  
  private JPanel mainPanel = new JPanel();
  private JPanel controlsPanel = new JPanel();
  private JButton startRound = new JButton("Start Test");
  private JLabel[] labelArray = new JLabel[LABEL_COUNT];
  private Integer[] activeRedTarget = new Integer[ACTIVE_RED_TARGET_TOTAL];
  private Integer[] activeBlueTarget = new Integer[ACTIVE_BLUE_TARGET_TOTAL];
  private Integer[] activeGreenTarget = new Integer[ACTIVE_GREEN_TARGET_TOTAL];
  private Random generator = new Random();
  private int timerCounter = 0;
  private int blueTargetCounter = 0;
  private int greenTargetCounter = 0;
  private int testTargetDelay = 0;
  
  private ImageIconSet iconSet = new ImageIconSet(TARGET_TOTAL-1);

  //Creates timer, runs test loop
  Timer swingTimer = new Timer(DELAY, new ActionListener(){

    @Override
    public void actionPerformed(ActionEvent e){
        timerAction();
        }
    });
  
  
  public TargetTest(){
    //Creates layouts
    
    //Main layout, contains target lables  
    GridLayout targetLayout = new GridLayout(11,10);
    mainPanel.setLayout(targetLayout);
    mainPanel.setPreferredSize(new Dimension(800, 880));
    mainPanel.setBackground(Color.white);
    targetLayout.setHgap(35);
    targetLayout.setVgap(35);

    //Secondary layout, unused, would be useful should the customer decide they 
    //want extra features added such as a start button
    GridLayout controlsLayout = new GridLayout(0,1);
    controlsPanel.setLayout(controlsLayout);
    controlsPanel.setPreferredSize(new Dimension(100, 100));
    controlsPanel.setBackground(Color.white);

    controlsPanel.add(startRound);

    //For loop, handles the creation of all labels
    for (int i = 0; i < labelArray.length; i++){

        labelArray[i] = new JLabel(); // create a JLabel with no text

        labelArray[i].setOpaque(true); // make its background color visible

        labelArray[i].setIcon(iconSet.getGrayIcon()); //sets icon to empty gray
        
        labelArray[i].setName(Integer.toString(i)); //sets name to location in the array

        //adds mouse listener to each label, and only to the label
        //causes only clicks on a labels to be registered 
        labelArray[i].addMouseListener(new MouseAdapter() { 
          @Override
          public void mouseClicked(MouseEvent me) { 
            int clickedLabel = Integer.parseInt(me.getComponent().getName());
            System.out.println("Clicked"); 
            if (labelArray[clickedLabel].getIcon()==iconSet.getRedTargetIcon()){
            labelArray[clickedLabel].setIcon(iconSet.getGrayIcon());
            iconSet.addHitCounter();
            System.out.println(iconSet.getTargetHitsCounter());
            }
          } 
        }); 
        
        
        mainPanel.add(labelArray[i]); // add it to the JPanel
    }

    mainPanel.revalidate(); 

    
    swingTimer.setInitialDelay(4 * DELAY); //sets delay

    swingTimer.start();
    
    
    }
       
    //handles action taken on each timer click
    private void timerAction(){
        
        timerEnd();
        
        if (timerCounter == TEST_TARGET_APPERANCE + testTargetDelay){
            manageTestTarget();
        }
        
        if (timerCounter == TEST_TARGET_DISAPPERANCE + testTargetDelay){
            labelArray[TEST_TARGET_LOCATION].setIcon(iconSet.getGrayIcon());
            //System.out.println("Test End");
        }
        //creates clickable red target in random, non occupied location
        //and handles removal of unclicked, expired targets
        int randRedTarget = generator.nextInt(100);
        while(labelArray[randRedTarget].getIcon()!=iconSet.getGrayIcon()){
                //System.out.println("Already a target in place");
                randRedTarget = generator.nextInt(100);
            }
        labelArray[randRedTarget].setIcon(iconSet.getRedTargetIcon());
          
        if (timerCounter<ACTIVE_RED_TARGET_TOTAL){
            activeRedTarget[timerCounter] = randRedTarget;
        }
        
        if (timerCounter>=ACTIVE_RED_TARGET_TOTAL){
            if(labelArray[activeRedTarget[0]].getIcon()!=iconSet.getGrayIcon()){
                labelArray[activeRedTarget[0]].setIcon(iconSet.getGrayIcon());
            }
            for(int i = 0; i < activeRedTarget.length-1; i++){
                activeRedTarget[i] = activeRedTarget[i+1];
            }
            activeRedTarget[ACTIVE_RED_TARGET_TOTAL-1] = randRedTarget;
        }

        if (timerCounter%4==0){
            manageBlueTargets();
        }
        
        timerCounter++;
        
        if (timerCounter%4==0){
            manageGreenTargets();
        }
        
        System.out.println("Timer: " + timerCounter);      
    }
    //creates secret test target in set location at set time, delays appearence
    //if the location  is occupied
    private void manageTestTarget(){     
        if(labelArray[TEST_TARGET_LOCATION].getIcon()==iconSet.getGrayIcon()){
            labelArray[TEST_TARGET_LOCATION].setIcon(iconSet.getTestTargetIcon());
            System.out.println("Test Start");
        }        
        else{
            testTargetDelay++;
            System.out.println("DELAY:" + testTargetDelay);
        }
    }
    //creates unclickable blue distraction target in random, non occupied location
    private void manageBlueTargets(){
        int randBlueTarget = generator.nextInt(100);
        
        while(labelArray[randBlueTarget].getIcon()!=iconSet.getGrayIcon()){
            randBlueTarget = generator.nextInt(100);
        }        
        labelArray[randBlueTarget].setIcon(iconSet.getBlueTargetIcon());

        if (blueTargetCounter<ACTIVE_BLUE_TARGET_TOTAL){
            activeBlueTarget[blueTargetCounter] = randBlueTarget;
        }

        if (blueTargetCounter>=ACTIVE_BLUE_TARGET_TOTAL){
            if(labelArray[activeBlueTarget[0]].getIcon()!=iconSet.getGrayIcon()){
                labelArray[activeBlueTarget[0]].setIcon(iconSet.getGrayIcon());
            }
            for(int i = 0; i < activeBlueTarget.length-1; i++){
                activeBlueTarget[i] = activeBlueTarget[i+1];
            }
            activeBlueTarget[ACTIVE_BLUE_TARGET_TOTAL-1] = randBlueTarget;
        }
        
        blueTargetCounter++;
    }
    //creates unclickable green distraction target in random, non occupied location
    private void manageGreenTargets(){
        int randGreenTarget = generator.nextInt(100);
        
        while(labelArray[randGreenTarget].getIcon()!=iconSet.getGrayIcon()){
            randGreenTarget = generator.nextInt(100);
        }        
        labelArray[randGreenTarget].setIcon(iconSet.getGreenTargetIcon());

        if (greenTargetCounter<ACTIVE_GREEN_TARGET_TOTAL){
            activeGreenTarget[greenTargetCounter] = randGreenTarget;
        }

        if (greenTargetCounter>=ACTIVE_GREEN_TARGET_TOTAL){
            if(labelArray[activeGreenTarget[0]].getIcon()!=iconSet.getGrayIcon()){
                labelArray[activeGreenTarget[0]].setIcon(iconSet.getGrayIcon());
            }
            for(int i = 0; i < activeGreenTarget.length-1; i++){
                activeGreenTarget[i] = activeGreenTarget[i+1];
            }
            activeGreenTarget[ACTIVE_GREEN_TARGET_TOTAL-1] = randGreenTarget;
        }
        
        greenTargetCounter++;
    }
    
    //manages completion of end loop and end of test message and stat
    private void timerEnd(){
        if (timerCounter >= iconSet.getTotalTargetCounter()){
                System.out.println("end");
                swingTimer.stop();
                int totalTargetHits = 0;
                totalTargetHits = iconSet.getTargetHitsCounter();
                JOptionPane.showMessageDialog(null,"Thank you for participating in our study! \n You scored " + totalTargetHits + " targets.");
        }
    }
            
            
    public JPanel getMainPanel(){
        return mainPanel;
    }

    public JPanel getControlsPanel(){
    return controlsPanel;
    }

    //creates and shows the visual elements
    private static void createAndShowUI(){
        JFrame frame = new JFrame("TargetTest");
        TargetTest test = new TargetTest();
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(test.getMainPanel());
        //frame.getContentPane().add(test.getControlsPanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
}
    
    
    //main
    public static void main(String[] args){

        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run(){createAndShowUI();}
      });
    }
} 

