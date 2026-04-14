package com.voicejournal.app.data.repository;

import com.voicejournal.app.data.local.db.dao.ContextDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class ContextRepository_Factory implements Factory<ContextRepository> {
  private final Provider<ContextDao> contextDaoProvider;

  public ContextRepository_Factory(Provider<ContextDao> contextDaoProvider) {
    this.contextDaoProvider = contextDaoProvider;
  }

  @Override
  public ContextRepository get() {
    return newInstance(contextDaoProvider.get());
  }

  public static ContextRepository_Factory create(Provider<ContextDao> contextDaoProvider) {
    return new ContextRepository_Factory(contextDaoProvider);
  }

  public static ContextRepository newInstance(ContextDao contextDao) {
    return new ContextRepository(contextDao);
  }
}
