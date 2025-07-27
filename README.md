# 🌦️ Weather Summary API

This is a Spring Boot-based application that fetches 5-day weather forecast data for a given city using the [OpenWeatherMap API](https://openweathermap.org/forecast5), processes the data, and returns a summary including:

- Average temperature
- Hottest day
- Coldest day

---

## 🚀 Features

- ✅ Integration with OpenWeatherMap using `WebClient`
- ⚡ Asynchronous data fetching using `@Async` and `CompletableFuture`
- 🧠 Smart caching using Spring Cache to avoid redundant API calls
- 🔒 Robust error handling for invalid inputs and external API failures
- 🧪 Unit tested with `JUnit` and `Mockito`

---

## 📦 Tech Stack

- **Java 17+**
- **Spring Boot 3+**
  - Spring Web
  - Spring WebFlux (`WebClient`)
  - Spring Cache (with `ConcurrentHashMap`)
  - Spring Boot Starter Test
- **Lombok** (optional)
- **OpenWeatherMap API**

---

## 📡 API Endpoint

### `GET /weather?city={cityName}`

#### Query Parameters:
- `city` (required): The city name (e.g., `London`)

#### Example Request:
```
GET http://localhost:8080/weather?city=London
```

#### Example Response:
```json
{
  "city": "London",
  "averageTemperature": 15.4,
  "hottestDay": "2024-07-29",
  "coldestDay": "2024-07-27"
}
```

---

## 🔧 Setup Instructions

1. **Clone the repo**  
   ```bash
   git clone https://github.com/Manulakavishka/Weather.git
   cd Weather
   ```

2. **Add your OpenWeatherMap API Key**  
   In `application.properties`:
   ```properties
   openweathermap.api.key=YOUR_API_KEY
   ```

3. **Run the app**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Test the API**
   Open browser or use Postman:
   ```
   http://localhost:8080/weather?city=London
   ```

---

## 🧪 Running Tests

To run unit tests:

```bash
./mvnw test
```

Includes:
- Controller unit test using `MockMvc`
- Service test with mocked WebClient
- Cache and error-handling tests

---

## 🧠 Core Concepts Covered

| Challenge             | Status |
|----------------------|--------|
| External API with WebClient | ✅ |
| Asynchronous Execution (`@Async`) | ✅ |
| Caching with Spring Cache | ✅ |
| Robust Error Handling | ✅ |
| Unit Testing (Service + Controller) | ✅ |

---

## 📁 Project Structure

```
├── controller/
│   └── WeatherController.java
├── service/
│   └── WeatherService.java
├── model/
│   └── WeatherSummary.java
├── config/
│   └── CacheConfig.java
├── test/
│   └── WeatherControllerTest.java
```

---

## 📜 License

This project is for educational/demo purposes and is not intended for production use. Feel free to modify and extend it.

---

## 👨‍💻 Author

**Your Name**  
[GitHub](https://github.com/Manulakavishka)
