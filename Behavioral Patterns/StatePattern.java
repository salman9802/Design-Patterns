
// The AudioPlayer class acts as a context. It also maintains a
// reference to an instance of one of the state classes that
// represents the current state of the audio player.
class AudioPlayer {
    State state;
    String UI, playlist, currentAudio;
    int volume;
    boolean playing = false;

    AudioPlayer() {
        this.state = new ReadyState(this);

        // Context delegates handling user input to a state
        // object. Naturally, the outcome depends on what state
        // is currently active, since each state can handle the
        // input differently.
        UI = "UserInterface";
        // UI = new UserInterface()
        // UI.lockButton.onClick(this.clickLock)
        // UI.playButton.onClick(this.clickPlay)
        // UI.nextButton.onClick(this.clickNext)
        // UI.prevButton.onClick(this.clickPrevious)
    }

    // Other objects must be able to switch the audio player's
    // active state.
    public void changeState(State state) { this.state = state; }

    // UI methods delegate execution to the active state.
    public void clickLock() { this.state.clickLock(); }
    public void clickPlay() { this.state.clickPlay(); }
    public void clickNext() { this.state.clickNext(); }
    public void clickPrevious() { this.state.clickPrevious(); }

    // A state may call some service methods on the context.
    public void startPlayback() {
        System.out.println("Starting Playback");
    }

    public void stopPlayback() {
        System.out.println("Stoping Playback");
    }

    public void nextAudio() {
        System.out.println("Playing next Audio");
    }

    public void previousAudio() {
        System.out.println("Playing previous Audio");
    }

    public void fastForward(int time) {
        System.out.println("Fast forward by: " + time);
    }

    public void rewind(int time) {
        System.out.println("Rewinding by: " + time);
    }
}

// The base state class declares methods that all concrete
// states should implement and also provides a backreference to
// the context object associated with the state. States can use
// the backreference to transition the context to another state.
abstract class State {
    protected AudioPlayer player;

    // Context passes itself through the state constructor. This
    // may help a state fetch some useful context data if it's
    // needed.
    State(AudioPlayer player) { this.player = player; }

    abstract void clickLock();
    abstract void clickPlay();
    abstract void clickNext();
    abstract void clickPrevious();
}

// Concrete states implement various behaviors associated with a
// state of the context.
class LockedState extends State {

    LockedState(AudioPlayer player) { super(player); }

    // When you unlock a locked player, it may assume one of two
    // states.
    public void clickLock() {
        if(this.player.playing) this.player.changeState(new PlayingState(this.player));
        else this.player.changeState(new ReadyState(this.player));
    }

    @Override
    void clickPlay() {
        // Locked, so do nothing
    }

    @Override
    void clickNext() {
        // Locked, so do nothing
    }

    @Override
    void clickPrevious() {
        // Locked, so do nothing
    }
}

// They can also trigger state transitions in the context.
class ReadyState extends State {

    ReadyState(AudioPlayer player) { super(player); }

    public void clickLock() {
        this.player.changeState(new LockedState(this.player));
    }
    
    public void clickPlay() {
        this.player.changeState(new PlayingState(this.player));
    }

    public void clickNext() {
        this.player.nextAudio();
    }
    
    public void clickPrevious() {
        this.player.previousAudio();
    }
}

class PlayingState extends State {

    PlayingState(AudioPlayer player) { super(player); }

    @Override
    void clickLock() {
        this.player.changeState(new LockedState(this.player));
    }

    @Override
    void clickPlay() {
        this.player.stopPlayback();
        this.player.changeState(new ReadyState(this.player));
    }

    @Override
    void clickNext() {
        // if (event.doubleclick)
        //     player.nextAudio()
        // else
        //     player.fastForward(5)
        this.player.nextAudio();
    }

    @Override
    void clickPrevious() {
        // if (event.doubleclick)
        //     player.previousAudio()
        // else
        //     player.rewind(5)
        this.player.previousAudio();
    }
}

public class StatePattern {
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();
        player.clickLock();
        player.clickLock();
        player.clickPlay();
        player.clickNext();
        player.clickNext();
        player.clickPrevious();
        player.clickLock();
    }
}
