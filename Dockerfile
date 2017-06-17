FROM docker.ocf.berkeley.edu/theocf/debian:stretch

# install needed packages
RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
    ant \
    apparmor-utils \
    build-essential \
    cmake \
    fakeroot \
    git-core \
    libapparmor1 \
    libbz2-dev \
    libgl1-mesa-dev \
    libgstreamer-plugins-base1.0-0 \
    libgstreamer1.0-0 \
    libjpeg62 \
    libpam-dev \
    libpango1.0-dev \
    libssl-dev \
    libxslt1-dev \
    openjdk-8-jdk \
    pkg-config \
    r-base \
    sudo \
    unzip \
    uuid-dev \
    wget \
    zlib1g-dev

# swap to java8
RUN update-alternatives --set java /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java

## run install-boost twice - boost exits 1 even though it has installed good enough for our uses.
## https://github.com/rstudio/rstudio/blob/master/vagrant/provision-primary-user.sh#L12-L15
COPY dependencies/common/install-boost /tmp/
RUN bash /tmp/install-boost || bash /tmp/install-boost

# install cmake
COPY package/linux/install-dependencies /tmp/
RUN bash /tmp/install-dependencies
