package com.jasmine.jasmine_core.Models;

import scala.Tuple2;

//latitude y, longitude x
public class JNCellsContainer extends JNBoxContainer {
    private int latitudeSize;
    private int longitudeSize;

    public JNCellsContainer(JNCoordinates topLeftCoordinates, JNCoordinates bottomRightCoordinates, int latitudeSize, int longitudeSize) {
        super(topLeftCoordinates, bottomRightCoordinates);
        this.latitudeSize = latitudeSize;
        this.longitudeSize = longitudeSize;
    }

    public Double getCellWidth() {
        return this.getWidth() / longitudeSize;
    }

    public Double getCellHeight() {
        return this.getHeight() / latitudeSize;
    }

    public Tuple2<Integer, Integer> getBelongingCellIndices(JNCoordinates coordinates) {
        int x = (int) Math.floor((coordinates.longitude - this.getTopLeftCoordinates().longitude) / this.getCellWidth());
        int y = (int) Math.floor((coordinates.latitude - this.getTopLeftCoordinates().latitude) / this.getCellHeight());
        return new Tuple2<>(x, y);
    }

    public JNBoxContainer getCellBoxContainer(int x, int y) {
        return new JNBoxContainer(
                new JNCoordinates(this.getTopLeftCoordinates().latitude + this.getCellHeight() * y, this.getTopLeftCoordinates().longitude + this.getCellWidth() * x),
                new JNCoordinates(this.getTopLeftCoordinates().latitude + this.getCellHeight() * (y + 1), this.getTopLeftCoordinates().longitude + this.getCellWidth() * (x + 1))
        );
    }

    public int getLatitudeSize() {
        return latitudeSize;
    }

    public void setLatitudeSize(int latitudeSize) {
        this.latitudeSize = latitudeSize;
    }

    public int getLongitudeSize() {
        return longitudeSize;
    }

    public void setLongitudeSize(int longitudeSize) {
        this.longitudeSize = longitudeSize;
    }
}