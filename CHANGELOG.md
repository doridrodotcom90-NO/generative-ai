# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0-SNAPSHOT] - 2026-06-18

### Added (Phase 1: Core Commerce)

#### Authentication & Authorization
- User login with email/phone and password
- JWT token-based authentication
- Role-based access control (RBAC)
- Refresh token mechanism
- User management endpoints
- Permission system
- Branch management
- Audit logging for critical actions

#### Product Management
- Create, read, update, delete products
- Product categories and brands
- Barcode/IMEI tracking
- Inventory management
- Stock transactions
- Product units (physical instances)

#### Sales & POS
- Create sales orders
- Sales item management
- Customer management
- Invoice generation
- Payment recording
- Support for multiple sale types (CASH, EMI, MIXED)
- Grand total calculation with tax and discount

#### Accounting
- Double-entry ledger system
- Chart of accounts
- Journal entry creation
- Journal entry lines
- Bank account management
- Account types (Asset, Liability, Equity, Income, Expense)

#### Infrastructure
- Spring Boot 3.2.0 with Java 17
- MySQL database with Liquibase migrations
- API Gateway for service routing
- Swagger/OpenAPI documentation
- Docker support with docker-compose
- GitHub Actions CI/CD workflows
- Comprehensive error handling
- Standard API response format

### API Endpoints (Phase 1)

#### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/logout` - User logout
- `POST /api/v1/auth/refresh-token` - Refresh access token
- `GET /api/v1/roles` - List all roles
- `GET /api/v1/permissions` - List all permissions

#### Users & Branches
- `POST /api/v1/users` - Create user
- `GET /api/v1/users/{id}` - Get user details
- `PUT /api/v1/users/{id}` - Update user
- `GET /api/v1/branches` - List branches

#### Products
- `POST /api/v1/products` - Create product
- `GET /api/v1/products` - List products (paginated)
- `GET /api/v1/products/{id}` - Get product details
- `PUT /api/v1/products/{id}` - Update product
- `DELETE /api/v1/products/{id}` - Delete product (soft delete)

#### Sales
- `POST /api/v1/sales` - Create sale
- `GET /api/v1/sales/{id}` - Get sale details
- `POST /api/v1/sales/{id}/confirm` - Confirm sale

#### Accounting
- `GET /api/v1/journal-entries/{id}` - Get journal entry
- `GET /api/v1/journal-entries/by-date-range` - Get entries by date range

### Documentation
- Comprehensive README.md
- Development setup guide
- Branch protection rules and workflow
- Contributing guidelines
- Architecture decision records (planned)
- API specification (Swagger)

### CI/CD & DevOps
- GitHub Actions workflow for building and testing
- Docker image building and publishing
- Code quality checks (CheckStyle, PMD, SpotBugs)
- Test coverage reporting (Codecov)
- Automated deployment to staging
- SonarQube integration (optional)

### Security
- JWT-based authentication
- Password hashing with bcrypt
- RBAC with field-level permissions
- Audit logging
- Input validation
- SQL injection prevention
- CORS configuration

## [Unreleased]

### Phase 2: EMI & Collections (Upcoming)
- EMI contract management
- Installment scheduling
- Payment collection
- Due reporting
- Customer device mapping
- Collection tracking

### Phase 3: Smart Lock Integration (Upcoming)
- Device registry
- Lock/Unlock commands
- Device health monitoring
- Sync logs
- Battery tracking
- Offline synchronization

### Phase 4: Mobile Banking & Reconciliation (Upcoming)
- bKash integration
- Nagad integration
- Rocket integration
- Transaction logging
- Settlement tracking
- Commission management
- Auto-posting to ledger
- Reconciliation reports

## Notes

- All endpoints return standardized API response format
- Database migrations run automatically on startup
- All services are stateless and horizontally scalable
- API documentation available at `/swagger-ui.html`