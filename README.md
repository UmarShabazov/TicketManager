# TicketManager

TicketManager is a Java-based application designed to analyze flight tickets. It processes flight data to calculate flight durations, analyze ticket prices, and generate useful insights like price differences and minimum flight times.

## Features

- **Flight Duration Calculation**: Calculate the minimum flight duration for each carrier on specific routes.
- **Price Analysis**: Analyze and compare ticket prices (average, median, and the difference between them) for flights.
- **JSON Deserialization**: Import and deserialize flight ticket data from a JSON file for further analysis.

## Tech Stack

- **Java 17**: Core language for the application.
- **Jackson**: For JSON serialization and deserialization.
- **JUnit**: For unit testing.
- **Maven**: For dependency management and build automation.

## How it Works

The application processes flight data by reading a JSON file with details about flights, including origin, destination, carrier, price, and times of departure and arrival. It then provides two key insights:
1. **Minimum Flight Time by Carrier**: The application calculates the shortest flight time for each carrier operating flights between Vladivostok (VVO) and Tel Aviv (TLV).
2. **Price Analysis**: It also calculates the average and median prices for the flights and shows the difference between the two.

## Installation

1. **Clone the repository**:  
   git clone https://github.com/UmarShabazov/TicketManager.git  
   cd TicketManager

2. **Build the project with Maven**:  
   mvn clean install

3. **Run the application**:  
   java -jar target/TicketManager-1.0-SNAPSHOT.jar <path_to_tickets.json>  

   Replace `<path_to_tickets.json>` with the actual path to the JSON file containing ticket data.

4. **Example Command**:  
   java -jar target/TicketManager-1.0-SNAPSHOT.jar ./data/tickets.json

   ## Example Output

After running the application, it will display the following information:

1. **Minimum Flight Time by Carrier**: The application will print the minimum flight time for each carrier operating between Vladivostok (VVO) and Tel Aviv (TLV). The time is displayed in hours and minutes.

   Example output:
   Minimum flight time by carrier:
   Aeroflot: 12 hours and 30 minutes  
   S7 Airlines: 11 hours and 45 minutes

2. **Price Difference**: The application will also calculate the difference between the average and median prices for the flights.

   Difference between average price and median price: 50.25
