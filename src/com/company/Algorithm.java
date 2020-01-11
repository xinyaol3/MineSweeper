package com.company;
import java.util.Random;
 class Algorithm {
     public char[][]GenerateGame(char[][]board){
         Random r = new Random();
         String element="";
         int mine_amount=board.length*board[0].length/9;
         int count=0;
                 for(int k=0;k<board.length;k++){
                     for(int l=0;l<board[0].length;l++){
                         element=""+element+"E";
                         if(count<mine_amount){
                             element=""+element+"M";
                         }
                         count++;
                     }
                 }
         for(int i=0;i<board.length;i++){
             for(int j=0;j<board[0].length;j++){
                 board[i][j]=element.charAt(r.nextInt(element.length()));
             }
         }
         return board;
     }
    public char[][] updateBoard(char[][] board, int[] click) {
        if(board[click[0]][click[1]]=='M'){
            board[click[0]][click[1]]='X';
            for(int i=0;i<board.length;i++){
                for(int j=0;j<board[0].length;j++){
                    if(board[i][j]=='M'){
                        board[i][j]='X';
                    }
                }
            }
            return board;
        }
        dfs(board,click[0],click[1]);
        return board;
    }
    public Character ElementReveal(char[][]board,int x, int y){
        int mine=0;
        if(x-1>=0){
            if(y-1>=0&&board[x-1][y-1]=='M'){
                mine++;
            }
            if(board[x-1][y]=='M'){
                mine++;
            }
            if(y+1<board[0].length&&board[x-1][y+1]=='M'){
                mine++;
            }
        }
        if(y-1>=0){
            if(board[x][y-1]=='M'){
                mine++;
            }
            if(x+1<board.length&&board[x+1][y-1]=='M'){
                mine++;
            }
        }
        if(y+1<board[0].length){
            if(board[x][y+1]=='M'){
                mine++;
            }
            if(x+1<board.length&&board[x+1][y+1]=='M'){
                mine++;
            }
        }
        if(x+1<board.length){
            if(board[x+1][y]=='M'){
                mine++;
            }
        }
        if(mine==0){
            return 'B';
        }
        String res=""+mine;
        return res.charAt(0);
    }
    public void dfs(char[][] board, int x, int y){
        Character start_char=ElementReveal(board,x,y);
        if(Character.isDigit(start_char)){
            board[x][y]=start_char;
        }
        if(start_char=='B'&&x-1>=0&&board[x-1][y]=='E'){
            board[x-1][y]=ElementReveal(board,x-1,y);
            dfs(board,x-1,y);
        }
        if(start_char=='B'&&y-1>=0&&board[x][y-1]=='E'){
            board[x][y-1]=ElementReveal(board,x,y-1);
            dfs(board,x,y-1);
        }
        if(start_char=='B'&&x+1<board.length&&board[x+1][y]=='E'){
            board[x+1][y]=ElementReveal(board,x+1,y);
            dfs(board,x+1,y);
        }
        if(start_char=='B'&&y+1<board[0].length&&board[x][y+1]=='E'){
            board[x][y+1]=ElementReveal(board,x,y+1);
            dfs(board,x,y+1);
        }
    }
}
