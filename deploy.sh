#!/bin/bash

# Production Deployment Script for Blog Application
echo "🚀 Deploying Blog Application to Production..."

# Set variables
APP_NAME="blog-application"
VERSION="1.0.0"
ENVIRONMENT=${1:-production}

# Function to print colored output
print_status() {
    echo -e "\033[1;32m$1\033[0m"
}

print_error() {
    echo -e "\033[1;31m$1\033[0m"
}

print_warning() {
    echo -e "\033[1;33m$1\033[0m"
}

# Check if environment is provided
if [ -z "$1" ]; then
    print_warning "⚠️  No environment specified, using 'production'"
fi

# Validate environment
if [[ ! "$ENVIRONMENT" =~ ^(development|staging|production)$ ]]; then
    print_error "❌ Invalid environment. Use: development, staging, or production"
    exit 1
fi

print_status "🎯 Deploying to: $ENVIRONMENT"

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    print_error "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Create environment-specific docker-compose file
print_status "📝 Creating environment-specific configuration..."
cp docker-compose.yml docker-compose.$ENVIRONMENT.yml

# Environment-specific configurations
case $ENVIRONMENT in
    "production")
        print_status "🏭 Configuring for production..."
        # Update docker-compose for production
        sed -i 's/MYSQL_ROOT_PASSWORD: rootpassword/MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD:-secure_root_password}/' docker-compose.$ENVIRONMENT.yml
        sed -i 's/MYSQL_PASSWORD: blogpassword/MYSQL_PASSWORD: ${MYSQL_PASSWORD:-secure_blog_password}/' docker-compose.$ENVIRONMENT.yml
        sed -i 's/SPRING_DATASOURCE_PASSWORD: blogpassword/SPRING_DATASOURCE_PASSWORD: ${MYSQL_PASSWORD:-secure_blog_password}/' docker-compose.$ENVIRONMENT.yml
        ;;
    "staging")
        print_status "🧪 Configuring for staging..."
        sed -i 's/8080:8080/8081:8080/' docker-compose.$ENVIRONMENT.yml
        sed -i 's/3307:3306/3308:3306/' docker-compose.$ENVIRONMENT.yml
        ;;
    "development")
        print_status "💻 Configuring for development..."
        # Development uses default settings
        ;;
esac

# Pull latest images
print_status "📥 Pulling latest base images..."
docker-compose -f docker-compose.$ENVIRONMENT.yml pull

# Build the application
print_status "🔨 Building application..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    print_error "❌ Maven build failed!"
    exit 1
fi

# Build Docker images
print_status "🐳 Building Docker images..."
docker-compose -f docker-compose.$ENVIRONMENT.yml build

if [ $? -ne 0 ]; then
    print_error "❌ Docker build failed!"
    exit 1
fi

# Stop existing services
print_status "🛑 Stopping existing services..."
docker-compose -f docker-compose.$ENVIRONMENT.yml down

# Start services with zero-downtime deployment
print_status "🚀 Starting services..."
docker-compose -f docker-compose.$ENVIRONMENT.yml up -d

if [ $? -ne 0 ]; then
    print_error "❌ Failed to start services!"
    exit 1
fi

# Wait for services to be healthy
print_status "⏳ Waiting for services to be ready..."
timeout=300
counter=0

while [ $counter -lt $timeout ]; do
    if docker-compose -f docker-compose.$ENVIRONMENT.yml ps | grep -q "Up (healthy)"; then
        break
    fi
    sleep 5
    counter=$((counter + 5))
    echo -n "."
done

echo ""

if [ $counter -ge $timeout ]; then
    print_error "❌ Services failed to become healthy within timeout"
    docker-compose -f docker-compose.$ENVIRONMENT.yml logs
    exit 1
fi

# Health check
print_status "🔍 Performing health checks..."
sleep 10

# Check application health
APP_PORT=$(docker-compose -f docker-compose.$ENVIRONMENT.yml port blog-app 8080 | cut -d: -f2)
if curl -f http://localhost:$APP_PORT/actuator/health > /dev/null 2>&1; then
    print_status "✅ Application health check passed"
else
    print_error "❌ Application health check failed"
    docker-compose -f docker-compose.$ENVIRONMENT.yml logs blog-app
    exit 1
fi

# Show service status
print_status "📊 Service Status:"
docker-compose -f docker-compose.$ENVIRONMENT.yml ps

# Show recent logs
print_status "📋 Recent logs:"
docker-compose -f docker-compose.$ENVIRONMENT.yml logs --tail=10

print_status "✅ Deployment completed successfully!"
print_status "🌐 Application URL: http://localhost:$APP_PORT"
print_status "📊 Swagger UI: http://localhost:$APP_PORT/swagger-ui.html"
print_status "❤️  Health Check: http://localhost:$APP_PORT/actuator/health"

# Cleanup
rm -f docker-compose.$ENVIRONMENT.yml

echo ""
echo "Deployment Summary:"
echo "  Environment: $ENVIRONMENT"
echo "  Version: $VERSION"
echo "  Status: ✅ Running"
echo ""
echo "Management Commands:"
echo "  View logs: docker-compose logs -f blog-app"
echo "  Scale app: docker-compose up -d --scale blog-app=3"
echo "  Stop: docker-compose down"
echo "  Update: ./deploy.sh $ENVIRONMENT"