package com.voicejournal.app.audio;

import android.content.Context;
import com.voicejournal.app.data.local.audio.AudioFileManager;
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
public final class AudioRecorder_Factory implements Factory<AudioRecorder> {
  private final Provider<Context> contextProvider;

  private final Provider<AudioFileManager> audioFileManagerProvider;

  public AudioRecorder_Factory(Provider<Context> contextProvider,
      Provider<AudioFileManager> audioFileManagerProvider) {
    this.contextProvider = contextProvider;
    this.audioFileManagerProvider = audioFileManagerProvider;
  }

  @Override
  public AudioRecorder get() {
    return newInstance(contextProvider.get(), audioFileManagerProvider.get());
  }

  public static AudioRecorder_Factory create(Provider<Context> contextProvider,
      Provider<AudioFileManager> audioFileManagerProvider) {
    return new AudioRecorder_Factory(contextProvider, audioFileManagerProvider);
  }

  public static AudioRecorder newInstance(Context context, AudioFileManager audioFileManager) {
    return new AudioRecorder(context, audioFileManager);
  }
}
