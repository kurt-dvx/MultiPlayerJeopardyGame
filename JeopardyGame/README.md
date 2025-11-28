# MultiPlayer Jeopardy Game

A console-based multiplayer Jeopardy game application built with Java, implementing design patterns for extensibility and maintainability.

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Building the Project

```bash
cd jeapordygame
mvn clean install
```

### Running the Game

```bash
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
│   ├── resources/                       # Sample game data files
│   └── test/                            # Unit and integration tests
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

Run all tests:
```bash
cd jeapordygame
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
