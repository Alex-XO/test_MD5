import java.security.MessageDigest
import kotlin.random.Random

fun generateRandomString(length: Int): String {
    val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map {allowedChars.random()}
        .joinToString("")
}

fun md5(input: String): String {
    return MessageDigest
        .getInstance("MD5")
        .digest(input.toByteArray())
        .joinToString(" ") { "%02x".format(it) }
}

fun testMD5Collisions(numberOfStrings: Int, lengthOfString: Int) {
    val hashes = HashMap<String,String>()
    var collisionFound = false

    for (i in 0 until numberOfStrings) {
        val randomString = generateRandomString(lengthOfString)
        val hash = md5(randomString)

        if (hashes.keys.contains(hash)) {
            println("Collision found: $randomString -> $hash")
            println("${hashes.get(hash)}")
            collisionFound = true
            break
        }

        hashes.put(hash, randomString)
    }

    if (!collisionFound) {
        println("No collisions found.")
    }
}

fun main() {
    val numberOfStrings = 100000
    val lengthOfString = 10
    testMD5Collisions(numberOfStrings, lengthOfString)
}