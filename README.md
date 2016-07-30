# setmaster
Example of real app with CleanArchitecture

This project demonstrate how create real app with well architecture

In project used:
- [CleanArchitecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) (simplifyed for Android)
- Simple MVP (how implement advanced MVP see [Ferro library](https://github.com/MaksTuev/ferro))
- [Dagger2](http://google.github.io/dagger/) 
- [RxJava](http://reactivex.io/)
- Useful packages structure

##Arhcitecture
The architecture is a simplified version of  [CleanArchitecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html).

The application divided into 4 layers:
- **Domain** - contains enterprise wide business rules, for most app this layer contains only core entities.
- **Interactor** - contains inner application's logic. This layer provides access to data storages and other services. Api of this layer should not depend on Android Framework and other libraries, besides infrastructural library like RxJava. Interactor layer consists of loosely bound modules, each of which has a well defined responsibility. Every module contains class, which provide access to functionality of this module. Other module's classes which contains details of the implementation should not be used by other parts of application. Inside this layer data objects, which are used by low-level storages, should be transformed to Domain layer's entities. A module can be used by Presenter or by other module. 
- **Presenter** - contains screen's logic
- **View** - is responsible for interaction with the user
In most cases, each layer must depends on only previous layer and Domain layer. 

##Packages structure
Packages sructure is hybrid of package-by-layer and package-by-feature styles.

Root package contains five subpackages
- **app** - contains the application class and other classes which is corresponding context of all applications (such as dagger application-level component)
- **domain** - corresponds Domain layer. If some entities is logically coupled, it should be placed inside appropriate subpackage.
- **interactor** - corresponds Interactor layer. Every module of this layer is placed inside appropriate subpackage.
- **ui** - corresponds Presenter and View layers. It contains four subpackages: 
  - **base** - contains base classes for View, Presenter, Dialog etc.
  - **screen** - each screen placed inside corresponding subpackges. This subpackage can contains View, Presenter, Dagger screen component, Dagger screen module, Adapters, ViewHolders, custom android.Views etc. If some of this classes is logically coupled, they should be placed inside appropriate subpackage. If some of screens is logically coupled, they should be placed inside appropriate subpackage inside screen package.
  - **common** - contains classes, which are used by several screens.
  - **util** - contains util classes, which are used only inside Presenter/View layers.
- **util** - util classes, which are used throughout the application.

Next example demonstrate how organize classes in Interactor layer's module.

For exmple you need create module for obtaining books from server or from cache. Then package for this module can look like this:
- **book** - is root module package, it contains BookRepository class and next subpackges:
  - **network** - contains api interface for retrofit and subpackages:
    - **response** - contains responce objects
    - **request** - contains request objects
  - **cache** - contains all classes, which is nessasry for book's cache, e.g. BookDao
  
BookRepository should transform objects which come from server and from cache to the Book entity.




