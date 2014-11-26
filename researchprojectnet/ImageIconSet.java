package researchprojectnet;

/**
 *Manages retrieving the icons from folder and organizes them as a set
 * 
 * @author Kevin
 */

import javax.swing.ImageIcon;


public class ImageIconSet {
    
    private int totalTargetCounter;
    private int targetHitsCounter;
    private ImageIcon redTargetIcon;
    private ImageIcon blueTargetIcon;
    private ImageIcon greenTargetIcon;
    private ImageIcon grayIcon;
    private ImageIcon testTargetIcon;
    
    public ImageIconSet() {
    totalTargetCounter=0;
    redTargetIcon = createImageIcon("images/targetRed.jpg", "redTarget");
    blueTargetIcon = createImageIcon("images/targetBlue.jpg", "blueTarget");
    greenTargetIcon = createImageIcon("images/targetGreen.jpg", "greenTarget");
    grayIcon = createImageIcon("images/grayIcon1.jpg", "grayIcon");
    testTargetIcon = createImageIcon("images/testTarget.jpg", "testTarget");
    }
    
    public ImageIconSet(int tempTotalTarget) {
    totalTargetCounter = tempTotalTarget;
    redTargetIcon = createImageIcon("images/targetRed.jpg", "redTarget");
    blueTargetIcon = createImageIcon("images/targetBlue.jpg", "blueTarget");
    greenTargetIcon = createImageIcon("images/targetGreen.jpg", "greenTarget");
    grayIcon = createImageIcon("images/grayIcon1.jpg", "grayIcon");
    testTargetIcon = createImageIcon("images/testTarget.jpg", "testTarget");
    }
    
    public int getTotalTargetCounter(){
        return totalTargetCounter;
    }
    
    public int getTargetHitsCounter(){
        return targetHitsCounter;
    }
    
    public ImageIcon getRedTargetIcon(){
        return redTargetIcon;
    }
    
    public ImageIcon getBlueTargetIcon(){
        return blueTargetIcon;
    }
    
    public ImageIcon getGreenTargetIcon(){
        return greenTargetIcon;
    }
    
    public ImageIcon getGrayIcon(){
        return grayIcon;
    }
    
    public ImageIcon getTestTargetIcon(){
        return testTargetIcon;
    }
    
    public void addHitCounter(){
        targetHitsCounter++;
    }
    
    private ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}
}
