import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {


    //attributes
    ExcelReader excelReader = new ExcelReader();
    ArrayList<String> topics = new ArrayList<String>(); //stores the topics selected by the user in the homepage
    ArrayList<Integer> usedQuestions = new ArrayList<Integer>(); //stores the questions that the user has already answered
    ArrayList<Integer> wrongAnswerRows = new ArrayList<Integer>(); //stores the row of the questions from the excel file that the user ansered incorrectly
    ArrayList<String> areaToFocus = new ArrayList<>(); //stores the topics the user should focus on
    int questionRow; //row of the question from the excel file that will be retrieved by the excel reader
    int correctAnswers = 0;
    String answerChoicePicked = ""; //stores the string of the answer that the user has picked
    boolean alreadyAnswered = false; //value to check if the user has already answered a question
    int durationInMinutes; //length of the quiz
    int durationTrack = 0; //length in the current program execution
    int topicIndex = 0; //iterator used to retrieve question from the excel file
    boolean isTopicsSelected = false; //value to check if at least 3 topics are selected
    boolean isDurationSelected = false; //value to check if a quiz duration is selected
    int totalNumberOfQuestions = 1;
    int wrongQuestionCount = 0; //a counter to check if all the initial wrongly answered questions has been answered during practice questions / also used as index of rows of the wrongly answered questions
    byte questionType = 0; //0 is quiz, 1 is practice questions
    final String SHEETNAME = "Feuil1";
    List<Integer> scores = new ArrayList<Integer>(){{
        add(0); //initialises the origin point of the graph
    }};


    //constructor
    Model(ExcelReader excelReader){
        excelReader = this.excelReader;
    }


    //methods

    //retrieve texts of the answer choices
    /**
     * gets the texts of answer A from excel file
     * @return the text retrieved
     */
    public String retrieveAnswerAText(){
        return excelReader.readExcel(SHEETNAME, questionRow, 2);
    }

    /**
     * gets the texts of answer B from excel file
     * @return the text retrieved
     */
    public String retrieveAnswerBText(){
        return excelReader.readExcel(SHEETNAME, questionRow, 3);
    }

    /**
     * gets the texts of answer C from excel file
     * @return the text retrieved
     */
    public String retrieveAnswerCText(){
        return excelReader.readExcel(SHEETNAME, questionRow, 4);
    }

    /**
     * gets the texts of answer D from excel file
     * @return the text retrieved
     */
    public String retrieveAnswerDText(){
        return excelReader.readExcel(SHEETNAME, questionRow, 5);
    }


    /**
     * checks user's picked answer with the actual answer
     * @param view an instance of the class 'view' in order to modify it
     */
    public boolean checkAnswer(){
        String modelAnswer = excelReader.readExcel(SHEETNAME, questionRow, 6); //retrieves the string of the correct answer
        if(modelAnswer.equals(answerChoicePicked)){
            if(alreadyAnswered == false) correctAnswers++; //checks if the user gets correct first try
            else{
                if(questionType == 0){ //if the current question type is quiz
                    wrongAnswerRows.add(questionRow);
                    areaToFocus.add(excelReader.readExcel(SHEETNAME, questionRow, 1));
                }
            }
            return true;
        }
        return false;
    }

    /**
     * gets the domain of the excel file according to the current topic
     * @return an arraylist with first element the lower bound and second element the upper bound
     */
    private ArrayList<Integer> getQuestionRowDomain(){
        ArrayList<Integer> domain = new ArrayList<>();
        int upperRowBound = 0;
        int lowerRowBound = 0;

        //check the current topic to decide on the domain of the "randomRow" variable
        //retrieve question text from that specific domain of rows from the excel file
        if(topics.get(topicIndex).equals("K")){
            lowerRowBound = 1;
            upperRowBound = 16;
        }
        else if(topics.get(topicIndex).equals("FAM")){
            lowerRowBound = 17;
            upperRowBound = 46;
        }
        else if(topics.get(topicIndex).equals("WPE")){
            lowerRowBound = 47;
            upperRowBound = 67;
        }
        else if(topics.get(topicIndex).equals("RM")){
            lowerRowBound = 68;
            upperRowBound = 75;
        }
        else if(topics.get(topicIndex).equals("TET")){
            lowerRowBound = 76;
            upperRowBound = 90;
        }
        else if(topics.get(topicIndex).equals("GE")){
            lowerRowBound = 91;
            upperRowBound = 101;
        }
        else if(topics.get(topicIndex).equals("IGM")){
            lowerRowBound = 102;
            upperRowBound = 113;
        }
        else if(topics.get(topicIndex).equals("TD")){
            lowerRowBound = 114;
            upperRowBound = 119;
        }
        domain.add(lowerRowBound);
        domain.add(upperRowBound);
        return domain;
    }

    /**
     * retrieves one question from each topic the user has selected
     * @return the string of the question from the excel file
     */
    //eg. topics = {"k", "fam", "wpe"} this function will take a question from "k", then from "fam", then from "wpe" to finally come back from "k" again
    public String questionProvider(){

        String tempTimeAddHolder; //temporarily stores the current length of the quiz
        if(topicIndex == topics.size()) topicIndex = 0; //restarts the cycle

        //set the domain of the rows
        int lowerRowBound = getQuestionRowDomain().get(0);
        int upperRowBound = getQuestionRowDomain().get(1);

        //retrieve question from excel file
        questionRow = lowerRowBound + (int)(Math.random() * ((upperRowBound - lowerRowBound) + 1));
        while(usedQuestions.contains(this.questionRow)){
            questionRow = lowerRowBound + (int)(Math.random() * ((upperRowBound - lowerRowBound) + 1));
        }
        usedQuestions.add(questionRow);

        //update
        topicIndex++;
        tempTimeAddHolder = excelReader.readExcel(SHEETNAME, questionRow, 7);
        durationTrack += Character.getNumericValue(tempTimeAddHolder.charAt(0));
        return excelReader.readExcel(SHEETNAME, questionRow, 0);
    }

    /**
     * generates the topics that need practice on
     * @return a list of all the topics that require practice
     */
    public ArrayList<String> checkWrongTopicOccurences(){

        ArrayList<String> topicPracticeRequired = new ArrayList<>(); //all the topics that require practice

        //checks the number of times the user has gotten questions of a certain topic wrong
        int kOccurences = Collections.frequency(areaToFocus, "Kinematics");
        int FAMOccurences = Collections.frequency(areaToFocus, "Forces and momentum");
        int WPEOccurences = Collections.frequency(areaToFocus, "Work, energy, power");
        int RMOccurences = Collections.frequency(areaToFocus, "Rotational mechanics");
        int TETOccurences = Collections.frequency(areaToFocus, "Thermal energy transfers");
        int GEOccurences = Collections.frequency(areaToFocus, "Greenhouse effect");
        int IDMOccurences = Collections.frequency(areaToFocus, "Ideal gas model");
        int TDOccurences = Collections.frequency(areaToFocus, "Thermodynamics");

        //determine the number of errors the user makes that qualify to be a topic to focus on
        int numberOfErrorsToCheck;
        if(durationInMinutes == 20) numberOfErrorsToCheck = 1;
        else if(durationInMinutes == 40) numberOfErrorsToCheck = 3;
        else numberOfErrorsToCheck = 4;
        if(kOccurences >= numberOfErrorsToCheck) topicPracticeRequired.add("Kinematics");
        if(FAMOccurences >= numberOfErrorsToCheck) topicPracticeRequired.add("Forces and momentum");
        if(WPEOccurences >= numberOfErrorsToCheck) topicPracticeRequired.add("Work, energy, power");
        if(RMOccurences >= numberOfErrorsToCheck) topicPracticeRequired.add("Rotational mechanics");
        if(TETOccurences >= numberOfErrorsToCheck) topicPracticeRequired.add("Thermal energy transfers");
        if(GEOccurences >= numberOfErrorsToCheck) topicPracticeRequired.add("Greenhouse effect");
        if(IDMOccurences >= numberOfErrorsToCheck) topicPracticeRequired.add("Ideal gas model");
        if(TDOccurences >= numberOfErrorsToCheck) topicPracticeRequired.add("Thermodynamics");
        return topicPracticeRequired;
    }

    /**
     * updates the scores the user got from all the quizzes
     */
    public void updateUserScores(){
        int scoreToAdd = (int)(((double) correctAnswers / (double) totalNumberOfQuestions) * 100);
        scores.add(scoreToAdd);
    }
}
