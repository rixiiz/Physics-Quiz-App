import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View extends JFrame{

    //attributes
    JButton startQuizButton = new JButton("Start quiz");
    private final JButton terminateButton = new JButton("Terminate");
    JButton nextButton = new JButton("Next");
    JButton startPracticeQuestionsButton = new JButton("Practice");
    private JLabel topicSelection = new JLabel("Select your topics: (atleast 3)");
    private JLabel preferredLength = new JLabel("Preferred length (mins): (need to select)");
    private JLabel quizOver = new JLabel("Quiz is over!");
    private JLabel title = new JLabel("Better Physics");
    private JLabel questionTextOnTopPage = new JLabel("Question");
    private JLabel graphAreaText = new JLabel("Overall progression graph:");
    private JLabel graphYAxisLabel = new JLabel("Score (%)");
    private JTextArea questionTextArea = new JTextArea("");
    private JTextArea resultTextArea = new JTextArea("0/0");
    private JTextArea topicsToFocusOn = new JTextArea("Focus On:\nNothing! You've mastered these topics!");
    String[] possibleQuizDurations = {"20", "40", "60"};
    JComboBox<String> quizDuration = new JComboBox(possibleQuizDurations);
    JRadioButton kinematics = new JRadioButton("Kinematics");
    JRadioButton forcesAndMomentum = new JRadioButton("Forces and Momentum");
    JRadioButton workPowerEnergy = new JRadioButton("Work, power, energy");
    JRadioButton idealGasModel = new JRadioButton("Ideal gas model");
    JRadioButton greenhouseEffect = new JRadioButton("Greenhouse effect");
    JRadioButton thermalDynamics = new JRadioButton("Thermaldynamics");
    JRadioButton rotationalMechanics = new JRadioButton("Rotational mechanics");
    JRadioButton thermalEnergyTransfer = new JRadioButton("Thermal energy transfer");
    JButton answerA = new JButton("A");
    JButton answerB = new JButton("B");
    JButton answerC = new JButton("C");
    JButton answerD = new JButton("D");
    JPanel homePage = new JPanel();
    JPanel questionPage = new JPanel();
    JPanel resultPage = new JPanel();


    //Constructor
    public View(){

        //initialise settings for the screen
        setTitle("Better physics");
        setLayout(null);
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //JPANELS
        homePage.setBounds(0, 0, 1440, 1024);
        homePage.setLayout(null);
        questionPage.setBounds(0, 0, 1440, 1024);
        questionPage.setLayout(null);
        JPanel topics = new JPanel();
        topics.setBounds(130, 200, 400, 520);
        topics.setLayout(null);
        topics.setBackground(Color.LIGHT_GRAY);
        JPanel questionArea = new JPanel();
        questionArea.setBounds(90, 100, 1250, 300);
        questionArea.setLayout(null);
        questionArea.setBackground(Color.LIGHT_GRAY);
        resultPage.setBounds(0, 0, 1440, 1024);
        resultPage.setLayout(null);

        JPanel graphArea = new JPanel();
        graphArea.setBounds(470, 70, 900, 400);
        graphArea.setLayout(null);
        graphArea.setBackground(Color.LIGHT_GRAY);


        //TEXTS AND LABELS
        title.setBounds(800, 50, 304, 222);
        title.setFont(new Font(null, Font.BOLD, 43));
        topicSelection.setBounds(140, 140, 350, 43);
        topicSelection.setFont(new Font(null, Font.BOLD, 24));
        preferredLength.setBounds(830, 450, 400, 50);
        preferredLength.setFont(new Font(null, Font.BOLD, 20));
        questionTextOnTopPage.setBounds(90, 50, 740, 40);
        questionTextOnTopPage.setFont(new Font(null, Font.BOLD, 30));
        questionTextOnTopPage.setBackground(Color.LIGHT_GRAY);
        questionTextOnTopPage.setOpaque(true);
        questionTextArea.setBounds(110, 115, 1210, 260);
        questionTextArea.setFont(new Font(null, Font.BOLD, 20));
        questionTextArea.setBackground(Color.LIGHT_GRAY);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setEditable(false);
        questionTextArea.setLineWrap(true);
        quizOver.setBounds(120, 100, 300, 200);
        quizOver.setFont(new Font(null, Font.BOLD, 45));
        resultTextArea.setBounds(120, 320, 300, 150);
        resultTextArea.setFont(new Font(null, Font.BOLD, 70));
        resultTextArea.setBackground(Color.LIGHT_GRAY);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setLineWrap(true);
        resultTextArea.setEditable(false);
        graphAreaText.setBounds(20, 15, 400, 30);
        graphAreaText.setFont(new Font(null, Font.BOLD, 25));
        graphYAxisLabel.setBounds(20, 160, 80, 50);
        graphYAxisLabel.setFont(new Font(null, Font.BOLD, 15));
        topicsToFocusOn.setBounds(120, 500, 800, 300);
        topicsToFocusOn.setFont(new Font(null, Font.BOLD, 25));
        topicsToFocusOn.setBackground(Color.LIGHT_GRAY);
        topicsToFocusOn.setWrapStyleWord(true);
        topicsToFocusOn.setLineWrap(true);
        topicsToFocusOn.setEditable(false);


        //BUTTONS
        startQuizButton.setBounds(830, 300, 250, 80);
        startQuizButton.setFont(new Font(null, Font.BOLD, 30));
        startQuizButton.setBackground(Color.GRAY);
        startQuizButton.setEnabled(false);
        answerA.setBounds(90, 420, 1250, 45);
        answerA.setBackground(Color.LIGHT_GRAY);
        answerA.setFont(new Font(null, Font.BOLD, 13));
        answerB.setBounds(90, 490, 1250, 45);
        answerB.setBackground(Color.LIGHT_GRAY);
        answerB.setFont(new Font(null, Font.BOLD, 13));
        answerC.setBounds(90, 560, 1250, 45);
        answerC.setBackground(Color.LIGHT_GRAY);
        answerC.setFont(new Font(null, Font.BOLD, 13));
        answerD.setBounds(90, 630, 1250, 45);
        answerD.setBackground(Color.LIGHT_GRAY);
        answerD.setFont(new Font(null, Font.BOLD, 13));
        terminateButton.setBounds(90, 700, 150, 70);
        terminateButton.setBackground(Color.LIGHT_GRAY);
        nextButton.setBounds(1190, 700, 150, 70);
        nextButton.setBackground(Color.LIGHT_GRAY);
        nextButton.setEnabled(false);
        startPracticeQuestionsButton.setBounds(1030, 600, 250, 100);
        startPracticeQuestionsButton.setBackground(Color.LIGHT_GRAY);
        startPracticeQuestionsButton.setFont(new Font(null, Font.BOLD, 30));


        //COMBOBOXES
        quizDuration.setBounds(830, 500, 150, 30);


        //'topics' PANEL
        kinematics.setBounds(20, 30, 170, 90);
        forcesAndMomentum.setBounds(210, 30, 170, 90);
        workPowerEnergy.setBounds(20, 150, 170, 90);
        idealGasModel.setBounds(210, 150, 170, 90);
        greenhouseEffect.setBounds(20, 270, 170, 90);
        thermalDynamics.setBounds(210, 270, 170, 90);
        rotationalMechanics.setBounds(20, 390, 170, 90);
        thermalEnergyTransfer.setBounds(210, 390, 170, 90);
        topics.add(kinematics);
        topics.add(forcesAndMomentum);
        topics.add(workPowerEnergy);
        topics.add(idealGasModel);
        topics.add(greenhouseEffect);
        topics.add(thermalDynamics);
        topics.add(rotationalMechanics);
        topics.add(thermalEnergyTransfer);


        //'graphArea' PANEL
        graphArea.add(graphAreaText);
        graphArea.add(graphYAxisLabel);


        //'questionArea' PANEL
        questionArea.add(questionTextOnTopPage);
        questionArea.add(questionTextArea);


        //HOME PAGE
        homePage.add(title);
        homePage.add(topicSelection);
        homePage.add(quizDuration);
        homePage.add(preferredLength);
        homePage.add(startQuizButton);
        homePage.add(topics);


        //QUESTION PAGE
        questionPage.add(questionArea);
        questionPage.setComponentZOrder(questionTextOnTopPage, 0);
        questionPage.add(answerA);
        questionPage.add(answerB);
        questionPage.add(answerC);
        questionPage.add(answerD);
        questionPage.add(questionTextArea);
        questionPage.setComponentZOrder(questionTextArea, 0);
        questionPage.add(terminateButton);
        questionPage.add(nextButton);


        //RESULT PAGE
        resultPage.add(quizOver);
        resultPage.add(resultTextArea);
        resultPage.add(graphArea);
        resultPage.add(topicsToFocusOn);
        resultPage.add(startPracticeQuestionsButton);


        //JFRAME
        add(homePage);
        add(questionPage);
        add(resultPage);
        //only the main page should be visible when the program first executes
        questionPage.setVisible(false);
        resultPage.setVisible(false);
    }


    //Methods

    //Button listeners
    public void addStartButtonListener(ActionListener listener) {startQuizButton.addActionListener(listener);}
    public void addNextButtonListener(ActionListener listener) {nextButton.addActionListener(listener);}
    public void addStartPracticeQuestionsButtonListener(ActionListener listener) {startPracticeQuestionsButton.addActionListener(listener);}
    public void addTerminateButtonListener(ActionListener listener) {terminateButton.addActionListener(listener);}
    public void addAnswerAButtonListener(ActionListener listener) {answerA.addActionListener(listener);}
    public void addAnswerBButtonListener(ActionListener listener) {answerB.addActionListener(listener);}
    public void addAnswerCButtonListener(ActionListener listener) {answerC.addActionListener(listener);}
    public void addAnswerDButtonListener(ActionListener listener) {answerD.addActionListener(listener);}
    public void addQuizDurationDropBoxListener(ActionListener listener) {quizDuration.addActionListener(listener);}
    public void addKinematicsListener(ActionListener listener) {kinematics.addActionListener(listener);}
    public void addForcesAndMomentumListener(ActionListener listener) {forcesAndMomentum.addActionListener(listener);}
    public void addWorkPowerEnergyListener(ActionListener listener) {workPowerEnergy.addActionListener(listener);}
    public void addRotationalMechanicsListener(ActionListener listener) {rotationalMechanics.addActionListener(listener);}
    public void addThermalEnergyTransfersListener(ActionListener listener) {thermalEnergyTransfer.addActionListener(listener);}
    public void addGreenhouseEffectListener(ActionListener listener) {greenhouseEffect.addActionListener(listener);}
    public void addIdealGasLawsListener(ActionListener listener) {idealGasModel.addActionListener(listener);}
    public void addThermodynamicsListener(ActionListener listener) {thermalDynamics.addActionListener(listener);}


    //display pages

    /**
     * only allows the home page to be shown on the screen
     */
    public void displayHomePage(){
        homePage.setVisible(true);
        questionPage.setVisible(false);
        resultPage.setVisible(false);
        startQuizButton.setEnabled(false);
    }

    /**
     * only allows the question page to be shown on the screen
     */
    public void displayQuestionPage(){
        nextButton.setEnabled(false);
        questionPage.setVisible(true);
        homePage.setVisible(false);
        resultPage.setVisible(false);
    }

    /**
     * only allows the result page to be shown on the screen
     */
    public void displayResultPage(){
        resultPage.setVisible(true);
        homePage.setVisible(false);
        questionPage.setVisible(false);
    }


    //Mutators
    public void setQuestionTextArea(String questionText){questionTextArea.setText(questionText);}
    public void setAnswerAText(String answerAText){answerA.setText(answerAText);}
    public void setAnswerBText(String answerBText){answerB.setText(answerBText);}
    public void setAnswerCText(String answerCText){answerC.setText(answerCText);}
    public void setAnswerDText(String answerDText){answerD.setText(answerDText);}
    public void setResultAreaText(String score){resultTextArea.setText(score);}
    public void setTopicsToFocusOnText(String focus){topicsToFocusOn.setText(focus);}

    //change button behaviors

    /**
     * change buttons behaviors when A is picked
     */
    public void answerChoiceAPicked(){
        answerA.setBackground(Color.green);
        answerB.setEnabled(false);
        answerC.setEnabled(false);
        answerD.setEnabled(false);
    }
    /**
     * change buttons behaviors when B is picked
     */
    public void answerChoiceBPicked(){
        answerB.setBackground(Color.green);
        answerA.setEnabled(false);
        answerC.setEnabled(false);
        answerD.setEnabled(false);
    }
    /**
     * change buttons behaviors when C is picked
     */
    public void answerChoiceCPicked(){
        answerC.setBackground(Color.green);
        answerB.setEnabled(false);
        answerA.setEnabled(false);
        answerD.setEnabled(false);
    }
    /**
     * change buttons behaviors when D is picked
     */
    public void answerChoiceDPicked(){
        answerD.setBackground(Color.green);
        answerB.setEnabled(false);
        answerC.setEnabled(false);
        answerA.setEnabled(false);
    }
}
