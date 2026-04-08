package com.voicejournal.app.data.repository

import com.voicejournal.app.data.local.db.dao.PersonDao
import com.voicejournal.app.data.local.db.entity.PersonEntity
import com.voicejournal.app.domain.model.Person
import com.voicejournal.app.util.DateTimeUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository @Inject constructor(
    private val personDao: PersonDao
) {

    fun getAll(): Flow<List<Person>> = personDao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }

    fun getById(id: String): Flow<Person?> = personDao.getById(id).map { it?.toDomain() }

    fun searchByName(query: String): Flow<List<Person>> = personDao.searchByName(query).map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun create(name: String, notes: String? = null): Person {
        val now = DateTimeUtil.now()
        val entity = PersonEntity(
            id = UUID.randomUUID().toString(),
            name = name,
            notes = notes,
            createdAt = now,
            updatedAt = now
        )
        personDao.insert(entity)
        return entity.toDomain()
    }

    suspend fun update(person: Person) {
        personDao.update(
            PersonEntity(
                id = person.id,
                name = person.name,
                notes = person.notes,
                createdAt = person.createdAt,
                updatedAt = DateTimeUtil.now()
            )
        )
    }

    suspend fun delete(person: Person) {
        personDao.delete(
            PersonEntity(
                id = person.id,
                name = person.name,
                notes = person.notes,
                createdAt = person.createdAt,
                updatedAt = person.updatedAt
            )
        )
    }

    private fun PersonEntity.toDomain() = Person(
        id = id,
        name = name,
        notes = notes,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
