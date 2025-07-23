package com.nadiavinabal.peyaapp.domain.repository

import com.nadiavinabal.peyaapp.data.datasource.ProductDataSource
import com.nadiavinabal.peyaapp.database.dao.ProductDao
import com.nadiavinabal.peyaapp.database.entities.toDomain
import com.nadiavinabal.peyaapp.database.entities.toEntity
import com.nadiavinabal.peyaapp.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val dataSource: ProductDataSource
) : ProductRepository {

    private val initialProducts = listOf(
        Product(
            _id = "68472a818559f3a51f77c2c2",
            name = "Tacos al pastor",
            price = 45.0,
            imageUrl = "https://comedera.com/wp-content/uploads/sites/9/2017/08/tacos-al-pastor-receta.jpg",
            description = "Tortillas con carne de cerdo marinada, piña y cebolla.",
            category = "General",
            hasDrink = true
        ),
        Product(
            _id = "68472aae8559f3a51f77c2c4",
            name = "Burrito de res",
            price = 60.0,
            imageUrl = "https://www.recetasnestle.com.mx/sites/default/files/srh_recipes/cff9881a271d21ae3d098ba68d5ecd18.jpg",
            description = "Con arroz, frijoles, carne de res y queso.",
            hasDrink = false,
            category = "General"
        ),
        Product(
            _id = "68472ae08559f3a51f77c2c6",
            name = "Hamburguesa clásica",
            price = 70.0,
            imageUrl = "https://imag.bonviveur.com/hamburguesa-clasica.jpg",
            description = "Pan artesanal, carne de res, lechuga, tomate y mayonesa.",
            hasDrink = false,
            category = "General",
        ),
        Product(
            _id = "68472b2d8559f3a51f77c2c9",
            name = "Pizza margarita",
            price = 120.0,
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDx46gfGPL3XKmoiXU_pQzvINxjjOFsXLoAA&s",
            hasDrink = false,
            description = "Salsa de tomate, mozzarella y albahaca fresca.",
            category = "General",
        ),
        Product(
            _id = "68472b3e8559f3a51f77c2cb",
            name= "Hot dog especial",
            description = "Salchicha jumbo, tocino, cebolla caramelizada y mostaza.",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaKqDVCWtGQzA2-d1WvRmTfPi0krczx2pwzQ&s",
            price= 50.0,
            hasDrink= true,
            category = "General"
        ),
        Product(
            _id = "68472bb28559f3a51f77c2cd",
            name = "Ensalada Cesar",
            description = "Lechuga romana, crutones, parmesano y aderezo César.",
            imageUrl= "https://cdn5.recetasdeescandalo.com/wp-content/uploads/2016/06/Ensalada-cesar-con-pollo-y-su-salsa-casera.-Receta-e-ingredientes.jpg",
            price = 55.0,
            hasDrink = false,
            category = "General"
        )


    )

    override suspend fun insertInitialProducts() {
        val existing = dao.getAllProducts().first()
        if (existing.isEmpty()) {
            dao.insertProducts(initialProducts.map { it.toEntity() })
        }
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return flow {
            try {
                val products = dataSource.getAllProducts()
                emit(products)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }.flowOn(Dispatchers.IO)
    }
}