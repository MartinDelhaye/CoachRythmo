package com.example.coachrythmo.presentation.suivi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coachrythmo.data.source.SessionDao
import com.example.coachrythmo.data.seed.SessionSeed
import com.example.coachrythmo.data.seed.SessionExerciseSeed
import com.example.coachrythmo.domain.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SuiviViewModel(
    private val sessionDao: SessionDao
) : ViewModel() {

    private val _sessions = MutableStateFlow<List<SessionUI>>(emptyList())
    val sessions: StateFlow<List<SessionUI>> = _sessions

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch {

            if (sessionDao.count() == 0) {

                val sessions = SessionSeed.getSessions()
                val ids = sessionDao.insertAll(sessions)

                val exercises = SessionExerciseSeed.build(ids)
                sessionDao.insertSessionExercises(exercises)
            }

            loadSessions()
        }
    }

    private suspend fun loadSessions() {

        val sessions = sessionDao.getAllSessionsOnce()

        val ui = sessions.mapNotNull { session ->

            val id = session.id ?: return@mapNotNull null

            val total = sessionDao.countExercises(id)
            val done = sessionDao.countDoneExercises(id)

            SessionUI(session, done, total)
        }

        _sessions.value = ui
    }

    data class SessionUI(
        val session: Session,
        val doneExercises: Int,
        val totalExercises: Int
    ) {
        val progress: Float
            get() = if (totalExercises == 0) 0f
            else doneExercises.toFloat() / totalExercises
    }
}
