# Calculator

A basic calculator written in Java. Evaluating the expression provided by the first
command line argument.

The expression must be passed as a single command-line argument, so it should be quoted.
Supported operators:
- `+`  addition
- `-`  subtraction
- `*`  multiplication
- `/`  division
- `()` grouping

## Usage

```console
./mvnw clean compile
java -cp target/calculator-1.0-SNAPSHOT.jar com.calculator.App "(1 + 2) * 4"
```
