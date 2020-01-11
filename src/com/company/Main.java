package com.company;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Scanner;

class GameSetting{
     public String diffculty() {
         Scanner myobj = new Scanner(System.in);
         System.out.println("Welcome to Minesweeper! Enjoy :)");
         System.out.println("Please choose the level you want to play");
         System.out.println("Press 1: Easy");
         System.out.println("Press 2: Medium");
         System.out.println("Press 3: Hard");
         String input = myobj.nextLine();
             if (input.equals("1")) {
                 return "Easy";
             } else if (input.equals("2")) {
                 return "Medium";
             }else if(input.equals("3")){
                 return "Hard";
             }
             else {
                 System.out.println("Sorry,this isn't an available option. Please Try again");
                 diffculty();
             }
         return "";
     }
     public HashMap<String,Integer> MatrixSetting(){
         HashMap<String,Integer>map=new HashMap<>();
         map.put("Easy",9);
         map.put("Medium",16);
         map.put("Hard",30);
         return map;
     }
}

class GUI extends JFrame implements ActionListener  {
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 200; //Width of the JFrame
    public static final int HEIGHT = 200; //Height of the JFrame

    GameSetting set = new GameSetting();
    int size = set.MatrixSetting().get(set.diffculty());
    char[][] board = new char[size][size];
    //Creating a matrix of buttons to make flexible layout
    public GUI() {
        super();
        setSize(WIDTH, HEIGHT);
        setTitle("                                                                                               " +
                "                                                " +
                "                                                                          Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Setting a layout
        setLayout(new GridLayout(size, size));
        Algorithm alg = new Algorithm();
        alg.GenerateGame(this.board);

    }

    JButton[][] buttons = new JButton[size][size];

    {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[0].length; col++) {
                JButton cells = new JButton();
                buttons[row][col] = cells;
                add(cells);
                cells.addActionListener(this);

            }
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Using getSource method to avoid multiple if statements and make it efficient
        //JButton myButton = (JButton) e.getSource();
        Algorithm alg=new Algorithm();

        for(int i=0;i<this.buttons.length;i++){
            for(int j=0;j<this.buttons[0].length;j++){
                JButton B=buttons[i][j];
                B.addMouseListener(new MouseAdapter() {
                    boolean check;
                    @Override
                    public void mouseClicked(MouseEvent m) {
                        super.mouseClicked(m);
                        if (m.getButton() == 3 && B.isEnabled()) {// if right click
                            if (!check) {
                                B.setText("F");
                                B.getModel().setPressed(false);
                                check = true;
                            } else if (check) {
                                B.setText("");
                                B.getModel().setPressed(false);
                                check = false;
                            }
                             B.setEnabled(true);
                        }
                    }
                });
                if(this.buttons[i][j]==e.getSource()){
                    int[]click=new int[2];
                    click[0]=i;
                    click[1]=j;
                    alg.updateBoard(this.board,click);
                    this.buttons[i][j].setEnabled(false);
                    break;
                }
            }
        }
        boolean[][]clicked=new boolean[size][size];
        int finished=0;
        for(int k=0;k<this.buttons.length;k++){
            for(int l=0;l<this.buttons[0].length;l++){
                if(this.board[k][l]!='E'&&this.board[k][l]!='M'&&this.board[k][l]!='B') {
                    this.buttons[k][l].setText("" + board[k][l]);
                    this.buttons[k][l].setEnabled(false);
                    clicked[k][l]=true;
                    finished++;
                }
                if(this.board[k][l]=='B') {
                    this.buttons[k][l].setEnabled(false);
                    clicked[k][l]=true;
                    finished++;
                }
                if(this.board[k][l]=='X'){
                    setTitle("                                                                                               " +
                            "                                                " +
                            "                                                                          Boom!!!");
                    break;
                }
                if(this.buttons[k][l].isEnabled()&&this.buttons[k][l].getText().equals("F")){
                    if(board[k][l]=='M') {
                        clicked[k][l] = true;
                        finished++;
                    }
                }
                else if(this.buttons[k][l].isEnabled()&&!this.buttons[k][l].getText().equals("F")){
                    if(clicked[k][l]==true) {
                        clicked[k][l] = false;
                        finished--;
                    }
                }
            }
        }
            if(finished==size*size){
                setTitle("                                                                                               " +
                        "                                                " +
                        "                                                                          Congratulation!");
            }
    }
}

public class Main {

    public static void main(String[] args) {
        // write your code here
        GUI gui=new GUI();
        //gui.pack();
        gui.setVisible(true);
    }
}

