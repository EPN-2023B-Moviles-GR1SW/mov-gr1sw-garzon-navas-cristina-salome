package com.example.examenii.databasefrs

import com.example.examenib.model.Producto
import com.example.examenib.model.Resenia
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestorePropietario {

    companion object{
        fun crearProducto(producto: Producto){
            val db = Firebase.firestore
            val productos = db.collection("productos")

            val datosProductos = hashMapOf(
                "nombre" to producto.nombre,
                "descripcion" to producto.descripcion,
                "precio" to producto.precio,
                "fechaCreacion" to producto.fechaCreacion
            )
            productos.document(producto.id).set(datosProductos)
        }

        fun consultarProductos( listener: (ArrayList<Producto>) -> Unit)
        {
            val db = Firebase.firestore
            val arregloProductos = arrayListOf<Producto>()
            val productosRefUnico = db.collection("productos")

            productosRefUnico
                .orderBy("nombre", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener {querySnapshot ->
                    // it == eso (lo que llegue)
                    for (productos in querySnapshot){
                        productos.id
                        arregloProductos.add(anadirProducto(productos))
                    }
                    listener(arregloProductos)
                }
                .addOnFailureListener{
                    // Errores
                }
        }


        fun anadirProducto(
            producto: QueryDocumentSnapshot
        ) : Producto {
            val nuevoProducto =  Producto(

                producto.id as String,
                producto.data.get("nombre") as String,
                producto.data.get("descripcion") as String,
                producto.data.get("precio") as Long,
                (producto.data.get("fechaCreacion") as com.google.firebase.Timestamp).toDate(),
                producto.data.get("resenia") as ArrayList<Resenia>?,
            )
            return nuevoProducto
        }

        fun consultarProducto(
            id: String,
            listener: (Producto) -> Unit,
        ) {
            val db = Firebase.firestore
            val productosRefUnica = db.collection("productos")
            productosRefUnica
                .document(id)
                .get() // obtener 1 DOCUMENTO
                .addOnSuccessListener { querySnapshot ->
                    val document = querySnapshot
                    val producto = Producto(
                        document.reference.id,
                        document.data?.get("nombre") as String,
                        document.data?.get("descripcion") as String,
                        document.data?.get("precio") as Long,
                        (document.data?.get("fechaCreacion") as com.google.firebase.Timestamp).toDate(),)
                    listener(producto)
                }
                .addOnFailureListener{
                    // Errores
                }
        }

        fun eliminarProducto(
            id: String
        ){
            val db = Firebase.firestore
            val productosRefUnica = db
                .collection("productos")

            productosRefUnica
                .document(id)
                .delete()
                .addOnCompleteListener{ /* si todo sale bien */}
                .addOnFailureListener{/* Si algo salio mal*/}
        }

        fun actualizarProductos(
            producto: Producto
        ){
            val db = Firebase.firestore
            val productosRefUnica = db
                .collection("productos")


            val datosActualizados = hashMapOf(
                "nombre" to producto.nombre,
                "descripcion" to producto.descripcion,
                "precio" to producto.precio,
                "fechaCreacion" to producto.fechaCreacion,
                "resenias" to listOf("opinion1", "opinion2"),
            )

            productosRefUnica
                .document(producto.id.toString())
                .update(datosActualizados)
                .addOnSuccessListener {
                    // Operación de actualización exitosa
                }
                .addOnFailureListener { e ->
                    // Manejar el error en caso de falla
                }
        }
    }
}