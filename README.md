#Welcome to my lunch!

This application is just the best application for lunch matching the ingredients in your fridge! 

## Want to run it?

You will require:
- Maven
- Java 8 SDK to build
- Docker (if you really want to be hype)

### Maven `build test run`

```
$ mvn clean test spring-boot:run
```

Application will be accessible on `http://localhost:8080/lunch`


### Docker `build run`

```
$ mvn clean package
$ docker build -t mylunch .
$ docker run -p 8080:8080 mylunch
```

### Docker `build run`

```
$ mvn clean package
$ docker build -t mylunch .
$ docker run -p 8080:8080 mylunch
```

## Implementation Discussions

- **Folder organisation:** Folder organisation in software engineering is always a hard discussion to have but I am a big 
    fan of feature/domain based organisation where each part of the business layer is not split in 
    layers (services/repository/...) but by feature domain (recipe, ingredient).
- **Service/Repository:** I purposefully not implemented a service layer for the ingredient repository since there is no 
    business logic to be implemented at this stage. Some prefer to keep only the service layer, some 
    others repository. It all depends how you manage your transactions. 
- **Caching:** I decided to call the data endpoint on both repositories each time synchronously. 
    But depending on different factors: 
    - are those ingredients/recipe fixed? 
    - How synchronous do we have to be? 
    - Is eventual consistency ok? 
    - How many calls will be done towards `/lunch`
    - ...
    I would strongly recommend to cache those data at the repository layer and to perform that call only when we really need it.
- `TODO` **Exceptions:** I tend to avoid verbose exception handling and rethrow unchecked RuntimeException. I prefer when the callee provides
    information about the exception it could raise (the one that he expects). Clearly not a lot of error handling was done properly
    here and we need to return **at least** a proper 500 if something goes wrong. For the purpose of this small task 
    I did not over-engineer the error handling design. You can see an overview of how I like to handle exception on the RecipeRepository objects.
- `TODO` **Environment profile:** We should change the application properties depending on the env we want.
- This list is probably not exhaustive. Feel free to ask any questions :)
