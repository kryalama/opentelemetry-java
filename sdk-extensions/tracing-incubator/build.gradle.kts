plugins {
  id("otel.java-conventions")
  id("otel.publish-conventions")

  id("otel.jmh-conventions")
  id("otel.animalsniffer-conventions")
}

// SDK modules that are still being developed.

description = "OpenTelemetry SDK Tracing Incubator"
otelJava.moduleName.set("io.opentelemetry.sdk.extension.trace.incubator")

dependencies {
  api(project(":api:all"))
  api(project(":sdk:all"))

  compileOnly(project(":sdk:trace-shaded-deps"))

  implementation(project(":api:metrics"))
  implementation(project(":semconv"))

  annotationProcessor("com.google.auto.value:auto-value")
  testImplementation(project(":sdk:testing"))
  testImplementation("com.google.guava:guava-testlib")

  jmh(project(":sdk:metrics"))
  jmh(project(":sdk:testing")) {
    // JMH doesn"t handle dependencies that are duplicated between the main and jmh
    // configurations properly, but luckily here it"s simple enough to just exclude transitive
    // dependencies.
    isTransitive = false
  }
  jmh(project(":exporters:otlp:trace")) {
    // The opentelemetry-exporter-otlp-trace depends on this project itself. So don"t pull in
    // the transitive dependencies.
    isTransitive = false
  }
  // explicitly adding the opentelemetry-exporter-otlp dependencies
  jmh(project(":exporters:otlp:common")) {
    isTransitive = false
  }
  jmh("io.opentelemetry.proto:opentelemetry-proto")

  jmh("com.google.guava:guava")
  jmh("io.grpc:grpc-api")
  jmh("io.grpc:grpc-netty-shaded")
  jmh("org.testcontainers:testcontainers") // testContainer for OTLP collector
}
