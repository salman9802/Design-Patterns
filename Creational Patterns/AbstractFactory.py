from abc import ABC, abstractmethod

# Example 1
class FoodFactory(ABC):

    @abstractmethod
    def order():
        pass

    @abstractmethod
    def pack():
        pass
class Biryani(FoodFactory):

    def __init__(self, foodCategory):
        self.foodCategory = foodCategory

    def order(self):
        print(f"You ordered '{self.foodCategory.type}' Biryani.")
        self.foodCategory.cook()
        self.foodCategory.done()

    def pack(self):
        print(f"Packed '{self.foodCategory.type}' Biryani and is now ready for takeaway.")
class Pulao(FoodFactory):

    def __init__(self, foodCategory):
        self.foodCategory = foodCategory

    def order(self):
        print(f"You ordered '{self.foodCategory.type}' Pulao.")
        self.foodCategory.cook()
        self.foodCategory.done()

    def pack(self):
        print(f"Packed '{self.foodCategory.type}' Pulao and is now ready for takeaway.")

class FoodCategory(ABC):

    @abstractmethod
    def cook():
        pass

    @abstractmethod
    def done():
        pass
class Veg(FoodCategory):

    def __init__(self):
        self.type = "Veg"
        
    def cook(self):
        print("Cutting Vegetables and Onions")
        
    def done(self):
        print("Done with cooking vegetables")
class NonVeg(FoodCategory):
    
    def __init__(self):
        self.type = "NonVeg"

    def cook(self):
        print("Cutting meat and marinating it")

    def done(self):
        print("Done cooking meat")


# Example 2
# Abstract classes for each distinct product in product family
class Chair(ABC):
    pass

class Sofa(ABC):
    pass

# Concrete Product Classes
class VictorianChair(Chair):
    def __str__(self):
        return "Created: Victorian Chair"

class ModernChair(Chair):
    def __str__(self):
        return "Created: Modern Chair"

class VictorianSofa(Sofa):
    def __str__(self):
        return "Created: Victorian Sofa"

class ModernSofa(Sofa):
    def __str__(self):
        return "Created: Modern Sofa"


# Abstract Factory
class FurnitureFactory(ABC):
    @abstractmethod
    def createChair():
        pass
    
    @abstractmethod
    def createSofa():
        pass

    def __str__(self):
        return f"Furnitures: {self.furnitureType} chair & sofa."


# Concrete Factory Classes
class VictorianFurnitureFactory(FurnitureFactory):

    def __init__(self):
        self.furnitureType = "Victorian"

    def createChair(self):
        self.chair = VictorianChair()

    def createSofa(self):
        self.sofa = VictorianSofa()

class ModernFurnitureFactory(FurnitureFactory):
    
    def __init__(self):
        self.furnitureType = "Modern"

    def createChair(self):
        self.chair = ModernChair()

    def createSofa(self):
        self.sofa = ModernSofa()



if __name__ == "__main__":
    # Example 1
    # category = "nonveg"
    # if category == "veg":
    #     foodType = Veg()
    # elif category == "nonveg":
    #     foodType = NonVeg()
    
    # biryani = Biryani(foodType)
    # pulao = Pulao(foodType)

    # biryani.order()
    # biryani.pack()
    
    # pulao.order()
    # pulao.pack()

    # Example 2
    style = "victorian"

    if style == "victorian":
        factory = VictorianFurnitureFactory()
    elif style == "modern":
        factory = ModernFurnitureFactory()
    else:
        print("Invalid furniture type")

    factory.createChair()
    factory.createSofa()

    print(factory.chair)
    print(factory.sofa)
    print(factory)