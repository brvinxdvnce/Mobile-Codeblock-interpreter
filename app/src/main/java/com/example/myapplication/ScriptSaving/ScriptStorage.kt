package com.example.myapplication.ScriptSaving

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.ForBuildScreen.CodeBlock
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


private val Context.dataStore by preferencesDataStore(name = "script_store")
private val SCRIPTS_KEY = stringPreferencesKey("scripts_json")
private val gson = Gson()

object ScriptStorage {
    fun saveScripts (context : Context, scripts: List<Script>) {
        val json = gson.toJson(scripts)
        runBlocking {
            context.dataStore.edit { prefs ->
                prefs[SCRIPTS_KEY] = json
            }
        }
    }

    fun loadScripts (context : Context) : List<Script> {
        return runBlocking {
            val prefs = context.dataStore.data.first()
            val json = prefs[SCRIPTS_KEY] ?: return@runBlocking emptyList()
            val type = object : TypeToken<List<Script>>() {}.type
            gson.fromJson(json, type)
        }
    }
}