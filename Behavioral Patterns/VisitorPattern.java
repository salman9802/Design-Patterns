import java.util.ArrayList;

// The element interface declares an `accept` method that takes
// the base visitor interface as an argument.
interface Shape {
    void move(int x, int y);
    void draw();
    void accept(Visitor v);
}

// Each concrete element class must implement the `accept`
// method in such a way that it calls the visitor's method that
// corresponds to the element's class.
class Dot implements Shape {
    int x, y;

    Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(int x, int y) {
        System.out.println("Moving dot x=" + x + " y=" + y);
    }

    @Override
    public void draw() {
        System.out.println("Drawing Dot.");
    }

    @Override
    public String toString() {
        return "Dot(x='" + this.x + "', y='" + this.y + "')";
    }

    // Note that we're calling `visitDot`, which matches the
    // current class name. This way we let the visitor know the
    // class of the element it works with.
    @Override
    public void accept(Visitor v) {
        v.visitDot(this);
    }
}

class Circle implements Shape{
    int x, y, radius;

    Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void move(int x, int y) {
        System.out.println("Moving circle x=" + x + " y=" + y);
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle.");
    }
    
    @Override
    public String toString() {
        return "Circle(x='" + this.x + "', y='" + this.y + "', radius='" + this.radius + "')";
    }

    @Override
    public void accept(Visitor v) {
        v.visitCircle(this);
    }
    
}

class Rectangle implements Shape {
    int x, y, width, height;

    Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move(int x, int y) {
        System.out.println("Moving rectangle x=" + x + " y=" + y);

    }

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle.");
    }

    @Override
    public String toString() {
        return "Rectangle(x='" + this.x + "', y='" + this.y + "', width='" + this.width + "', height='" + this.height + "')";
    }

    @Override
    public void accept(Visitor v) {
        v.visitRectangle(this);
    }
}

// The Visitor interface declares a set of visiting methods that
// correspond to element classes. The signature of a visiting
// method lets the visitor identify the exact class of the
// element that it's dealing with.
interface Visitor {
    void visitDot(Dot d);
    void visitCircle(Circle c);
    void visitRectangle(Rectangle r);
}

// Concrete visitors implement several versions of the same
// algorithm, which can work with all concrete element classes.
//
// You can experience the biggest benefit of the Visitor pattern
// when using it with a complex object structure such as a
// Composite tree. In this case, it might be helpful to store
// some intermediate state of the algorithm while executing the
// visitor's methods over various objects of the structure.
class XMLExportVisitor implements Visitor {
    @Override
    public void visitDot(Dot d) {
        // Export the dot's ID and center coordinates.
        System.out.println("XMLExport(" + d + ")");
    }

    @Override
    public void visitCircle(Circle c) {
        // Export the circle's ID, center coordinates and
        // radius.
        System.out.println("XMLExport(" + c + ")");
    }

    @Override
    public void visitRectangle(Rectangle r) {
        // Export the rectangle's ID, left-top coordinates,
        // width and height.
        System.out.println("XMLExport(" + r + ")");
    }
}


public class VisitorPattern {
    public static void main(String[] args) {
        // The client code can run visitor operations over any set of
        // elements without figuring out their concrete classes. The
        // accept operation directs a call to the appropriate operation
        // in the visitor object.
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(new Dot(10, 10));
        shapes.add(new Circle(20, 20, 15));
        shapes.add(new Rectangle(30, 30, 14, 15));

        XMLExportVisitor exportVisitor = new XMLExportVisitor();

        for(Shape shape : shapes) {
            shape.accept(exportVisitor);
        }
    }
}