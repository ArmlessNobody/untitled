package com.MyFirstProgram;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


public class Main extends Application{



    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        int size_of = 4, size_fo = 4, size = 0;
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10,20,20,20));
        AtomicInteger score = new AtomicInteger();
        Label score_label = new Label("Число ходов : 0");
        score_label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 23));
        score_label.setPadding(new Insets(20,20,5,20));
        ArrayList points = new ArrayList();

        Random random = new Random();
        ArrayList<Button> labels = new ArrayList();


        while (points.size() != 15){
            int r = random.nextInt(16);
            if (points.indexOf(r) == -1 && r != 0){
                points.add(r);
              
            }
        }



        for (int i = 0; i < size_of; i++){
            for (int j = 0; j < size_fo; j++){

                if (size == 0){
                    Button label = new Button("");
                    label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                    label.setMinSize(100,100);
                    labels.add(label);

                    gridpane.add(labels.get(size), j, i);

                } else {
                    Button label = new Button("" + points.get(size-1));
                    label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                    label.setMinSize(100,100);
                    labels.add(label);
                    gridpane.add(labels.get(size), j, i);
                }
                size++;
            }
        }



        for (int i = 0; i < labels.size(); i++){
            int finalI = i;
            labels.get(i).setOnDragDetected((MouseEvent event) -> {

                Dragboard db = labels.get(finalI).startDragAndDrop(TransferMode.ANY);



                ClipboardContent content = new ClipboardContent();

                content.putString(labels.get(finalI).getText());

                db.setContent(content);

                event.consume();

            });
        }

        for (int i = 0; i < labels.size(); i++) {
            int finalI = i;
            labels.get(i).setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    int temp = -5;
                    for (int j = 0; j < labels.size(); j++){
                        if (event.getGestureSource() == labels.get(j)){
                            temp = j;
                        }
                    }
                    int temp_temp = -5;
                    for (int j = 0; j < labels.size(); j++){
                        if (labels.get(j).getText() == ""){
                            temp_temp = j;
                        }
                    }



                    if ((event.getGestureSource() != labels.get(finalI)) && (labels.get(finalI).getText() == "") && ((temp - temp_temp == 1) || (temp - temp_temp == -1) ||(temp - temp_temp == 4) || (temp - temp_temp == -4)))  {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
            });
        }


        for (int i = 0; i < labels.size(); i++) {
            int finalI = i;
            labels.get(i).setOnDragDropped((DragEvent event) -> {
                int temp = 0;
                Dragboard db = event.getDragboard();


                for (int j = 0; j < labels.size(); j++ ){
                    if (labels.get(j).getText() == db.getString()){
                        temp = j;
                        break;
                    }
                }



                labels.get(temp).setText("");

                labels.get(finalI).setText(db.getString());

                score.getAndIncrement();
                score_label.setText("Число ходов : "+score);


                String label_string = "";

                for (int j = 0; j < labels.size(); j++){
                    label_string = label_string + labels.get(j).getText();


                }

                if (label_string.equals("123456789101112131415")){

                    score_label.setText("Число ходов : 0");
                    Label label1 = new Label("Поздравляю, Вы прошли игру за " + score + " ходов!");
                    label1.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                    try {

                        FileWriter writer = new FileWriter("Stats.txt");

                        writer.write(String.valueOf(score));

                        writer.close();
                    } catch (IOException e) {
                        System.out.println("fafafa");
                        throw new RuntimeException(e);

                    }
                    score.set(0);
                    Scene scene = new Scene(label1);
                    Stage stage1 = new Stage();
                    stage1.setScene(scene);
                    stage1.initOwner(stage);
                    stage1.initModality(Modality.WINDOW_MODAL);
                    stage1.show();
                    points.clear();
                    labels.clear();
                    gridpane.getChildren().clear();

                    while (points.size() != 15){
                        int r_r = random.nextInt(16);
                        if (points.indexOf(r_r) == -1 && r_r != 0){
                            points.add(r_r);

                        }
                    }

                    int finalsize = 0;
                    for (int l = 0; l < size_of; l++){
                        for (int j = 0; j < size_fo; j++){

                            if (finalsize== 0){
                                Button label = new Button("");
                                label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                label.setMinSize(100,100);
                                labels.add(label);
                                gridpane.add(labels.get(finalsize), j, l);

                            } else {

                                Button label = new Button("" + points.get(finalsize-1));

                                label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                label.setMinSize(100,100);

                                labels.add(label);

                                gridpane.add(labels.get(finalsize), j, l);
                            }
                            finalsize++;
                        }
                    }

                }

                event.consume();
            });
        }


        Button new_game = new Button("Новая игра");
        new_game.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 25));
        new_game.setMinSize(250,10);
        Button save_game = new Button("Сохранить игру");
        save_game.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 25));
        save_game.setMinSize(250,10);
        Button load_game = new Button("Загрузить игру");
        load_game.setMinSize(250,10);
        load_game.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 25));
        load_game.setMinSize(250,10);
        Button results_game = new Button("Статистика");
        results_game.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 25));
        results_game.setMinSize(250,10);
        VBox.setMargin(save_game, new Insets(5, 20,10,20));
        VBox.setMargin(load_game,new Insets(5, 20,10,20));
        VBox.setMargin(results_game,new Insets(5, 20,10,20));
        VBox.setMargin(new_game, new Insets(20, 20,10,20));


        new_game.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event_) {
                int finalsize = 0;
                score.set(0);
                score_label.setText("Число ходов : 0");
                points.clear();
                labels.clear();
                gridpane.getChildren().clear();
                while (points.size() != 15){
                    int r_r = random.nextInt(16);
                    if (points.indexOf(r_r) == -1 && r_r != 0){
                        points.add(r_r);

                    }
                }


                for (int i = 0; i < size_of; i++){
                    for (int j = 0; j < size_fo; j++){

                        if (finalsize== 0){
                            Button label = new Button("");
                            label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                            label.setMinSize(100,100);
                            labels.add(label);
                            gridpane.add(labels.get(finalsize), j, i);

                        } else {

                            Button label = new Button("" + points.get(finalsize-1));

                            label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                            label.setMinSize(100,100);

                            labels.add(label);

                            gridpane.add(labels.get(finalsize), j, i);
                        }
                        finalsize++;
                    }
                }

                for (int i = 0; i < labels.size(); i++){
                    int finalI = i;
                    labels.get(i).setOnDragDetected((MouseEvent event) -> {

                        Dragboard db = labels.get(finalI).startDragAndDrop(TransferMode.ANY);



                        ClipboardContent content = new ClipboardContent();

                        content.putString(labels.get(finalI).getText());

                        db.setContent(content);

                        event.consume();

                    });
                }

                for (int i = 0; i < labels.size(); i++) {
                    int finalI = i;
                    labels.get(i).setOnDragOver(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            int temp = -5;
                            for (int j = 0; j < labels.size(); j++){
                                if (event.getGestureSource() == labels.get(j)){
                                    temp = j;
                                }
                            }
                            int temp_temp = -5;
                            for (int j = 0; j < labels.size(); j++){
                                if (labels.get(j).getText() == ""){
                                    temp_temp = j;
                                }
                            }



                            if ((event.getGestureSource() != labels.get(finalI)) && (labels.get(finalI).getText() == "") && ((temp - temp_temp == 1) || (temp - temp_temp == -1) ||(temp - temp_temp == 4) || (temp - temp_temp == -4)))  {
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                            }
                            event.consume();
                        }
                    });
                }


                for (int i = 0; i < labels.size(); i++) {
                    int finalI = i;
                    labels.get(i).setOnDragDropped((DragEvent event) -> {
                        int temp = 0;
                        Dragboard db = event.getDragboard();


                        for (int j = 0; j < labels.size(); j++ ){
                            if (labels.get(j).getText() == db.getString()){
                                temp = j;
                                break;
                            }
                        }



                        labels.get(temp).setText("");

                        labels.get(finalI).setText(db.getString());

                        score.getAndIncrement();
                        score_label.setText("Число ходов : "+score);


                        String label_string = "";

                        for (int j = 0; j < labels.size(); j++){
                            label_string = label_string + labels.get(j).getText();


                        }

                        if (label_string.equals("123456789101112131415")){

                            score_label.setText("Число ходов : 0");
                            Label label1 = new Label("Поздравляю, Вы прошли игру за " + score + " ходов!");
                            label1.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                            try {
                                FileWriter writer = new FileWriter("Stats.txt");
                                writer.write(String.valueOf(score));

                                writer.close();
                            } catch (IOException e) {
                                System.out.println("fafafa");
                                throw new RuntimeException(e);

                            }
                            score.set(0);
                            Scene scene = new Scene(label1);
                            Stage stage1 = new Stage();
                            stage1.setScene(scene);
                            stage1.initOwner(stage);
                            stage1.initModality(Modality.WINDOW_MODAL);
                            stage1.show();
                            points.clear();
                            labels.clear();
                            gridpane.getChildren().clear();

                            while (points.size() != 15){
                                int r_r = random.nextInt(16);
                                if (points.indexOf(r_r) == -1 && r_r != 0){
                                    points.add(r_r);

                                }
                            }

                            int finalsize_ = 0;
                            for (int l = 0; l < size_of; l++){
                                for (int j = 0; j < size_fo; j++){

                                    if (finalsize_== 0){
                                        Button label = new Button("");
                                        label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                        label.setMinSize(100,100);
                                        labels.add(label);
                                        gridpane.add(labels.get(finalsize_), j, l);

                                    } else {

                                        Button label = new Button("" + points.get(finalsize_-1));

                                        label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                        label.setMinSize(100,100);

                                        labels.add(label);

                                        gridpane.add(labels.get(finalsize_), j, l);
                                    }
                                    finalsize_++;
                                }
                            }

                        }

                        event.consume();
                    });
                }
            }
        });

        load_game.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event_) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(//
                        new FileChooser.ExtensionFilter("Текстовый файл", "*.txt"));
                File file = fileChooser.showOpenDialog(stage);

                try {
                    FileReader reader = new FileReader(file.getAbsolutePath());
                    Scanner scanner = new Scanner(reader);
                    String string = scanner.nextLine();
                    points.clear();
                    labels.clear();
                    gridpane.getChildren().clear();
                    int point_null = 0;
                    for (int i = 0; points.size() != 15; i++){
                        if (!string.split(" ")[i].equals("0")) {
                            points.add(string.split(" ")[i]);

                        } else {
                            point_null = i;
                            for (int j = i; points.size() != 15; j++) {
                                i++;

                                points.add(string.split(" ")[i]);

                            }
                            break;


                        }


                    }


                    int flag = 0;
                    int finalsize = 0;
                    for (int i = 0; i < size_of; i++){
                        for (int j = 0; j < size_fo; j++){

                            if (finalsize == point_null){
                                Button label = new Button("");
                                label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                label.setMinSize(100,100);
                                labels.add(label);
                                gridpane.add(labels.get(finalsize), j, i);
                                flag = 1;

                            } else {


                                if (flag == 0) {
                                    Button label = new Button("" + points.get(finalsize));
                                    label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                    label.setMinSize(100,100);

                                    labels.add(label);

                                    gridpane.add(labels.get(finalsize), j, i);
                                } else {
                                    Button label = new Button("" + points.get(finalsize-1));
                                    label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                    label.setMinSize(100,100);

                                    labels.add(label);

                                    gridpane.add(labels.get(finalsize), j, i);
                                }

                            }
                            finalsize++;
                        }
                    }
                    score.set(Integer.parseInt(string.split(" ")[string.split(" ").length - 1]));
                    score_label.setText("Число ходов: " + score);
                    for (int i = 0; i < labels.size(); i++){
                        int finalI = i;
                        labels.get(i).setOnDragDetected((MouseEvent event) -> {

                            Dragboard db = labels.get(finalI).startDragAndDrop(TransferMode.ANY);



                            ClipboardContent content = new ClipboardContent();

                            content.putString(labels.get(finalI).getText());

                            db.setContent(content);

                            event.consume();

                        });
                    }

                    for (int i = 0; i < labels.size(); i++) {
                        int finalI = i;
                        labels.get(i).setOnDragOver(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                int temp = -5;
                                for (int j = 0; j < labels.size(); j++){
                                    if (event.getGestureSource() == labels.get(j)){
                                        temp = j;
                                    }
                                }
                                int temp_temp = -5;
                                for (int j = 0; j < labels.size(); j++){
                                    if (labels.get(j).getText() == ""){
                                        temp_temp = j;
                                    }
                                }



                                if ((event.getGestureSource() != labels.get(finalI)) && (labels.get(finalI).getText() == "") && ((temp - temp_temp == 1) || (temp - temp_temp == -1) ||(temp - temp_temp == 4) || (temp - temp_temp == -4)))  {
                                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                                }
                                event.consume();
                            }
                        });
                    }


                    for (int i = 0; i < labels.size(); i++) {
                        int finalI = i;
                        labels.get(i).setOnDragDropped((DragEvent event) -> {
                            int temp = 0;
                            Dragboard db = event.getDragboard();


                            for (int j = 0; j < labels.size(); j++ ){
                                if (labels.get(j).getText() == db.getString()){
                                    temp = j;
                                    break;
                                }
                            }



                            labels.get(temp).setText("");

                            labels.get(finalI).setText(db.getString());

                            score.getAndIncrement();
                            score_label.setText("Число ходов : "+score);


                            String label_string = "";

                            for (int j = 0; j < labels.size(); j++){
                                label_string = label_string + labels.get(j).getText();


                            }

                            if (label_string.equals("123456789101112131415")){

                                score_label.setText("Число ходов : 0");
                                Label label1 = new Label("Поздравляю, Вы прошли игру за " + score + " ходов!");
                                label1.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));

                                try {
                                    FileReader reader1 = new FileReader("Stats.txt");
                                    Scanner scanner1 = new Scanner(reader1);
                                    String string2 = "";
                                    while (scanner1.hasNextLine()){
                                        string2 = string2 +  scanner1.nextLine() + "\n";
                                    }
                                    string2 = string2   + score;

                                    FileWriter writer = new FileWriter("Stats.txt");
                                    writer.write(String.valueOf(string2));

                                    writer.close();
                                } catch (IOException e) {
                                    System.out.println("fafafa");
                                    throw new RuntimeException(e);

                                }


                                score.set(0);
                                Scene scene = new Scene(label1);
                                Stage stage1 = new Stage();
                                stage1.setScene(scene);
                                stage1.initOwner(stage);
                                stage1.initModality(Modality.WINDOW_MODAL);
                                stage1.show();
                                points.clear();
                                labels.clear();
                                gridpane.getChildren().clear();

                                while (points.size() != 15){
                                    int r_r = random.nextInt(16);
                                    if (points.indexOf(r_r) == -1 && r_r != 0){
                                        points.add(r_r);

                                    }
                                }

                                int finalsize_ = 0;
                                for (int l = 0; l < size_of; l++){
                                    for (int j = 0; j < size_fo; j++){

                                        if (finalsize_== 0){
                                            Button label = new Button("");
                                            label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                            label.setMinSize(100,100);
                                            labels.add(label);
                                            gridpane.add(labels.get(finalsize_), j, l);

                                        } else {

                                            Button label = new Button("" + points.get(finalsize_-1));

                                            label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 30));
                                            label.setMinSize(100,100);

                                            labels.add(label);

                                            gridpane.add(labels.get(finalsize_), j, l);
                                        }
                                        finalsize_++;
                                    }
                                }

                            }

                            event_.consume();
                        });
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }


        });

        save_game.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(//
                        new FileChooser.ExtensionFilter("Текстовый файл", "*.txt"));
                File file = fileChooser.showOpenDialog(stage);

                try {
                    FileWriter nFile = new FileWriter(file.getAbsolutePath());
                    for (int i = 0; i < labels.size(); i++){
                        if (labels.get(i).getText() != ""){
                            nFile.write(labels.get(i).getText() + " ");
                        } else{
                            nFile.write( "0 ");
                        }

                    }
                    nFile.write(score_label.getText().split(" ")[score_label.getText().split(" ").length - 1]);
                    nFile.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        results_game.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Label label = new Label("Статистика");
                label.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 23));
                label.setPadding(new Insets(20,20,20,20));
                try {
                    FileReader reader = new FileReader("Stats.txt");
                    Scanner scanner = new Scanner(reader);
                    GridPane statpane= new GridPane();
                    statpane.setPadding(new Insets(0,20,20,20));
                    Label label_stats = new Label(" Попытка № " );
                    label_stats.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 23));
                    Label label_stats_2 = new Label(" Число шагов ");
                    label_stats_2.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 23));
                    statpane.add(label_stats, 0 ,0);
                    statpane.add(label_stats_2, 1,0);
                    System.out.println(scanner.hasNextLine());
                    while(scanner.hasNextLine()){

                        int z =  statpane.getRowCount();
                        Label label1 = new Label("  " +  z);
                        System.out.println(statpane.getRowCount() + 1);
                        label1.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 23));
                        statpane.add(label1, 0, statpane.getRowCount());

                        Label label2 = new Label("  " +  scanner.nextLine());
                        System.out.println(label2.getText());
                        label2.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, 23));
                        statpane.add(label2, 1, statpane.getRowCount() - 1);
                    }




                    statpane.setGridLinesVisible(true);
                    VBox vBox2 = new VBox(label, statpane);
                    Scene scene = new Scene(vBox2);
                    Stage stage1 = new Stage();
                    stage1.setScene(scene);
                    stage1.initOwner(stage);
                    stage1.initModality(Modality.WINDOW_MODAL);
                    stage1.show();

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }












            }
        });


        VBox group = new VBox(score_label, new_game, load_game, save_game, results_game );
        HBox group1 = new HBox(gridpane, group);
        Scene scene = new Scene(group1);
        stage.setScene(scene);
        stage.setTitle("JavaFX Application");
        stage.show();
    }
}