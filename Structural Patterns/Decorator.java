
// The component interface defines operations that can be
// altered by decorators.
interface DataSource {
    String readData();
    void writeData(String data);
}

// Concrete components provide default implementations for the
// operations. There might be several variations of these
// classes in a program.
class FileDataSource implements DataSource {
    String data;

    // FileDataSource(String filename) { ... }

    @Override
    public String readData() {
        // System.out.println("FileRead('" + this.data + "')");
        // return "['" + this.data + "']";
        return this.data;
    }

    @Override
    public void writeData(String data) {
        this.data = data;
        // System.out.println("['" + data + "']");
    }
}

// The base decorator class follows the same interface as the
// other components. The primary purpose of this class is to
// define the wrapping interface for all concrete decorators.
// The default implementation of the wrapping code might include
// a field for storing a wrapped component and the means to
// initialize it.
class DataSourceDecorator implements DataSource {
    // protected DataSource wrappee;
    public DataSource wrappee;

    DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    // Concrete decorators may call the parent implementation of
    // the operation instead of calling the wrapped object
    // directly. This approach simplifies extension of decorator
    // classes.
    @Override
    public String readData() {
        return wrappee.readData();
    }

    // The base decorator simply delegates all work to the
    // wrapped component. Extra behaviors can be added in
    // concrete decorators.
    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }
}

// Concrete decorators must call methods on the wrapped object,
// but may add something of their own to the result. Decorators
// can execute the added behavior either before or after the
// call to a wrapped object.
class EncryptionDecorator extends DataSourceDecorator {

    EncryptionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        // 1. Encrypt passed data.
        // 2. Pass encrypted data to the wrappee's writeData
        // method.
        this.wrappee.writeData("Encryption('" + data + "')");
    }

    @Override
    public String readData() {
        // 1. Get data from the wrappee's readData method.
        // 2. Try to decrypt it if it's encrypted.
        // 3. Return the result.
        return "Decryption('" + this.wrappee.readData() + "')";
    }
}

// You can wrap objects in several layers of decorators.
class CompressionDecorator extends DataSourceDecorator {

    CompressionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        // 1. Compress passed data.
        // 2. Pass compressed data to the wrappee's writeData
        // method.
        this.wrappee.writeData("Compression('" + data + "')");
    }

    @Override
    public String readData() {
        // 1. Get data from the wrappee's readData method.
        // 2. Try to decompress it if it's compressed.
        // 3. Return the result.
        return "Decompression('" + this.wrappee.readData() + "')";
    }
}

class Application {
    DataSource source;
    public void dumbUsageExample() {
        String data = "Hello world";

        this.source = new FileDataSource();
        this.source.writeData(data); // The target file has been written with plain data.

        this.source = new CompressionDecorator(this.source);
        this.source.writeData(data); // The target file has been written with compressed data.

        this.source = new EncryptionDecorator(this.source);
        this.source.writeData(data); // The file has been written with compressed and encrypted data.
    }
}

class DataManager {
    DataSource source;

    DataManager(DataSource source) { this.source = source; }

    public String load() { return "DataManager('" + this.source.readData() + "')"; }

    public void save(String data) { this.source.writeData(data); }

    // ...Other useful methods...
}

public class Decorator {
    public static void main(String[] args) {
        // Option 1. A simple example of a decorator assembly.
        // Application app = new Application();
        // app.dumbUsageExample();
        // System.out.println("\nRetrieved Data: " + app.source.readData());
        
        // Option 2. Client code that uses an external data source.
        // SalaryManager objects neither know nor care about data
        // storage specifics. They work with a pre-configured data
        // source received from the app configurator.
        boolean enabledEncryption = true, enabledCompression = true;
        DataSource source = new FileDataSource();
        // The app can assemble different stacks of decorators at
        // runtime, depending on the configuration or environment.
        if(enabledEncryption) source = new EncryptionDecorator(source);
        if(enabledCompression) source = new CompressionDecorator(source);
        source.writeData("Hello World!");
        
        
        // source = ((DataSourceDecorator)source).wrappee;
        // System.out.println("\nData after encryption: " + source.readData());
        
        // source = ((DataSourceDecorator)source).wrappee;
        // System.out.println("\nData without any decorators: " + source.readData());
        
        System.out.println("\nData: " + source.readData());

        DataManager logger = new DataManager(source);
        System.out.println("Read with manager: " + logger.load());
    }
}
