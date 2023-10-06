
// The "abstraction" defines the interface for the "control"
// part of the two class hierarchies. It maintains a reference
// to an object of the "implementation" hierarchy and delegates
// all of the real work to this object.
class RemoteControl {
    protected Device device;

    RemoteControl() { }

    RemoteControl(Device device) {
        this.device = device;
    }

    public void togglePower() {
        if(this.device.isEnabled()) this.device.disable();
        else this.device.enable();
    }

    public void volumeDown() { this.device.setVolume(this.device.getVolume() - 10); }
    public void volumeUp() { this.device.setVolume(this.device.getVolume() + 10); }
    public void channelDown() { this.device.setChannel(this.device.getChannel() - 1); }
    public void channelUp() { this.device.setChannel(this.device.getChannel() + 1); }
}

// You can extend classes from the abstraction hierarchy
// independently from device classes.
class AdvancedRemoteControl extends RemoteControl {

    AdvancedRemoteControl(Device device) { super(device); }

    public void mute() { this.device.setVolume(0); }
}

// The "implementation" interface declares methods common to all
// concrete implementation classes. It doesn't have to match the
// abstraction's interface. In fact, the two interfaces can be
// entirely different. Typically the implementation interface
// provides only primitive operations, while the abstraction
// defines higher-level operations based on those primitives.
interface Device {
    boolean isEnabled();
    void enable();
    void disable();
    int getVolume();
    void setVolume(int percent);
    int getChannel();
    void setChannel(int channel);
}

// All devices follow the same interface.
class TV implements Device {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void enable() {
        // ...
    }

    @Override
    public void disable() {
        // ...
    }

    @Override
    public int getVolume() {
        return 2;
    }

    @Override
    public void setVolume(int percent) {
        // ...
    }

    @Override
    public int getChannel() {
        return 1;
    }

    @Override
    public void setChannel(int channel) {
        // ...
    }

}

class Radio implements Device {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void enable() {
        // ...
    }

    @Override
    public void disable() {
        // ...
    }

    @Override
    public int getVolume() {
        return 0;
    }

    @Override
    public void setVolume(int percent) {
        // ...
    }

    @Override
    public int getChannel() {
        return 1;
    }

    @Override
    public void setChannel(int channel) {
        // ...
    }

}

public class Bridge {
    public static void main(String[] args) {
        // Somewhere in client code.
        TV tv = new TV();
        RemoteControl tvRemote = new RemoteControl(tv);
        tvRemote.togglePower();

        Radio radio = new Radio();
        AdvancedRemoteControl radioRemote = new AdvancedRemoteControl(radio);
        radioRemote.mute();
    }
}
