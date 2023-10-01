

// Say you have two classes with compatible interfaces:
// RoundHole and RoundPeg.
class RoundHole {
    int radius;

    RoundHole(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return this.radius;
    }

    public boolean fits(RoundPeg peg) {
        return this.getRadius() >= peg.getRadius();
    }
}

class RoundPeg {
    int radius;

    RoundPeg() { }

    RoundPeg(int radius) {
        this.radius = radius;
    }
    
    public int getRadius() {
        return this.radius;
    }
}

// But there's an incompatible class: SquarePeg.
class SquarePeg {
    int width;

    SquarePeg(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }
}

// An adapter class lets you fit square pegs into round holes.
// It extends the RoundPeg class to let the adapter objects act
// as round pegs.
class SquarePegAdapter extends RoundPeg {
    // In reality, the adapter contains an instance of the
    // SquarePeg class.
    SquarePeg peg;

    SquarePegAdapter(SquarePeg peg) {
        this.peg = peg;
    }

    public int getRadius() {
        // The adapter pretends that it's a round peg with a
        // radius that could fit the square peg that the adapter
        // actually wraps.
        return (int) (this.peg.getWidth() * Math.sqrt(2) / 2);
    }
}



public class Adapter {
    public static void main(String[] args) {
        RoundHole rh = new RoundHole(5);
        RoundPeg rp = new RoundPeg(3);
        System.out.println(rh.fits(rp)); // true

        SquarePeg sp = new SquarePeg(3);
        // System.out.println(rh.fits(sp)); // this won't compile (incompatible types)

        SquarePegAdapter spa = new SquarePegAdapter(sp);
        System.out.println(rh.fits(spa)); // true

        SquarePeg spLarge = new SquarePeg(9);
        SquarePegAdapter spaLarge = new SquarePegAdapter(spLarge);
        System.out.println(rh.fits(spaLarge)); // false
    }
}
