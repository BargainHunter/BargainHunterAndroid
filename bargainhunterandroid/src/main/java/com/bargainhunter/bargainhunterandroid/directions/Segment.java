package com.bargainhunter.bargainhunterandroid.directions;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by vasovourka on 12/16/14.
 */
public class Segment {
    private LatLng start;

    private String instruction;

    private int length;

    private double distance;

    public Segment() {
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(final String turn) {
        this.instruction = turn;
    }

    public void setPoint(final LatLng point) {
        start = point;
    }

    public LatLng startPoint() {
        return start;
    }

    public Segment copy() {
        final Segment copy = new Segment();
        copy.start = start;
        copy.instruction = instruction;
        copy.length = length;
        copy.distance = distance;
        return copy;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
