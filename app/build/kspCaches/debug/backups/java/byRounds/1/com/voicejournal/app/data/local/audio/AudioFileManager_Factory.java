package com.voicejournal.app.data.local.audio;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AudioFileManager_Factory implements Factory<AudioFileManager> {
  private final Provider<Context> contextProvider;

  public AudioFileManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AudioFileManager get() {
    return newInstance(contextProvider.get());
  }

  public static AudioFileManager_Factory create(Provider<Context> contextProvider) {
    return new AudioFileManager_Factory(contextProvider);
  }

  public static AudioFileManager newInstance(Context context) {
    return new AudioFileManager(context);
  }
}
