package com.example.signupandlogin;

public class DataPoint {
    int x;
    int y;

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    double z;

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    float f;

    public DataPoint(int x, double z) {
        this.x = x;
        this.z = z;
    }

    public DataPoint() {
    }
public DataPoint(int x, float f)
{
    this.x = x;
    this.f = f;
}
    public DataPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
