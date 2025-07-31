#!/bin/bash

echo "🧪 Testing Blog Application API..."

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Test health endpoint
echo -e "${YELLOW}1. Testing Health Endpoint...${NC}"
if curl -s http://localhost:8080/actuator/health | grep -q "UP"; then
    echo -e "${GREEN}✅ Health check passed${NC}"
else
    echo -e "${RED}❌ Health check failed${NC}"
    exit 1
fi

# Test user registration
echo -e "${YELLOW}2. Testing User Registration...${NC}"
USER_RESPONSE=$(curl -s -X POST http://localhost:8080/api/users/registerUser \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "Password123",
    "about": "Test user for API testing"
  }')

if echo "$USER_RESPONSE" | grep -q "Test User"; then
    echo -e "${GREEN}✅ User registration successful${NC}"
    USER_ID=$(echo "$USER_RESPONSE" | grep -o '"id":[0-9]*' | cut -d':' -f2)
    echo "   User ID: $USER_ID"
else
    echo -e "${RED}❌ User registration failed${NC}"
    echo "Response: $USER_RESPONSE"
fi

# Test getting all users
echo -e "${YELLOW}3. Testing Get All Users...${NC}"
if curl -s http://localhost:8080/api/users/multipleUser | grep -q "Test User"; then
    echo -e "${GREEN}✅ Get users successful${NC}"
else
    echo -e "${RED}❌ Get users failed${NC}"
fi

# Test creating a category
echo -e "${YELLOW}4. Testing Category Creation...${NC}"
CATEGORY_RESPONSE=$(curl -s -X POST http://localhost:8080/api/categories/save \
  -H "Content-Type: application/json" \
  -d '{
    "categoryTitle": "Technology",
    "categoryDescription": "Tech related posts"
  }')

if echo "$CATEGORY_RESPONSE" | grep -q "Technology"; then
    echo -e "${GREEN}✅ Category creation successful${NC}"
    CATEGORY_ID=$(echo "$CATEGORY_RESPONSE" | grep -o '"categoryId":[0-9]*' | cut -d':' -f2)
    echo "   Category ID: $CATEGORY_ID"
else
    echo -e "${RED}❌ Category creation failed${NC}"
    echo "Response: $CATEGORY_RESPONSE"
fi

echo -e "${GREEN}🎉 API testing completed!${NC}"
echo ""
echo "Next steps:"
echo "  - Visit http://localhost:8080/swagger-ui.html for interactive API docs"
echo "  - Check logs: docker-compose logs -f"
echo "  - Stop services: docker-compose down"