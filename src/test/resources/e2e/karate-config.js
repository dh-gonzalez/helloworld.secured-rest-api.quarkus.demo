function fn() {
    var config = {};

    // Accept self-signed certificates
    // TODO - this has to be removed when self-wigned replaced by signed certificates
    karate.configure('ssl', true);

    return config;
}
