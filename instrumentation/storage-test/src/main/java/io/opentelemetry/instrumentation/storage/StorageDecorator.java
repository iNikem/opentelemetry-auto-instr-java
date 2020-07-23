/*
 * Copyright The OpenTelemetry Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentelemetry.instrumentation.storage;

import io.grpc.Context;
import io.opentelemetry.auto.bootstrap.instrumentation.decorator.BaseDecorator;

public class StorageDecorator extends BaseDecorator {
  public static final StorageDecorator DECORATE = new StorageDecorator();

  static final Context.Key<String> CONTEXT_CLIENT_SPAN_KEY = Context.key("some-key");

  public Context attach(String value) {
    return Context.current().withValue(CONTEXT_CLIENT_SPAN_KEY, value);
  }

  public String getValue(Context context) {
    return CONTEXT_CLIENT_SPAN_KEY.get(context);
  }
}
