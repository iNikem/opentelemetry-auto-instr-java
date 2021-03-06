plugins {
  id("otel.javaagent-instrumentation")
}

muzzle {
  pass {
    group.set("com.twilio.sdk")
    module.set("twilio")
    // this is first version in maven central (there's a 0.0.1 but that is really 7.14.4)
    versions.set("[6.6.9,8.0.0)")
  }
}

dependencies {
  library("com.twilio.sdk:twilio:6.6.9")

  // included to make sure the apache httpclient nested spans are suppressed
  testInstrumentation(project(":instrumentation:apache-httpclient:apache-httpclient-4.0:javaagent"))

  latestDepTestLibrary("com.twilio.sdk:twilio:7.+")
}

tasks.withType<Test>().configureEach {
  // TODO run tests both with and without experimental span attributes
  jvmArgs("-Dotel.instrumentation.twilio.experimental-span-attributes=true")
}
