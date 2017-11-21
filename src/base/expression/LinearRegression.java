package base.expression;

import base.MathMe;
import base.Point;

/**
 * 一個線性回歸計算器
 * Created by zyvis on 2017/5/26.
 */
public class LinearRegression {
    Point[] points;
    boolean expression=false;
    double avrX, avrY,b,a;

    public LinearRegression(Point[] points) {
        this.points = points;
    }

    private void installExpression() {
        avrX = avrY = 0;
        for (Point p : points) {
            avrX += p.getX();
            avrY += p.getY();
        }
        avrX/=points.length;
        avrY/=points.length;
        double up=0,down=0;
        for (Point p : points) {
            up+=(p.getX()*p.getY()-points.length*avrX*avrY);
            down+=(MathMe.power(p.getX(),2)-points.length*Math.pow(avrX,2));
        }
        b=up/down;
        a=avrY-b*avrX;
        expression=true;
    }
    public synchronized double calculate(double x){
        if(!expression)installExpression();
        return b*x+a;
    }



}
