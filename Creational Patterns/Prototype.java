import java.util.Arrays;

// Base prototype.
abstract class Shape {
    int X, Y;
    String color;

    // A regular constructor.
    Shape() {
        this.X = this.Y = 0;
        this.color = "black";
    }

    // The prototype constructor. A fresh object is initialized
    // with values from the existing object.
    Shape(Shape source) {
        this();
        this.X = source.X;
        this.Y = source.Y;
        this.color = source.color;
    }

    // The clone operation returns one of the Shape subclasses.
    abstract public Shape copy();
}

// Concrete prototype. The cloning method creates a new object
// in one go by calling the constructor of the current class and
// passing the current object as the constructor's argument.
// Performing all the actual copying in the constructor helps to
// keep the result consistent: the constructor will not return a
// result until the new object is fully built; thus, no object
// can have a reference to a partially-built clone.
class Rectangle extends Shape {
    int width, height;

    Rectangle() {
        this.width = this.height = 0;
        this.color = "black";
    }

    Rectangle(Rectangle source) {
        // A parent constructor call is needed to copy private
        // fields defined in the parent class.
        super(source);
        this.width = source.width;
        this.height = source.height;
    }

    @Override
    public Rectangle copy() {
        return new Rectangle(this);
    }

    @Override
    public String toString() {
        return "Rectangle[ width='" + this.width + "', height='" + this.height + "' ]";
    }
}

class Circle extends Shape {
    int radius;

    Circle() {
        super();
        this.radius = 0;
    }

    Circle(Circle source) {
        super(source);
        this.radius = source.radius;
    }

    @Override
    public Circle copy() {
        return new Circle(this);
    }
    
    @Override
    public String toString() {
        return "Circle[ radius='" + this.radius + "' ]";
    }
}

public class Prototype {
    public static void main(String[] args) {
        // Somewhere in the client code.
        Circle circle = new Circle();
        circle.X = 10;
        circle.Y = 20;
        circle.radius = 5;
        System.out.println("Circle: " + circle);

        Rectangle rec = new Rectangle();
        rec.width = 100;
        rec.height = 200;
        System.out.println("Rectangle: " + rec);

        Circle circle2 = circle.copy();
        System.out.println("Circle Copy: " + circle2);

        Rectangle rec2 = rec.copy();
        System.out.println("Rectangle Copy: " + rec2);

        Shape shapes[] = new Shape[5];
        shapes[0] = circle;
        shapes[1] = rec;
        shapes[2] = rec2;
        shapes[3] = circle2;

        System.out.println("Original Array: " + Arrays.toString(shapes));

        // Prototype rocks because it lets you produce a copy of
        // an object without knowing anything about its type.
        Shape shapesCopy[] = new Shape[5];

        // For instance, we don't know the exact elements in the
        // shapes array. All we know is that they are all
        // shapes. But thanks to polymorphism, when we call the
        // `clone` method on a shape the program checks its real
        // class and runs the appropriate clone method defined
        // in that class. That's why we get proper clones
        // instead of a set of simple Shape objects.
        for(int i = 0; i < shapes.length; i++) {
            if(shapes[i] != null) shapesCopy[i] = shapes[i].copy();
        }
        System.out.println("Copied Array: " + Arrays.toString(shapesCopy));
        System.out.println("Are both array referencing to same object: " + Arrays.equals(shapes, shapesCopy));
        // The `shapesCopy` array contains exact copies of the
        // `shape` array's children.
    }
}
