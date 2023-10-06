import java.util.HashMap;
import java.util.Map;

// The base publisher class includes subscription management
// code and notification methods.
class EventManager {
    private HashMap<CustomerListener, String> listeners = new HashMap<>();

    public void subscribe(String eventType, CustomerListener listener) {
        listeners.put(listener, eventType);
    }
    
    public void unsubscribe(String eventType, CustomerListener listener) {
        listeners.remove(listener, eventType);
    }

    public void notify(String eventType, Object data) {

        for (Map.Entry<CustomerListener, String> subscriber : listeners.entrySet()) {
            if(subscriber.getValue().equals(eventType)) {
                subscriber.getKey().update(data);
            }
        }
    }
}

// The concrete publisher contains real business logic that's
// interesting for some subscribers. We could derive this class
// from the base publisher, but that isn't always possible in
// real life because the concrete publisher might already be a
// subclass. In this case, you can patch the subscription logic
// in with composition, as we did here.
class Company {
    public EventManager events;

    Company() {
        this.events = new EventManager();
    }

    // Methods of business logic can notify subscribers about
    // changes.
    public void regularSale(String product) {
        this.events.notify("regular-sale", product);
    }

    public void premiumSale(String product) {
        this.events.notify("premium-sale", product);
    }

    // public void endSale(String product) {
    //     this.events.notify("premium-sale", product);
    // }
}

// Here's the subscriber interface. If your programming language
// supports functional types, you can replace the whole
// subscriber hierarchy with a set of functions.
interface CustomerListener {
    public void update(Object data);
}

// Concrete subscribers react to updates issued by the publisher
// they are attached to.
class RegularCustomer implements CustomerListener {
    String name;

    RegularCustomer(String name) {
        this.name = name;
    }

    @Override
    public void update(Object product) {
        System.out.println("Hello, " + this.name + ". " + "A sale has started and will end in 3 days. '" + product + "' will have a 10% discount during the sale. Hurry!!");
    }
}

class PremiumCustomer implements CustomerListener {
    String name;

    PremiumCustomer(String name) {
        this.name = name;
    }

    @Override
    public void update(Object product) {
        System.out.println("Hello, " + this.name + ". " + "As our premium customer we have a suprise for you. '" + product + "' is having a 20% discount for the next 1 week. Don't miss this opportunity. Shop now!!");
    }
}

public class Observer {
    public static void main(String[] args) {
        // An application can configure publishers and subscribers at
        // runtime
        Company company = new Company();

        RegularCustomer rc1 = new RegularCustomer("RC1");
        RegularCustomer rc2 = new RegularCustomer("RC2");
        RegularCustomer rc3 = new RegularCustomer("RC3");

        PremiumCustomer pc1 = new PremiumCustomer("PC1");
        PremiumCustomer pc2 = new PremiumCustomer("PC2");

        company.events.subscribe("regular-sale", rc1);
        company.events.subscribe("regular-sale", rc2);
        company.events.subscribe("regular-sale", rc3);
        company.events.subscribe("premium-sale", pc1);
        company.events.subscribe("premium-sale", pc2);

        System.out.println("Sale Starts");
        company.regularSale("Samsung Phone");
        company.premiumSale("iPhone X");
    }
}
