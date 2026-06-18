# Development Setup Guide

## Prerequisites

### System Requirements
- **Java**: JDK 17 or higher
- **Maven**: 3.8.0 or higher
- **MySQL**: 8.0 or higher
- **Redis**: 7.0 or higher
- **Docker**: 20.0+ (optional but recommended)
- **Git**: 2.30+

### Required Tools
```bash
# Install Java
java -version

# Install Maven
mvn -version

# Install Docker (optional)
docker --version
docker-compose --version
```

## Local Development Setup

### 1. Clone the Repository
```bash
git clone https://github.com/doridrodotcom90-NO/generative-ai.git
cd generative-ai
git checkout feature/erp-spring-boot-setup
```

### 2. Set Up Database

#### Option A: Using Docker Compose (Recommended)
```bash
# Start MySQL and Redis
docker-compose up -d

# Verify services are running
docker-compose ps
```

#### Option B: Manual MySQL Setup
```bash
# Create database
mysql -u root -p

CREATE DATABASE erp_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'erp_user'@'localhost' IDENTIFIED BY 'erp_pass';
GRANT ALL PRIVILEGES ON erp_db.* TO 'erp_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

### 3. Configure Environment Variables

#### Create `.env` file
```bash
cp .env.example .env
```

#### Edit `.env`
```env
# Database
DB_URL=jdbc:mysql://localhost:3306/erp_db
DB_USER=erp_user
DB_PASSWORD=erp_pass

# JWT
JWT_SECRET=your-dev-secret-key-change-this

# Spring Profiles
SPRING_PROFILES_ACTIVE=dev

# Server Port
SERVER_PORT=8080
```

### 4. Build the Project
```bash
# Clean and build all modules
mvn clean install

# Build without running tests
mvn clean install -DskipTests

# Build specific module
mvn clean install -pl auth-service
```

### 5. Run Services

#### All Services at Once (Docker)
```bash
docker-compose -f docker-compose.dev.yml up
```

#### Individual Services (Maven)
```bash
# Terminal 1: API Gateway
mvn spring-boot:run -pl api-gateway -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Terminal 2: Auth Service
mvn spring-boot:run -pl auth-service -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Terminal 3: Product Service
mvn spring-boot:run -pl product-service -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Terminal 4: Sales Service
mvn spring-boot:run -pl sales-service -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Terminal 5: Accounting Service
mvn spring-boot:run -pl accounting-service -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### 6. Verify Setup

#### Check API Gateway
```bash
curl -s http://localhost:8080/health | jq .
```

#### Check Swagger UI
```
http://localhost:8080/swagger-ui.html
```

#### Test Login Endpoint
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "emailOrPhone": "admin@example.com",
    "password": "password123"
  }'
```

## IDE Setup

### IntelliJ IDEA

1. **Open Project**
   - File → Open → Select project root
   - Choose "Open as Project"

2. **Configure JDK**
   - File → Project Structure → Project
   - Set SDK to JDK 17

3. **Enable Maven Integration**
   - File → Settings → Build, Execution, Deployment → Maven
   - Ensure Maven home is configured

4. **Install Plugins**
   - Lombok plugin
   - SonarLint plugin

5. **Configure Run Configurations**
   - Add Maven run config for each service
   - Set profiles: `-Dspring.profiles.active=dev`

### VS Code

1. **Install Extensions**
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Maven for Java
   - REST Client

2. **Configure Settings**
   ```json
   {
     "java.home": "/path/to/jdk17",
     "maven.executable.path": "/path/to/maven/bin/mvn"
   }
   ```

## Database Migrations

### Run Migrations
```bash
# Automatic (on startup)
# Set in application.yml: spring.liquibase.enabled=true

# Manual (if needed)
mvn liquibase:update
```

### Create New Migration
```bash
# Create new changelog file
touch db/changelog/006-your-changes.xml

# Add to master.xml
<include file="db/changelog/006-your-changes.xml"/>
```

### Rollback Migration
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=1
```

## Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=AuthServiceTest
```

### Generate Coverage Report
```bash
mvn jacoco:report
# View report at: target/site/jacoco/index.html
```

### Integration Tests
```bash
mvn verify
```

## Common Development Tasks

### Add New Module Dependency
```bash
# Edit parent pom.xml
# Add to <module> section

# Or use Maven plugin
mvn archetype:generate -DgroupId=com.erp.commerce \
  -DartifactId=new-service \
  -DarchetypeArtifactId=maven-archetype-quickstart
```

### Format Code
```bash
mvn spotless:apply
```

### Check Code Quality
```bash
mvn clean verify sonar:sonar
```

### Build Docker Image
```bash
docker build -t erp-commerce:latest .
```

## Troubleshooting

### Maven Build Fails
```bash
# Clean cache
mvn clean
rm -rf ~/.m2/repository/com/erp/commerce

# Rebuild
mvn install -DskipTests
```

### Database Connection Error
```bash
# Check MySQL running
sudo service mysql status

# Or Docker
docker-compose ps

# Verify credentials in application.yml
```

### Port Already in Use
```bash
# Find process using port
lsof -i :8080

# Kill process
kill -9 <PID>

# Or use different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8085"
```

### Liquibase Lock Error
```bash
# Reset lock
mvn liquibase:releaseLocks

# Or directly in DB
DELETE FROM databasechangeloglock WHERE ID = 1;
```

## Performance Tips

1. **Use SSD** for faster build times
2. **Increase Maven heap**:
   ```bash
   export MAVEN_OPTS="-Xmx2048m"
   ```
3. **Enable parallel tests**:
   ```bash
   mvn test -P parallel
   ```
4. **Use Maven offline mode** (after first build):
   ```bash
   mvn -o clean install
   ```

## Debugging

### Debug Mode
```bash
mvn spring-boot:run -pl auth-service \
  -Dspring-boot.run.arguments="--debug"
```

### Remote Debugging
```bash
# Start with remote debug port
mvn spring-boot:run -pl auth-service \
  -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

# Connect from IDE on port 5005
```

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Maven Documentation](https://maven.apache.org/guides/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Project README](../README.md)
- [Branch Protection Rules](./BRANCH_PROTECTION.md)

## Getting Help

- Check existing GitHub issues
- Review team wiki
- Ask in team chat
- Contact DevOps lead