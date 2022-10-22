FROM base_java:v1

WORKDIR /project

# to eliminate caching of the layer
ADD "https://www.random.org/cgi-bin/randbyte?nbytes=10&format=h" skipcache
COPY jars/merchant-service.jar /project/project.jar
COPY run_in_container.sh /project

RUN ["chmod", "+x", "run_in_container.sh"]
ENTRYPOINT ["./run_in_container.sh"]