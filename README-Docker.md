# 🐳 Containerized Blog Application

A fully containerized Spring Boot blogging application with MySQL, Redis, and Nginx reverse proxy.

## 📋 Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Deployment](#deployment)
- [API Documentation](#api-documentation)
- [Monitoring & Health Checks](#monitoring--health-checks)
- [Troubleshooting](#troubleshooting)
- [Production Considerations](#production-considerations)

## ✨ Features

- **Multi-container setup** with Docker Compose
- **MySQL database** for persistent data storage
- **Redis caching** for improved performance
- **Nginx reverse proxy** with SSL termination
- **Health checks** and monitoring
- **Environment-specific configurations**
- **Security hardening** with non-root containers
- **Automated deployment scripts**

## 🏗️ Architecture

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│    Nginx    │────│   Blog App  │────│   MySQL     │
│ (Port 80/443)│    │ (Port 8080) │    │ (Port 3306) │
└─────────────┘    └─────────────┘    └─────────────┘
                           │
                   ┌─────────────┐
                   │    Redis    │
                   │ (Port 6379) │
                   └─────────────┘
```

## 📋 Prerequisites

- **Docker** 20.10+ and **Docker Compose** 2.0+
- **Git** for cloning the repository
- **curl** for health checks (optional)
- **openssl** for SSL certificates (optional)

### Installation Commands

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install docker.io docker-compose-plugin git curl openssl

# CentOS/RHEL
sudo yum install docker docker-compose git curl openssl

# macOS (with Homebrew)
brew install docker docker-compose git curl openssl

# Start Docker service
sudo systemctl start docker
sudo systemctl enable docker
```

## 🚀 Quick Start

### 1. Clone and Build

```bash
# Clone the repository
git clone <repository-url>
cd blogging-application

# Make scripts executable
chmod +x build.sh deploy.sh

# Build and run the application
./build.sh
```

### 2. Access the Application

Once the containers are running:

- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health
- **Database**: localhost:3307 (blogdb/bloguser/blogpassword)
- **Redis**: localhost:6379

### 3. Test the Application

```bash
# Check health
curl http://localhost:8080/actuator/health

# Register a new user
curl -X POST http://localhost:8080/api/users/registerUser \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "Password123",
    "about": "Test user"
  }'
```

## ⚙️ Configuration

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_PROFILES_ACTIVE` | `docker` | Active Spring profile |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://mysql:3306/blogdb` | Database URL |
| `SPRING_DATASOURCE_USERNAME` | `bloguser` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | `blogpassword` | Database password |
| `MYSQL_ROOT_PASSWORD` | `rootpassword` | MySQL root password |
| `REDIS_HOST` | `redis` | Redis host |
| `REDIS_PORT` | `6379` | Redis port |

### Custom Configuration

Create a `.env` file in the project root:

```bash
# Database Configuration
MYSQL_ROOT_PASSWORD=your_secure_root_password
MYSQL_PASSWORD=your_secure_password
SPRING_DATASOURCE_PASSWORD=your_secure_password

# Application Configuration
SPRING_PROFILES_ACTIVE=production
```

## 🚀 Deployment

### Development Environment

```bash
./build.sh
```

### Staging Environment

```bash
./deploy.sh staging
```

### Production Environment

```bash
# Set environment variables
export MYSQL_ROOT_PASSWORD=secure_root_password
export MYSQL_PASSWORD=secure_blog_password

# Deploy
./deploy.sh production
```

### Manual Deployment

```bash
# Build the application
./mvnw clean package -DskipTests

# Start services
docker-compose up -d

# Scale the application (optional)
docker-compose up -d --scale blog-app=3

# View logs
docker-compose logs -f blog-app
```

## 📚 API Documentation

### Available Endpoints

#### User Management
- `POST /api/users/registerUser` - Register new user
- `GET /api/users/multipleUser` - Get all users
- `GET /api/users/singleUser/{userId}` - Get user by ID
- `PUT /api/users/update/{userId}` - Update user
- `DELETE /api/users/delete/{userId}` - Delete user (Admin only)

#### Post Management
- `POST /api/post/save/user/{userId}/category/{categoryId}` - Create post
- `GET /api/post/postList` - Get all posts (paginated)
- `GET /api/post/getPost/{postId}` - Get post by ID
- `PUT /api/post/update/{postId}` - Update post
- `DELETE /api/post/delete/{postId}` - Delete post
- `GET /api/post/search/{keyword}` - Search posts

#### Category Management
- `POST /api/categories/save` - Create category
- `GET /api/categories/get` - Get all categories
- `GET /api/categories/get/{categoryId}` - Get category by ID

### Swagger Documentation

Access the interactive API documentation at:
- **Local**: http://localhost:8080/swagger-ui.html
- **Production**: https://your-domain.com/swagger-ui.html

## 📊 Monitoring & Health Checks

### Health Endpoints

- **Application Health**: `/actuator/health`
- **Application Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`

### Container Health

```bash
# Check container status
docker-compose ps

# View container health
docker inspect blog-application --format='{{.State.Health.Status}}'

# Monitor logs
docker-compose logs -f
```

### Performance Monitoring

```bash
# Monitor resource usage
docker stats

# Check database connections
docker exec blog-mysql mysql -u bloguser -p -e "SHOW PROCESSLIST;"

# Redis monitoring
docker exec blog-redis redis-cli info
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Port Already in Use
```bash
# Find process using port 8080
sudo netstat -tlnp | grep :8080
sudo kill -9 <PID>

# Or use different ports
docker-compose down
sed -i 's/8080:8080/8081:8080/' docker-compose.yml
docker-compose up -d
```

#### 2. Database Connection Issues
```bash
# Check MySQL container
docker logs blog-mysql

# Test database connection
docker exec blog-mysql mysql -u bloguser -p -e "SELECT 1;"

# Reset database
docker-compose down -v
docker-compose up -d
```

#### 3. Application Won't Start
```bash
# Check application logs
docker logs blog-application

# Check if all dependencies are ready
docker-compose ps

# Restart specific service
docker-compose restart blog-app
```

#### 4. SSL Certificate Issues
```bash
# Regenerate certificates
rm -rf nginx/ssl/*
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout nginx/ssl/nginx.key -out nginx/ssl/nginx.crt \
  -subj "/C=US/ST=State/L=City/O=Organization/CN=localhost"
docker-compose restart nginx
```

### Debug Commands

```bash
# Enter application container
docker exec -it blog-application bash

# Check application properties
docker exec blog-application cat /app/application-docker.properties

# Database shell
docker exec -it blog-mysql mysql -u bloguser -p blogdb

# Redis CLI
docker exec -it blog-redis redis-cli

# Check nginx configuration
docker exec blog-nginx nginx -t
```

## 🏭 Production Considerations

### Security

1. **Change default passwords**:
   ```bash
   export MYSQL_ROOT_PASSWORD=$(openssl rand -base64 32)
   export MYSQL_PASSWORD=$(openssl rand -base64 32)
   ```

2. **Use proper SSL certificates**:
   ```bash
   # Replace self-signed certificates with real ones
   cp your-domain.crt nginx/ssl/nginx.crt
   cp your-domain.key nginx/ssl/nginx.key
   ```

3. **Configure firewall**:
   ```bash
   # Allow only necessary ports
   sudo ufw allow 80/tcp
   sudo ufw allow 443/tcp
   sudo ufw enable
   ```

### Performance

1. **Resource limits**:
   ```yaml
   # Add to docker-compose.yml
   deploy:
     resources:
       limits:
         memory: 1G
         cpus: '0.5'
   ```

2. **Database optimization**:
   ```sql
   -- MySQL configuration
   SET GLOBAL innodb_buffer_pool_size = 1073741824; -- 1GB
   SET GLOBAL max_connections = 200;
   ```

3. **Application scaling**:
   ```bash
   # Scale application instances
   docker-compose up -d --scale blog-app=3
   ```

### Backup & Recovery

```bash
# Database backup
docker exec blog-mysql mysqldump -u bloguser -p blogdb > backup.sql

# Volume backup
docker run --rm -v blog_mysql_data:/data -v $(pwd):/backup alpine tar czf /backup/mysql-backup.tar.gz /data

# Restore database
docker exec -i blog-mysql mysql -u bloguser -p blogdb < backup.sql
```

### Monitoring

Consider adding monitoring tools:
- **Prometheus** for metrics collection
- **Grafana** for visualization
- **ELK Stack** for log aggregation
- **Jaeger** for distributed tracing

## 📞 Support

For issues and questions:
1. Check the [Troubleshooting](#troubleshooting) section
2. Review container logs: `docker-compose logs`
3. Check application health: `curl http://localhost:8080/actuator/health`

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.