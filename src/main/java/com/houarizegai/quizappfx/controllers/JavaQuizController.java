package com.houarizegai.quizappfx.controllers;

import com.houarizegai.quizappfx.App;
import com.houarizegai.quizappfx.dao.db.QuestionDao;
import com.houarizegai.quizappfx.dao.vo.QuestionVo;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.jensd.fx.glyphs.emojione.EmojiOneView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaQuizController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private AnchorPane parent;
    
    @FXML
    private StackPane root;

    @FXML
    private AnchorPane homePane, testPane, finishedPane, resultPane;
    @FXML
    private Pane menuPane;

    public static JFXDialog aboutDialog;

    @FXML
    private EmojiOneView emoji;

    @FXML
    private ProgressIndicator progress;

    @FXML
    private Label titleLbl;

    @FXML
    private TextArea questionLbl;

    @FXML
    private JFXRadioButton response1, response2, response3, response4;

    private JFXSnackbar toastMsg;

    // Type of Test (Basic, OOP, ...)
    public static int choix;

    // This list contain the question
    private List<QuestionVo> questions;
    // Save instance (Number Question => Response)
    private Map<Integer, Integer> instanceQuestion;
    // Max number of question
    private static int MAX_NUMBER_QUESTION;
    // Index currant question
    private static int indexCurrantQuestion = 0;

    @FXML
    private Label scoreLbl, precentageScoreLbl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDrageable();
        toastMsg = new JFXSnackbar(homePane);
        try {
            AnchorPane aboutPane = FXMLLoader.load(getClass().getResource("/views/About.fxml"));
            aboutDialog = new JFXDialog(root, aboutPane, JFXDialog.DialogTransition.TOP);
        } catch (IOException ex) {
            Logger.getLogger(JavaQuizController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // main part
    private void makeStageDrageable() {
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            App.stage.setX(event.getScreenX() - xOffset);
            App.stage.setY(event.getScreenY() - yOffset);
            App.stage.setOpacity(0.7f);
            }
        });
        parent.setOnDragDone((e) -> {
            App.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((e) -> {
            App.stage.setOpacity(1.0f);
        });

    }

    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /* Start Home Action */
    @FXML
    private void btnBasic() {
        choix = 1;
        redirect();
    }

    @FXML
    private void btnOperator() {
        choix = 2;
        redirect();
    }

    @FXML
    private void btnTables() {
        choix = 3;
        redirect();
    }

    @FXML
    private void btnOOP() {
        choix = 4;
        redirect();
    }

    @FXML
    private void btnPoly() {
        choix = 5;
        redirect();
    }

    @FXML
    private void btnException() {
        choix = 6;
        redirect();
    }

    @FXML
    private void btnAdvanced() {
        choix = 7;
        redirect();
    }

    private void redirect() {
        // load question from database
        questions = new QuestionDao().getQuestion(choix);
        if (questions == null) {
            // back to home => connection failed
            toastMsg.show("Connection failed !", 4000);
            return;
        }

        switch (choix) {
            case 1: {
                titleLbl.setText("Java Basic");
            }
            break;
            case 2: {
                titleLbl.setText("Java Operator/Condition");
            }
            break;
            case 3: {
                titleLbl.setText("Java Tables/Loops");
            }
            break;
            case 4: {
                titleLbl.setText("Java OOP");
            }
            break;
            case 5: {
                titleLbl.setText("Java Polymorphisme/Enum");
            }
            break;
            case 6: {
                titleLbl.setText("Java Exception");
            }
            break;
            case 7: {
                titleLbl.setText("Java Advanced");
            }
            break;
        }

        MAX_NUMBER_QUESTION = questions.size();
        instanceQuestion = new HashMap<>();
        for (int i = 0; i < questions.size(); i++) {
            instanceQuestion.put(i, 0);
        }
        loadQuestion(0);

        homePane.setVisible(false);
        testPane.setVisible(true);
    }

    @FXML
    private void btnAbout() {
        if (aboutDialog.isVisible()) {
            return;
        }

        aboutDialog.show();
    }

    /* End Home Action */
    @FXML
    private void btnPrevious() {
        saveInstance();
        if (indexCurrantQuestion == 0) {
            return;
        }
        indexCurrantQuestion--;
        loadQuestion(indexCurrantQuestion);
    }

    @FXML
    private void btnNext() {
        saveInstance();
        if (indexCurrantQuestion == MAX_NUMBER_QUESTION - 1) {
            testPane.setVisible(false);
            finishedPane.setVisible(true);
            return;
        }
        indexCurrantQuestion++;
        loadQuestion(indexCurrantQuestion);

    }

    private void loadQuestion(int index) {
        progress.setProgress(indexCurrantQuestion / (float) MAX_NUMBER_QUESTION);

        questionLbl.setText(questions.get(index).getQuestion());
        response1.setText(questions.get(index).getResponse()[0]);
        response2.setText(questions.get(index).getResponse()[1]);
        response3.setText(questions.get(index).getResponse()[2]);
        response4.setText(questions.get(index).getResponse()[3]);

        switch (instanceQuestion.get(index)) {
            case 0: {
                response1.setSelected(false);
                response2.setSelected(false);
                response3.setSelected(false);
                response4.setSelected(false);
            }
            break;
            case 1: {
                response1.setSelected(true);
                response2.setSelected(false);
                response3.setSelected(false);
                response4.setSelected(false);
            }
            break;
            case 2: {
                response1.setSelected(false);
                response2.setSelected(true);
                response3.setSelected(false);
                response4.setSelected(false);
            }
            break;
            case 3: {
                response1.setSelected(false);
                response2.setSelected(false);
                response3.setSelected(true);
                response4.setSelected(false);
            }
            break;
            case 4: {
                response1.setSelected(false);
                response2.setSelected(false);
                response3.setSelected(false);
                response4.setSelected(true);
            }
            break;
        }

    }

    private void saveInstance() {
        if (response1.isSelected()) {
            instanceQuestion.replace(indexCurrantQuestion, 1);
        } else if (response2.isSelected()) {
            instanceQuestion.replace(indexCurrantQuestion, 2);
        } else if (response3.isSelected()) {
            instanceQuestion.replace(indexCurrantQuestion, 3);
        } else if (response4.isSelected()) {
            instanceQuestion.replace(indexCurrantQuestion, 4);
        }
    }

    @FXML
    private void radioClicked(ActionEvent e) {
        response1.setSelected(false);
        response2.setSelected(false);
        response3.setSelected(false);
        response4.setSelected(false);
        ((JFXRadioButton) e.getSource()).setSelected(true);
    }

    /* Start Finished & Result Part */
    @FXML
    void btnBackHome() {
        questions = null;
        indexCurrantQuestion = 0;
        testPane.setVisible(false);
        finishedPane.setVisible(false);
        resultPane.setVisible(false);
        homePane.setVisible(true);
    }

    @FXML
    void btnBackTest() {
        finishedPane.setVisible(false);
        testPane.setVisible(true);
    }

    @FXML
    private void btnViewResult() {
        int score = 0;
        for (int i = 0; i < MAX_NUMBER_QUESTION; i++) {
            int v = instanceQuestion.get(i);
            if (v == 0) {
                continue;
            }
            if (questions.get(i).getResponse()[v - 1].equals(questions.get(i).getSolution())) {
                score += 1;
            }
        }

        // Load result to resultPane
        scoreLbl.setText(score + "/" + MAX_NUMBER_QUESTION);
        if (score >= (MAX_NUMBER_QUESTION / 2.0)) {
            scoreLbl.setStyle(scoreLbl.getStyle() + "; -fx-text-fill: #0B0");
            //emoji.setGlyphName("GRINNING");
        } else {
            scoreLbl.setStyle(scoreLbl.getStyle() + "; -fx-text-fill: #E00");
            //emoji.setGlyphName("SLIGHT_FROWN");
        }

        precentageScoreLbl.setText(String.valueOf(score * 100.0 / MAX_NUMBER_QUESTION).concat("  ").substring(0, 5) + "%");

        finishedPane.setVisible(false);
        resultPane.setVisible(true);
    }

    /* End Finished & Result Part */
}
