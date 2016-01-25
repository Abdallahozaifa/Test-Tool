/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2;

import gui.MyGUI;
import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author hea113
 */
public class Lab2 {
    private static Robot robot = null; 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException {
        // Testing tool created      
        TestTool testTool = new TestTool();
        testTool.setSize(400,450);
        testTool.setLocation(870, 90);
        testTool.setVisible(true);
        Toolkit tk = Toolkit.getDefaultToolkit();
        
        // event listener for the .Jar GUI application
        MyAWTEventListener ml = new MyAWTEventListener(){
            JTextField num1;
            JTextField num2;
            JTextField sum;
            JButton submitButton;
            int num1Count=0,num2Count=0;
    
            @Override
            public void eventDispatched(AWTEvent event) {
                MouseEvent me = (MouseEvent) event;
                //System.out.println(me.getX());
                if(me.getID() == MouseEvent.MOUSE_CLICKED){
                    try{
                        //testNumbers(me);
                        JTextField jtf = (JTextField) me.getSource();
                        int yCoord = jtf.getY(), xCoord = jtf.getX();
                        
                        // detects if num1 textbox is clicked
                        if(yCoord == 28){
                            num1 = jtf;
                            num1.setText(Integer.toString(TestTool.num1InputFile.get(num1Count)));
                            num1Count++;
                        }
                        //detects if num2 textbox is clicked
                        if(yCoord == 108){
                            num2 = jtf;
                            num2.setText(Integer.toString(TestTool.num2InputFile.get(num2Count)));
                            num2Count++;
                        }
                        // detects if the sum textbox is clicked
                        if(yCoord == 179){
                            sum = jtf;
                        }
                    }catch(ClassCastException cce){
                        try{
                            JButton jbt = (JButton) me.getSource();
                            submitButton = jbt;
                        }catch(ClassCastException cce2){
                            
                        }
                        // ok button is clicked
                        if(submitButton != null){
                            try{
                                //stores the numbers and prints them out to the textarea 
                                int num1Input = Integer.parseInt(num1.getText());
                                int num2Input = Integer.parseInt(num2.getText());
                                int sumInput = Integer.parseInt(sum.getText());
                                int diff = Math.abs((num1Input + num2Input) - sumInput);
                                String validity = isValid(num1Input,num2Input,sumInput); 
                                String textLine = "%s + %s = %s " + validity + " diff=" + diff +"\n";
                                String textAreaText = num1Input + " +" + num2Input + " = " + sumInput + "       "+ validity +"    diff=" + diff +"\n"; 
                                System.out.printf(textLine, num1.getText(),num2.getText(),sum.getText());
                                printTextField(textAreaText);
                            }catch(NullPointerException npe){
                                //System.out.println("cool");
                            }
                        }
                    }      
                }
            }
            // prints the text field to the text area
            public void printTextField(String text) {
                testTool.validationTextArea.append(text);
            }
            
            // determines weather the case is valid or not
            private String isValid(int num1, int num2, int sum){
                if(num1 + num2 != sum){
                    return "Failed!";
                }
                return "Passed!";
            } 
        };
        tk.addAWTEventListener(ml, AWTEvent.MOUSE_EVENT_MASK); 
    }
    
    //simulates a mouse click with the robot
    public static void simulateMouseClick(int x,int y,int delay) throws AWTException{
        robot = new Robot();
        robot.mouseMove(x, y);
        robot.delay(delay);
        robot.mousePress(MouseEvent.BUTTON1_MASK);
        robot.mouseRelease(MouseEvent.BUTTON1_MASK);
        //System.out.println("It Worked!");
    }
    
    // robot clicks all the textboxes then submits
    public static void robotAdd(int delay) throws AWTException{
        int guiXLoc = TestTool.getGuiXLoc();
        int guiYLoc = TestTool.getGuiYLoc();
        Lab2.simulateMouseClick(guiXLoc + 300, guiYLoc + 80, delay);
        Lab2.simulateMouseClick(guiXLoc + 300, guiYLoc + 160, delay);
        Lab2.simulateMouseClick(guiXLoc + 300, guiYLoc + 240, delay);
        Lab2.simulateMouseClick(guiXLoc + 290, guiYLoc + 360, delay);
    }
}

