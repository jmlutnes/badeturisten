package no.uio.ifi.in2000.team37.badeturisten.data.favourite
import android.provider.BaseColumns

object FavouriteContract {
    // Define table contents
    class FavoriteEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorites"
            const val COLUMN_ID = "id"
            const val COLUMN_BEACH = "beach"
        }
    }
}