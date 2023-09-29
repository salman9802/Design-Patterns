# Classification of Patterns
All patterns can be categorized by their intent, or purpose. The three main groups of patterns:

**Creational patterns** provide object creation mechanisms that increase flexibility and reuse of existing code.

**Structural patterns** explain how to assemble objects and classes into larger structures, while keeping these structures flexible and efficient.

**Behavioral patterns** take care of effective communication and the assignment of responsibilities between objects.

***

## Creational patterns

### Factory Method
**Factory Method** is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.

![Alt text](img/factory-method.png)

#### How to implement

1. Make all products follow the same interface. This interface should declare methods that make sense in every product.

2. Add an empty factory method inside the creator class. The return type of the method should match the common product interface.

3. In the creator’s code find all references to product constructors. One by one, replace them with calls to the factory method, while extracting the product creation code into the factory method.

    You might need to add a temporary parameter to the factory method to control the type of returned product.

    At this point, the code of the factory method may look pretty ugly. It may have a large switch statement that picks which product class to instantiate. But don’t worry, we’ll fix it soon enough.

4. Now, create a set of creator subclasses for each type of product listed in the factory method. Override the factory method in the subclasses and extract the appropriate bits of construction code from the base method.

5. If there are too many product types and it doesn’t make sense to create subclasses for all of them, you can reuse the control parameter from the base class in subclasses.

    For instance, imagine that you have the following hierarchy of classes: the base Mail class with a couple of subclasses: AirMail and GroundMail; the Transport classes are Plane, Truck and Train. While the AirMail class only uses Plane objects, GroundMail may work with both Truck and Train objects. You can create a new subclass (say TrainMail) to handle both cases, but there’s another option. The client code can pass an argument to the factory method of the GroundMail class to control which product it wants to receive.

6. If, after all of the extractions, the base factory method has become empty, you can make it abstract. If there’s something left, you can make it a default behavior of the method.

***

### Abstract Factory
**Abstract Factory** is a creational design pattern that lets you produce families of related objects without specifying their concrete classes.

> *If there is only one variant use Factory Method.*
>
>*If there are multiple variants use Abstract Factory.*

![Alt text](img/abstract-factory.png)

#### How to implement
1. Map out a matrix of distinct product types versus variants of these products.

2. Declare abstract product interfaces for all product types. Then make all concrete product classes implement these interfaces.

3. Declare the abstract factory interface with a set of creation methods for all abstract products.

4. Implement a set of concrete factory classes, one for each product variant.

5. Create factory initialization code somewhere in the app. It should instantiate one of the concrete factory classes, depending on the application configuration or the current environment. Pass this factory object to all classes that construct products.

6. Scan through the code and find all direct calls to product constructors. Replace them with calls to the appropriate creation method on the factory object.

***

### Factory Comparison
1. #### Factory
Factory is an ambiguous term that stands for a function, method or class that supposed to be producing something. Most commonly, factories produce objects. But they may also produce files, records in databases, etc.

For instance, any of these things may be casually referenced as a “factory”:

a function or method that creates a program’s GUI;
a class that creates users;
static method that calls a class constructor in a certain way;
one of the creational design patterns.
Usually, when someone says a word “factory,” the exact meaning is supposed to be clear from a context. But if you’re in doubt, just ask. There’s a chance that the author is ignorant.

2. #### Creation method
Creation method defined in the Refactoring To Patterns book as “a method that creates objects.” This means that every result of a factory method pattern is a “creation method” but not necessarily the reverse. It also means that you can substitute the term “creation method” wherever Martin Fowler uses the term “factory method” in Refactoring and wherever Joshua Bloch uses the term “static factory method” in Effective Java.

In reality, the creation method is just a wrapper around a constructor call. It may just have a name that better expresses your intentions. On the other hand, it may help to isolate your code from changes to the constructor. It could even contain some particular logic that would return existing objects instead of creating new.

A lot of people would call such methods “factory method” just because it produces new objects: The logic is straightforward: the method creates objects and since all factories creates objects, this method should clearly be a factory method. Naturally, there’s a lot of confusion when it comes to the real Factory Method pattern.

In the following example, `next` is a creation method:
```php
class Number {
    private $value;

    public function __construct($value) {
        $this->value = $value;
    }

    public function next() {
        return new Number ($this->value + 1);
    }
}
```

3. #### Static creation (or factory) method
Static creation method is a creation method declared as `static`. In other words, it can be called on a class and doesn’t require an object to be created.

Don’t be confused when someone calls methods like this a “static factory method”. That’s just a bad habit. The Factory Method is a design pattern that relies on inheritance. If you make it `static`, you can no longer extend it in subclasses, which defeats the purpose of the pattern.

When a static creation method returns new objects it becomes an alternative constructor.

It might be useful when:

You have to have several different constructors that have different purposes but their signatures match. For instance, having both `Random(int max)` and `Random(int min)` is impossible in Java, C++, C#, and many other languages. And the most popular workaround is to create several static methods that call the default constructor and set appropriate values afterward.

You want to reuse existing objects, instead of instancing new ones (see, the Singleton pattern). Constructors in most programming languages have to return new class instances. The static creation method is a workaround to this limitation. Inside a static method, your code can decide whether to create a fresh instance by calling the constructor or return an existing object from some cache.

In the following example, the `load` method is a static creation method. It provides a convenient way to retrieve users from a database.
```php
class User {
    private $id, $name, $email, $phone;

    public function __construct($id, $name, $email, $phone) {
        $this->id = $id;
        $this->name = $name;
        $this->email = $email;
        $this->phone = $phone;
    }

    public static function load($id) {
        list($id, $name, $email, $phone) = DB::load_data('users', 'id', 'name', 'email', 'phone');
        $user = new User($id, $name, $email, $phone);
        return $user;
    }
}
```

4. #### Simple factory
The Simple factory pattern  describes a class that has one creation method with a large conditional that based on method parameters chooses which product class to instantiate and then return.

People usually confuse simple factories with a general factories or with one of the creational design patterns. In most cases, a simple factory is an intermediate step of introducing Factory Method or Abstract Factory patterns.

A simple factory is usually represented by a single method in a single class. Over time, this method might become too big, so you may decide to extract parts of the method to subclasses. Once you do it several times, you might discover that the whole thing turned into the classic factory method pattern.

By the way, if you declare a simple factory `abstract`, it doesn’t magically become the abstract factory pattern.

Here’s an example of simple factory:
```php
class UserFactory {
    public static function create($type) {
        switch ($type) {
            case 'user': return new User();
            case 'customer': return new Customer();
            case 'admin': return new Admin();
            default:
                throw new Exception('Wrong user type passed.');
        }
    }
}
```

5. #### Factory Method pattern
The Factory Method  is a creational design pattern that provides an interface for creating objects but allows subclasses to alter the type of an object that will be created.

If you have a creation method in base class and subclasses that extend it, you might be looking at the factory method.
```php
abstract class Department {
    public abstract function createEmployee($id);

    public function fire($id) {
        $employee = $this->createEmployee($id);
        $employee->paySalary();
        $employee->dismiss();
    }
}

class ITDepartment extends Department {
    public function createEmployee($id) {
        return new Programmer($id);
    }
}

class AccountingDepartment extends Department {
    public function createEmployee($id) {
        return new Accountant($id);
    }
}
```

6. #### Abstract Factory pattern
The Abstract Factory  is a creational design pattern that allows producing families of related or dependent objects without specifying their concrete classes.

What are the "families of objects"? For instance, take this set of classes: `Transport` + `Engine` + `Controls`. There might be several variants of these:

1. `Car` + `CombustionEngine` + `SteeringWheel`
2. `Plane` + `JetEngine` + `Yoke`

If your program doesn’t operate with product families, then you don’t need an abstract factory.

And again, a lot of people mix-up the abstract factory pattern with a simple factory class declared as `abstract`. Don’t do that!

***

### Builder
**Builder** is a creational design pattern that lets you construct complex objects step by step. The pattern allows you to produce different types and representations of an object using the same construction code.

![Alt text](img/builder.png)

#### How to implement
1. Make sure that you can clearly define the common construction steps for building all available product representations. Otherwise, you won’t be able to proceed with implementing the pattern.

2. Declare these steps in the base builder interface.

3. Create a concrete builder class for each of the product representations and implement their construction steps.

    Don’t forget about implementing a method for fetching the result of the construction. The reason why this method can’t be declared inside the builder interface is that various builders may construct products that don’t have a common interface. Therefore, you don’t know what would be the return type for such a method. However, if you’re dealing with products from a single hierarchy, the fetching method can be safely added to the base interface.

4. Think about creating a director class. It may encapsulate various ways to construct a product using the same builder object.

5. The client code creates both the builder and the director objects. Before construction starts, the client must pass a builder object to the director. Usually, the client does this only once, via parameters of the director’s class constructor. The director uses the builder object in all further construction. There’s an alternative approach, where the builder is passed to a specific product construction method of the director.

6. The construction result can be obtained directly from the director only if all products follow the same interface. Otherwise, the client should fetch the result from the builder.


***

### Prototype
**Prototype** is a creational design pattern that lets you copy existing objects without making your code dependent on their classes.

![Alt text](img/prototype.png)

#### How to implement

1. Create the prototype interface and declare the clone method in it. Or just add the method to all classes of an existing class hierarchy, if you have one.

2. A prototype class must define the alternative constructor that accepts an object of that class as an argument. The constructor must copy the values of all fields defined in the class from the passed object into the newly created instance. If you’re changing a subclass, you must call the parent constructor to let the superclass handle the cloning of its private fields.

    If your programming language doesn’t support method overloading, you won’t be able to create a separate “prototype” constructor. Thus, copying the object’s data into the newly created clone will have to be performed within the clone method. Still, having this code in a regular constructor is safer because the resulting object is returned fully configured right after you call the new operator.

3. The cloning method usually consists of just one line: running a new operator with the prototypical version of the constructor. Note, that every class must explicitly override the cloning method and use its own class name along with the new operator. Otherwise, the cloning method may produce an object of a parent class.

4. Optionally, create a centralized prototype registry to store a catalog of frequently used prototypes.

    You can implement the registry as a new factory class or put it in the base prototype class with a static method for fetching the prototype. This method should search for a prototype based on search criteria that the client code passes to the method. The criteria might either be a simple string tag or a complex set of search parameters. After the appropriate prototype is found, the registry should clone it and return the copy to the client.

    Finally, replace the direct calls to the subclasses’ constructors with calls to the factory method of the prototype registry.

***

## Singleton
**Singleton** is a creational design pattern that lets you ensure that a class has only one instance, while providing a global access point to this instance.

![Alt text](img/singleton.png)

#### How to implement
1. Add a private static field to the class for storing the singleton instance.

2. Declare a public static creation method for getting the singleton instance.

3. Implement “lazy initialization” inside the static method. It should create a new object on its first call and put it into the static field. The method should always return that instance on all subsequent calls.

4. Make the constructor of the class private. The static method of the class will still be able to call the constructor, but not the other objects.

5. Go over the client code and replace all direct calls to the singleton’s constructor with calls to its static creation method.



***

## Structural patterns

#### How to implement

## Behavioral patterns

#### How to implement

***
