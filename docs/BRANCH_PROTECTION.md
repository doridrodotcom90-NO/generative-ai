# Branch Protection Rules & Git Workflow

## Overview
This document outlines the branch protection rules and development workflow for the ERP Commerce Platform project.

## Branch Strategy

We follow a modified Git Flow branching model:

### Main Branches

#### 1. **main** (Production)
- **Purpose**: Production-ready code only
- **Protection Rules**:
  - ✅ Require pull request reviews before merging (2+ approvals)
  - ✅ Require status checks to pass (CI/CD)
  - ✅ Require branches to be up to date before merging
  - ✅ Require code reviews from code owners
  - ✅ Dismiss stale pull request approvals
  - ✅ Require deployment to be successful (if applicable)
  - ✅ Require a linear history (no merge commits)
  - ❌ No direct pushes allowed
  - ❌ No force pushes allowed

#### 2. **develop** (Development/Staging)
- **Purpose**: Integration branch for features and fixes
- **Protection Rules**:
  - ✅ Require pull request reviews before merging (1+ approval)
  - ✅ Require status checks to pass (CI/CD)
  - ✅ Require branches to be up to date before merging
  - ✅ Allow squash merges only
  - ❌ No direct pushes allowed
  - ⚠️ Allow force pushes (limited to admins only)

### Supporting Branches

#### 3. **Feature Branches** (feature/*)
- **Naming**: `feature/FEATURE-NAME` or `feature/JIRA-123-feature-description`
- **Source**: Branch from `develop`
- **Target**: Merge back to `develop`
- **Rules**:
  - Must pass all CI checks
  - Requires at least 1 code review
  - Auto-delete head branch after merge

**Examples**:
```bash
git checkout -b feature/user-authentication
git checkout -b feature/JIRA-456-add-payment-gateway
git checkout -b feature/product-inventory
```

#### 4. **Bugfix Branches** (bugfix/*)
- **Naming**: `bugfix/BUG-NAME` or `bugfix/JIRA-789-bug-description`
- **Source**: Branch from `develop`
- **Target**: Merge back to `develop`
- **Rules**:
  - Must include test case for the bug
  - Requires at least 1 code review
  - Priority: High

**Examples**:
```bash
git checkout -b bugfix/login-error
git checkout -b bugfix/JIRA-789-invoice-calculation
```

#### 5. **Hotfix Branches** (hotfix/*)
- **Naming**: `hotfix/HOTFIX-NAME` or `hotfix/JIRA-999-critical-fix`
- **Source**: Branch from `main`
- **Target**: Merge to both `main` and `develop`
- **Rules**:
  - For critical production bugs only
  - Requires 2+ approvals
  - Fastest deployment process
  - Must create a release tag

**Examples**:
```bash
git checkout -b hotfix/security-patch
git checkout -b hotfix/JIRA-999-payment-failure
```

#### 6. **Release Branches** (release/*)
- **Naming**: `release/v1.0.0` or `release/1.0.0-rc1`
- **Source**: Branch from `develop`
- **Target**: Merge to `main` and back to `develop`
- **Rules**:
  - Bug fixes only (no new features)
  - Version bump in all pom.xml files
  - Create release notes
  - Tag final release on main

**Examples**:
```bash
git checkout -b release/v1.0.0
git checkout -b release/v2.1.0-beta
```

## Commit Message Convention

### Format
```
[MODULE] Brief description (50 chars max)

Optional detailed explanation.
Can span multiple lines.
Explain WHAT and WHY, not HOW.

Closes #123
Related to #456
```

### Module Tags
```
[AUTH]        - Authentication/Authorization changes
[PRODUCT]     - Product management changes
[SALES]       - Sales/POS changes
[EMI]         - EMI management changes
[DEVICE]      - Device management changes
[ACCOUNTING]  - Accounting/Ledger changes
[BANKING]     - Mobile banking changes
[REPORTING]   - Reporting/Analytics changes
[GATEWAY]     - API Gateway changes
[DB]          - Database migrations
[CI/CD]       - GitHub Actions/DevOps
[DOCS]        - Documentation changes
[INFRA]       - Infrastructure changes
```

### Examples
```
[AUTH] Add JWT token refresh logic

Implements refresh token endpoint that extends session duration.
Adds automatic token refresh on expiration.

Closes #123

---

[SALES] Fix invoice calculation with multiple discounts

Calculation now correctly applies:
1. Item-level discounts
2. Line-level discounts
3. Invoice-level discounts

Bugfix for critical issue in v1.2.0
Related to #456

---

[DB] Add EMI and device management tables

New migrations:
- 006-emi-tables.xml
- 007-device-tables.xml

---

[CI/CD] Add SonarQube integration to CI pipeline
```

## Pull Request Guidelines

### PR Title Format
```
[MODULE] Brief description - Issue #123
```

### PR Template
```markdown
## Description
Brief description of changes

## Related Issue
Closes #123

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Added unit tests
- [ ] Added integration tests
- [ ] Manual testing completed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex logic
- [ ] Documentation updated
- [ ] No new warnings generated
- [ ] Tests pass locally
- [ ] No breaking changes

## Screenshots (if applicable)
```

## Code Review Process

### Responsibilities of Author
1. Keep PR focused and small (< 400 lines)
2. Write clear commit messages
3. Add test coverage (>80%)
4. Update documentation
5. Address all review comments
6. Keep PR updated with `develop`

### Responsibilities of Reviewer
1. Review within 24 hours
2. Check code quality
3. Verify test coverage
4. Check for security issues
5. Provide constructive feedback
6. Approve with confidence

### Review Checklist
- [ ] Code is readable and maintainable
- [ ] Logic is correct and efficient
- [ ] Error handling is adequate
- [ ] Security best practices followed
- [ ] Tests are comprehensive
- [ ] Documentation is accurate
- [ ] No breaking changes (unless intended)
- [ ] Performance impact is acceptable

## Release Process

### 1. Create Release Branch
```bash
git checkout -b release/v1.0.0 develop
```

### 2. Update Version Numbers
- Update all `pom.xml` files
- Update `README.md` version
- Update `CHANGELOG.md`

### 3. Create PR to main
```bash
git push origin release/v1.0.0
# Create PR: develop -> main
```

### 4. Merge to main
- Requires 2+ approvals
- All CI checks must pass

### 5. Create Release Tag
```bash
git checkout main
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

### 6. Merge back to develop
```bash
git checkout develop
git merge --no-ff main
git push origin develop
```

## CI/CD Workflow

### On Every Push
1. ✅ Build Maven project
2. ✅ Run unit tests
3. ✅ Run integration tests
4. ✅ Code quality checks (SonarQube)
5. ✅ Build Docker images
6. ✅ Push to registry (if applicable)

### On Pull Request
1. ✅ All above checks
2. ✅ Require status checks to pass
3. ✅ Code review required
4. ✅ Approve before merge

### On Merge to develop
1. ✅ Deploy to staging environment
2. ✅ Run smoke tests
3. ✅ Notify team on Slack

### On Merge to main
1. ✅ Deploy to production
2. ✅ Health checks
3. ✅ Smoke tests
4. ✅ Notify team
5. ✅ Create GitHub release notes

## Forbidden Actions

⛔ **Never do this**:
- Direct pushes to `main` or `develop`
- Force push to `main`
- Merge without reviews
- Commit secrets or credentials
- Commit large binary files
- Commit node_modules, target/, .idea/
- Skip CI checks
- Create PR without description
- Merge your own PR

## Emergency Procedures

### Critical Production Bug
1. Create hotfix branch from `main`
2. Fix and test thoroughly
3. Create PR with 🔴 **URGENT** label
4. Require 2+ approvals
5. Deploy immediately after merge
6. Backport to `develop`

## Code Ownership

### CODEOWNERS File
```
# Auth Service
auth-service/                          @auth-team

# Product Service
product-service/                       @product-team

# Sales Service
sales-service/                        @sales-team

# Accounting Service
accounting-service/                   @accounting-team

# Infrastructure
.github/                              @devops-team
Dockerfile                            @devops-team
docker-compose.yml                    @devops-team

# Database
db/changelog/                         @database-team

# Default
*                                     @core-team
```

## Access Levels

### Admin Access
- Merge PRs without review (emergencies only)
- Force push to release branches
- Delete branches
- Manage GitHub settings

### Maintainer Access
- Review and merge PRs
- Create release branches
- Close issues

### Contributor Access
- Create feature branches
- Create PRs
- Comment on issues

## Troubleshooting

### Merge Conflict
```bash
# Update your branch
git fetch origin
git merge origin/develop

# Resolve conflicts manually
# Stage resolved files
git add .

# Complete merge
git commit -m "Resolve merge conflicts"
git push origin feature/your-feature
```

### Accidentally Pushed to main
```bash
# Contact DevOps team immediately
# Revert commit on main
git revert <commit-hash>
git push origin main
```

### Undo Last Commit (before push)
```bash
git reset --soft HEAD~1
# Make changes
git add .
git commit -m "Updated commit message"
```

## References
- [Git Flow Cheatsheet](https://danielkummer.github.io/git-flow-cheatsheet/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [GitHub Flow](https://guides.github.com/introduction/flow/)

## Questions?
Refer to the team's wiki or contact the DevOps lead.