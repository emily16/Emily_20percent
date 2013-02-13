/*So, what I want to do with this is make a square board that has bombs placed randomly through it
 * When a user selects a square there are 2 options.
 * 1. You die
 * 2. You don't die.
 * If you don't die, you see a number which shows the number of bombs adjacent to the square you picked.
 * It would be really cool if I could have the user imput the width and height they wanted to use...
 * So, I'm going to import basically everything in case I need it.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;
import java.awt.Color; //I CAN CHANGE THE BOARD'S COLOR!

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Minesweeper extends JPanel{
  int w; //width
  int h; //height
  JButton [][] buttons; //buttons so that you can click on things
  int [][] bombs; //placement of all the bombs in the field
  int numB; // number of bombs total
  int remaining; //nubmer of bombs remaining
  int difficulty;
  
  Minesweeper(){
    //This whole section took a really long time to research and write up, so here's hoping it works!
    w= Integer.parseInt(JOptionPane.showInputDialog("Pick a width...")); //So this should let the user input a desired dimension
    h= Integer.parseInt(JOptionPane.showInputDialog("Pick a height..."));
    difficulty= Integer.parseInt(JOptionPane.showInputDialog("Pick a difficulty 1-10")); //This will help decide how many bombs there should be (also user input)
    setPreferredSize(new Dimension(500,500));
    setLayout(new GridLayout(w,h));
    buttons = new JButton[w][h];
    for(int i=0; i<w; i++){
      for(int j=0; j<h; j++){ //making buttons
        buttons[i][j]= new JButton();
        buttons[i][j].addActionListener(new Listen(i, j));
        buttons[i][j].setText("");
        add(buttons[i][j]);
      }
    }
    this.init(); //this should go to the method that starts the whole thing up...
  }
  
  void init(){ //so this is the method that starts the whole thing...
    for(int i=0; i<w; i++){
      for(int j=0; j<h; j++){
        buttons[i][j].setBackground(Color.gray); //it makes a gray button (I even figured out how to change colors!)
        buttons[i][j].setText(""); //Makes the button blank on the front so that you can't see if it is a bomb or not.
      }
    }
    
    bombs = new int[w][h]; //makes a board of bombs
    numB = w*h*difficulty/40+1; //decided how many bombs to include based on difficulty level chosen
    left=w*h-numB; //how many bombs are remaining
    fillBombs(); //filling the board with teh correct bombs
    fillRest(); //filling the rest of the board
  }
  
  public void fillBombs(){ //filing the board with the bombs
    Random r = new Random();
    while(numB > 0){ 
      int x = r.nextInt(w);
      int y = r.nextInt(h);
      if (bombs [x][y]==0){
        bombs [x][y] = -1;
        numB--;
      }
    }
  }
  
  public void fillRest(){ //filling the rest of the board with empty squares
    for(int i=0; i<w; i++){
      for(int j=0; j<h; j++){
        if(bombs[i][j]!=-1){
          bombs[i][j]=numSurrounding(i,j);
        }
      }  
      