from abc import ABC, abstractmethod

# The component interface declares common operations for both
# simple and complex objects of a composition.
class OrderItem(ABC):
    @abstractmethod 
    def cost(self):
        return self.price
    
    def __str__(self):
        return f"{self.name}({self.price} Rs.)"

# The leaf class represents end objects of a composition. A
# leaf object can't have any sub-objects. Usually, it's leaf
# objects that do the actual work, while composite objects only
# delegate to their sub-components.
class Product(OrderItem):
    def __init__(self, name, price) -> None:
        super().__init__()
        self.name = name
        self.price = price

    def cost(self):
        return self.price

# The composite class represents complex components that may
# have children. Composite objects usually delegate the actual
# work to their children and then "sum up" the result
class ProductBox(OrderItem):
    def __init__(self, name):
        super().__init__()
        self.name = name
        self.products = []

    def addItem(self, item):
        self.products.append(item)

    def removeItem(self, item):
        self.products.remove(item)

    # A composite executes its primary logic in a particular
    # way. It traverses recursively through all its children,
    # collecting and summing up their results. Since the
    # composite's children pass these calls to their own
    # children and so forth, the whole object tree is traversed
    # as a result.
    def cost(self):
        totalCost = 0
        for item in self.products:
            totalCost += item.cost()
        return totalCost
    
    def __str__(self) -> str:
        return f"{self.name}[{', '.join([product.__str__() for product in self.products])}]"
        # print(', '.join([product.__str__() for product in self.products]))
        # for product in self.products:
        #     print(product)
        # return ""


if __name__ == "__main__":
    hammer = Product("Hammer", 10000)
    
    phone = Product("Phone", 5000)
    charger = Product("Charger", 500)
    headphones = Product("Headphones", 8000)

    electronicBox = ProductBox("Electronics")
    electronicBox.addItem(phone)
    electronicBox.addItem(charger)
    electronicBox.addItem(headphones)

    print("Product 1:", hammer)
    print("Electronic Item Box:", electronicBox)

    print()

    print("Product 1 Cost:", hammer.cost())
    print("Electronic Box Cost:", electronicBox.cost())



