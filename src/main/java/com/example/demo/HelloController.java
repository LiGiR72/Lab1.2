package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class HelloController  {
    @FXML                                                   //Да, давай ебать требовать добавление ссаных тегов к каждому ебаному элементу
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
    private TextArea  Atext;
    @FXML
    private TextArea  Btext;
    @FXML
    private TextArea  Ctext;
    @FXML
    private TextArea  Dtext;
    @FXML
    private TextArea  Etext;
    @FXML
    private TextArea  Ftext;
    @FXML
    private TextArea  Gtext;
    @FXML
    private TextArea  Htext;

    private void exceptionHandler(RootThread... thread){

        for (RootThread thr: thread) {
            try {
                thr.join();
            }catch (InterruptedException e){
                thr.interrupt();
            }
        }


    }

    @FXML
    protected void onExecuteButtonAction() throws InterruptedException{                                // Полнейшая нефильтрованая дрисня, это худшее что я когда либо писал
        RootThread threadA = new RootThread(Atext, labelA);                                            // Создаем потоки, привязываем в конструкторе к ним текстбокс
        RootThread threadB = new RootThread(Btext, labelB);
        RootThread threadC = new RootThread(Ctext, labelC);
        RootThread threadD = new RootThread(Dtext, labelD);
        RootThread threadE = new RootThread(Etext, labelE);
        RootThread threadF = new RootThread(Ftext, labelF);
        RootThread threadG = new RootThread(Gtext, labelG);
        RootThread threadH = new RootThread(Htext, labelH);
        threadA.setCount(0).setCountTarget(5).setCreate(()->{threadD.run(); threadC.run(); threadE.run();});                            //Задаем отрезки для потоков и потоки которые они запустит перед тем как помереть
        threadB.setCount(0).setCountTarget(10).setCreate(()->{
            exceptionHandler(threadE);threadG.run(); threadF.run();});                 //В точках синхронизации потоки запускаются после joint()
        threadC.setCount(5).setCountTarget(15);
        threadD.setCount(5).setCountTarget(15).setCreate(()->{exceptionHandler(threadC, threadG); threadH.run();});
        threadE.setCount(5).setCountTarget(10);
        threadF.setCount(10).setCountTarget(20);
        threadG.setCount(10).setCountTarget(15);
        threadH.setCount(15).setCountTarget(20);
        threadA.run(); threadB.run();
    }


}