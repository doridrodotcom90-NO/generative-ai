# Security Policy

## Reporting Security Issues

⚠️ **Do NOT** report security vulnerabilities publicly in GitHub issues.

Instead, please email: **security@erp-commerce.local** with details.

### What to Include
- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if any)

## Security Best Practices

### For Developers

1. **Never commit secrets**
   - Use `.env` files (add to `.gitignore`)
   - Use environment variables in production
   - Use AWS Secrets Manager or HashiCorp Vault

2. **Always validate input**
   ```java
   @NotBlank(message = "Email is required")
   @Email(message = "Email should be valid")
   private String email;
   ```

3. **Use parameterized queries**
   ```java
   // Good - prevents SQL injection
   Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
   
   // Bad - vulnerable
   Query query = em.createQuery("SELECT * FROM users WHERE email = '" + email + "'");
   ```

4. **Hash passwords**
   ```java
   passwordEncoder.encode(rawPassword);
   ```

5. **Log security events**
   ```java
   log.warn("Failed login attempt for user: {}", userId);
   ```

### For Deployment

1. **Keep dependencies updated**
   ```bash
   mvn dependency:check-updates
   ```

2. **Use HTTPS only**
   - Get SSL certificates
   - Configure in application.yml

3. **Enable CORS carefully**
   ```yaml
   allowedOrigins:
     - "https://yourdomain.com"
   ```

4. **Set secure headers**
   - X-Frame-Options: DENY
   - X-Content-Type-Options: nosniff
   - X-XSS-Protection: 1; mode=block

5. **Database security**
   - Use strong passwords
   - Limit database user privileges
   - Enable SSL for database connections
   - Regular backups
   - Encryption at rest

## Compliance

- GDPR: PII masking in reports
- SOC 2: Audit logging
- ISO 27001: Access controls

## Incident Response

1. **Assess severity**
2. **Isolate affected systems**
3. **Notify security team**
4. **Prepare patch**
5. **Deploy fix**
6. **Post-mortem analysis**

## Security Checklist

- [ ] No hardcoded secrets
- [ ] Input validation on all endpoints
- [ ] Output encoding
- [ ] HTTPS enabled
- [ ] SQL injection prevention
- [ ] XSS protection
- [ ] CSRF protection
- [ ] Rate limiting enabled
- [ ] Audit logging enabled
- [ ] Dependencies updated