# Ждем пока Kafka запустится (примерная задержка, можно улучшить)
sleep 10

kafka-topics.sh --create --topic job4j_notification \
                --bootstrap-server kafka:9092 \
                --partitions 3 \
                --replication-factor 1 \
                --if-not-exists