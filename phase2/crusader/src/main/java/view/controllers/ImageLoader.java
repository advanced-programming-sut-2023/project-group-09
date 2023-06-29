package view.controllers;

import controller.gamestructure.GameImages;

public class ImageLoader extends Thread{
    @Override
    public void run() {
        GameImages.loadImages();
    }
}
