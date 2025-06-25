import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountdownTimer {
    //attributes
    private View view;
    private Model model;
    private DrawGraph drawGraph;
    private int minutes = 0;
    private int seconds = 0;
    Timer timer;
    JLabel timerLabel = new JLabel("");


    //constructor
    public CountdownTimer(Model model, View view, DrawGraph drawGraph){
        this.timerLabel = timerLabel;
        this.model = model;
        this.view = view;
        this.drawGraph = drawGraph;
        timerLabel.setBounds(900, 53, 500, 30);
    }


    //methods

    /**
     * This starts the timer
     */
    public void startTimer(){
        minutes = model.durationInMinutes; //total number of minutes
        int totalSeconds = minutes*60 + seconds + 1; //total number of seconds
        timer = new Timer(1000, new ActionListener() {
            int count = totalSeconds;
            @Override
            public void actionPerformed(ActionEvent e) {
                count--;
                if(count >= 0){ //if the timer is still running
                    int remainingMinutes = count/60;
                    int remainingSeconds = count%60;
                    timerLabel.setText(String.format("Time Remaining: %02d:%02d", remainingMinutes, remainingSeconds)); //formatting
                }
                else{
                    timer.stop();

                    //update result page if time runs out
                    int scoreToAdd = (int)(((double)model.correctAnswers / (double)model.totalNumberOfQuestions) * 100);
                    model.scores.add(scoreToAdd);
                    drawGraph.showGraph(model.scores, view);
                    view.setTopicsToFocusOnText("You ran out of time!");
                    view.displayResultPage();
                }
            }
        });
        timer.start();
    }
}
