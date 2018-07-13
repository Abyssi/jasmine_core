echo "Generating Kafka StatefulSet..."
kops toolbox template \
--template containers/kafka/templates/jasmine_kafka_stateful_set_template.yml \
--values containers/kafka/config/jasmine_kafka_stateful_set_config.yml \
--output containers/kafka/stateful_set/jasmine_kafka_stateful_set.yml \
--format-yaml
echo "Kafka StatefulSet yml file ready!"
