package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RootThread extends Thread{

    int count;
    int countTarget;

    ThreadCreate create = null;
    @FXML
    TextArea textArea;
    @FXML
    Label label;

    public RootThread(int count, int countTarget, TextArea textArea) {
        this.count = count;
        this.countTarget = countTarget;
        this.textArea = textArea;
    }

    public RootThread(TextArea textArea, Label label) {
        this.textArea = textArea;
        this.label = label;
    }

    @Override
    public void run() {

        label.setText("Работает");
        countUp();
        if(create != null){
            create.create();
        }
        label.setText("Остановлен");
    }

    public void countUp(){
        textArea.setText("");
        do {
            count++;
            textArea.setText(textArea.getText() + "\n" + count);
//            try{
//                sleep(500);
//            }catch (InterruptedException e){   // Хихихи, костыль
//                this.interrupt();
//            }

        }while (count < countTarget);
    }

    RootThread setCreate(ThreadCreate create){
        this.create = create;
        return this;
    }

    public int getCount() {
        return count;
    }

    public int getCountTarget() {
        return countTarget;
    }

    public RootThread setCount(int count) {
        this.count = count;
        return this;
    }

    public RootThread setCountTarget(int countTarget) {
        this.countTarget = countTarget;
        return this;
    }
}
