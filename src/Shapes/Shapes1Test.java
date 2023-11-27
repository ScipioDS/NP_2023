package Shapes;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
class ShapesApplication{
    ArrayList<Canvas> canvases;
    public ShapesApplication(){
        canvases = new ArrayList<Canvas>();
    }
    public int readCanvases(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream);
        int counter = 0;

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String canvas_id = line.split(" ")[0];
            String [] sizes = line.split(" ");

            ArrayList<Integer> canvas_sizes = new ArrayList<Integer>();
            for (int i = 1; i < sizes.length; i++) {
                canvas_sizes.add(Integer.parseInt(sizes[i]));
                counter++;
            }
            canvases.add(new Canvas(canvas_id, canvas_sizes));
        }
        return counter;
    }
    public void printLargestCanvasTo(OutputStream outputStream) {
        int location = 0;

        for(int i = 0; i < canvases.size(); i++) {
            if(canvases.get(i).getPerimeter() > canvases.get(location).getPerimeter()) {
                location = i;
            }
        }

        PrintWriter print = new PrintWriter(outputStream);
        print.println(canvases.get(location));
        print.flush();
    }
}
class Canvas{
    private String canvas_id;
    private ArrayList<Integer> canvas_sizes;

    public Canvas(){
        canvas_id = "";
        canvas_sizes = new ArrayList<>();
    }
    public Canvas(String canvas_id, ArrayList<Integer> canvas_sizes){
        this.canvas_id = canvas_id;
        this.canvas_sizes = canvas_sizes;
    }

    public int getSize(){
        return canvas_sizes.size();
    }
    public int getPerimeter(){
        int perimetar = 0;
        for (int size : canvas_sizes) {
            perimetar+=size;
        }
        return perimetar * 4;
    }

    @Override
    public String toString() {
        return canvas_id + " " + getSize() + " " + getPerimeter();
    }
}