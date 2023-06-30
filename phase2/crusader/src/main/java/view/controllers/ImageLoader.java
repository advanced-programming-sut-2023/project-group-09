package view.controllers;

import controllers.gamestructure.GameImages;

public class ImageLoader extends Thread{
    @Override
    public void run() {
        GameImages.loadImages();
    }
}
