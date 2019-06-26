package com.codingtive.dicodingmade.mvp;

class BarPresenter {
    private BarView view;

    BarPresenter(BarView view) {
        this.view = view;
    }

    public double volume(double length, double width, double height) {
        return length * width * height;
    }

    void calculateVolume(double length, double width, double height) {
        double volume = volume(length, width, height);
        BarModel model = new BarModel(volume);
        view.showVolume(model);
    }
}
