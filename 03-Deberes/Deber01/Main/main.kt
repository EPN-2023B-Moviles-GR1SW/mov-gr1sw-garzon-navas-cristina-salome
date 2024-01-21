package Main

import Modelos.Propietario
import Modelos.Mascota
import java.text.SimpleDateFormat
import java.util.*
import Archivos.PropietarioArchivos
import Archivos.MascotaArchivos

fun main() {
    val propietarioArchivos = PropietarioArchivos(System.getProperty("user.dir")+ "\\Deber01\\Datos\\PropietarioData.txt")
    val mascotaArchivos = MascotaArchivos(System.getProperty("user.dir")+ "\\Deber01\\Datos\\MascotaData.txt")

    val scanner = Scanner(System.`in`)
    var opcion: Int

    do {
        println("=== Menú ===")
        println("1. Agregar Propietario")
        println("2. Agregar Mascota")
        println("3. Mostrar Propietarios")
        println("4. Mostrar Mascotas")
        println("5. Actualizar Propietario")
        println("6. Actualizar Mascota")
        println("7. Eliminar Propietario")
        println("8. Eliminar Mascota")
        println("9. Salir")

        print("Seleccione una opción: ")
        opcion = scanner.nextInt()

        when (opcion) {
            1 -> {
                agregarPropietario(propietarioArchivos)
            }
            2 -> {
                agregarMascota(propietarioArchivos, mascotaArchivos)
            }
            3 -> {
                mostrarPropietarios(propietarioArchivos)
            }
            4 -> {
                mostrarMascotas(mascotaArchivos)
            }
            5 -> {
                actualizarPropietario(propietarioArchivos)
            }
            6 -> {
                actualizarMascota(propietarioArchivos, mascotaArchivos)
            }
            7 -> {
                eliminarPropietario(propietarioArchivos)
            }
            8 -> {
                eliminarMascota(mascotaArchivos)
            }
            9 -> {
                println("Saliendo de la aplicación.")
            }
            else -> println("Opción no válida. Inténtelo nuevamente.")
        }
    } while (opcion != 9)
}

fun agregarPropietario(propietarioArchivos: PropietarioArchivos) {
    println("=== Agregar Propietario ===")
    print("ID: ")
    val id = readLine()?.toInt() ?: 0

    print("Nombre: ")
    val nombre = readLine() ?: ""

    print("Edad: ")
    val edad = readLine()?.toInt() ?: 0

    print("¿Tiene Mascotas? (true/false): ")
    val tieneMascotas = readLine()?.toBoolean() ?: false

    print("Fecha de Registro (dd/MM/yyyy): ")
    val fechaRegistroStr = readLine() ?: ""
    val fechaRegistro = SimpleDateFormat("dd/MM/yyyy").parse(fechaRegistroStr)

    val nuevoPropietario = Propietario(id, nombre, edad, tieneMascotas, fechaRegistro)
    propietarioArchivos.guardarPropietario(nuevoPropietario)

    println("Propietario agregado correctamente.\n")
}

fun agregarMascota(propietarioArchivos: PropietarioArchivos, mascotaArchivos: MascotaArchivos) {
    println("=== Agregar Mascota ===")
    print("ID: ")
    val id = readLine()?.toInt() ?: 0

    print("Nombre: ")
    val nombre = readLine() ?: ""

    print("Raza: ")
    val raza = readLine() ?: ""

    print("Edad: ")
    val edad = readLine()?.toInt() ?: 0

    print("Peso: ")
    val peso = readLine()?.toDouble() ?: 0.0

    print("Propietario ID: ")
    val propietarioId = readLine()?.toInt() ?: 0

    print("Nombre del Propietario: ")
    val nombrePropietario = readLine() ?: ""

    print("Fecha de Registro (dd/MM/yyyy): ")
    val fechaRegistroStr = readLine() ?: ""
    val fechaRegistro = SimpleDateFormat("dd/MM/yyyy").parse(fechaRegistroStr)

    val nuevaMascota = Mascota(id, nombre, raza, edad, peso, propietarioId, nombrePropietario, fechaRegistro)
    mascotaArchivos.guardarMascota(nuevaMascota)

    println("Mascota agregada correctamente.\n")
}

fun mostrarPropietarios(propietarioArchivos: PropietarioArchivos) {
    println("=== Propietarios ===")
    val propietarios = propietarioArchivos.obtenerPropietarios()
    propietarios.forEach { println(it) }
    println()
}

fun mostrarMascotas(mascotaArchivos: MascotaArchivos) {
    println("=== Mascotas ===")
    val mascotas = mascotaArchivos.obtenerMascotas()
    mascotas.forEach { println(it) }
    println()
}

fun actualizarPropietario(propietarioArchivos: PropietarioArchivos) {
    println("=== Actualizar Propietario ===")
    // Capturar ID del propietario a actualizar
    print("Ingrese el ID del Propietario a actualizar: ")
    val idActualizar = readLine()?.toIntOrNull()

    if (idActualizar != null) {
        // Verificar si el propietario existe
        val propietarioExistente = propietarioArchivos.obtenerPropietario(idActualizar)

        // Si el propietario existe, permitir la actualización
        if (propietarioExistente != null) {
            // Capturar nuevos datos del usuario...
            print("Nuevo nombre (Enter para mantener el actual '${propietarioExistente.nombre}'): ")
            val nuevoNombre = readLine()?.takeIf { it.isNotBlank() } ?: propietarioExistente.nombre

            print("Nueva edad (Enter para mantener la actual '${propietarioExistente.edad}'): ")
            val nuevaEdad = readLine()?.toIntOrNull() ?: propietarioExistente.edad

            print("¿Tiene Mascotas? (true/false) (Enter para mantener el actual '${propietarioExistente.tieneMascotas}'): ")
            val nuevaTieneMascotas = readLine()?.toBoolean() ?: propietarioExistente.tieneMascotas

            print("Nueva fecha de Registro (dd/MM/yyyy) (Enter para mantener la actual '${SimpleDateFormat("dd/MM/yyyy").format(propietarioExistente.fechaRegistro)}'): ")
            val nuevaFechaRegistroStr = readLine()
            val nuevaFechaRegistro = if (nuevaFechaRegistroStr.isNullOrBlank()) propietarioExistente.fechaRegistro else SimpleDateFormat("dd/MM/yyyy").parse(nuevaFechaRegistroStr)

            val propietarioActualizado = Propietario(idActualizar, nuevoNombre, nuevaEdad, nuevaTieneMascotas, nuevaFechaRegistro)
            propietarioArchivos.actualizarPropietario(propietarioActualizado)
            println("Propietario actualizado correctamente.\n")
        } else {
            println("No se encontró un Propietario con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}

fun actualizarMascota(propietarioArchivos: PropietarioArchivos, mascotaArchivos: MascotaArchivos) {
    println("=== Actualizar Mascota ===")
    // Capturar ID de la mascota a actualizar
    print("Ingrese el ID de la Mascota a actualizar: ")
    val idActualizar = readLine()?.toIntOrNull()

    if (idActualizar != null) {
        // Verificar si la mascota existe
        val mascotaExistente = mascotaArchivos.obtenerMascota(idActualizar)

        // Si la mascota existe, permitir la actualización
        if (mascotaExistente != null) {
            // Capturar nuevos datos del usuario...
            print("Nuevo nombre (Enter para mantener el actual '${mascotaExistente.nombre}'): ")
            val nuevoNombre = readLine()?.takeIf { it.isNotBlank() } ?: mascotaExistente.nombre

            print("Nueva raza (Enter para mantener la actual '${mascotaExistente.raza}'): ")
            val nuevaRaza = readLine()?.takeIf { it.isNotBlank() } ?: mascotaExistente.raza

            print("Nueva edad (Enter para mantener la actual '${mascotaExistente.edad}'): ")
            val nuevaEdad = readLine()?.toIntOrNull() ?: mascotaExistente.edad

            print("Nuevo peso (Enter para mantener el actual '${mascotaExistente.peso}'): ")
            val nuevoPeso = readLine()?.toDoubleOrNull() ?: mascotaExistente.peso

            print("Nuevo ID del Propietario (Enter para mantener el actual '${mascotaExistente.propietarioId}'): ")
            val nuevoPropietarioId = readLine()?.toIntOrNull() ?: mascotaExistente.propietarioId

            print("Nuevo nombre del Propietario (Enter para mantener el actual '${mascotaExistente.nombrePropietario}'): ")
            val nuevoNombrePropietario = readLine()?.takeIf { it.isNotBlank() } ?: mascotaExistente.nombrePropietario

            print("Nueva fecha de Registro (dd/MM/yyyy) (Enter para mantener la actual '${SimpleDateFormat("dd/MM/yyyy").format(mascotaExistente.fechaRegistro)}'): ")
            val nuevaFechaRegistroStr = readLine()
            val nuevaFechaRegistro = if (nuevaFechaRegistroStr.isNullOrBlank()) mascotaExistente.fechaRegistro else SimpleDateFormat("dd/MM/yyyy").parse(nuevaFechaRegistroStr)

            val mascotaActualizada = Mascota(idActualizar, nuevoNombre, nuevaRaza, nuevaEdad, nuevoPeso, nuevoPropietarioId, nuevoNombrePropietario, nuevaFechaRegistro)
            mascotaArchivos.actualizarMascota(mascotaActualizada)
            println("Mascota actualizada correctamente.\n")
        } else {
            println("No se encontró una Mascota con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}

fun eliminarPropietario(propietarioArchivos: PropietarioArchivos) {
    println("=== Eliminar Propietario ===")
    // Capturar ID del propietario a eliminar
    print("Ingrese el ID del Propietario a eliminar: ")
    val idEliminar = readLine()?.toIntOrNull()

    if (idEliminar != null) {
        // Verificar si el propietario existe
        val propietarioExistente = propietarioArchivos.obtenerPropietario(idEliminar)

        // Si el propietario existe, permitir la eliminación
        if (propietarioExistente != null) {
            propietarioArchivos.eliminarPropietario(idEliminar)
            println("Propietario eliminado correctamente.\n")
        } else {
            println("No se encontró un Propietario con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}

fun eliminarMascota(mascotaRepository: MascotaArchivos) {
    println("=== Eliminar Mascota ===")
    // Capturar ID de la mascota a eliminar
    print("Ingrese el ID de la Mascota a eliminar: ")
    val idEliminar = readLine()?.toIntOrNull()

    if (idEliminar != null) {
        // Verificar si la mascota existe
        val mascotaExistente = mascotaRepository.obtenerMascota(idEliminar)

        // Si la mascota existe, permitir la eliminación
        if (mascotaExistente != null) {
            mascotaRepository.eliminarMascota(idEliminar)
            println("Mascota eliminada correctamente.\n")
        } else {
            println("No se encontró una Mascota con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}


