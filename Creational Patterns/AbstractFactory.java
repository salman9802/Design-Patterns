// The abstract factory interface declares a set of methods that
// return different abstract products. These products are called
// a family and are related by a high-level theme or concept.
// Products of one family are usually able to collaborate among
// themselves. A family of products may have several variants,
// but the products of one variant are incompatible with the
// products of another variant.
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Concrete factories produce a family of products that belong
// to a single variant. The factory guarantees that the
// resulting products are compatible. Signatures of the concrete
// factory's methods return an abstract product, while inside
// the method a concrete product is instantiated.
class WinFactory implements GUIFactory{

    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }

}

// Each concrete factory has a corresponding product variant.
class MacFactory implements GUIFactory{

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }

}

// Each distinct product of a product family should have a base
// interface. All variants of the product must implement this
// interface.
interface Button {
    String paint();
}

// Concrete products are created by corresponding concrete
// factories.
class WinButton implements Button {

    @Override
    public String paint() {
        return "Render a button in Windows style.";
    }

}

class MacButton implements Button {

    @Override
    public String paint() {
        return "Render a button in macOS style.";
    }

}

// Here's the base interface of another product. All products
// can interact with each other, but proper interaction is
// possible only between products of the same concrete variant.
interface Checkbox {
    String paint();
}

class WinCheckbox implements Checkbox {

    @Override
    public String paint() {
        return "Render a checkbox in Windows style.";
    }
    
} 

class MacCheckbox implements Checkbox {

    @Override
    public String paint() {
        return "Render a checkbox in macOS style.";
    }

}


// The client code works with factories and products only
// through abstract types: GUIFactory, Button and Checkbox. This
// lets you pass any factory or product subclass to the client
// code without breaking it.
class Application {
    private GUIFactory factory;
    private Button button;
    private Checkbox checkbox;
    
    Application(GUIFactory factory) {
        this.factory = factory;
    }

    public void createUI() {
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
    }

    public void paint() {
        System.out.println(this.button.paint());
        System.out.println(this.checkbox.paint());
    }
}



public class AbstractFactory {
    public static void main(String args[]) throws Exception{
        // Win & Mac are variants
        // Button & Checkbox are products (together as family of products)
        
        String os = args.length > 0 ? (args[0]) : "windows";
        GUIFactory factory;

        if(os.equals("windows")) {
            factory = new WinFactory();
        } else if(os.equals("mac")) {
            factory = new MacFactory();
        } else {
            throw new Exception("Error! Unknown operating system.");
        }

        Application app = new Application(factory);
        app.createUI();
        app.paint();
    }
}
