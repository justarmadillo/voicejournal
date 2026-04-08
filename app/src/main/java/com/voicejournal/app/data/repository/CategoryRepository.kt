package com.voicejournal.app.data.repository

import com.voicejournal.app.data.local.db.dao.CategoryDao
import com.voicejournal.app.data.local.db.entity.CategoryEntity
import com.voicejournal.app.domain.model.Category
import com.voicejournal.app.util.DateTimeUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {

    fun getAll(): Flow<List<Category>> = categoryDao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }

    fun getById(id: String): Flow<Category?> = categoryDao.getById(id).map { it?.toDomain() }

    suspend fun create(name: String, colorHex: String? = null): Category {
        val now = DateTimeUtil.now()
        val entity = CategoryEntity(
            id = UUID.randomUUID().toString(),
            name = name,
            colorHex = colorHex,
            createdAt = now,
            updatedAt = now
        )
        categoryDao.insert(entity)
        return entity.toDomain()
    }

    suspend fun update(category: Category) {
        categoryDao.update(
            CategoryEntity(
                id = category.id,
                name = category.name,
                colorHex = category.colorHex,
                createdAt = category.createdAt,
                updatedAt = DateTimeUtil.now()
            )
        )
    }

    suspend fun delete(category: Category) {
        categoryDao.delete(
            CategoryEntity(
                id = category.id,
                name = category.name,
                colorHex = category.colorHex,
                createdAt = category.createdAt,
                updatedAt = category.updatedAt
            )
        )
    }

    private fun CategoryEntity.toDomain() = Category(
        id = id,
        name = name,
        colorHex = colorHex,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
