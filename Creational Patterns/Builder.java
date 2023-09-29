
// Using the Builder pattern makes sense only when your products
// are quite complex and require extensive configuration. The
// following two products are related, although they don't have
// a common interface.
class Car {
    // A car can have a GPS, trip computer and some number of
    // seats. Different models of cars (sports car, SUV,
    // cabriolet) might have different features installed or
    // enabled.
    public int noOfSeats;
    public String model, engine, tripComputer, GPS;

    public String toString() {
        return "Car[ model='" + this.model + "', engine='" + this.engine + "', trip-computer='" + this.tripComputer + "', gps='" + this.GPS + "', no-of-seats='" + this.noOfSeats + "']";
    }
}

class Manual {
    // Each car should have a user manual that corresponds to
    // the car's configuration and describes all its features.
    int noOfSeats;
    String model, engine, tripComputer, GPS;

    public String toString() {
        return "CarManual[ model='" + this.model + "', engine='" + this.engine + "', trip-computer='" + this.tripComputer + "', gps='" + this.GPS + "', no-of-seats='" + this.noOfSeats + "']";
    }
}

// The builder interface specifies methods for creating the
// different parts of the product objects.
interface VehicleBuilder {
    void reset();
    void setSeats(int seats);
    void setEngine(String engine);
    void setTripComputer(String tripComputer);
    void setGPS(String gps);
    void setModel(String model);
}

// The concrete builder classes follow the builder interface and
// provide specific implementations of the building steps. Your
// program may have several variations of builders, each
// implemented differently.
class CarBuilder implements VehicleBuilder {
    private Car car;

    // A fresh builder instance should contain a blank product
    // object which it uses in further assembly.
    CarBuilder() {
        this.reset();
    }

    // The reset method clears the object being built.
    @Override
    public void reset() {
        this.car = new Car();
    }

    // All production steps work with the same product instance.
    @Override
    public void setSeats(int seats) {
        this.car.noOfSeats = seats;
    }

    @Override
    public void setEngine(String engine) {
        this.car.engine = engine;
    }

    @Override
    public void setTripComputer(String tripComputer) {
        this.car.tripComputer = tripComputer;
    }

    @Override
    public void setGPS(String gps) {
        this.car.GPS = gps;
    }

    @Override
    public void setModel(String model) {
        this.car.model = model;
    }

    // Concrete builders are supposed to provide their own
    // methods for retrieving results. That's because various
    // types of builders may create entirely different products
    // that don't all follow the same interface. Therefore such
    // methods can't be declared in the builder interface (at
    // least not in a statically-typed programming language).
    //
    // Usually, after returning the end result to the client, a
    // builder instance is expected to be ready to start
    // producing another product. That's why it's a usual
    // practice to call the reset method at the end of the
    // `getProduct` method body. However, this behavior isn't
    // mandatory, and you can make your builder wait for an
    // explicit reset call from the client code before disposing
    // of the previous result.
    public Car getProduct() {
        Car product = this.car;
        this.reset();
        return product;
    }
}

// Unlike other creational patterns, builder lets you construct
// products that don't follow the common interface.
class CarManualBuilder implements VehicleBuilder {
    private Manual manual;

    @Override
    public void reset() {
        this.manual = new Manual();
    }

    @Override
    public void setSeats(int seats) {
        this.manual.noOfSeats = seats; // Document car seat features.
    }

    @Override
    public void setEngine(String engine) {
        this.manual.engine = engine; // Add engine instructions.
    }

    @Override
    public void setTripComputer(String tripComputer) {
        this.manual.tripComputer = tripComputer; // Add trip computer instructions.
    }

    @Override
    public void setGPS(String gps) {
        this.manual.GPS = gps;  // Add GPS instructions.
    }

    @Override
    public void setModel(String model) {
        this.manual.model = model;
    }

    public Manual getProduct() {
        Manual product = this.manual; // Return the manual.
        this.reset(); // Reset the builder.
        return product;
    }
}


// The director is only responsible for executing the building
// steps in a particular sequence. It's helpful when producing
// products according to a specific order or configuration.
// Strictly speaking, the director class is optional, since the
// client can control builders directly.
class CarDirector {
    public void constructSportsCar(VehicleBuilder builder) {
        builder.reset();
        builder.setModel("SportsCar");
        builder.setSeats(2);
        builder.setEngine("SportEngine");
        builder.setTripComputer("SportTripComputer");
        builder.setGPS("SportGPS");
    }

    public void constructSUV(VehicleBuilder builder) {
        builder.reset();
        builder.setModel("SUV");
        builder.setSeats(4);
        builder.setEngine("SUVEngine");
        builder.setTripComputer("SUVTripComputer");
        builder.setGPS("SUVGPS");
    }
}



public class Builder {
    public static void main(String args[]) {
        CarDirector director = new CarDirector();

        CarBuilder carBuilder = new CarBuilder();
        director.constructSUV(carBuilder);


        // The final product is often retrieved from a builder
        // object since the director isn't aware of and not
        // dependent on concrete builders and products.
        Car suvCar = carBuilder.getProduct();
        System.out.println("Build Complete: " + suvCar);

        carBuilder.reset(); // This can be optional if we reset after getting product
        director.constructSportsCar(carBuilder);
        Car sportsCar = carBuilder.getProduct();
        System.out.println("Build Complete: " + sportsCar);

        CarManualBuilder suvManualBuilder = new CarManualBuilder();
        director.constructSUV(suvManualBuilder);

        Manual suvManual = suvManualBuilder.getProduct();
        System.out.println("Build Complete: " + suvManual);
    }
}
