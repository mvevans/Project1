package dev.mvevans.project1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Project1 extends Activity implements View.OnClickListener {

    int numMoves;
    boolean done;
    enum player{Blank,X,O};

    int n = 3;
    player[][] grid = new player[n][n];
    Button[][] buttons = new Button[n][n];
    Button nButton;
    TextView winnerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project1);

        winnerText = (TextView)findViewById(R.id.winTextView);
        nButton = (Button)findViewById(R.id.buttonRestart);

        buttons[0][0] = (Button)findViewById(R.id.button00);
        buttons[0][1] = (Button)findViewById(R.id.button01);
        buttons[0][2] = (Button)findViewById(R.id.button02);

        buttons[1][0] = (Button)findViewById(R.id.button10);
        buttons[1][1] = (Button)findViewById(R.id.button11);
        buttons[1][2] = (Button)findViewById(R.id.button12);

        buttons[2][0] = (Button)findViewById(R.id.button20);
        buttons[2][1] = (Button)findViewById(R.id.button21);
        buttons[2][2] = (Button)findViewById(R.id.button22);

        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                buttons[i][j].setOnClickListener(this);
                grid[i][j] = player.Blank;
            }
        }

        done = false;
        numMoves = 0;
        nButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(!done) {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (view.equals(buttons[i][j])) {
                        if (grid[i][j] == player.Blank) {
                            player curPlayer;
                            switch (numMoves % 2) {
                                case 0:                         //x always starts
                                    curPlayer = player.X;
                                    buttons[i][j].setText("X");
                                    break;
                                case 1:
                                    curPlayer = player.O;
                                    buttons[i][j].setText("O");
                                    break;
                                default:
                                    curPlayer = player.X;
                                    buttons[i][j].setText("X");
                                    break;
                            }
                            grid[i][j] = curPlayer;
                            checkMove(i, j, curPlayer);
                            numMoves++;
                            break;
                        }
                        break;
                    }
        }else{
            if(view.equals(nButton)){
                done = false;
                numMoves = 0;
                winnerText.setText("");
                nButton.setVisibility(View.INVISIBLE);
                nButton.setEnabled(false);
                for (int i=0; i<n; i++){
                    for (int j=0; j<n; j++){
                        buttons[i][j].setText("");
                        buttons[i][j].setTextColor(getResources().getColor(R.color.black));
                        grid[i][j] = player.Blank;
                    }
                }
            }
        }
    }

    void checkMove(int x, int y, player p){

        boolean noWinner = true;

        //columns
        for(int i = 0; i < n; i++){
            if(grid[x][i] != p) {
                break;
            }else{
                buttons[x][i].setTextColor(getResources().getColor(R.color.red));
            }
            if(i == n-1){
                noWinner = false;
                winnerText.setText(playerToString(p)+" Wins!");
            }
        }

        //rows
        if(noWinner) {
            for (int i = 0; i < n; i++) {
                if (grid[i][y] != p) {
                    break;
                }else{
                    buttons[i][y].setTextColor(getResources().getColor(R.color.red));
                }
                if (i == n - 1) {
                    winnerText.setText(playerToString(p)+" Wins!");
                    noWinner = false;
                }
            }
        }

        //diagonal
        if(noWinner) {
            if (x == y) {
                //we're on a diagonal
                for (int i = 0; i < n; i++) {
                    if (grid[i][i] != p){
                        break;
                    }else{
                        buttons[i][i].setTextColor(getResources().getColor(R.color.red));
                    }
                    if (i == n - 1) {
                        winnerText.setText(playerToString(p)+" Wins!");
                        noWinner = false;
                    }
                }
            }
        }

        //reverse diagonal
        if(noWinner) {
            for (int i = 0; i < n; i++) {
                if (grid[i][(n - 1) - i] != p) {
                    break;
                }else{
                    buttons[i][((n-1)-i)].setTextColor(getResources().getColor(R.color.red));
                }
                if (i == n - 1) {
                    winnerText.setText(playerToString(p)+" Wins!");
                    noWinner = false;
                }
            }
        }

        //tie
        if(noWinner) {
            if (numMoves == ((n*n)-1)) {
                winnerText.setText("Tie Game");
                done = true;
            }
        }

        //clear red text
        if(noWinner){
            for(int i = 0;i<n;i++){
                for(int j = 0;j<n;j++){
                    buttons[i][j].setTextColor(getResources().getColor(R.color.black));
                }
            }
        }else{
            done = true;
        }

        if(done){
            nButton.setVisibility(View.VISIBLE);
            nButton.setEnabled(true);
        }

    }

    String playerToString(player p){
        if(p == player.X){
            return "X";
        }else {
            return "O";
        }
    }
}
