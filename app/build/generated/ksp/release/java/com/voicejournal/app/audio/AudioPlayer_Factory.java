package com.voicejournal.app.audio;

import com.voicejournal.app.data.local.audio.AudioFileManager;
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
public final class AudioPlayer_Factory implements Factory<AudioPlayer> {
  private final Provider<AudioFileManager> audioFileManagerProvider;

  public AudioPlayer_Factory(Provider<AudioFileManager> audioFileManagerProvider) {
    this.audioFileManagerProvider = audioFileManagerProvider;
  }

  @Override
  public AudioPlayer get() {
    return newInstance(audioFileManagerProvider.get());
  }

  public static AudioPlayer_Factory create(Provider<AudioFileManager> audioFileManagerProvider) {
    return new AudioPlayer_Factory(audioFileManagerProvider);
  }

  public static AudioPlayer newInstance(AudioFileManager audioFileManager) {
    return new AudioPlayer(audioFileManager);
  }
}
