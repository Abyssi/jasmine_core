

# About
jasmine_core is the control and monitor JASMINE component that manages data stream processing.

# Configuration

JASMINE is customizable. It can be specified a path to a `.properties` file in order to override the configurations.
The following table shows configurations options:

|             property             | type    | description                                                                                                                                  | example        |
|--------------------------------- |---------|----------------------------------------------------------------------------------------------------------------------------------------------|----------------|
|flink.parallelism|int|flink parallelism|4|
|flink.operation-chaining.enabled=false|boolean|operation chaining|false|
|flink.checkpoint.millis|Long|checkpoint millis|60000|
|flink.latency.millis|Long|latency millis|30000|
|flink.snapshot-compression.enabled|boolean|snapshot compression|true|
|flink.memory-state-size|Long|memory state size|524288000|
|use.window.chaining|boolean|window chaining|false|
|use.redis.sink|boolean|redis sink|false|
|use.log.sink|boolean|log sink|false|
|flink.redis.host|String|redis host|www.jasmine.cf|
|flink.redis.port |int|redis port|32079|
|flink.kafka.bootstrap-servers |String|kafka bootstrap servers|www.jasmine.cf:32094|
|flink.kafka.consumer.group-id|String|kafka group id|core_group|
|kafka.semaphore.topic|String|semaphore topic|semaphore-topic|
|kafka.mobile.topic|String|mobile topic|mobile-topic|
|kafka.top.ten.crossroads.topic|String|top crossroads topic|top-ten-crossroads-|
|kafka.outlier.crossroads.topic|String|outlier crossroads topic|outlier-crossroads-|
|kafka.damaged.semaphore.topic|String|damaged traffic lights topic|damaged-semaphore|
|kafka.top.semaphore.route.topic|String|semaphore route topic|top-semaphore-route|
|kafka.semaphore.controller.topic|String|controller topic|semaphore-controller|
|kafka.topic.suffix|String|Kafka topic suffix|-topic|
|kafka.small.window|Long|small window|900000|
|kafka.small.window.slide|Long|small window slide|60000|
|kafka.medium.window|Long|medium window|3600000|
|kafka.medium.window.slide|Long|medium window slide|240000|
|kafka.large.window|Long|large window|86400000|
|kafka.large.window.slide|Long|large window slide|5760000|
|kafka.route.window|Long|route window|300000|
|kafka.route.window.slide |Long|kafka route window slide|60000|
|kafka.controller.window |Long|controller window|300000|
|kafka.controller.window.slide|int|kafka window slide|0|
|grid.top.left.latitude|double|top left latitude|41.959598|
|grid.top.left.longitude|double|top left longitude|12.389655|
|grid.bottom.right.latitude|double|bottom right latitude|41.836425|
|grid.bottom.right.longitude|double|bottom right longitude|12.593245|
|grid.latitude.sections=5|int|number of grids|5|
|grid.longitude.sections=5|int|number of grids|5|
|controller.street.max.capacity|int|street maximum capacity|175|
|controller.lost.time|int|lost time|4|
|controller.cycle.duration | int|traffic light cycle duration|200|
|controller.yellow.duration| int| yellow light duration|4|
|controller.all.red.duration| int| all red duration|2|
