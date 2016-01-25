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
        // TODO code application logic here
        
        
        TestTool testTool = new TestTool();
        testTool.setSize(400,450);
        testTool.setLocation(870, 90);
        testTool.setVisible(true);
        
        
        Toolkit tk = Toolkit.getDefaultToolkit();
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
                        if(yCoord == 28){
                            num1 = jtf;
                            num1.setText(Integer.toString(TestTool.num1InputFile.get(num1Count)));
                            System.out.println("you got it!");
                            num1Count++;
                        }
                        if(yCoord == 108){
                            num2 = jtf;
                            num2.setText(Integer.toString(TestTool.num2InputFile.get(num2Count)));
                            num2Count++;
                        }
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
            public void printTextField(String text) {
                testTool.validationTextArea.append(text);
            }
            
            private String isValid(int num1, int num2, int sum){
                if(num1 + num2 != sum){
                    return "Failed!";
                }
                return "Passed!";
            }
            
            private void testNumbers(MouseEvent me) throws AWTException{
                JTextField jtf = (JTextField) me.getSource();
                int yCoord = jtf.getY(), xCoord = jtf.getX();
                if(yCoord == 28){
                    num1 = jtf;
                    num1.setText(String.valueOf(num1Count));
                    System.out.println(num1.getText());
                    num1Count++;
                }
                if(yCoord == 108){
                    num2 = jtf;
                    num2.setText(String.valueOf(num2Count));
                    System.out.println(num2.getText());
                    num2Count++;
                }
                if(yCoord == 179){
                    sum = jtf;
                }              
            } 
        };
        tk.addAWTEventListener(ml, AWTEvent.MOUSE_EVENT_MASK); 
    }
    public static void simulateMouseClick(int x,int y,int delay) throws AWTException{
        robot = new Robot();
        robot.mouseMove(x, y);
        robot.delay(delay);
        robot.mousePress(MouseEvent.BUTTON1_MASK);
        robot.mouseRelease(MouseEvent.BUTTON1_MASK);
        //System.out.println("It Worked!");
    }
    
    // robot clicks element1 inserts from x and element2 from y
    public static void robotAdd(int delay) throws AWTException{
        int guiXLoc = TestTool.getGuiXLoc();
        int guiYLoc = TestTool.getGuiYLoc();
        Lab2.simulateMouseClick(guiXLoc + 300, guiYLoc + 80, delay);
        Lab2.simulateMouseClick(guiXLoc + 300, guiYLoc + 160, delay);
        Lab2.simulateMouseClick(guiXLoc + 300, guiYLoc + 240, delay);
        Lab2.simulateMouseClick(guiXLoc + 290, guiYLoc + 360, delay);
    }
}

