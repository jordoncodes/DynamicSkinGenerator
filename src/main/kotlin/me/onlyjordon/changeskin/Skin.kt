package me.onlyjordon.changeskin

class Skin {

    companion object {

//        private val skinsJson = HashMap<BufferedImage, String>()

//        /**
//         * Add a skin to mineskin
//         * @param skin The skin to add
//         * @return The JSON response from the mineskin API
//         * @throws IOException If the skin could not be added
//         */
//        fun getJsonFromSkin(skin: BufferedImage): String? {
//            if (skinsJson.containsKey(skin)) {
//                return skinsJson[skin]
//            }
//            val buffer: ByteArray
//            ByteArrayOutputStream().use {
//                ImageIO.write(skin, "png", it)
//                it.flush()
//                buffer = it.toByteArray()
//            }
//            // use java's built in http client instead
//            val client = HttpClient.newHttpClient()
//            val request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.mineskin.org/generate/upload?visibility=1"))
//                .header("Content-Type", "image/png")
//                .POST(HttpRequest.BodyPublishers.ofByteArray(buffer))
//                .build()
//            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//            skinsJson[skin] = response.body()
//            return response.body()
//        }

    }

}