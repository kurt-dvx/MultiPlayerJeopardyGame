# MultiPlayer Jeopardy Game

A console-based multiplayer Jeopardy game application built with Java, implementing design patterns for extensibility and maintainability.

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Option 1: Using Microsoft Java Extension Pack (Recommended)

The easiest way to get started is to use the Microsoft Java Extension Pack:

1. Install the **[Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)** in VS Code
2. Open the project in VS Code
3. Click the **Run** button (▶️) in the editor to run the main application
4. The program will start automatically

### Option 2: Using Maven from Terminal

```bash
cd ./jeapordygame
mvn clean install
mvn exec:java -Dexec.mainClass="com.comp3607.Main"
```

## Project Structure

```
jeapordygame/
├── src/
│   ├── main/java/com/comp3607/
│   │   ├── Main.java                    # Application entry point
│   │   ├── config/                      # Configuration management
│   │   ├── factory/                     # Factory pattern for question parsers
│   │   ├── model/                       # Core game models (Player, Question, GameSession, etc.)
│   │   ├── observer/                    # Observer pattern for game notifications
│   │   ├── parser/                      # Question parsers (CSV, JSON, XML)
│   │   ├── service/                     # Business logic (GameService, EventLogService)
│   │   ├── strategy/                    # Strategy pattern for report generation
│   │   ├── template/                    # Template method pattern for parsing
│   │   └── UI/                          # Console UI components
│   ├── main/resources/                  # Sample game data files
│   └── test/
│       ├── java/com/comp3607/
│       │   ├── integration/             # Integration tests
│       │   │   └── GameIntegrationTest.java
│       │   └── unit/                    # Unit tests
│       │       ├── model/               # Model tests (PlayerTest, QuestionTest)
│       │       ├── parser/              # Parser tests (CSVQuestionParserTest, etc.)
│       │       ├── service/             # Service tests
│       │       └── strategy/            # Strategy tests
│       └── resources/                   # Test data files (test-questions.csv, etc.)
└── pom.xml                              # Maven configuration
```

## Key Features

- **Multi-player Support**: Support for multiple concurrent players
- **Flexible Question Loading**: Parse questions from CSV, JSON, or XML formats
- **Observer Pattern**: Real-time game notifications
- **Report Generation**: Generate game reports in multiple formats (PDF, Text)
- **Comprehensive Testing**: Unit and integration tests with JUnit 5 and Mockito

## Design Patterns

- **Factory Pattern**: `QuestionParserFactory` for creating appropriate parsers
- **Strategy Pattern**: `ReportGenerator` strategies for different output formats
- **Template Method**: `AbstractQuestionParser` for consistent parsing workflows
- **Observer Pattern**: `GameObserver` and `GameNotifier` for event handling
- **MVC Pattern**: Separation of UI, business logic, and data models

## Testing

### Using VS Code Java Extension Pack (Recommended)

1. Navigate to the test folder: `src/test`
2. Right-click on **test** and select **Run Tests** to run all tests
3. Alternatively, you can test at different levels:
   - Navigate to and right-click on the `integration/` or `unit/` folder and select **Run Tests** for specific groups
   - Right-click on a specific test file (e.g., `PlayerTest.java`) and select **Run Tests** for individual files
   - Click on a test method and use the Run/Debug code lens for single methods

### Using Maven from Terminal

Run all tests:
```bash
cd ./jeapordygame
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=PlayerTest
```

## Dependencies

- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework for tests
- **Jackson**: JSON processing
- **Apache PDFBox**: PDF report generation
- **Apache POI**: Excel/document handling

## License

This project is part of COMP3607 coursework.
