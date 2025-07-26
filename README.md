# Crave Clock Portal

A time-saving corporate dining platform built with Spring Boot, designed specifically for corporate employees to discover menus, compare options, and make informed dining decisions without physically visiting cafeterias. The platform eliminates the need to walk around checking menus, saving valuable time during lunch breaks and meal planning.

## 🍽️ Features

### Core Functionality

- **Employee Authentication & Registration** - Secure JWT-based authentication for corporate employees
- **Menu Discovery & Comparison** - Browse all cafeteria menus in one place without physical visits
- **Real-time Menu Updates** - View current day's menus, specials, and availability across all cafeterias
- **Smart Recommendations** - Get personalized meal suggestions based on preferences and past choices
- **Time-Saving Features** - Quick menu browsing, favorites, and one-click ordering
- **Cafeteria Status** - Check crowd levels, wait times, and operating hours remotely
- **Corporate Payment Integration** - Employee meal allowances, corporate cards, and expense tracking
- **Push Notifications** - Real-time updates on menu changes, specials, and meal readiness
- **Employee Profiles** - Manage dietary preferences, favorites, and meal history for faster decisions

### Technical Features

- **RESTful API** - Comprehensive REST endpoints for all operations
- **Database Integration** - PostgreSQL database with JPA/Hibernate
- **Security** - Spring Security with JWT authentication
- **Logging** - Comprehensive logging with file rotation
- **Docker Support** - Containerized deployment ready
- **Environment Configuration** - Flexible configuration management

## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 3.5.3
- **Java Version**: 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA with Hibernate
- **Security**: Spring Security with JWT
- **Build Tool**: Maven
- **Containerization**: Docker
- **Logging**: SLF4J with Logback

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database
- Docker (optional, for containerized deployment)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Crave_Clock_Portal
```

### 2. Database Setup

Create a PostgreSQL database and update the configuration in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/craveclock_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Environment Variables

Set the following environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/craveclock_db
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

### 4. Build and Run

#### Using Maven

```bash
# Build the project
mvn clean package

# Run the application
java -jar target/Crave_Clock_Portal-0.0.1-SNAPSHOT.jar
```

#### Using Maven Wrapper

```bash
# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run
```

### 5. Docker Deployment

```bash
# Build Docker image
docker build -t crave-clock-portal .

# Run container
docker run -p 8080:8080 crave-clock-portal
```

## 🌐 API Documentation

The application runs on port 8082 by default. All endpoints are prefixed with `/api`.

### Authentication Endpoints

#### User Registration

```http
POST /api/onBoardUser
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phoneNumber": "+1234567890",
  "address": "123 Main St, City, State"
}
```

#### User Login

```http
POST /api/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

### Menu Discovery & Comparison Endpoints

#### Get All Cafeterias with Current Menus

```http
GET /api/cafeterias/menus
```

#### Get Real-time Menu for Specific Cafeteria

```http
GET /api/cafeterias/{cafeteriaId}/menu
```

#### Compare Menus Across Cafeterias

```http
GET /api/cafeterias/compare?cafeteriaIds=1,2,3
```

#### Get Today's Specials Across All Cafeterias

```http
GET /api/cafeterias/specials
```

#### Search Menus by Dish Type

```http
GET /api/cafeterias/search?dishType=vegetarian&mealType=lunch
```

#### Get Cafeteria Status (Crowd Level, Wait Times)

```http
GET /api/cafeterias/{cafeteriaId}/status
```

### Meal Pre-ordering Endpoints

#### Pre-order a Meal

```http
POST /api/meals/preorder
Content-Type: application/json

{
  "employeeId": 1,
  "cafeteriaId": 1,
  "mealType": "LUNCH",
  "pickupTime": "12:30",
  "items": [
    {
      "menuId": 1,
      "quantity": 1,
      "specialInstructions": "No onions, extra cheese"
    }
  ]
}
```

#### Get Employee's Pre-orders

```http
GET /api/meals/preorders/{employeeId}
```

#### Update Pre-order

```http
PUT /api/meals/preorders/{orderId}
Content-Type: application/json

{
  "pickupTime": "13:00",
  "items": [
    {
      "menuId": 2,
      "quantity": 1
    }
  ]
}
```

#### Cancel Pre-order

```http
DELETE /api/meals/preorders/{orderId}
```

### Order Management Endpoints (Corporate Dining)

### Order Management Endpoints

#### Place Corporate Meal Order

```http
POST /api/orders
Content-Type: application/json

{
  "employeeId": 1,
  "cafeteriaId": 1,
  "mealType": "LUNCH",
  "paymentMethod": "CORPORATE_CARD",
  "items": [
    {
      "menuId": 1,
      "quantity": 1,
      "specialInstructions": "Vegetarian option, no dairy"
    }
  ]
}
```

#### Get Employee Meal History

```http
GET /api/orders/employee/{employeeId}
```

#### Get Corporate Expense Report

```http
GET /api/orders/expenses/{employeeId}?startDate=2024-01-01&endDate=2024-01-31
```

### Payment Endpoints

#### Initiate Corporate Payment

```http
POST /api/payment/initiate
Content-Type: application/json

{
  "employeeId": 1,
  "orderId": 1,
  "amount": 15.75,
  "paymentMethod": "CORPORATE_CARD",
  "expenseCategory": "MEALS"
}
```

#### Update Payment Status

```http
POST /api/payment/update-status?paymentId=1&status=SUCCESS&transactionId=txn123
```

#### Get Payment Status

```http
GET /api/payment/status/{paymentId}
```

### Profile Management Endpoints

#### Get Employee Profile

```http
GET /api/profile/{employeeId}
```

#### Update Employee Profile

```http
PUT /api/profile/{employeeId}
Content-Type: application/json

{
  "fullName": "John Doe",
  "employeeId": "EMP001",
  "department": "Engineering",
  "dietaryPreferences": ["VEGETARIAN", "NO_DAIRY"],
  "allergies": ["NUTS"],
  "mealAllowance": 25.00
}
```

#### Update Employee Push Token

```http
PUT /api/profile/{employeeId}/push-token
Content-Type: application/json

{
  "expoPushToken": "ExponentPushToken[xxxxxxxxxxxxxxxxxxxxxx]"
}
```

#### Get Personalized Menu Recommendations

```http
GET /api/cafeterias/recommendations/{employeeId}
```

#### Get Employee's Favorite Dishes

```http
GET /api/cafeterias/favorites/{employeeId}
```

#### Add Dish to Favorites

```http
POST /api/cafeterias/favorites
Content-Type: application/json

{
  "employeeId": 1,
  "menuItemId": 1,
  "cafeteriaId": 1
}
```

#### Get Corporate Cafeteria Announcements

```http
GET /api/cafeterias/{cafeteriaId}/announcements
```

## 📁 Project Structure

```
src/main/java/com/example/crave/clock/portal/
├── controller/          # REST API controllers
├── dto/                # Data Transfer Objects
├── entity/             # JPA entities
├── filters/            # JWT authentication filters
├── repository/         # Data access layer
├── request/            # Request models
├── response/           # Response models
├── security/           # Security configuration
├── service/            # Service interfaces
├── serviceImpl/        # Service implementations
└── util/               # Utility classes
```

## 🔧 Configuration

### Application Properties

Key configuration options in `application.properties`:

- `server.port`: Application port (default: 8082)
- `spring.jpa.hibernate.ddl-auto`: Database schema generation (update/create)
- `logging.file.name`: Log file location
- `logging.level.com.example`: Debug logging level

### Security Configuration

- JWT-based authentication
- Stateless session management
- CSRF disabled for API endpoints
- All `/api/**` endpoints are publicly accessible

## 📊 Database Schema

The application uses the following main entities:

- `OnboardedUserEntity` - Employee accounts and profiles
- `VendorEntity` - Corporate cafeteria information
- `MenuItemEntity` - Menu items and pricing
- `PreOrderEntity` - Meal pre-orders
- `OrderEntity` - Corporate meal order information
- `OrderItemEntity` - Individual order items
- `PaymentEntity` - Corporate payment transactions
- `ExpenseEntity` - Employee meal expense tracking
- `FavoriteEntity` - Employee's favorite dishes for quick access
- `CafeteriaStatusEntity` - Real-time cafeteria crowd levels and wait times

## 🚀 Deployment

### Production Deployment

1. Set up a PostgreSQL database
2. Configure environment variables
3. Build the application: `mvn clean package`
4. Deploy the JAR file to your server
5. Run: `java -jar Crave_Clock_Portal-0.0.1-SNAPSHOT.jar`

### Docker Deployment

```bash
# Build image
docker build -t crave-clock-portal .

# Run with environment variables
docker run -d \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/db \
  -e SPRING_DATASOURCE_USERNAME=user \
  -e SPRING_DATASOURCE_PASSWORD=pass \
  crave-clock-portal
```

## 📝 Logging

The application uses structured logging with the following features:

- Console and file logging
- Log rotation (10MB max file size, 50MB total cap)
- Debug level logging for application packages
- Timestamp-based log patterns

Log files are stored in the `logs/` directory.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:

- Create an issue in the repository
- Contact the development team
- Check the application logs for debugging information

## ⏰ Time-Saving Benefits

This platform is specifically designed to save employees' valuable time:

- **No More Walking Around** - View all cafeteria menus from your desk
- **Quick Decision Making** - Compare options across multiple cafeterias instantly
- **Smart Recommendations** - Get personalized suggestions based on your preferences
- **Favorites System** - Quick access to your preferred dishes
- **Real-time Updates** - Know about menu changes and specials without visiting
- **Crowd Monitoring** - Check wait times and crowd levels before heading out
- **One-Click Ordering** - Order your favorite meals with minimal clicks

## 🔄 Version History

- **v0.0.1-SNAPSHOT**: Initial release with core functionality
  - Employee authentication and registration
  - Real-time menu discovery across all cafeterias
  - Menu comparison and search functionality
  - Personalized recommendations and favorites
  - Cafeteria status monitoring (crowd levels, wait times)
  - Meal pre-ordering system
  - Corporate meal order processing
  - Corporate payment and expense tracking
  - Push notifications for menu updates and meal readiness
