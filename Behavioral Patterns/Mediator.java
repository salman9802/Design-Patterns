
// The mediator interface declares a method used by components
// to notify the mediator about various events. The mediator may
// react to these events and pass the execution to other
// components.
interface IMediator {
    public void notify(Component sender, String event);
}

// The concrete mediator class. The intertwined web of
// connections between individual components has been untangled
// and moved into the mediator.
class AuthenticationDialog implements IMediator {
    private String title;
    private Checkbox loginOrRegisterChkBx;
    private Textbox loginUsername, loginPassword;
    private Textbox registrationUsername, registrationPassword, registrationEmail;
    private Button okBtn, cancelBtn;

    AuthenticationDialog() {
        // Create all component objects by passing the current
        // mediator into their constructors to establish links.
    }

    public AuthenticationDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public AuthenticationDialog setLoginOrRegisterChkBx(Checkbox loginOrRegisterChkBx) {
        this.loginOrRegisterChkBx = loginOrRegisterChkBx;
        return this;
    }

    public AuthenticationDialog setLoginUsername(Textbox loginUsername) {
        this.loginUsername = loginUsername;
        return this;
    }

    public AuthenticationDialog setLoginPassword(Textbox loginPassword) {
        this.loginPassword = loginPassword;
        return this;
    }

    public AuthenticationDialog setRegistrationUsername(Textbox registrationUsername) {
        this.registrationUsername = registrationUsername;
        return this;
    }

    public AuthenticationDialog setRegistrationPassword(Textbox registrationPassword) {
        this.registrationPassword = registrationPassword;
        return this;
    }

    public AuthenticationDialog setRegistrationEmail(Textbox registrationEmail) {
        this.registrationEmail = registrationEmail;
        return this;
    }

    public AuthenticationDialog setOkBtn(Button okBtn) {
        this.okBtn = okBtn;
        return this;
    }

    public AuthenticationDialog setCancelBtn(Button cancelBtn) {
        this.cancelBtn = cancelBtn;
        return this;
    }


    // When something happens with a component, it notifies the
    // mediator. Upon receiving a notification, the mediator may
    // do something on its own or pass the request to another
    // component.
    @Override
    public void notify(Component sender, String event) {
        if(sender == this.loginOrRegisterChkBx && event == "check") {
            if(this.loginOrRegisterChkBx.checked) {
                this.title = "login";
                // 1. Show login form components.
                // 2. Hide registration form components.
            } else {
                this.title = "Register";
                // 1. Show registration form components.
                // 2. Hide login form components
            }
        }

        if(sender == okBtn && event == "click") {
            if(loginOrRegisterChkBx.checked) {
                // Try to find a user using login credentials.
                boolean found = true;
                if(!found) {
                    // Show an error message above the login
                    // field.
                }
            } else {
                // 1. Create a user account using data from the
                // registration fields.
                // 2. Log that user in.
                // ...
            }
        }
    }
}

// Components communicate with a mediator using the mediator
// interface. Thanks to that, you can use the same components in
// other contexts by linking them with different mediator
// objects.
class Component {
    IMediator dialog;

    Component() { }

    Component(IMediator dialog) { this.dialog = dialog; }

    public void click() { this.dialog.notify(this, "click"); }
    public void keypress() { this.dialog.notify(this, "keypress"); }
}

// Concrete components don't talk to each other. They have only
// one communication channel, which is sending notifications to
// the mediator.
class Button extends Component {
    Button(IMediator mediator) { this.dialog = mediator; }
}

class Textbox extends Component {
    Textbox(IMediator mediator) { this.dialog = mediator; }
}

class Checkbox extends Component {
    boolean checked = true;

    Checkbox(IMediator mediator) { this.dialog = mediator; }

    public void check() {
        this.dialog.notify(this, "check");
    }
    // ...
}

public class Mediator {
    public static void main(String[] args) {
        // private Checkbox loginOrRegisterChkBx;
        // private Textbox loginUsername, loginPassword;
        // private Textbox registrationUsername, registrationPassword, registrationEmail;
        // private Button okBtn, cancelBtn;
        AuthenticationDialog mediator = new AuthenticationDialog();

        Checkbox loginOrRegisterChkBx = new Checkbox(mediator);
        Textbox loginUsername = new Textbox(mediator);
        Textbox loginPassword = new Textbox(mediator);
        Textbox registrationUsername = new Textbox(mediator);
        Textbox registrationPassword = new Textbox(mediator);
        Textbox registrationEmail = new Textbox(mediator);
        Button okBtn = new Button(mediator);
        Button cancelBtn = new Button(mediator);

        mediator.setCancelBtn(cancelBtn)
            .setLoginOrRegisterChkBx(loginOrRegisterChkBx)
            .setLoginPassword(loginPassword)
            .setLoginUsername(loginUsername)
            .setOkBtn(okBtn)
            .setRegistrationEmail(registrationEmail)
            .setRegistrationPassword(registrationPassword)
            .setRegistrationUsername(registrationUsername)
            .setTitle("Title");

        loginOrRegisterChkBx.check();
    }
}
