package com.voicejournal.app.ui.people;

import com.voicejournal.app.data.local.db.dao.PersonDao;
import com.voicejournal.app.data.repository.PersonRepository;
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
public final class PeopleListViewModel_Factory implements Factory<PeopleListViewModel> {
  private final Provider<PersonDao> personDaoProvider;

  private final Provider<PersonRepository> personRepositoryProvider;

  public PeopleListViewModel_Factory(Provider<PersonDao> personDaoProvider,
      Provider<PersonRepository> personRepositoryProvider) {
    this.personDaoProvider = personDaoProvider;
    this.personRepositoryProvider = personRepositoryProvider;
  }

  @Override
  public PeopleListViewModel get() {
    return newInstance(personDaoProvider.get(), personRepositoryProvider.get());
  }

  public static PeopleListViewModel_Factory create(Provider<PersonDao> personDaoProvider,
      Provider<PersonRepository> personRepositoryProvider) {
    return new PeopleListViewModel_Factory(personDaoProvider, personRepositoryProvider);
  }

  public static PeopleListViewModel newInstance(PersonDao personDao,
      PersonRepository personRepository) {
    return new PeopleListViewModel(personDao, personRepository);
  }
}
