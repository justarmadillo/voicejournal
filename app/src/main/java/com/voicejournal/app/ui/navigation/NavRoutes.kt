package com.voicejournal.app.ui.navigation

import kotlinx.serialization.Serializable

@Serializable object Home
@Serializable data class RecordFlow(val fileName: String, val durationMs: Long)
@Serializable data class FinalizeDraft(val draftId: String)
@Serializable data class PersonDetail(val personId: String)
@Serializable data class LogDetail(val voiceLogId: String)
@Serializable object PeopleList
@Serializable object Categories
@Serializable object Search
@Serializable object Settings
