# Contributing to ERP Commerce Platform

Thank you for your interest in contributing! This guide will help you get started.

## Code of Conduct

- Be respectful and inclusive
- Focus on the code, not the person
- Help others learn and grow
- Report issues through proper channels

## Getting Started

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/your-feature`
3. **Make changes** with clear commit messages
4. **Run tests**: `mvn test`
5. **Create a Pull Request**

## Development Workflow

### Before Starting
- Check existing issues and PRs
- Comment on issue if you plan to work on it
- Wait for assignment confirmation

### While Working
- Write clean, readable code
- Follow existing code style
- Add tests for new features
- Update documentation
- Keep commits focused and atomic

### Before Submitting PR
- Run `mvn clean verify`
- Ensure tests pass
- Update CHANGELOG.md
- Fill out PR template completely

## Code Style

### Java
- Follow Google Java Style Guide
- Use 4 spaces for indentation
- Max line length: 120 characters
- Use meaningful variable names
- Add JavaDoc for public methods

### Naming Conventions
```java
// Classes: PascalCase
public class UserService { }

// Methods/Variables: camelCase
public void getUserById(Long id) { }
private String userName;

// Constants: UPPER_SNAKE_CASE
private static final String API_KEY = "key";

// Package names: lowercase
package com.erp.commerce.auth;
```

## Testing Requirements

- Minimum 80% code coverage
- Unit tests for all business logic
- Integration tests for APIs
- Test names should be descriptive

```java
// Good
@Test
public void createUser_withValidData_shouldReturnNewUser() { }

// Bad
@Test
public void test1() { }
```

## Commit Messages

See [BRANCH_PROTECTION.md](./BRANCH_PROTECTION.md#commit-message-convention) for detailed guidelines.

```bash
# Format
[MODULE] Brief description

Optional detailed explanation.
Explain WHAT and WHY, not HOW.

Closes #123

# Example
git commit -m "[AUTH] Add JWT token refresh endpoint

Implements refresh token logic to extend user sessions.
Tokens expire after 24 hours and can be renewed.

Closes #456"
```

## Documentation

- Update README.md for user-facing changes
- Update code comments for complex logic
- Add JavaDoc for public APIs
- Update CHANGELOG.md with PR title

## Review Process

1. Automated checks must pass
2. At least 1 code review required
3. All feedback must be addressed
4. Maintainer will merge after approval

## Reporting Issues

- Check if issue already exists
- Provide clear description
- Include steps to reproduce
- Attach screenshots/logs if relevant
- Specify your environment

## Questions?

- Check documentation first
- Search existing issues
- Ask in team discussion
- Contact project maintainers

**Happy coding! 🚀**