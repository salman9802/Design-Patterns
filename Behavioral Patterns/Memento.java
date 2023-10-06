
// The originator holds some important data that may change over
// time. It also defines a method for saving its state inside a
// memento and another method for restoring the state from it.
class Editor {
    private String text;
    private int curX, curY, selectionWidth;

    public Editor setText(String text) {
        this.text = text;
        return this;
    }

    public Editor setCursor(int curX, int curY) {
        this.curX = curX;
        this.curY = curY;
        return this;
    }

    public Editor setSelectionWidth(int selectionWidth) {
        this.selectionWidth = selectionWidth;
        return this;
    }

    public String toString() {
        return "Editor(text='" + this.text + "', cursor='" + this.curX + ", " + this.curY + "', selection-width='" + this.selectionWidth + "')";
    }

    // Saves the current state inside a memento.
    public Snapshot createSnapshot() {
        // Memento is an immutable object; that's why the
        // originator passes its state to the memento's
        // constructor parameters.
        return new Snapshot(this, this.text, this.curX, this.curY, this.selectionWidth);
    }
}

// The memento class stores the past state of the editor.
class Snapshot {
    private Editor editor; // This originator's reference is used to reflect changes
    private String text;
    private int curX, curY, selectionWidth;

    Snapshot(Editor editor, String text, int curX, int curY, int selectionWidth) {
        this.editor = editor;
        this.text = text;
        this.curX = curX;
        this.curY = curY;
        this.selectionWidth = selectionWidth;
    }

    // At some point, a previous state of the editor can be
    // restored using a memento object.
    public void restore() {
        this.editor.setText(this.text);
        this.editor.setCursor(this.curX, this.curY);
        this.editor.setSelectionWidth(this.selectionWidth);
    }
}

// A command object can act as a caretaker. In that case, the
// command gets a memento just before it changes the
// originator's state. When undo is requested, it restores the
// originator's state from a memento.
class Command {
    private Snapshot backup;
    private Editor editor;

    Command(Editor editor) {
        this.editor = editor;
    }

    public void makeBackup() {
        this.backup = this.editor.createSnapshot();
    }

    public void undo() {
        if(this.backup != null) this.backup.restore();
    }
}

public class Memento {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.setText("Initial Text")
            .setCursor(10, 10)
            .setSelectionWidth(20);
        
        System.out.println("Initial Editor State: " + editor);
        Command command = new Command(editor);
        command.makeBackup();

        System.out.println("Making snapshot...");

        editor.setText("Text 2")
            .setCursor(20, 20)
            .setSelectionWidth(100);
        
        System.out.println("\nAnother Editor State: " + editor);

        System.out.println("Reverting snapshot...");
        command.undo();

        System.out.println("\nCurrent Editor State" + editor);
    }
}
