import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller {

    //attributes
    private Model model;
    private View view;
    private ExcelReader excelReader;
    private CountdownTimer countdownTimer;
    private DrawGraph drawGraph;


    //MAIN PROGRAM EXECUTION
    public static void main(String[] args){

        View view = new View();
        ExcelReader excelReader = new ExcelReader();
        Model model = new Model(excelReader);
        DrawGraph drawGraph = new DrawGraph(model.scores);
        CountdownTimer countDownTimer = new CountdownTimer(model, view, drawGraph);

        view.setVisible(true);

        new Controller(model, view, excelReader, countDownTimer, drawGraph);
    }


    //constructor
    public Controller(Model model, View view, ExcelReader excelReader, CountdownTimer countdownTimer, DrawGraph drawGraph){

        //construct the classes
        this.model = model;
        this.view = view;
        this.excelReader = excelReader;
        this.countdownTimer = countdownTimer;
        this.drawGraph = drawGraph;

        //construct the listeners
        this.view.addStartButtonListener(new startQuizButtonListener());
        this.view.addNextButtonListener(new nextButtonListener());
        this.view.addTerminateButtonListener(new terminateButtonListener());
        this.view.addStartPracticeQuestionsButtonListener(new startPracticeQuestionsListener());
        this.view.addAnswerAButtonListener(new answerAButtonListener());
        this.view.addAnswerBButtonListener(new answerBButtonListener());
        this.view.addAnswerCButtonListener(new answerCButtonListener());
        this.view.addAnswerDButtonListener(new answerDButtonListener());
        this.view.addQuizDurationDropBoxListener(new quizDurationDropBoxListener());
        this.view.addKinematicsListener(new kinematicsListener());
        this.view.addForcesAndMomentumListener(new forcesAndMomentumListener());
        this.view.addWorkPowerEnergyListener(new workPowerEnergyListener());
        this.view.addRotationalMechanicsListener(new rotationalMechanicsListener());
        this.view.addThermalEnergyTransfersListener(new thermalEnergyTransferListener());
        this.view.addGreenhouseEffectListener(new greenhouseEffectListener());
        this.view.addIdealGasLawsListener(new idealGasModelListener());
        this.view.addThermodynamicsListener(new thermalDynamicsListener());
    }


    //Classes for listener
    private class startQuizButtonListener implements ActionListener{

        /**
         * fully initialises and displays the question page with all the necessary components
         * @param e the event to be processed when the user clicks on the start button
         */
        @Override
        public void actionPerformed(ActionEvent e){

            //initialise the screen and variables
            view.displayQuestionPage();
            model.correctAnswers = 0;
            model.questionType = 0;
            countdownTimer.startTimer();
            String questionText;

            //retrieve question and answer texts
            questionText = model.questionProvider();
            String answerAText = model.retrieveAnswerAText();
            String answerBText = model.retrieveAnswerBText();
            String answerCText = model.retrieveAnswerCText();
            String answerDText = model.retrieveAnswerDText();

            //display texts retrieved on the screen
            view.setQuestionTextArea(questionText);
            view.setAnswerAText(answerAText);
            view.setAnswerBText(answerBText);
            view.setAnswerCText(answerCText);
            view.setAnswerDText(answerDText);

            //add the timer to the screen
            countdownTimer.timerLabel.setText("00:00");
            countdownTimer.timerLabel.setFont(new Font(null, Font.BOLD, 30));
            countdownTimer.timerLabel.setVisible(true);
            view.questionPage.add(countdownTimer.timerLabel);
        }
    }

    private class nextButtonListener implements ActionListener{
        /**
         * re-initialises the question page
         * determines whether the quiz is over
         * retrieves question and answer texts to display if the current program is in the question page
         * fully initialises and displays the result with all the necessary components if the current program is in the result page
         * @param e the event to be processed when the user clicks on the next button
         */
        @Override
        public void actionPerformed(ActionEvent e){

            //reset question page
            model.alreadyAnswered = false;
            view.answerA.setEnabled(true);
            view.answerA.setBackground(Color.LIGHT_GRAY);
            view.answerB.setEnabled(true);
            view.answerB.setBackground(Color.LIGHT_GRAY);
            view.answerC.setEnabled(true);
            view.answerC.setBackground(Color.LIGHT_GRAY);
            view.answerD.setEnabled(true);
            view.answerD.setBackground(Color.LIGHT_GRAY);

            //check if the quiz is over
            if(model.durationTrack >= model.durationInMinutes){

                //display the resultpage
                view.displayResultPage();

                //prepare texts to display
                view.setResultAreaText(String.valueOf(model.correctAnswers) + "/" + String.valueOf(model.totalNumberOfQuestions));
                String topicsToFocusOn = "Focus on:\n";
                model.updateUserScores();

                //putting the text on the screen
                ArrayList<String> topicPracticeRequired = model.checkWrongTopicOccurences();
                for(int i=0;i<topicPracticeRequired.size();i++){
                    topicsToFocusOn += topicPracticeRequired.get(i);
                    topicsToFocusOn += "\n";
                }
                view.setTopicsToFocusOnText(topicsToFocusOn);

                //putting graph on screen
                drawGraph.showGraph(model.scores, view);
            }

            //if the quiz is not over
            else{

                //prepare page and variables for the next question
                view.displayQuestionPage();
                model.totalNumberOfQuestions++;
                String questionText;

                //if the current type is quiz OR all the wrongly answered questions has been ansered again in the practice questions
                if(model.questionType == 0 || model.wrongQuestionCount == model.wrongAnswerRows.size())
                    questionText = model.questionProvider(); //retrieve random question
                else{
                    //feature to redo the question gotten wrong in practice questions again
                    model.questionRow = model.wrongAnswerRows.get(model.wrongQuestionCount);
                    questionText = excelReader.readExcel(model.SHEETNAME, model.questionRow, 0);
                    model.wrongQuestionCount++;
                }

                //retrieve answer texts
                String answerAText = model.retrieveAnswerAText();
                String answerBText = model.retrieveAnswerBText();
                String answerCText = model.retrieveAnswerCText();
                String answerDText = model.retrieveAnswerDText();

                //display the texts
                view.setQuestionTextArea(questionText);
                view.setAnswerAText(answerAText);
                view.setAnswerBText(answerBText);
                view.setAnswerCText(answerCText);
                view.setAnswerDText(answerDText);
                view.nextButton.setEnabled(false); //disable the button
            }
        }
    }

    private class terminateButtonListener implements ActionListener{
        /**
         * resets all the variables and brings the user back to the home page
         * @param e the event to be processed when the user clicks on the terminate button
         */
        @Override
        public void actionPerformed(ActionEvent e){

            //reset everything
            countdownTimer.timer.stop();
            model.durationTrack = 0;
            model.isDurationSelected = false;
            model.usedQuestions.clear();
            model.correctAnswers = 0;
            model.wrongQuestionCount = 0;
            model.wrongAnswerRows.clear();
            model.totalNumberOfQuestions = 1;
            model.areaToFocus.clear();
            model.topicIndex = 0;
            model.alreadyAnswered = false;
            view.answerA.setEnabled(true);
            view.answerA.setBackground(Color.LIGHT_GRAY);
            view.answerB.setEnabled(true);
            view.answerB.setBackground(Color.LIGHT_GRAY);
            view.answerC.setEnabled(true);
            view.answerC.setBackground(Color.LIGHT_GRAY);
            view.answerD.setEnabled(true);
            view.answerD.setBackground(Color.LIGHT_GRAY);

            //display homepage
            view.displayHomePage();
        }
    }

    private class startPracticeQuestionsListener implements ActionListener{
        /**
         * starts the practice questions
         * retrieves the question and answer texts from the excel file to display
         * @param e the event to be processed when the user clicks on the practice button
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == view.startPracticeQuestionsButton){

                //initalise and prepare variables
                model.questionType = 1;
                view.displayQuestionPage();

                //this exceeds the total number of minutes added from my question pool so make sure the practice questions will keep running
                model.durationInMinutes = 1000;

                //make sure the timer doesnt show and run
                countdownTimer.timer.stop();
                countdownTimer.timerLabel.setVisible(false);

                //checks if there are any questions the user has gotten wrong
                String questionText;
                if(model.wrongQuestionCount == model.wrongAnswerRows.size()){
                    //retrieve random question according to the topics the user has selected in the beginning
                    questionText = model.questionProvider();
                }
                else{
                    //redo the wrongly answered questions
                    model.questionRow = model.wrongAnswerRows.get(model.wrongQuestionCount);
                    questionText = excelReader.readExcel(model.SHEETNAME, model.questionRow, 0);
                    model.wrongQuestionCount++;
                }

                //retrieve answer text
                String answerAText = model.retrieveAnswerAText();
                String answerBText = model.retrieveAnswerBText();
                String answerCText = model.retrieveAnswerCText();
                String answerDText = model.retrieveAnswerDText();

                //display answer text
                view.setQuestionTextArea(questionText);
                view.setAnswerAText(answerAText);
                view.setAnswerBText(answerBText);
                view.setAnswerCText(answerCText);
                view.setAnswerDText(answerDText);
            }
        }
    }

    private class quizDurationDropBoxListener implements ActionListener{
        /**
         * listener to listen what the user selects from the drop box
         * checks if the necessary requirements are met for the user to start the quiz
         * @param e the event to be processed when the user hovers the drop box
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.quizDuration){
                String tempHolder = (String) view.quizDuration.getSelectedItem();
                model.durationInMinutes = Integer.parseInt(tempHolder);
                model.isDurationSelected = true;

                //enable the start quiz button if both topics AND a duration are selected
                if(model.isTopicsSelected == true) view.startQuizButton.setEnabled(true);
            }
        }
    }

    //METHOD LISTENERS FOR TOPICS
    private class kinematicsListener implements ActionListener{
        /**
         * manipulates what topics the user has selected
         * checks if the requirements are met for the user to start the quiz
         * @param e the event to be processed when the user checks this radiobutton
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.kinematics && view.kinematics.isSelected()){ //checks if this has already been selected
                if(!model.topics.contains("K")) model.topics.add("K"); //only add if the topics is not already in the list
                if(model.topics.size()>=3){ //if this condition is met to start the quiz
                    model.isTopicsSelected = true;

                    //enable the start quiz button if both topics AND a duration are selected
                    if(model.isDurationSelected == true) view.startQuizButton.setEnabled(true);
                }
            }
            else if(e.getSource() == view.kinematics && !view.kinematics.isSelected()){ //checks if this has been unselected
                if(model.topics.contains("K")) model.topics.remove("K");

                //disable the start qui button if the requirements arent met
                if(model.topics.size()<3){
                    model.isTopicsSelected = false;
                    view.startQuizButton.setEnabled(false);
                }
            }
        }
    }

    //ALL OF THE FOLLOWING 7 LISTENERS HAVE THE SAME LOGIC AND CODE AS THE ONE ABOVE
    private class forcesAndMomentumListener implements ActionListener{
        /**
         * manipulates what topics the user has selected
         * checks if the requirements are met for the user to start the quiz
         * @param e the event to be processed when the user checks this radiobutton
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.forcesAndMomentum && view.forcesAndMomentum.isSelected()){ //checks if this has already been selected
                if(!model.topics.contains("FAM")) model.topics.add("FAM");
                if(model.topics.size()>=3){
                    model.isTopicsSelected = true;
                    if(model.isDurationSelected == true) view.startQuizButton.setEnabled(true);
                }
            }
            else if(e.getSource() == view.forcesAndMomentum && !view.forcesAndMomentum.isSelected()){
                if(model.topics.contains("FAM")) model.topics.remove("FAM");
                if(model.topics.size()<3){
                    model.isTopicsSelected = false;
                    view.startQuizButton.setEnabled(false);
                }
            }
        }
    }

    private class workPowerEnergyListener implements ActionListener{
        /**
         * manipulates what topics the user has selected
         * checks if the requirements are met for the user to start the quiz
         * @param e the event to be processed when the user checks this radiobutton
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.workPowerEnergy && view.workPowerEnergy.isSelected()){ //checks if this has already been selected
                if(!model.topics.contains("WPE")) model.topics.add("WPE");
                if(model.topics.size()>=3){
                    model.isTopicsSelected = true;
                    if(model.isDurationSelected == true) view.startQuizButton.setEnabled(true);
                }
            }
            else if(e.getSource() == view.workPowerEnergy && !view.workPowerEnergy.isSelected()){
                if(model.topics.contains("WPE")) model.topics.remove("WPE");
                if(model.topics.size()<3){
                    model.isTopicsSelected = false;
                    view.startQuizButton.setEnabled(false);
                }
            }
        }
    }

    private class rotationalMechanicsListener implements ActionListener{
        /**
         * manipulates what topics the user has selected
         * checks if the requirements are met for the user to start the quiz
         * @param e the event to be processed when the user checks this radiobutton
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.rotationalMechanics && view.rotationalMechanics.isSelected()){ //checks if this has already been selected
                if(!model.topics.contains("RM")) model.topics.add("RM");
                if(model.topics.size()>=3){
                    model.isTopicsSelected = true;
                    if(model.isDurationSelected == true) view.startQuizButton.setEnabled(true);
                }
            }
            else if(e.getSource() == view.rotationalMechanics && !view.rotationalMechanics.isSelected()){
                if(model.topics.contains("RM")) model.topics.remove("RM");
                if(model.topics.size()<3){
                    model.isTopicsSelected = false;
                    view.startQuizButton.setEnabled(false);
                }
            }
        }
    }

    private class thermalEnergyTransferListener implements ActionListener{
        /**
         * manipulates what topics the user has selected
         * checks if the requirements are met for the user to start the quiz
         * @param e the event to be processed when the user checks this radiobutton
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.thermalEnergyTransfer && view.thermalEnergyTransfer.isSelected()){ //checks if this has already been selected
                if(!model.topics.contains("TET")) model.topics.add("TET");
                if(model.topics.size()>=3){
                    model.isTopicsSelected = true;
                    if(model.isDurationSelected == true) view.startQuizButton.setEnabled(true);
                }
            }
            else if(e.getSource() == view.thermalEnergyTransfer && !view.thermalEnergyTransfer.isSelected()){
                if(model.topics.contains("TET")) model.topics.remove("TET");
                if(model.topics.size()<3){
                    model.isTopicsSelected = false;
                    view.startQuizButton.setEnabled(false);
                }
            }
        }
    }

    private class greenhouseEffectListener implements ActionListener{
        /**
         * manipulates what topics the user has selected
         * checks if the requirements are met for the user to start the quiz
         * @param e the event to be processed when the user checks this radiobutton
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.greenhouseEffect && view.greenhouseEffect.isSelected()){ //checks if this has already been selected
                if(!model.topics.contains("GE")) model.topics.add("GE");
                if(model.topics.size()>=3){
                    model.isTopicsSelected = true;
                    if(model.isDurationSelected == true) view.startQuizButton.setEnabled(true);
                }
            }
            else if(e.getSource() == view.greenhouseEffect && !view.greenhouseEffect.isSelected()){
                if(model.topics.contains("GE")) model.topics.remove("GE");
                if(model.topics.size()<3){
                    model.isTopicsSelected = false;
                    view.startQuizButton.setEnabled(false);
                }
            }
        }
    }

    private class idealGasModelListener implements ActionListener {
        /**
         * manipulates what topics the user has selected
         * checks if the requirements are met for the user to start the quiz
         * @param e the event to be processed when the user checks this radiobutton
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view.idealGasModel && view.idealGasModel.isSelected()) { //checks if this has already been selected
                if (!model.topics.contains("IGM")) model.topics.add("IGM");
                if(model.topics.size()>=3){
                    model.isTopicsSelected = true;
                    if(model.isDurationSelected == true) view.startQuizButton.setEnabled(true);
                }
            }
            else if(e.getSource() == view.idealGasModel && !view.idealGasModel.isSelected()){
                if(model.topics.contains("IGM")) model.topics.remove("IGM");
                if(model.topics.size()<3){
                    model.isTopicsSelected = false;
                    view.startQuizButton.setEnabled(false);
                }
            }
        }
    }

    private class thermalDynamicsListener implements ActionListener{
        /**
         * manipulates what topics the user has selected
         * checks if the requirements are met for the user to start the quiz
         * @param e the event to be processed when the user checks this radiobutton
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.thermalDynamics && view.thermalDynamics.isSelected()){ //checks if this has already been selected
                if(!model.topics.contains("TD")) model.topics.add("TD");
                if(model.topics.size()>=3){
                    model.isTopicsSelected = true;
                    if(model.isDurationSelected == true) view.startQuizButton.setEnabled(true);
                }
            }
            else if(e.getSource() == view.thermalDynamics && !view.thermalDynamics.isSelected()){
                if(model.topics.contains("TD")) model.topics.remove("TD");
                if(model.topics.size()<3){
                    model.isTopicsSelected = false;
                    view.startQuizButton.setEnabled(false);
                }
            }
        }
    }

    //METHOD LISTENERS FOR ANSWER BUTTONS
    private class answerAButtonListener implements ActionListener{
        /**
         * checks the answer selected by the user with the correct answer
         * @param e the event to be processed when the user checks this button
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.answerA){ //checks the picked answer with the actual answer
                model.answerChoicePicked = "A";
                boolean isCorrectAnswer = model.checkAnswer();

                if(isCorrectAnswer){
                    view.answerChoiceAPicked();
                    view.nextButton.setEnabled(true);
                }
                else view.answerA.setBackground(Color.red);

                model.alreadyAnswered = true;
                view.answerA.setEnabled(false);
            }
        }
    }

    private class answerBButtonListener implements ActionListener{
        /**
         * checks the answer selected by the user with the correct answer
         * @param e the event to be processed when the user checks this button
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == view.answerB){ //checks the picked answer with the actual answer
                model.answerChoicePicked = "B";
                boolean isCorrectAnswer = model.checkAnswer();

                if(isCorrectAnswer){
                    view.answerChoiceBPicked();
                    view.nextButton.setEnabled(true);
                }
                else view.answerB.setBackground(Color.red);

                model.alreadyAnswered = true;
                view.answerB.setEnabled(false);
            }
        }
    }

    private class answerCButtonListener implements ActionListener{
        /**
         * checks the answer selected by the user with the correct answer
         * @param e the event to be processed when the user checks this button
         */
        @Override
        public void actionPerformed(ActionEvent e){ //checks the picked answer with the actual answer
            if(e.getSource() == view.answerC){
                model.answerChoicePicked = "C";
                boolean isCorrectAnswer = model.checkAnswer();

                if(isCorrectAnswer){
                    view.answerChoiceCPicked();
                    view.nextButton.setEnabled(true);
                }
                else view.answerC.setBackground(Color.red);

                model.alreadyAnswered = true;
                view.answerC.setEnabled(false);
            }
        }
    }

    private class answerDButtonListener implements ActionListener{
        /**
         * checks the answer selected by the user with the correct answer
         * @param e the event to be processed when the user checks this button
         */
        @Override
        public void actionPerformed(ActionEvent e){ //checks the picked answer with the actual answer
            if(e.getSource() == view.answerD){
                model.answerChoicePicked = "D";
                boolean isCorrectAnswer = model.checkAnswer();

                if(isCorrectAnswer){
                    view.answerChoiceDPicked();
                    view.nextButton.setEnabled(true);
                }
                else view.answerD.setBackground(Color.red);

                model.alreadyAnswered = true;
                view.answerD.setEnabled(false);
            }
        }
    }
}
