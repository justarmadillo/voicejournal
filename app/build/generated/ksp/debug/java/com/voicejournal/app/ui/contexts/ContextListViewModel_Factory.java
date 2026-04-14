package com.voicejournal.app.ui.contexts;

import com.voicejournal.app.data.local.db.dao.ContextDao;
import com.voicejournal.app.data.repository.ContextRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ContextListViewModel_Factory implements Factory<ContextListViewModel> {
  private final Provider<ContextDao> contextDaoProvider;

  private final Provider<ContextRepository> contextRepositoryProvider;

  public ContextListViewModel_Factory(Provider<ContextDao> contextDaoProvider,
      Provider<ContextRepository> contextRepositoryProvider) {
    this.contextDaoProvider = contextDaoProvider;
    this.contextRepositoryProvider = contextRepositoryProvider;
  }

  @Override
  public ContextListViewModel get() {
    return newInstance(contextDaoProvider.get(), contextRepositoryProvider.get());
  }

  public static ContextListViewModel_Factory create(Provider<ContextDao> contextDaoProvider,
      Provider<ContextRepository> contextRepositoryProvider) {
    return new ContextListViewModel_Factory(contextDaoProvider, contextRepositoryProvider);
  }

  public static ContextListViewModel newInstance(ContextDao contextDao,
      ContextRepository contextRepository) {
    return new ContextListViewModel(contextDao, contextRepository);
  }
}
