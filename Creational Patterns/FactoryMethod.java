// The creator class declares the factory method that must
// return an object of a product class. The creator's subclasses
// usually provide the implementation of this method.
abstract class Dialog {

    // Abstract factory method
    abstract public Button createButton();

    // Note that, despite its name, the creator's primary
    // responsibility isn't creating products. It usually
    // contains some core business logic that relies on product
    // objects returned by the factory method. Subclasses can
    // indirectly change that business logic by overriding the
    // factory method and returning a different type of product
    // from it.
    public void render() {
        Button btn = createButton();
        System.out.println(btn.onClick());
        System.out.println(btn.render());
    }
}

// Concrete creators override the factory method to change the
// resulting product's type.
class WindowDialog extends Dialog {

    @Override
    public Button createButton() {
        return new WindowButton();
    }

}

class WebDialog extends Dialog {

    @Override
    public Button createButton() {
        return new WebButton();
    }

}


// The product interface declares the operations that all
// concrete products must implement.
interface Button {
    String onClick();
    String render();
}

// Concrete products provide various implementations of the
// product interface.
class WindowButton implements Button{

    @Override
    public String onClick() {
        return "Bind a native OS click event.";
    }

    @Override
    public String render() {
        return "Render a button in Windows style.";
    }
}

class WebButton implements Button{

    @Override
    public String onClick() {
        return "Bind a web browser click event.";
    }

    @Override
    public String render() {
        return "Return an HTML representation of a button.\n<button onclick= 'fn()'>Button</button>";
    }
}

public class FactoryMethod {
    public static Dialog dialog;
    public static void main(String args[]){

        // The application picks a creator's type depending on the
        // current configuration or environment settings.
        String currentOS = "web";

        if(currentOS == "windows") {
            dialog = new WindowDialog();
        } else if(currentOS == "web") {
            dialog = new WebDialog();
        }

        // System.out.println(dialog);

        // The client code works with an instance of a concrete
        // creator, albeit through its base interface. As long as
        // the client keeps working with the creator via the base
        // interface, you can pass it any creator's subclass.
        
        dialog.render();
    }
}