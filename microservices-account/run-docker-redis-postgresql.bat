echo "Run Redis"
docker run -p 6380:6379 --name myredis -v myredis-data:/data -d redis:6.0.6-alpine
echo "Success create redis container"
echo "Success create redis container"
echo "Success create redis container"
echo "Success create redis container"
echo "Success create redis container"
echo "Success create redis container"
echo "Success create redis container"
echo "Success create redis container"

echo "Run postgresql"
docker run -p 5433:5432 --name mypostgres -v mypg-data:/var/lib/postgresql/data -e POSTGRES_PASSWORD=P@ssw0rd postgres:11-alpine

echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
echo "Success create postgresql container"
