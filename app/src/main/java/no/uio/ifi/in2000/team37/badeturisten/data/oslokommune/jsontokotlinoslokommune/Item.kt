package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune


import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.Algolia

data class Item(
    val id: String,
    val name: String,
    val label: String,
    val placeholder: String,
    val algolia: Algolia?,
    val value: Any
)