package Shapes;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
class IrregularCanvasException extends Exception {
    IrregularCanvasException(String id, double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f", id, maxArea));
    }
}
enum Type{Square, Circle}

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication2 shapesApplication = new ShapesApplication2(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);
    }
}
class ShapesApplication2 {
    ArrayList<Canvas2> canvases;
    double maxArea;
    private void addCanvas(Canvas2 canvas2) throws IrregularCanvasException{
        if(canvas2.maxArea() > maxArea){
            throw new IrregularCanvasException(canvas2.getCanvas_id(),maxArea);
        }
        canvases.add(canvas2);
    }
    public ShapesApplication2(){
        canvases = new ArrayList<Canvas2>();
        maxArea = 0;
    }
    public ShapesApplication2(double maxArea){
        canvases = new ArrayList<Canvas2>();
        this.maxArea = maxArea;
    }
    public void readCanvases(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int counter = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String canvas_id = line.split(" ")[0];
            String[] sizes = line.split(" ");

            ArrayList<Figure> figures = new ArrayList<Figure>();
            for (int i = 1; i < sizes.length; i += 2) {
                if (sizes[i].equals("C")) {
                    figures.add(new Circle(Integer.parseInt(sizes[i + 1])));
                } else {
                    figures.add(new Square(Integer.parseInt(sizes[i + 1])));
                }
            }
            try {
                addCanvas(new Canvas2(canvas_id,figures));
            } catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
    public void printCanvases(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(outputStream);
        ArrayList<Canvas2> sorted_canvases = canvases;
        sorted_canvases.sort(Collections.reverseOrder());

        for (Canvas2 canvas2 : sorted_canvases){
            printWriter.println(canvas2);
        }

        printWriter.flush();
    }
}
class Figure implements Comparable<Figure>{
    protected Type type;
    protected int size;
    Figure(){}
    Figure(Type type, int size){
        this.type = type;
        this.size = size;
    }
    int getSize(){
        return this.size;
    }
    double getArea() {
        return 0;
    }
    Type getType(){
        return type;
    }

    @Override
    public int compareTo(Figure o) {
        return Double.compare(getArea(),o.getArea());
    }
}
class Square extends Figure{
    public Square(){
        super();
    }
    public Square(int size){
        super(Type.Square, size);
    }
    @Override
    public double getArea() {
        return size * size;
    }
}
class Circle extends Figure{
    public Circle(){
        super();
    }
    public Circle(int size){
        super(Type.Circle, size);
    }
    @Override
    public double getArea() {
        return size * size * Math.PI;
    }
}
class Canvas2 implements Comparable<Canvas2> {
    private String canvas_id;
    private ArrayList<Figure> figures;

    public Canvas2(){
        canvas_id = "";
        figures = new ArrayList<>();
    }
    public Canvas2(String canvas_id, ArrayList<Figure> figures){
        this.canvas_id = canvas_id;
        this.figures = figures;
    }

    public int getSize(){
        return figures.size();
    }

    public String getCanvas_id() {
        return canvas_id;
    }

    public double maxArea(){
        return Collections.max(figures).getArea();
    }
    public double minArea(){
        return Collections.min(figures).getArea();
    }
    public double averageArea() {
        double avg = 0;
        for (Figure figure : figures){
            avg+=figure.getArea();
        }
        return avg / figures.size();
    }

    public double getTotalArea() {
        double total = 0;
        for(Figure figure : figures) {
            total += figure.getArea();
        }
        return total;
    }

    public long getSquares() {
        return figures.stream().filter(x -> x.getType().equals(Type.Square)).count();
    }

    public long getCircles() {
        return figures.stream().filter(x -> x.getType().equals(Type.Circle)).count();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f", canvas_id, figures.size(), getCircles(), getSquares(), minArea(), maxArea(), averageArea());
    }

    @Override
    public int compareTo(Canvas2 o) {
        return Double.compare(getTotalArea(),o.getTotalArea());
    }
}