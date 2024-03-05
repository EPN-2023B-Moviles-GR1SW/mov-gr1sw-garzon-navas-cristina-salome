package com.example.examenii.databasefrs


import com.example.examenib.model.Resenia
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreMascota {
    companion object{
        fun crearResenia(resenia: Resenia, idProducto: String){
            val db = Firebase.firestore
            val resenias = db.collection("resenias")

            val datosResenia = hashMapOf(
                "comentario" to resenia.comentario,
                "calificacion" to resenia.calificacion,
                "recomendado" to resenia.recomendado,
                "fechaPublicacion" to resenia.fechaPublicacion,
                "idProducto" to idProducto
            )
            resenias.document(resenia.id).set(datosResenia)
        }

        fun consultarResenias(listener: (ArrayList<Resenia>) -> Unit)
        {
            val db = Firebase.firestore
            val arregloResenias = arrayListOf<Resenia>()
            val reseniasRefUnico = db.collection("resenias")

            reseniasRefUnico
                .orderBy("nombre", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener {querySnapshot ->
                    for (resenia in querySnapshot){
                        resenia.id
                        arregloResenias.add(anadirResenia(resenia))
                    }
                    listener(arregloResenias)
                }
                .addOnFailureListener{
                    // Errores
                }
        }


        fun anadirResenia(
            resenia: QueryDocumentSnapshot
        ) : Resenia {
            val nuevaResenia =  Resenia(
                resenia.id as String,
                resenia.data.get("comentario") as String,
                resenia.data.get("calificacion") as Long,
                resenia.data.get("recomendado") as Boolean,
                (resenia.data.get("fechaPublicacion") as com.google.firebase.Timestamp).toDate()
            )
            return nuevaResenia
        }

        fun consultarResenia(
            id: String,
            onSuccess: (Resenia) -> Unit
        ) {
            val db = Firebase.firestore
            val reseniasRefUnica = db.collection("resenias")
            reseniasRefUnica
                .document(id)
                .get() // obtener 1 DOCUMENTO
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.exists()) {
                            val resenia = Resenia(
                                document.reference.id,
                                document.data?.get("comentario") as String,
                                document.data?.get("calificacion") as Long,
                                document.data?.get("recomendado") as Boolean,
                                (document.data?.get("fechaPublicacion") as com.google.firebase.Timestamp).toDate()
                            )
                            onSuccess(resenia)
                        } else {
                            //salio mal
                        }
                    } else {
                        //salio mal
                    }
                }
        }

        fun eliminarResenia(
            id: String
        ){
            val db = Firebase.firestore
            val materiasRefUnica = db
                .collection("resenias")

            materiasRefUnica
                .document(id)
                .delete()
                .addOnCompleteListener{ /* si todo sale bien */}
                .addOnFailureListener{/* Si algo salio mal*/}
        }

        fun actualizarResenia(
            resenia: Resenia
        ){
            val db = Firebase.firestore
            val reseniasRefUnica = db
                .collection("resenias")

            val datosActualizados = hashMapOf(
                "comentario" to resenia.comentario,
                "calificacion" to resenia.calificacion,
                "recomendado" to resenia.recomendado,
                "fechaPublicacion" to resenia.fechaPublicacion
            )

            reseniasRefUnica
                .document(resenia.id)
                .update(datosActualizados as Map<String, Any>)
                .addOnSuccessListener {
                    // Operación de actualización exitosa
                }
                .addOnFailureListener { e ->
                    // Manejar el error en caso de falla
                }
        }

        fun consultarReseniasProductos(
            id: String,
            listener: (ArrayList<Resenia>) -> Unit
        ){
            var arregloResenias = arrayListOf<Resenia>()
            val db = Firebase.firestore
            val reseniasRefUnica = db.collection("resenias")
            reseniasRefUnica
                .whereEqualTo("idProducto", id)
                .orderBy("calificacion", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (resenia in querySnapshot){
                        resenia.id
                        arregloResenias.add(anadirResenia(resenia))
                    }
                    listener(arregloResenias)
                }
                .addOnFailureListener{
                    // Errores
                }
        }
    }
}