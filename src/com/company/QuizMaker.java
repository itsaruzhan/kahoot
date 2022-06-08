package com.company;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static com.company.Quiz.loadFromFile;


public class QuizMaker extends Application {
    static Questions_Ui[] questionUis;
    static int current = 0;
    static Scene scene;
    static VBox vBox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        AtomicReference<String> path = new AtomicReference<>("");

        Button button = new Button("Choose a File");
        Image img = new Image(new FileInputStream("asset/img/background.jpg"));
        ImageView back = new ImageView(img);
        back.setFitHeight(650);
        back.setFitWidth(835);
        StackPane pane = new StackPane();
        pane.setPrefSize(835,650);
        pane.getChildren().add(button);
        pane.setAlignment(button,Pos.CENTER);
        vBox = new VBox(pane);
        vBox.setPrefSize(835,650);
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        pane.setBackground(bGround);
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            path.set(selectedFile.getAbsolutePath());
            vBox.getChildren().clear();
            if(!path.get().isEmpty()){

                System.out.println(path.get());
                Quiz quiz = null;

                try{
                    quiz = loadFromFile(path.get());
                    questionUis = new Questions_Ui[quiz.getQuestions().size()];
                    for (int i = 0; i < quiz.getQuestions().size(); i++) {
                        if (quiz != null) {
                            questionUis[i] = new Questions_Ui(quiz, quiz.getQuestions().get(i),i==quiz.getQuestions().size()-1, i);
                        }
                    }

                    vBox.getChildren().add(questionUis[0]);
                    questionUis[0].music(true);
                }
                catch(InvalidQuizFormatException ex) {
                    ex.printStackTrace();}

                catch(IOException ex){
                    ex.printStackTrace();
                }

            }
        });

        scene = new Scene(vBox ,835, 650);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Questions_Ui extends BorderPane {
    boolean isCorrect;
    Label time;
    static Media media = new Media(new File("asset/kahoot_music.mp3").toURI().toString());
    static MediaPlayer mediaPlayer = new MediaPlayer(media);
    MediaView mediaView = new MediaView(mediaPlayer);
    public Questions_Ui(Quiz quiz, Question question, boolean last, int ind) {
        setWidth(900); //set width of the BorderPane
        setHeight(600);
        Button next = new Button(">");
        Button previous = new Button("<");
        setLeft(previous);
        setRight(next);
        next.setFont(Font.font("arial", FontWeight.BOLD,22));
        previous.setFont(Font.font("arial",FontWeight.BOLD,22));

        getChildren().add(mediaView);
        previous.setVisible(ind != 0);

        AtomicReference<String> answer = new AtomicReference<>("");

        Label description = new Label();
        description.setFont(Font.font("arial",FontWeight.BOLD,15));

        ImageView k = new ImageView(new Image(new File("asset/img/k.png").toURI().toString()));

        k.setFitHeight(35);
        k.setFitWidth(55);
        HBox quesHbox = new HBox(10,k,description);
        quesHbox.setAlignment(Pos.CENTER);
        time = curTime(); //start timer
        VBox quesVbox = new VBox(50,quesHbox,time);

        setTop(quesVbox);
        quesVbox.setAlignment(Pos.CENTER);
        quesVbox.setPadding(new Insets(20));
        if (question instanceof Test) {
            k.setVisible(false);
            description.setText((ind+1) +". "+ question.getDescription());
            Test test = (Test) question;
            ToggleGroup toggleGroup = new ToggleGroup();
            RadioButton r1 = new RadioButton(test.getOptionAt(0));
            RadioButton r2 = new RadioButton(test.getOptionAt(1));
            RadioButton r3 = new RadioButton(test.getOptionAt(2));
            RadioButton r4 = new RadioButton(test.getOptionAt(3));

            ImageView testImg = new ImageView(new Image(new File("asset/img/logo.png").toURI().toString()));
            testImg.setFitWidth(450);
            testImg.setFitHeight(250);


            r1.setTextFill(Color.rgb(255,255,255));
            r2.setTextFill(Color.rgb(255,255,255));
            r3.setTextFill(Color.rgb(255,255,255));
            r4.setTextFill(Color.rgb(255,255,255));

            r1.setPadding(new Insets(10));
            r2.setPadding(new Insets(10));
            r3.setPadding(new Insets(10));
            r4.setPadding(new Insets(10));

            r1.setFont(Font.font("arial",FontWeight.BOLD,15));
            r2.setFont(Font.font("arial",FontWeight.BOLD,15));
            r3.setFont(Font.font("arial",FontWeight.BOLD,15));
            r4.setFont(Font.font("arial",FontWeight.BOLD,15));

            Rectangle rec1 = new Rectangle();
            Rectangle rec2 = new Rectangle();
            Rectangle rec3 = new Rectangle();
            Rectangle rec4 = new Rectangle();

            rec1.setWidth(400);
            rec2.setWidth(400);
            rec3.setWidth(400);
            rec4.setWidth(400);

            rec1.setHeight(100);
            rec2.setHeight(100);
            rec3.setHeight(100);
            rec4.setHeight(100);

            rec1.setFill(Color.rgb(237, 34, 12));
            rec2.setFill(Color.rgb(212, 168, 8));
            rec3.setFill(Color.rgb(7, 62, 181));
            rec4.setFill(Color.rgb(6, 105, 6));

            r1.setToggleGroup(toggleGroup);
            r2.setToggleGroup(toggleGroup);
            r3.setToggleGroup(toggleGroup);
            r4.setToggleGroup(toggleGroup);
            toggleGroup.selectedToggleProperty(
            ).addListener(event -> {
                answer.set(((RadioButton)toggleGroup.getSelectedToggle()).getText());
            });

            GridPane gridPane = new GridPane();
            gridPane.setHgap(5);
            gridPane.setVgap(5);
            gridPane.add(rec1, 0,0);
            gridPane.add(r1, 0,0);

            gridPane.add(rec2, 0,1);
            gridPane.add(r2, 0,1);

            gridPane.add(rec3, 1,0);
            gridPane.add(r3, 1,0);

            gridPane.add(rec4, 1,1);
            gridPane.add(r4, 1,1);

            gridPane.setPrefSize(805,220);
            setBottom(gridPane);
            setCenter(testImg);
            gridPane.setPadding(new Insets(10));
            gridPane.setAlignment(Pos.BOTTOM_CENTER);
        }
        else if(question instanceof  FillIn){
            k.setVisible(true);
            description.setText((ind+1) +". "+ question.getDescription().replace("{blank}", "___________"));
            Image fillImg = new Image(new File("asset/img/fillin.png").toURI().toString());
            ImageView fillinIv = new ImageView(fillImg);
            fillinIv.setFitWidth(550);
            fillinIv.setFitHeight(350);

            Label fillinLabel = new Label("Type your answer here:");
            FillIn fillIn = (FillIn) question;
            TextField textField = new TextField(""); //for typing your answer
            textField.setMaxWidth(400);

            VBox textFieldVbox = new VBox(20,fillinLabel,textField);
            setCenter(fillinIv);
            setTop(quesVbox);
            textFieldVbox.setAlignment(Pos.CENTER);
            textFieldVbox.setPadding(new Insets(20));
            setBottom(textFieldVbox);

            textField.textProperty().addListener(event -> {
                answer.set(textField.getText());
            });
        }
        setPrefSize(835,650);

        previous.setOnMouseClicked(event -> {
            if(QuizMaker.current>0){
                QuizMaker.vBox.getChildren().clear();
                QuizMaker.scene.setRoot(QuizMaker.questionUis[--QuizMaker.current]);
            }
        });

        if(last){
            next.setText("✔");
        }
        next.setOnMouseClicked(event -> {
            isCorrect = quiz.check(question, QuizMaker.current,answer.get()); //check whether the answer correct or not
            if(QuizMaker.current<4) {
                QuizMaker.scene.setRoot(QuizMaker.questionUis[++QuizMaker.current]);
            }
            if(last){ // if we click next button on the last question UI it means we clear the Pane and add another information about submitted quiz
                getChildren().clear();
                music(false);

                Label label1 = new Label("Your Result:");
                label1.setFont(Font.font("arial",FontWeight.BOLD,25));

                Label percentLabel = new Label("");
                Label timeLabel = new Label("");

                Label correctAns = new Label("");
                percentLabel.setFont(Font.font("arial",20));
                correctAns.setFont(Font.font("arial",20));
                timeLabel.setFont(Font.font("arial",20));
                ImageView submitImg = new ImageView(new Image(new File("asset/img/result.png").toURI().toString()));
                submitImg.setFitHeight(250);
                submitImg.setFitWidth(350);
                Button showBut = new Button("Show Answers");
                Button closeBut = new Button("Close test");
                showBut.setFont(Font.font("arial",FontWeight.BOLD,20));
                closeBut.setFont(Font.font("arial",FontWeight.BOLD,20));
                closeBut.setStyle("-fx-background-color:rgb(237, 34, 12)");
                showBut.setStyle("-fx-background-color:rgb(7, 62, 181)");
                showBut.setPrefWidth(400);
                closeBut.setPrefWidth(400);
                showBut.setPrefHeight(80);
                closeBut.setPrefHeight(80);

                int count = 0;
                for (Questions_Ui que: QuizMaker.questionUis
                ) {
                    if(que.isCorrect){
                        count++;
                    }
                }
                correctAns.setText("Number of correct answers: " + count+"/"+ QuizMaker.questionUis.length);
                timeLabel.setText("Finished in " + time.getText());
                double percent = count * 100.0 / QuizMaker.questionUis.length;
                percentLabel.setText(percent+ "%");
                VBox submitVbox = new VBox(8);
                submitVbox.getChildren().addAll(label1,percentLabel, correctAns,timeLabel,showBut,closeBut,submitImg);
                submitVbox.setAlignment(Pos.CENTER);
                setCenter(submitVbox);

                closeBut.setOnMouseEntered(event1 -> {
                    QuizMaker.scene.setCursor(Cursor.HAND);
                });
                closeBut.setOnMouseExited(event1 -> {
                    QuizMaker.scene.setCursor(Cursor.DEFAULT);
                });

                closeBut.setOnMouseClicked(event1 -> {
                    Stage stage = (Stage) QuizMaker.scene.getWindow();
                    stage.close();
                });
            }

        });


    }

    public Label curTime(){
        Label times = new Label("00:00");
        AnimationTimer animationTimer = new AnimationTimer() {
            long time;
            long minute=0;

            @Override
            public void start() {
                time = System.currentTimeMillis();
                super.start();
            }

            @Override
            public void handle(long now) {
                long mil = System.currentTimeMillis() - time;
                long second = mil/1000;


                if(second==60){
                    minute++;
                    time = System.currentTimeMillis();


                }
                String sec = "";
                String min = "";

                if(second<10)
                    sec= "0"+second;
                if(second>=10)
                    sec= ""+second;
                if(minute<10)
                    min = "0"+minute;
                if(minute>=10)
                    min = ""+minute;

                times.setText(min+":"+sec);
            }
        };
        animationTimer.start();

        return times;

    }
    public static void music(Boolean on){
        if(on)
            mediaPlayer.play();
        else
            mediaPlayer.stop();
    }
}