package com.google.samples.apps.nowinandroid.core.network.fake

import com.google.samples.apps.nowinandroid.core.model.data.NewsResourceType.Codelab
import com.google.samples.apps.nowinandroid.core.network.model.NetworkNewsResource
import com.google.samples.apps.nowinandroid.core.network.model.NetworkTopic
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.intellij.lang.annotations.Language

/* ktlint-disable max-line-length */

object FakeDataSource {
    val sampleTopic = NetworkTopic(
        id = "2",
        name = "Headlines",
        shortDescription = "News we want everyone to see",
        longDescription = "Stay up to date with the latest events and announcements from Android!",
        url = "",
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/now-in-android.appspot.com/o/img%2Fic_topic_Headlines.svg?alt=media&token=506faab0-617a-4668-9e63-4a2fb996603f"
    )
    val sampleResource = NetworkNewsResource(
        id = "1",
        episodeId = "60",
        title = "Android Basics with Compose",
        content = "We released the first two units of Android Basics with Compose, our first free course that teaches Android Development with Jetpack Compose to anyone; you do not need any prior programming experience other than basic computer literacy to get started. You’ll learn the fundamentals of programming in Kotlin while building Android apps using Jetpack Compose, Android’s modern toolkit that simplifies and accelerates native UI development. These two units are just the beginning; more will be coming soon. Check out Android Basics with Compose to get started on your Android development journey",
        url = "https://android-developers.googleblog.com/2022/05/new-android-basics-with-compose-course.html",
        headerImageUrl = "https://developer.android.com/images/hero-assets/android-basics-compose.svg",
        authors = listOf("20"),
        publishDate = LocalDateTime(
            year = 2022,
            monthNumber = 5,
            dayOfMonth = 4,
            hour = 23,
            minute = 0,
            second = 0,
            nanosecond = 0
        ).toInstant(TimeZone.UTC),
        type = Codelab,
        topics = listOf("3", "11", "10"),
    )

    @Language("JSON")
    val topicsData = """
[
    {
      "id": "2",
      "name": "Premier League",
      "shortDescription": "The Premier League, also known as the English Premier League.",
      "longDescription": "The Premier League, also known as the English Premier League, is the top level of the English football league system. Contested by 20 clubs, it operates on a system of promotion and relegation with the English Football League. Seasons typically run from August to May with each team playing 38 matches.",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/EPL.jpeg",
      "url": ""
    },
    {
      "id": "3",
      "name": "Champions League",
      "shortDescription": "he UEFA Champions League is an annual club football competition organised by the Union of European Football Associations",
      "longDescription": "The UEFA Champions League is an annual club football competition organised by the Union of European Football Associations and contested by top-division European clubs, deciding the competition winners through a round robin group stage to qualify for a double-legged knockout format, and a single leg final.",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/UCL.png",
      "url": ""
    }
  ]
    """.trimIndent()

    @Language("JSON")
    val data = """
[
    {
      "id": "1",
      "episodeId": "60",
      "title": "Liverpool vs Real Madrid",
      "content": "This will be the ninth meeting between Liverpool and Real Madrid in the European Cup/UEFA Champions League – after winning the first three between 1981 and 2009, the Reds are winless in the last five (D1 L4), including a defeat in the final in 2018.\n\nLiverpool and Real Madrid will meet for the third time in the final of the European Cup/UEFA Champions League, the most between two clubs across the history of the two competitions. Liverpool won the first final back in 1981 (1-0), before Real Madrid beat Jürgen Klopp’s side 3-1 in 2018.\n\nLiverpool are looking to win the European Cup/UEFA Champions League for the seventh time in their history – a victory in Paris would take them level with AC Milan (7), and leave them only behind opponents Real Madrid (13) for the most overall victories across the two competitions.\n\nReal Madrid are aiming to win the European Cup/UEFA Champions League for the 14th time, which would then be twice as many as any other team (7, AC Milan). Los Blancos have lifted the trophy on each of the last seven occasions when they have appeared in the final, with their last defeat in the showpiece coming against Liverpool back in 1981 – a game that was also played in Paris thanks to an Alan Kennedy winner.\n\nLiverpool manager Jürgen Klopp has faced Real Madrid more times than any other opponent in the UEFA Champions League (9), winning just three of those matches (D2 L4). Among teams he has faced at least three times in the competition, against no other opponent does Klopp have a lower win percentage (33%).\n\nTip: 2.10 - Liverpool (1)",
      "url": "https://www.uefa.com/uefachampionsleague/match/2034586--liverpool-vs-real-madrid/",
      "headerImageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/images/Tottenham-vs-Liverpool-UCL-Final.png",
      "publishDate": "2022-05-28T02:00:00.000Z",
      "type": "Article 📚",
      "topics": [
        "3"
      ],
      "authors": [
        "2"
      ]
    },
    {
      "id": "2",
      "episodeId": "60",
      "title": "Liverpool lost the UCL title",
      "content": "Real Madrid have won the European Cup/Champions League on 14 occasions – twice as many times as any other side in the history of the competition.\n\nOnly Juventus (5) have lost more UEFA Champions League finals than Liverpool (3), while no manager has lost more finals in the competition’s history than Liverpool’s Jürgen Klopp (3).\n\nEach of the last three UEFA Champions League finals have finished with a 1-0 scoreline – as many as in the first 27 finals of the competition.\n\nLiverpool 0-1 Real Madrid",
      "url": "https://www.uefa.com/uefachampionsleague/match/2034586--liverpool-vs-real-madrid/",
      "headerImageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/images/Liverpool-vs-Real-Salah.png",
      "publishDate": "2022-05-30T02:00:00.000Z",
      "type": "Article 📚",
      "topics": [
        "3"
      ],
      "authors": [
        "2"
      ]
    }
  ]
    """.trimIndent()

    @Language("JSON")
    val authors = """
 [
    {
      "id": "1",
      "name": "Man City",
      "mediumPage": "",
      "twitter": "https://twitter.com/ManCity",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Man.+City.png",
      "bio": ""
    },
    {
      "id": "2",
      "name": "Liverpool",
      "mediumPage": "",
      "twitter": "https://twitter.com/LFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Liverpool.png",
      "bio": ""
    },
    {
      "id": "3",
      "name": "Chelsea",
      "mediumPage": "",
      "twitter": "https://twitter.com/ChelseaFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Chelsea.png",
      "bio": ""
    },
    {
      "id": "4",
      "name": "Tottenham",
      "mediumPage": "",
      "twitter": "https://twitter.com/SpursOfficial",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Tottenham.jpeg",
      "bio": ""
    },
    {
      "id": "5",
      "name": "Arsenal",
      "mediumPage": "",
      "twitter": "https://twitter.com/Arsenal",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Arsenal.png",
      "bio": ""
    },
    {
      "id": "6",
      "name": "Man Utd",
      "mediumPage": "",
      "twitter": "https://twitter.com/ManUtd",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Man+Utd.png",
      "bio": ""
    },
    {
      "id": "7",
      "name": "West Ham",
      "mediumPage": "",
      "twitter": "https://twitter.com/WestHam",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/West+Ham.png",
      "bio": ""
    },
    {
      "id": "8",
      "name": "Leicester",
      "mediumPage": "",
      "twitter": "https://twitter.com/LCFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Leicester+City.png",
      "bio": ""
    },
    {
      "id": "9",
      "name": "Brighton",
      "mediumPage": "",
      "twitter": "https://twitter.com/OfficialBHAFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Brighton.png",
      "bio": ""
    },
    {
      "id": "10",
      "name": "Wolves",
      "mediumPage": "",
      "twitter": "https://twitter.com/Wolves",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Wolves.png",
      "bio": ""
    },
    {
      "id": "11",
      "name": "Newcastle",
      "mediumPage": "",
      "twitter": "https://twitter.com/NUFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Newcastle.png",
      "bio": ""
    },
    {
      "id": "12",
      "name": "Crystal Palace",
      "mediumPage": "",
      "twitter": "https://twitter.com/CPFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Crystal+Palace.png",
      "bio": ""
    },
    {
      "id": "13",
      "name": "Brentford",
      "mediumPage": "",
      "twitter": "https://twitter.com/BrentfordFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Brentford.png",
      "bio": ""
    },
    {
      "id": "14",
      "name": "Aston Villa",
      "mediumPage": "",
      "twitter": "https://twitter.com/AVFCOfficial",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Aston+Villa.jpeg",
      "bio": ""
    },
    {
      "id": "15",
      "name": "Southampton",
      "mediumPage": "",
      "twitter": "https://twitter.com/SouthamptonFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Southampton.png",
      "bio": ""
    },
    {
      "id": "16",
      "name": "Everton",
      "mediumPage": "",
      "twitter": "https://twitter.com/Everton",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Everton.png",
      "bio": ""
    },
    {
      "id": "17",
      "name": "Leeds",
      "mediumPage": "",
      "twitter": "https://twitter.com/LUFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Leeds.png",
      "bio": ""
    },
    {
      "id": "18",
      "name": "Burnley",
      "mediumPage": "",
      "twitter": "https://twitter.com/BurnleyOfficial",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Burnley.png",
      "bio": ""
    },
    {
      "id": "19",
      "name": "Watford",
      "mediumPage": "",
      "twitter": "https://twitter.com/WatfordFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Watford.png",
      "bio": ""
    },
    {
      "id": "20",
      "name": "Norwich",
      "mediumPage": "",
      "twitter": "https://twitter.com/NorwichCityFC",
      "imageUrl": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Norwich.png",
      "bio": ""
    }
  ]
    """.trimIndent()

    @Language("JSON")
    val predictions = """
[
    {
      "id": "1",
      "matchName": "Man Utd - Leicester",
      "matchDate": "2018-08-11T14:30:00.000Z",
      "status": "https://firebasestorage.googleapis.com/v0/b/infinitybet-cbaa8.appspot.com/o/check.png?alt=media&token=908e4367-97eb-4c7b-9545-315ddce4a44f",
      "team1": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Man+Utd.png",
      "team2": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Leicester+City.png",
      "uniqueTip": "2.00 - Over 2.5 Goals"
    },
    {
      "id": "2",
      "matchName": "Newcastle - Tottenham",
      "matchDate": "2018-08-11T17:00:00.000Z",
      "status": "https://firebasestorage.googleapis.com/v0/b/infinitybet-cbaa8.appspot.com/o/uncheck.png?alt=media&token=93e607f6-73ba-41a6-9056-7c4e49a48d10",
      "team1": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Newcastle.png",
      "team2": "https://infinity-bet-storage-ea386551145656-staging.s3.eu-west-2.amazonaws.com/Tottenham.jpeg",
      "uniqueTip": "1.95 - Both Teams to Score (No)"
    }
]
    """.trimIndent()
}

/* ktlint-enable max-line-length */
