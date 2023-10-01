
from abc import ABC, abstractmethod

class Burger(ABC):
    @abstractmethod
    def create(self):
        pass

    def cookBurger(self):
        try:
            self.create()
            print(f'Cooked {self.__class__.__name__}( {", ".join(self.ingredients)} )')
        except AttributeError as e:
            print("Cannot Create Burger.\nCause: No ingredients found")
        except Exception as e:
            print("Cannot Create Burger.\nCause: " + e.__class__.__name__)


class CheeseBurger(Burger):

    def create(self):
        self.ingredients = ['cheese', 'meat']

class VegBurger(Burger):

    def create(self):
        self.ingredients = ['vegetables', 'capciucm', 'mushrooms']


order = 'veg'

burger = CheeseBurger() if order == 'cheese' else VegBurger()
burger = Burger()
burger.cookBurger()

