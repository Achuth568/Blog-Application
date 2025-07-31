#!/bin/bash

# Blog Application Build Script
echo "🚀 Building Blog Application..."

# Set variables
APP_NAME="blog-application"
VERSION="1.0.0"

# Function to print colored output
print_status() {
    echo -e "\033[1;32m$1\033[0m"
}

print_error() {
    echo -e "\033[1;31m$1\033[0m"
}

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    print_error "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Clean up previous containers and images
print_status "🧹 Cleaning up previous containers..."
docker-compose down --remove-orphans
docker system prune -f

# Build the application
print_status "🔨 Building Spring Boot application..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    print_error "❌ Maven build failed!"
    exit 1
fi

# Build Docker images
print_status "🐳 Building Docker images..."
docker-compose build --no-cache

if [ $? -ne 0 ]; then
    print_error "❌ Docker build failed!"
    exit 1
fi

# Start the services
print_status "🚀 Starting services..."
docker-compose up -d

if [ $? -ne 0 ]; then
    print_error "❌ Failed to start services!"
    exit 1
fi

# Wait for services to be healthy
print_status "⏳ Waiting for services to be ready..."
sleep 30

# Check service health
print_status "🔍 Checking service health..."
docker-compose ps

# Show logs
print_status "📋 Recent logs:"
docker-compose logs --tail=20

print_status "✅ Blog application is running!"
print_status "🌐 Application URL: http://localhost:8080"
print_status "📊 Swagger UI: http://localhost:8080/swagger-ui.html"
print_status "❤️  Health Check: http://localhost:8080/actuator/health"
print_status "🗄️  Database: localhost:3307 (blogdb/bloguser/blogpassword)"

echo ""
echo "Useful commands:"
echo "  View logs: docker-compose logs -f"
echo "  Stop services: docker-compose down"
echo "  Restart: docker-compose restart"
echo "  Shell access: docker exec -it blog-application bash"