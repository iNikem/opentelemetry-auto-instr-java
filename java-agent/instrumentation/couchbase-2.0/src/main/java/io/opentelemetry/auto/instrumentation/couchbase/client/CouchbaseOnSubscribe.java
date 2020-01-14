package io.opentelemetry.auto.instrumentation.couchbase.client;

import static io.opentelemetry.auto.instrumentation.couchbase.client.CouchbaseClientDecorator.DECORATE;

import io.opentelemetry.auto.api.MoreTags;
import io.opentelemetry.auto.instrumentation.api.AgentSpan;
import io.opentelemetry.auto.instrumentation.rxjava.TracedOnSubscribe;
import java.lang.reflect.Method;
import rx.Observable;

public class CouchbaseOnSubscribe extends TracedOnSubscribe {
  private final String resourceName;
  private final String bucket;

  public CouchbaseOnSubscribe(
      final Observable originalObservable, final Method method, final String bucket) {
    super(originalObservable, "couchbase.call", DECORATE);

    final Class<?> declaringClass = method.getDeclaringClass();
    final String className =
        declaringClass.getSimpleName().replace("CouchbaseAsync", "").replace("DefaultAsync", "");
    resourceName = className + "." + method.getName();
    this.bucket = bucket;
  }

  @Override
  protected void afterStart(final AgentSpan span) {
    super.afterStart(span);

    span.setTag(MoreTags.RESOURCE_NAME, resourceName);

    if (bucket != null) {
      span.setTag("bucket", bucket);
    }
  }
}