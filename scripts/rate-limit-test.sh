for i in {1..5}
do
  curl -i -X POST -H "Content-Type: application/json" -d '{"key1":"value"}' http://localhost:4567/greeting;
done