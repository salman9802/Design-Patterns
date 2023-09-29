
class Singleton:
    # If you comment __new__() & uncomment pass
    # you will get different objects
    # pass
    
    def __new__(cls):
        if not hasattr(cls, 'instance'):
            cls.instance = super().__new__(cls)
        return cls.instance


s1 = Singleton()
s2 = Singleton()

print(s1 is s2)