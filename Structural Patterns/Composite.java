import java.util.ArrayList;

// The component interface declares common operations for both
// simple and complex objects of a composition.
interface Graphic {
    void move(int x, int y);
    void draw();
}

// The leaf class represents end objects of a composition. A
// leaf object can't have any sub-objects. Usually, it's leaf
// objects that do the actual work, while composite objects only
// delegate to their sub-components.
class Dot implements Graphic {
    int x, y;

    Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a dot at x='" + this.x + "' and y='" + this.y + "'");
    }
}

// All component classes can extend other components.
class Circle extends Dot {
    int radius;

    Circle(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a circle at x='" + this.x + "' and y='" + this.y + "' with radius='" + this.radius + "'");
    }
}

// The composite class represents complex components that may
// have children. Composite objects usually delegate the actual
// work to their children and then "sum up" the result.
class CompoundGraphic implements Graphic {
    ArrayList<Graphic> children = new ArrayList<>();

    // A composite object can add or remove other components
    // (both simple or complex) to or from its child list.
    public void add(Graphic child) { this.children.add(child); }
    public void remove(Graphic child) { this.children.remove(child); }

    @Override
    public void move(int x, int y) {
        for (Graphic child : this.children) {
            child.move(x, y);
        }
    }

    // A composite executes its primary logic in a particular
    // way. It traverses recursively through all its children,
    // collecting and summing up their results. Since the
    // composite's children pass these calls to their own
    // children and so forth, the whole object tree is traversed
    // as a result.
    @Override
    public void draw() {
        // 1. For each child component:
        //     - Draw the component.
        //     - Update the bounding rectangle.
        // 2. Draw a dashed rectangle using the bounding
        // coordinates.
        for (Graphic child : this.children) {
            child.draw();
        }
    }
}


public class Composite {
    public static void main(String[] args) {
        // The client code works with all the components via their base
        // interface. This way the client code can support simple leaf
        // components as well as complex composites.
        CompoundGraphic all;

        // method load
        all = new CompoundGraphic();
        all.add(new Dot(1, 2));
        all.add(new Circle(5, 3, 10));

        // Combine selected components into one complex composite
        // component.
        // method groupSelected(components: array of Graphic)
        Graphic components[] = new Graphic[1];
        components[0] = new Dot(1, 2);
        CompoundGraphic group = new CompoundGraphic();
        for (Graphic component : components) {
            group.add(component);
            all.remove(component);
        }
        // Add the group
        all.add(group);
        // All components will be drawn.
        all.draw();
    }
}
