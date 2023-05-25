package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RootThread extends Thread {

    private int count;
    private int countTarget;

    private boolean isPaused = false;

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
        Platform.runLater(() -> label.setText("Работает"));

        countUp();
        if (create != null) {
            create.create();
        }
        Platform.runLater(() -> label.setText("Остановлен"));
    }

    public void countUp() {
        Platform.runLater(() -> textArea.setText(""));
        do {
            if(isPaused){
                Thread.yield();
                continue;
            }
            count++;
            Platform.runLater(() -> textArea.setText(textArea.getText() + "\n" + count));
            try {
                sleep(500);
            } catch (
                    InterruptedException e) {   // Паузы для наглядности которые каким то образом тормозят главный поток
                this.interrupt();              // В дальний ящик собственно
            }

        } while (count < countTarget);
    }

    RootThread setCreate(ThreadCreate create) {  // Внизу сеттери и геттеры (RootThread в возвращемом типе чтобы можно было комманды в цепь строить)
        this.create = create;
        return this;
    }

    public int getCount() {
        return count;
    }

    public RootThread setCount(int count) {
        this.count = count;
        return this;
    }

    public int getCountTarget() {
        return countTarget;
    }

    public RootThread setCountTarget(int countTarget) {
        this.countTarget = countTarget;
        return this;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
