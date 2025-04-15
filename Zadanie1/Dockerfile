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

# Verify installations
CMD ["bash", "-c", "python3.10 --version && java -version && kotlin -version"]