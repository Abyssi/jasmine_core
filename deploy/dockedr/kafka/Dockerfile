FROM ubuntu:16.04

ENV SCALA_VERSION 2.11
ENV KAFKA_VERSION 1.1.0
ENV KAFKA_HOME /usr/local/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION"
ENV KAFKA_DATA_DIR /var/lib/kafka/data
#ADD scripts/start_kafka.sh /usr/bin/start_kafka.sh

# install java + others
RUN apt-get update && apt-get install -y \
  wget \
  openjdk-8-jdk

# install kafka
#RUN wget http://apache.cs.utah.edu/kafka/"$KAFKA_VERSION"/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
#  tar -xzf kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
#  mv kafka_"$SCALA_VERSION"-"$KAFKA_VERSION" $KAFKA_HOME

RUN set -x && \
    wget http://www.apache.org/dist/kafka/"$KAFKA_VERSION"/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
    wget "http://www.apache.org/dist/kafka/"$KAFKA_VERSION"/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz.asc" && \
    wget "http://kafka.apache.org/KEYS" && \
    tar -xzf kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
    gpg --import KEYS && \
    gpg --batch --verify kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz.asc kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
    mv kafka_"$SCALA_VERSION"-"$KAFKA_VERSION" $KAFKA_HOME && \
    rm -r kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz.asc kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz

#COPY log4j.properties $KAFKA_HOME/config/

# change start_kafka permissions
# RUN chmod 744 /usr/bin/start_kafka.sh

#expose port 9092 to external world
EXPOSE 9092
#expose port 9093 to external world
EXPOSE 9093
#expose port 9094 to external world
EXPOSE 9094

#CMD ["/usr/bin/start_kafka.sh"]
