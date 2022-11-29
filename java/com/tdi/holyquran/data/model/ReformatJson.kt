//package com.tdi.holyquran.data.model
//
//import android.content.Context
//import com.google.gson.Gson
//import kotlin.random.Random
//
//
//class ReformatJson() {
//
////    fun getAzkar(context: Context): List<Azkar>{
////        var jsonString: String?
////        jsonString = "mm"
////        try {
////            jsonString = context.assets.open("raw/azkar.json")
////                .bufferedReader()
////                .use { it.readText() }
////        } catch (ioException: IOException) {
////
////            Toast.makeText(context,ioException.message,Toast.LENGTH_LONG).show()
////            Log.d("json",ioException.message.toString())
////
////        }
////
////        val listAzkar = object : TypeToken<List<Azkar>>() {}.type
////        context.assets.close()
////        return Gson().fromJson(jsonString, listAzkar)
////    }
//
//    fun getAzkar(context: Context): Azkar? {
//        val myJson = """
//    {
//        "category": "أذكار الصباح",
//        "count": "1",
//        "description": "من قالها حين يصبح أجير من الجن حتى يمسى ومن قالها حين يمسى أجير من الجن حتى يصبح.",
//        "reference": "[آية الكرسى - البقرة 255].",
//        "zekr": "أَعُوذُ بِاللهِ مِنْ الشَّيْطَانِ الرَّجِيمِ\nاللّهُ لاَ إِلَـهَ إِلاَّ هُوَ الْحَيُّ الْقَيُّومُ لاَ تَأْخُذُهُ سِنَةٌ وَلاَ نَوْمٌ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الأَرْضِ مَن ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلاَّ بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلاَ يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلاَّ بِمَا شَاء وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالأَرْضَ وَلاَ يَؤُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ.\n[آية الكرسى - البقرة 255]."
//    }
//""".trimIndent()
//        var gson = Gson()
//        var mMineUserEntity = gson.fromJson(myJson, Azkar::class.java)
//        return mMineUserEntity
//    }
////    fun getAzkar(context: Context): List<Azkar> {
////
////
////    val jsonArray = JSONArray("raw/azkar.json")
////
////    val totalAzkar: Int = jsonArray.length()
////
////    val random = Random.nextInt(totalAzkar)
////
////    val quoteData: JSONObject = jsonArray.getJSONObject(random)
////
////    val zekr = quoteData.getString("zekr")
////    val category = quoteData.getString("category")
////    val json: String = readFileAsString(file)
////
////    return listOf(Azkar(category,null,null,category,zekr))
////
////}
//
////
////    fun readJson(context: Context) {
////        val parser = JsonParser()
////        try {
////            val fileReader = FileReader("raw/azkar.json")
////            val json = (JSONObject("raw/azkar.json"))
////            parser.parse(fileReader)
////            val zekr = json.get("zekr")
////            val cat =  json.get("category")
////            val ref =  json.get("reference")
//////            System.out.println("title: " + title)
//////            System.out.println("author: " + author)
//////            System.out.println("price: " + price)
////            val characters = (JSONArray("raw/azkar.json"))
////            json.get("characters")
////            val i = characters.getJSONObject(Random.nextInt(0,336))
////
////        } catch (ex:Exception) {
////                ex.printStackTrace() }
////    }
//
//
//
//
//
//    fun fromJsonToDataModel(azkar: Azkar?): Azkar {
//        val index = Random.nextInt(0,336)
//        val zekr = azkar?.zekr
//        val ref = azkar?.reference
//        val cat = azkar?.category
//        val count = azkar?.count
//        val desc = azkar?.description
//        return Azkar(cat ,count,desc,ref,zekr)
//    }
//}