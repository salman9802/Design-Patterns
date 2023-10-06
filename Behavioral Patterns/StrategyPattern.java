
// The strategy interface declares operations common to all
// supported versions of some algorithm. The context uses this
// interface to call the algorithm defined by the concrete
// strategies.
interface Strategy {
    public int execute(int a, int b);
}

// Concrete strategies implement the algorithm while following
// the base strategy interface. The interface makes them
// interchangeable in the context.
class ConcreteStrategyAdd implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
}

class ConcreteStrategySubtract implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a - b;
    }
}

class ConcreteStrategyMultipy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a * b;
    }
}

// The context defines the interface of interest to clients.
class Context {
    // The context maintains a reference to one of the strategy
    // objects. The context doesn't know the concrete class of a
    // strategy. It should work with all strategies via the
    // strategy interface.
    private Strategy strategy;

    // Usually the context accepts a strategy through the
    // constructor, and also provides a setter so that the
    // strategy can be switched at runtime.
    public void setStrategy(Strategy strategy) { this.strategy = strategy; }

    // The context delegates some work to the strategy object
    // instead of implementing multiple versions of the
    // algorithm on its own.
    public int executeStrategy(int a, int b) {
        return this.strategy.execute(a, b);
    }
}

public class StrategyPattern {
    public static void main(String[] args) {
        // The client code picks a concrete strategy and passes it to
        // the context. The client should be aware of the differences
        // between strategies in order to make the right choice.
        Context context = new Context();
        int a = 10;
        int b = 5;

        String action = "add";

        if(action.equals("add")) context.setStrategy(new ConcreteStrategyAdd());
        else if(action.equals("sub")) context.setStrategy(new ConcreteStrategySubtract());
        else if(action.equals("mul")) context.setStrategy(new ConcreteStrategyMultipy());

        System.out.println("Result: " + context.executeStrategy(a, b));
    }
}
