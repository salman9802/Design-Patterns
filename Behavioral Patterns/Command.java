
// The base command class defines the common interface for all
// concrete commands.

import java.util.ArrayList;

abstract class AbstractCommand {
    protected Application app;
    protected Editor editor;
    protected String backup;

    AbstractCommand() { }

    AbstractCommand(Application app, Editor editor) {
        this.app = app;
        this.editor = editor;
    }

    // Make a backup of the editor's state.
    public void saveBackup() {
        this.backup = this.editor.text;
    }

    // Restore the editor's state.
    public void undo() {
        this.editor.text = this.backup;
    }

    // The execution method is declared abstract to force all
    // concrete commands to provide their own implementations.
    // The method must return true or false depending on whether
    // the command changes the editor's state.
    abstract boolean execute();
}

// The concrete commands go here.
class CopyCommand extends AbstractCommand {
    // The copy command isn't saved to the history since it
    // doesn't change the editor's state.
    @Override
    public boolean execute() {
        this.app.clipboard = this.editor.getSelection();
        return false;
    }
}

class CutCommand extends AbstractCommand {
    // The cut command does change the editor's state, therefore
    // it must be saved to the history. And it'll be saved as
    // long as the method returns true.
    @Override
    public boolean execute() {
        this.saveBackup();
        this.app.clipboard = this.editor.getSelection();
        this.editor.deleteSelection();
        return true;
    }
}

class PasteCommand extends AbstractCommand {
    @Override
    public boolean execute() {
        this.saveBackup();
        this.app.clipboard = this.editor.getSelection();
        this.editor.deleteSelection();
        return true;
    }
}

// The undo operation is also a command.
class UndoCommand extends AbstractCommand {
    @Override
    public boolean execute() {
        this.app.undo();
        return false;
    }
}

// The global command history is just a stack.
class CommandHistory {
    private ArrayList<AbstractCommand> history = new ArrayList<>();

    // Last in...
    public void push(AbstractCommand c) {
        // Push the command to the end of the history array.
        this.history.add(c);
    }

    // ...first out
    public AbstractCommand pop() {
        // Get the most recent command from the history.
        return this.history.get(this.history.size() - 1);
    }
}

// The editor class has actual text editing operations. It plays
// the role of a receiver: all commands end up delegating
// execution to the editor's methods.
class Editor {
    String text = "Hello world"; // assume the cursor is always last

    public String getSelection() {
        // Return selected text.
        return this.text;
    }

    public void deleteSelection() {
        // Delete selected text.
        this.text = null;
    }

    public void replaceSelection(String text) {
        // Insert the clipboard's contents at the current
        // position.
        this.text += text;
    }
}

class Application {
    public String clipboard;
    public Editor[] editors;
    public Editor activeEditor;
    public CommandHistory history;

    public void undo() {
        AbstractCommand command = history.pop();
        if (command != null)
            command.undo();
    }
}


public class Command {
    public static void main(String[] args) {
        /* The rest of the code is the application pseudocode as it's not feasible to implement all this
        
        // The application class sets up object relations. It acts as a
        // sender: when something needs to be done, it creates a command
        // object and executes it.
        class Application is
            field clipboard: string
            field editors: array of Editors
            field activeEditor: Editor
            field history: CommandHistory

            // The code which assigns commands to UI objects may look
            // like this.
            method createUI() is
                // ...
                copy = function() { executeCommand(
                    new CopyCommand(this, activeEditor)) }
                copyButton.setCommand(copy)
                shortcuts.onKeyPress("Ctrl+C", copy)

                cut = function() { executeCommand(
                    new CutCommand(this, activeEditor)) }
                cutButton.setCommand(cut)
                shortcuts.onKeyPress("Ctrl+X", cut)

                paste = function() { executeCommand(
                    new PasteCommand(this, activeEditor)) }
                pasteButton.setCommand(paste)
                shortcuts.onKeyPress("Ctrl+V", paste)

                undo = function() { executeCommand(
                    new UndoCommand(this, activeEditor)) }
                undoButton.setCommand(undo)
                shortcuts.onKeyPress("Ctrl+Z", undo)

            // Execute a command and check whether it has to be added to
            // the history.
            method executeCommand(command) is
                if (command.execute)
                    history.push(command)

            // Take the most recent command from the history and run its
            // undo method. Note that we don't know the class of that
            // command. But we don't have to, since the command knows
            // how to undo its own action.
            method undo() is
                command = history.pop()
                if (command != null)
                    command.undo()
        */
    }
}
