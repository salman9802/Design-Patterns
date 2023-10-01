import java.util.ArrayList;

// In this example, the Flyweight pattern helps to reduce memory usage when rendering millions of tree objects on a canvas.

// The flyweight class contains a portion of the state of a
// tree. These fields store values that are unique for each
// particular tree. For instance, you won't find here the tree
// coordinates. But the texture and colors shared between many
// trees are here. Since this data is usually BIG, you'd waste a
// lot of memory by keeping it in each tree object. Instead, we
// can extract texture, color and other repeating data into a
// separate object which lots of individual tree objects can
// reference.
class TreeType {
    String name, color, texture;

    TreeType(String name, String color, String texture){
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public String toString() {
        return "[ " + "name='" + this.name + "' color='" + this.color + "' texture='" + this.texture + "' ]";
    }

    public void draw(String canvas, int x, int y) {
        // 1. Create a bitmap of a given type, color & texture.
        // 2. Draw the bitmap on the canvas at X and Y coords.
        System.out.println("Drawing a Tree{\n\tIntrinsic Data: " + this + "\n\tExtrinsic Data: [ canvas='" + canvas + "' x='" + x + "' y='" + y + "' ]\n}");
    }
}

// Flyweight factory decides whether to re-use existing
// flyweight or to create a new object.
class TreeFactory {
    static ArrayList<TreeType> treeTypes = new ArrayList<>(); // pool of flyweights

    static TreeType getTreeType(String name, String color, String texture) {
        TreeType type = null;
        // Check if a flyweight with given intrinsic properties exists in the pool
        for (TreeType treeType : treeTypes) {
            if(treeType.name.equals(name) && treeType.color.equals(color) && treeType.texture.equals(texture)) {
                type = treeType;
                break;
            }
        }
        // If not, create a new flyweight and add it to the pool
        if(type == null) {
            type = new TreeType(name, color, texture);
            treeTypes.add(type);
        }
        return type;
    }
}

// The contextual object contains the extrinsic part of the tree
// state. An application can create billions of these since they
// are pretty small: just two integer coordinates and one
// reference field.
class Tree {
    int x, y;
    TreeType type;

    Tree(int x, int y, TreeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(String canvas) {
        type.draw(canvas, x, y);
    }
}

// The Tree and the Forest classes are the flyweight's clients.
// You can merge them if you don't plan to develop the Tree
// class any further.
class Forest {
    ArrayList<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture) {
        TreeType type = TreeFactory.getTreeType(name, color, texture);
        Tree tree = new Tree(x, y, type);
        trees.add(tree);
    }

    public void draw(String canvas) {
        for(Tree tree : trees) {
            tree.draw(canvas);
        }
    }
}


public class Flyweight {
    public static void main(String[] args) {
        // 
        // TreeType mangoTree = new TreeType("Mango", "brown & green", "rough");
        // TreeType bananaTree = new TreeType("Banana", "brown & yellow", "spiky");

        // Tree tree1 = new Tree(10, 20, mangoTree);
        // Tree tree2 = new Tree(20, 40, mangoTree);
        // Tree tree3 = new Tree(15, 30, bananaTree);

        Forest forest = new Forest();
        forest.plantTree(10, 20, "Mango", "brown & green", "rough");
        forest.plantTree(20, 30, "Mango", "brown & green", "rough");
        forest.plantTree(15, 30, "Banana", "brown & yellow", "spiky");

        forest.draw("Screen");
        System.out.println("\nTreeType Pool: " + TreeFactory.treeTypes);
        System.out.println(TreeFactory.treeTypes.size());
    }
}
