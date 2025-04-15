FROM ubuntu:20.04

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

# Verify Python 3.10 installation
RUN python3.10 --version
CMD ["python3.10", "--version"]