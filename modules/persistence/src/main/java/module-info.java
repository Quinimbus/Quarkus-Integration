module cloud.quinimbus.integration.quarkus.persistence {
    exports cloud.quinimbus.integration.quarkus.persistence;

    requires cloud.quinimbus.config.api;
    requires cloud.quinimbus.config.cdi;
    requires cloud.quinimbus.persistence.api;
    requires io.vertx.core;
    requires jakarta.annotation;
    requires jakarta.cdi;
    requires jakarta.inject;
    requires quarkus.core;
}
