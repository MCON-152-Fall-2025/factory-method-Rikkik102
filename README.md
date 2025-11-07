# RecipeShare

RecipeShare is a web application built with Spring Boot that allows users to create, search, and share recipes. The goal of RecipeShare is to provide an easy-to-use platform for discovering new recipes, sharing your own, and connecting with other cooking enthusiasts.

## Goals

- Allow users to create and manage their own recipes.
- Enable searching and browsing of recipes by category, ingredients, or keywords.
- Provide sharing features so users can share recipes with others.
- Offer a simple, intuitive web interface.

## Features

- RESTful API for recipe management.
- Web-based user interface.
- Recipe search and filtering.
- Recipe sharing functionality.

## Installation

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- Git

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/recipeshare.git
   cd recipeshare
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   The application will start on [http://localhost:8081](http://localhost:8081).

## Usage

- Access the home page at [http://localhost:8081](http://localhost:8081).
- Use the API endpoints to create, search, and share recipes (see API documentation for details).

## Example JSON payloads

The API accepts a `type` field to indicate the recipe subtype. If `type` is omitted the factory defaults to `BASIC`.
Each payload may also include `servings` (an integer) in addition to the usual `title`, `description`, `ingredients`, and `instructions`.

- Create a BASIC recipe (or omit `type` to default to BASIC):

```json
{
  "type": "BASIC",
  "title": "Simple Cake",
  "description": "A plain, delicious cake",
  "ingredients": "flour, sugar, eggs, butter",
  "instructions": "Mix ingredients and bake at 350F for 30 minutes",
  "servings": 8
}
```

- Create a VEGETARIAN recipe:

```json
{
  "type": "VEGETARIAN",
  "title": "Veggie Pasta",
  "description": "Pasta with fresh vegetables",
  "ingredients": "pasta, tomatoes, zucchini, olive oil, garlic",
  "instructions": "Sauté vegetables, cook pasta, toss together",
  "servings": 4
}
```

- Create a DESSERT recipe:

```json
{
  "type": "DESSERT",
  "title": "Chocolate Mousse",
  "description": "Light and airy chocolate mousse",
  "ingredients": "chocolate, eggs, sugar, cream",
  "instructions": "Melt chocolate, fold in whipped cream and egg whites, chill",
  "servings": 6
}
```

- Create a DAIRY recipe:

```json
{
  "type": "DAIRY",
  "title": "Cheesy Potato Gratin",
  "description": "Creamy potato gratin with cheese",
  "ingredients": "potatoes, cream, gruyere, butter, garlic",
  "instructions": "Layer potatoes and cheese, bake until golden",
  "servings": 6
}
```

You can POST these JSON bodies to the create endpoint:

```bash
curl -X POST http://localhost:8081/api/recipes \
  -H "Content-Type: application/json" \
  -d '{"type":"VEGETARIAN","title":"Veggie Pasta","description":"...","ingredients":"...","instructions":"...","servings":4}'
```

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.

## Contact

For questions or feedback, please open an issue on GitHub.

## Reference Documentation

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Apache Maven Documentation](https://maven.apache.org/guides/index.html)
I have accessed this file 
