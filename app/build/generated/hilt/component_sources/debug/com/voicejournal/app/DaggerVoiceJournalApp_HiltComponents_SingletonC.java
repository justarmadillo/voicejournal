package com.voicejournal.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.voicejournal.app.audio.AudioPlayer;
import com.voicejournal.app.audio.AudioRecorder;
import com.voicejournal.app.data.local.audio.AudioFileManager;
import com.voicejournal.app.data.local.db.VoiceJournalDatabase;
import com.voicejournal.app.data.local.db.dao.CategoryDao;
import com.voicejournal.app.data.local.db.dao.PersonDao;
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao;
import com.voicejournal.app.data.local.db.dao.VoiceLogDao;
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao;
import com.voicejournal.app.data.repository.BackupRepository;
import com.voicejournal.app.data.repository.CategoryRepository;
import com.voicejournal.app.data.repository.PersonRepository;
import com.voicejournal.app.data.repository.VoiceLogRepository;
import com.voicejournal.app.di.AppModule_ProvideCategoryDaoFactory;
import com.voicejournal.app.di.AppModule_ProvideDatabaseFactory;
import com.voicejournal.app.di.AppModule_ProvidePersonDaoFactory;
import com.voicejournal.app.di.AppModule_ProvideVoiceLogCategoryDaoFactory;
import com.voicejournal.app.di.AppModule_ProvideVoiceLogDaoFactory;
import com.voicejournal.app.di.AppModule_ProvideVoiceNoteDaoFactory;
import com.voicejournal.app.ui.categories.CategoriesViewModel;
import com.voicejournal.app.ui.categories.CategoriesViewModel_HiltModules;
import com.voicejournal.app.ui.home.HomeViewModel;
import com.voicejournal.app.ui.home.HomeViewModel_HiltModules;
import com.voicejournal.app.ui.logdetail.LogDetailViewModel;
import com.voicejournal.app.ui.logdetail.LogDetailViewModel_HiltModules;
import com.voicejournal.app.ui.people.PeopleListViewModel;
import com.voicejournal.app.ui.people.PeopleListViewModel_HiltModules;
import com.voicejournal.app.ui.people.PersonDetailViewModel;
import com.voicejournal.app.ui.people.PersonDetailViewModel_HiltModules;
import com.voicejournal.app.ui.record.FinalizeDraftViewModel;
import com.voicejournal.app.ui.record.FinalizeDraftViewModel_HiltModules;
import com.voicejournal.app.ui.record.RecordFlowViewModel;
import com.voicejournal.app.ui.record.RecordFlowViewModel_HiltModules;
import com.voicejournal.app.ui.search.SearchViewModel;
import com.voicejournal.app.ui.search.SearchViewModel_HiltModules;
import com.voicejournal.app.ui.settings.SettingsViewModel;
import com.voicejournal.app.ui.settings.SettingsViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerVoiceJournalApp_HiltComponents_SingletonC {
  private DaggerVoiceJournalApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public VoiceJournalApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements VoiceJournalApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public VoiceJournalApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements VoiceJournalApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public VoiceJournalApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements VoiceJournalApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public VoiceJournalApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements VoiceJournalApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public VoiceJournalApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements VoiceJournalApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public VoiceJournalApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements VoiceJournalApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public VoiceJournalApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements VoiceJournalApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public VoiceJournalApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends VoiceJournalApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends VoiceJournalApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends VoiceJournalApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends VoiceJournalApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(9).put(LazyClassKeyProvider.com_voicejournal_app_ui_categories_CategoriesViewModel, CategoriesViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_voicejournal_app_ui_record_FinalizeDraftViewModel, FinalizeDraftViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_voicejournal_app_ui_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_voicejournal_app_ui_logdetail_LogDetailViewModel, LogDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_voicejournal_app_ui_people_PeopleListViewModel, PeopleListViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_voicejournal_app_ui_people_PersonDetailViewModel, PersonDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_voicejournal_app_ui_record_RecordFlowViewModel, RecordFlowViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_voicejournal_app_ui_search_SearchViewModel, SearchViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_voicejournal_app_ui_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_voicejournal_app_ui_people_PeopleListViewModel = "com.voicejournal.app.ui.people.PeopleListViewModel";

      static String com_voicejournal_app_ui_record_FinalizeDraftViewModel = "com.voicejournal.app.ui.record.FinalizeDraftViewModel";

      static String com_voicejournal_app_ui_people_PersonDetailViewModel = "com.voicejournal.app.ui.people.PersonDetailViewModel";

      static String com_voicejournal_app_ui_search_SearchViewModel = "com.voicejournal.app.ui.search.SearchViewModel";

      static String com_voicejournal_app_ui_settings_SettingsViewModel = "com.voicejournal.app.ui.settings.SettingsViewModel";

      static String com_voicejournal_app_ui_categories_CategoriesViewModel = "com.voicejournal.app.ui.categories.CategoriesViewModel";

      static String com_voicejournal_app_ui_home_HomeViewModel = "com.voicejournal.app.ui.home.HomeViewModel";

      static String com_voicejournal_app_ui_logdetail_LogDetailViewModel = "com.voicejournal.app.ui.logdetail.LogDetailViewModel";

      static String com_voicejournal_app_ui_record_RecordFlowViewModel = "com.voicejournal.app.ui.record.RecordFlowViewModel";

      @KeepFieldType
      PeopleListViewModel com_voicejournal_app_ui_people_PeopleListViewModel2;

      @KeepFieldType
      FinalizeDraftViewModel com_voicejournal_app_ui_record_FinalizeDraftViewModel2;

      @KeepFieldType
      PersonDetailViewModel com_voicejournal_app_ui_people_PersonDetailViewModel2;

      @KeepFieldType
      SearchViewModel com_voicejournal_app_ui_search_SearchViewModel2;

      @KeepFieldType
      SettingsViewModel com_voicejournal_app_ui_settings_SettingsViewModel2;

      @KeepFieldType
      CategoriesViewModel com_voicejournal_app_ui_categories_CategoriesViewModel2;

      @KeepFieldType
      HomeViewModel com_voicejournal_app_ui_home_HomeViewModel2;

      @KeepFieldType
      LogDetailViewModel com_voicejournal_app_ui_logdetail_LogDetailViewModel2;

      @KeepFieldType
      RecordFlowViewModel com_voicejournal_app_ui_record_RecordFlowViewModel2;
    }
  }

  private static final class ViewModelCImpl extends VoiceJournalApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<CategoriesViewModel> categoriesViewModelProvider;

    private Provider<FinalizeDraftViewModel> finalizeDraftViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<LogDetailViewModel> logDetailViewModelProvider;

    private Provider<PeopleListViewModel> peopleListViewModelProvider;

    private Provider<PersonDetailViewModel> personDetailViewModelProvider;

    private Provider<RecordFlowViewModel> recordFlowViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.categoriesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.finalizeDraftViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.logDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.peopleListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.personDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.recordFlowViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(9).put(LazyClassKeyProvider.com_voicejournal_app_ui_categories_CategoriesViewModel, ((Provider) categoriesViewModelProvider)).put(LazyClassKeyProvider.com_voicejournal_app_ui_record_FinalizeDraftViewModel, ((Provider) finalizeDraftViewModelProvider)).put(LazyClassKeyProvider.com_voicejournal_app_ui_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_voicejournal_app_ui_logdetail_LogDetailViewModel, ((Provider) logDetailViewModelProvider)).put(LazyClassKeyProvider.com_voicejournal_app_ui_people_PeopleListViewModel, ((Provider) peopleListViewModelProvider)).put(LazyClassKeyProvider.com_voicejournal_app_ui_people_PersonDetailViewModel, ((Provider) personDetailViewModelProvider)).put(LazyClassKeyProvider.com_voicejournal_app_ui_record_RecordFlowViewModel, ((Provider) recordFlowViewModelProvider)).put(LazyClassKeyProvider.com_voicejournal_app_ui_search_SearchViewModel, ((Provider) searchViewModelProvider)).put(LazyClassKeyProvider.com_voicejournal_app_ui_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_voicejournal_app_ui_record_FinalizeDraftViewModel = "com.voicejournal.app.ui.record.FinalizeDraftViewModel";

      static String com_voicejournal_app_ui_categories_CategoriesViewModel = "com.voicejournal.app.ui.categories.CategoriesViewModel";

      static String com_voicejournal_app_ui_people_PersonDetailViewModel = "com.voicejournal.app.ui.people.PersonDetailViewModel";

      static String com_voicejournal_app_ui_search_SearchViewModel = "com.voicejournal.app.ui.search.SearchViewModel";

      static String com_voicejournal_app_ui_people_PeopleListViewModel = "com.voicejournal.app.ui.people.PeopleListViewModel";

      static String com_voicejournal_app_ui_logdetail_LogDetailViewModel = "com.voicejournal.app.ui.logdetail.LogDetailViewModel";

      static String com_voicejournal_app_ui_home_HomeViewModel = "com.voicejournal.app.ui.home.HomeViewModel";

      static String com_voicejournal_app_ui_record_RecordFlowViewModel = "com.voicejournal.app.ui.record.RecordFlowViewModel";

      static String com_voicejournal_app_ui_settings_SettingsViewModel = "com.voicejournal.app.ui.settings.SettingsViewModel";

      @KeepFieldType
      FinalizeDraftViewModel com_voicejournal_app_ui_record_FinalizeDraftViewModel2;

      @KeepFieldType
      CategoriesViewModel com_voicejournal_app_ui_categories_CategoriesViewModel2;

      @KeepFieldType
      PersonDetailViewModel com_voicejournal_app_ui_people_PersonDetailViewModel2;

      @KeepFieldType
      SearchViewModel com_voicejournal_app_ui_search_SearchViewModel2;

      @KeepFieldType
      PeopleListViewModel com_voicejournal_app_ui_people_PeopleListViewModel2;

      @KeepFieldType
      LogDetailViewModel com_voicejournal_app_ui_logdetail_LogDetailViewModel2;

      @KeepFieldType
      HomeViewModel com_voicejournal_app_ui_home_HomeViewModel2;

      @KeepFieldType
      RecordFlowViewModel com_voicejournal_app_ui_record_RecordFlowViewModel2;

      @KeepFieldType
      SettingsViewModel com_voicejournal_app_ui_settings_SettingsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.voicejournal.app.ui.categories.CategoriesViewModel 
          return (T) new CategoriesViewModel(singletonCImpl.categoryRepositoryProvider.get());

          case 1: // com.voicejournal.app.ui.record.FinalizeDraftViewModel 
          return (T) new FinalizeDraftViewModel(singletonCImpl.personRepositoryProvider.get(), singletonCImpl.categoryRepositoryProvider.get(), singletonCImpl.voiceLogRepositoryProvider.get(), viewModelCImpl.savedStateHandle);

          case 2: // com.voicejournal.app.ui.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.voiceLogRepositoryProvider.get(), singletonCImpl.audioRecorderProvider.get());

          case 3: // com.voicejournal.app.ui.logdetail.LogDetailViewModel 
          return (T) new LogDetailViewModel(singletonCImpl.voiceLogRepositoryProvider.get(), singletonCImpl.categoryRepositoryProvider.get(), singletonCImpl.voiceNoteDao(), singletonCImpl.audioFileManagerProvider.get(), singletonCImpl.audioPlayerProvider.get(), singletonCImpl.audioRecorderProvider.get(), viewModelCImpl.savedStateHandle);

          case 4: // com.voicejournal.app.ui.people.PeopleListViewModel 
          return (T) new PeopleListViewModel(singletonCImpl.personDao(), singletonCImpl.personRepositoryProvider.get());

          case 5: // com.voicejournal.app.ui.people.PersonDetailViewModel 
          return (T) new PersonDetailViewModel(singletonCImpl.personRepositoryProvider.get(), singletonCImpl.voiceLogRepositoryProvider.get(), singletonCImpl.audioPlayerProvider.get(), viewModelCImpl.savedStateHandle);

          case 6: // com.voicejournal.app.ui.record.RecordFlowViewModel 
          return (T) new RecordFlowViewModel(singletonCImpl.personRepositoryProvider.get(), singletonCImpl.categoryRepositoryProvider.get(), singletonCImpl.voiceLogRepositoryProvider.get(), singletonCImpl.audioFileManagerProvider.get(), viewModelCImpl.savedStateHandle);

          case 7: // com.voicejournal.app.ui.search.SearchViewModel 
          return (T) new SearchViewModel(singletonCImpl.voiceLogRepositoryProvider.get());

          case 8: // com.voicejournal.app.ui.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.backupRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends VoiceJournalApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends VoiceJournalApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends VoiceJournalApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<VoiceJournalDatabase> provideDatabaseProvider;

    private Provider<CategoryRepository> categoryRepositoryProvider;

    private Provider<PersonRepository> personRepositoryProvider;

    private Provider<AudioFileManager> audioFileManagerProvider;

    private Provider<VoiceLogRepository> voiceLogRepositoryProvider;

    private Provider<AudioRecorder> audioRecorderProvider;

    private Provider<AudioPlayer> audioPlayerProvider;

    private Provider<BackupRepository> backupRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private CategoryDao categoryDao() {
      return AppModule_ProvideCategoryDaoFactory.provideCategoryDao(provideDatabaseProvider.get());
    }

    private PersonDao personDao() {
      return AppModule_ProvidePersonDaoFactory.providePersonDao(provideDatabaseProvider.get());
    }

    private VoiceLogDao voiceLogDao() {
      return AppModule_ProvideVoiceLogDaoFactory.provideVoiceLogDao(provideDatabaseProvider.get());
    }

    private VoiceLogCategoryDao voiceLogCategoryDao() {
      return AppModule_ProvideVoiceLogCategoryDaoFactory.provideVoiceLogCategoryDao(provideDatabaseProvider.get());
    }

    private VoiceNoteDao voiceNoteDao() {
      return AppModule_ProvideVoiceNoteDaoFactory.provideVoiceNoteDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<VoiceJournalDatabase>(singletonCImpl, 1));
      this.categoryRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<CategoryRepository>(singletonCImpl, 0));
      this.personRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PersonRepository>(singletonCImpl, 2));
      this.audioFileManagerProvider = DoubleCheck.provider(new SwitchingProvider<AudioFileManager>(singletonCImpl, 4));
      this.voiceLogRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<VoiceLogRepository>(singletonCImpl, 3));
      this.audioRecorderProvider = DoubleCheck.provider(new SwitchingProvider<AudioRecorder>(singletonCImpl, 5));
      this.audioPlayerProvider = DoubleCheck.provider(new SwitchingProvider<AudioPlayer>(singletonCImpl, 6));
      this.backupRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<BackupRepository>(singletonCImpl, 7));
    }

    @Override
    public void injectVoiceJournalApp(VoiceJournalApp voiceJournalApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.voicejournal.app.data.repository.CategoryRepository 
          return (T) new CategoryRepository(singletonCImpl.categoryDao());

          case 1: // com.voicejournal.app.data.local.db.VoiceJournalDatabase 
          return (T) AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.voicejournal.app.data.repository.PersonRepository 
          return (T) new PersonRepository(singletonCImpl.personDao());

          case 3: // com.voicejournal.app.data.repository.VoiceLogRepository 
          return (T) new VoiceLogRepository(singletonCImpl.voiceLogDao(), singletonCImpl.voiceLogCategoryDao(), singletonCImpl.voiceNoteDao(), singletonCImpl.personDao(), singletonCImpl.audioFileManagerProvider.get());

          case 4: // com.voicejournal.app.data.local.audio.AudioFileManager 
          return (T) new AudioFileManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.voicejournal.app.audio.AudioRecorder 
          return (T) new AudioRecorder(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.audioFileManagerProvider.get());

          case 6: // com.voicejournal.app.audio.AudioPlayer 
          return (T) new AudioPlayer(singletonCImpl.audioFileManagerProvider.get());

          case 7: // com.voicejournal.app.data.repository.BackupRepository 
          return (T) new BackupRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.personDao(), singletonCImpl.categoryDao(), singletonCImpl.voiceLogDao(), singletonCImpl.voiceLogCategoryDao(), singletonCImpl.voiceNoteDao(), singletonCImpl.audioFileManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
