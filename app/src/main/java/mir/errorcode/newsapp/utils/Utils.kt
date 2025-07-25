package mir.errorcode.newsapp.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object Utils {
    fun formatDate(isoDate: String?): String {
        return try {
            val parsedDate = ZonedDateTime.parse(isoDate)
            val formatter = DateTimeFormatter.ofPattern("HH:mm MM/dd", Locale.getDefault())
            parsedDate.format(formatter)
        } catch (e: Exception) {
            ""
        }
    }

}