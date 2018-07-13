echo "Generating Zookeeper StatefulSet..."
kops toolbox template \
--template containers/zookeeper/templates/jasmine_zookeeper_stateful_set_template.yml \
--values containers/zookeeper/config/jasmine_zookeeper_stateful_set_config.yml \
--output containers/zookeeper/stateful_set/jasmine_zookeeper_stateful_set.yml \
--format-yaml
echo "Zookeeper StatefulSet yml file ready!"
