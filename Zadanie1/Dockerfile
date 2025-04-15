FROM ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update -y && apt-get upgrade -y && \
    apt-get install -y \
    software-properties-common \
    gnupg2

# Repo for python3.10
RUN add-apt-repository ppa:deadsnakes/ppa
RUN apt-get update -y

# Install python3.10
RUN apt-get install -y python3.10 python3.10-distutils

# Install Java 8
RUN apt-get install -y openjdk-8-jdk

# Install Kotlin
RUN apt-get update && apt-get install -y curl zip unzip && \
    curl -s "https://get.sdkman.io" | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install kotlin"
ENV PATH="/root/.sdkman/candidates/kotlin/current/bin:$PATH"

# Install Gradle
ENV GRADLE_VERSION=8.7
RUN curl -sSL https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o gradle.zip && \
    unzip gradle.zip -d /opt/gradle && \
    ln -s /opt/gradle/gradle-${GRADLE_VERSION}/bin/gradle /usr/bin/gradle && \
    rm gradle.zip

# Run gradle project
COPY app /app
WORKDIR /app

RUN gradle build

# Verify installations, run gradle app
CMD ["bash", "-c", "python3.10 --version && java -version && kotlin -version && gradle -version && gradle run"]