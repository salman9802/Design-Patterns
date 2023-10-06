import java.util.ArrayList;
import java.util.HashMap;

class Profile {
    int id;
    String email;

    Profile(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }
}

// The collection interface must declare a factory method for
// producing iterators. You can declare several methods if there
// are different kinds of iteration available in your program.
interface SocialNetwork {
    public ProfileIterator createFriendsIterator(int profileId);
    public ProfileIterator createCoworkersIterator(int profileId);
}

// Each concrete collection is coupled to a set of concrete
// iterator classes it returns. But the client isn't, since the
// signature of these methods returns iterator interfaces.
class Facebook implements SocialNetwork {
    // ... The bulk of the collection's code should go here ...
    // private HashMap<Integer, String> friends = new HashMap<>();
    // private HashMap<Integer, String> coworkers = new HashMap<>();
    private Profile[] friends;
    private Profile[] coworkers;

    Facebook() {
        this.friends = new Profile[2];
        this.friends[0] = new Profile(0, "traversy@gmail.com");
        this.friends[1] = new Profile(1, "codewithharry@gmail.com");

        this.coworkers = new Profile[2];
        this.coworkers[0] = new Profile(0, "freecodecamp@gmail.com");
        this.coworkers[1] = new Profile(1, "calebcurry@gmail.com");
    }

    public Profile[] socialGraphRequest(int profileId, String type) {
        if(type.equals("friends")) return this.friends;
        else if(type.equals("coworkers")) return this.coworkers;
        else return null;
    }

    // Iterator creation code.
    @Override
    public ProfileIterator createFriendsIterator(int profileId) {
        return new FacebookIterator(this, profileId, "friends");
    }

    @Override
    public ProfileIterator createCoworkersIterator(int profileId) {
        return new FacebookIterator(this, profileId, "coworkers");
    }
}

// The common interface for all iterators.
interface ProfileIterator {
    public Profile getNext();
    public boolean hasMore();
}


// The concrete iterator class.
class FacebookIterator implements ProfileIterator {
    // The iterator needs a reference to the collection that it
    // traverses.
    private Facebook facebook;
    private int profileId;
    private String type;

    // An iterator object traverses the collection independently
    // from other iterators. Therefore it has to store the
    // iteration state.
    private int currentPosition = 0;
    private Profile cache[];

    FacebookIterator(Facebook facebook, int profileId, String type) {
        this.facebook = facebook;
        this.profileId = profileId;
        this.type = type;
    }

    private void lazyInit() {
        if(this.cache == null) {
            this.cache = facebook.socialGraphRequest(profileId, type);
        }
    }

    // Each concrete iterator class has its own implementation
    // of the common iterator interface.
    public Profile getNext() {
        Profile result = null;

        if(this.hasMore()) result = cache[currentPosition++];
        return result;
    }

    public boolean hasMore() {
        this.lazyInit();
        return currentPosition < cache.length;
    }
}


// Here is another useful trick: you can pass an iterator to a
// client class instead of giving it access to a whole
// collection. This way, you don't expose the collection to the
// client.
//
// And there's another benefit: you can change the way the
// client works with the collection at runtime by passing it a
// different iterator. This is possible because the client code
// isn't coupled to concrete iterator classes.
class SocialSpammer {
    public void send(ProfileIterator iterator, String message) {
        Profile profile;
        while(iterator.hasMore()) {
            profile = iterator.getNext();
            System.out.println("System.sendMail(" + profile.getEmail() + ", " + message + ")");
        }
    }
}

// The application class configures collections and iterators
// and then passes them to the client code.
class Application {
    SocialNetwork network;
    SocialSpammer spammer;

    public void config() {
        // if working with Facebook
        //     this.network = new Facebook()
        // if working with LinkedIn
        //     this.network = new LinkedIn()
        this.network = new Facebook();
        this.spammer = new SocialSpammer();
    }

    public void sendSpamToFriends(Profile profile) {
        ProfileIterator iterator = this.network.createFriendsIterator(0);
        this.spammer.send(iterator, "My Friend, I got a special deal for ya!");
    }

    public void sendSpamToCoworkers(Profile profile) {
        ProfileIterator iterator = this.network.createCoworkersIterator(0);
        this.spammer.send(iterator, "I thought this might help you!");
    }
}

public class Iterator {
    public static void main(String[] args) {
        // 
        Application app = new Application();
        app.config();
        app.sendSpamToCoworkers(new Profile(0, "client@gmail.com"));
        app.sendSpamToCoworkers(new Profile(1, "client@gmail.com"));
    }
}
