package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    public Button pause;
    public Button resume;
    @FXML
    //Да, давай ебать требовать добавление ссаных тегов к каждому ебаному элементу
    private Label labelA;
    @FXML
    private Label labelB;
    @FXML
    private Label labelC;
    @FXML
    private Label labelD;
    @FXML
    private Label labelE;
    @FXML
    private Label labelF;
    @FXML
    private Label labelG;
    @FXML
    private Label labelH;


    @FXML
    private TextArea Atext;
    @FXML
    private TextArea Btext;
    @FXML
    private TextArea Ctext;
    @FXML
    private TextArea Dtext;
    @FXML
    private TextArea Etext;
    @FXML
    private TextArea Ftext;
    @FXML
    private TextArea Gtext;
    @FXML
    private TextArea Htext;


    private final List<RootThread>  threads = new ArrayList<>();

    private void exceptionHandler(RootThread... thread) {

        for (RootThread thr : thread) {
            try {
                thr.join();
            } catch (InterruptedException e) {
                thr.interrupt();
            }
        }


    }

    @FXML
    protected void onExecuteButtonAction() {                                // Полнейшая нефильтрованая дрисня, это худшее что я когда либо писал
        Atext.clear(); Btext.clear(); Ctext.clear();
        Dtext.clear(); Etext.clear(); Ftext.clear();
        Gtext.clear(); Htext.clear();
        RootThread threadA = new RootThread("A", Atext, labelA);                                            // Создаем потоки, привязываем в конструкторе к ним текстбокс
        RootThread threadB = new RootThread("B", Btext, labelB);
        RootThread threadC = new RootThread("C", Ctext, labelC);
        RootThread threadD = new RootThread("D", Dtext, labelD);
        RootThread threadE = new RootThread("E", Etext, labelE);
        RootThread threadF = new RootThread("F", Ftext, labelF);
        RootThread threadG = new RootThread("G", Gtext, labelG);
        RootThread threadH = new RootThread("H", Htext, labelH);
        threadA.setCount(0).setCountTarget(5).setCreate(() -> {
            threadD.start();
            threadC.start();
            threadE.start();
        });                            //Задаем отрезки для потоков и потоки которые они запустит перед тем как помереть
        threadB.setCount(0).setCountTarget(10).setCreate(() -> {
            exceptionHandler(threadE);
            threadG.start();
            threadF.start();
        });                 //В точках синхронизации потоки запускаются после joint()
        threadC.setCount(5).setCountTarget(15);
        threadD.setCount(5).setCountTarget(15).setCreate(() -> {
            exceptionHandler(threadC, threadG);
            threadH.start();
        });
        threadE.setCount(5).setCountTarget(10);
        threadF.setCount(10).setCountTarget(20);
        threadG.setCount(10).setCountTarget(15);
        threadH.setCount(15).setCountTarget(20);
        pause.setDisable(false);
        threads.addAll(List.of(new RootThread[]{threadA, threadB, threadC, threadD, threadE, threadF, threadG, threadH}));
        threadA.start();
        threadB.start();

    }

    @FXML
    protected void onExecutePauseButtonAction() {
        resume.setDisable(false);
        for (RootThread thr:threads) {
            thr.setPaused(true);
        }
        pause.setDisable(true);
    }

    @FXML
    protected void onExecuteResumeButtonAction(){
        pause.setDisable(false);
        for (RootThread thr:threads) {
            thr.setPaused(false);
        }
        resume.setDisable(true);
    }



}