module cloud.quinimbus.integration.quarkus.persistence {
    exports cloud.quinimbus.integration.quarkus.persistence;

    requires cloud.quinimbus.config.api;
    requires cloud.quinimbus.config.cdi;
    requires cloud.quinimbus.persistence.api;
    requires quarkus.core;
    requires io.vertx.core;
    requires jakarta.enterprise.cdi.api;
    requires java.annotation;
    requires jakarta.inject.api;
}
