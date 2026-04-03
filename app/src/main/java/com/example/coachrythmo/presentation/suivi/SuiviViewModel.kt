package com.example.coachrythmo.presentation.suivi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coachrythmo.data.source.SessionDao
import com.example.coachrythmo.domain.model.SessionWithStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SuiviViewModel(
    private val sessionDao: SessionDao
) : ViewModel() {

    private val _sessions = MutableStateFlow<List<SessionWithStats>>(emptyList())
    val sessions: StateFlow<List<SessionWithStats>> = _sessions

    init {
        loadSessions()
    }

    private fun loadSessions() {
        viewModelScope.launch {
            _sessions.value = sessionDao.getSessionsWithStats()
        }
    }
}