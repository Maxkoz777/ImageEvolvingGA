package model;

public class Triangle {

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Point point1;
    Point point2;
    Point point3;

    public Triangle(Point point1, Point point2, Point point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    int[] getArrayX() {
        return new int[]{point1.x, point2.x, point3.x};
    }

    int[] getArrayY() {
        return new int[]{point1.y, point2.y, point3.y};
    }

}
