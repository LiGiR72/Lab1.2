package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RootThread extends Thread{

    int count;
    int countTarget;

    ThreadCreate create = null;  //Функциональный интерфейс чтобы можно было лямбдами настраивать а не ебошить 8 классов для каждого потока
    @FXML
    TextArea textArea;
    @FXML
    Label label;

    public RootThread(String name, TextArea textArea, Label label) {
        super(name);
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
//                sleep(100);
//            }catch (InterruptedException e){   // Паузы для наглядности которые каким то образом тормозят главный поток
//                this.interrupt();              // В дальний ящик собственно
//            }

        }while (count < countTarget);
    }

    RootThread setCreate(ThreadCreate create){  // Внизу сеттери и геттеры (RootThread в возвращемом типе чтобы можно было комманды в цепь строить)
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
