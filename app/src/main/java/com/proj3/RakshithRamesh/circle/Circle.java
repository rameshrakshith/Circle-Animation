package com.proj3.RakshithRamesh.circle;

//Rakshith Ramesh


class Circle {

    Circle(Float x, Float y, Float radius, String color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    //x and y are the center coordinates of the circle
    private Float x=0f;
    private Float y=0f;
    private Float radius=0f;
    private String color = "BLACK"; //Default color is BLACK
    private Integer canMoveFlag = 0;
    private Float xDirectionVelocity = 0f;
    private Float yDirectionVelocity = 0f;

    Float getX() {
        return x;
    }

    void setX(Float x) {
        this.x = x;
    }

    Float getY() {
        return y;
    }

    void setY(Float y) {
        this.y = y;
    }

    Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

//    String getColor() {
//        return color;
//    }

    public void setColor(String color) {
        this.color = color;
    }

    Integer getCanMoveFlag() {
        return canMoveFlag;
    }

    void setCanMoveFlag(Integer canMoveFlag) {
        this.canMoveFlag = canMoveFlag;
    }

    Float getXDirectionVelocity() {
        return xDirectionVelocity;
    }

    void setXDirectionVelocity(Float xDirectionVelocity) {
        this.xDirectionVelocity = xDirectionVelocity;
    }

    Float getYDirectionVelocity() {
        return yDirectionVelocity;
    }

    void setYDirectionVelocity(Float yDirectionVelocity) {
        this.yDirectionVelocity = yDirectionVelocity;
    }
}
