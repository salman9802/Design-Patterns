import java.util.ArrayList;

// The handler interface declares a method for executing a
// request.
interface ComponentWithContextualHelp {
    void showHelp();
}

// The base class for simple components.
abstract class Component implements ComponentWithContextualHelp {
    String tooltipText;

    // The component's container acts as the next link in the
    // chain of handlers.
    protected Container container;

    // The component shows a tooltip if there's help text
    // assigned to it. Otherwise it forwards the call to the
    // container, if it exists.
    @Override
    public void showHelp() {
        if(this.tooltipText != null) {
            // Show tooltip
            System.out.println("Tooltip: " + this.tooltipText);
        } else
            this.container.showHelp();
    }
}

// Containers can contain both simple components and other
// containers as children. The chain relationships are
// established here. The class inherits showHelp behavior from
// its parent.
abstract class Container extends Component{
    // This is the Composite Design Pattern
    protected ArrayList<Component> children = new ArrayList<>();

    public void add(Component child) {
        this.children.add(child);
        child.container = this; // The child should also know it's parent for CoR Pattern
    }
}

// Primitive components may be fine with default help
// implementation...
class Button extends Component {
    // ...
}

// But complex components may override the default
// implementation. If the help text can't be provided in a new
// way, the component can always call the base implementation
// (see Component class).
class Panel extends Container {
    String modalHelpText;

    @Override
    public void showHelp() {
        if(this.modalHelpText != null) {
            // Show a modal window with the help text.
            System.out.println("ModalWindow(" + this.modalHelpText + ")");
        }
        else
            super.showHelp();
    }
}

// ...same as above...
class Dailog extends Container {
    String wikiPageURL;

    @Override
    public void showHelp() {
        if(this.wikiPageURL != null) {
            // Open the wiki help page.
            System.out.println("WikiHelpPage(" + this.wikiPageURL + ")");
        }
        else
            super.showHelp();
    }
}

public class ChainOfResponsibility {
    public static void main(String args[]) {
        // Client code.
        // Every application configures the chain differently.
        Dailog dialog = new Dailog(); // Budget Reports
        dialog.wikiPageURL = "https://...";
        Panel panel = new Panel();
        panel.modalHelpText = "This panel does...";
        Button ok = new Button();
        ok.tooltipText = "This is an OK button that...";
        Button cancel = new Button();
        // ...
        panel.add(ok);
        panel.add(cancel);
        dialog.add(panel);

        // Passes the request to panel
        onF1KeyPress(cancel); // ModalWindow(This panel does...)

        // Resolves the request itself
        onF1KeyPress(ok); // Tooltip: This is an OK button that...

        // Resolves the request itself
        onF1KeyPress(panel); // ModalWindow(This panel does...)

        // Resolves the request itself
        onF1KeyPress(dialog); // WikiHelpPage(https://...)
    }

    public static void onF1KeyPress(ComponentWithContextualHelp component) {
        component.showHelp();
    }
}