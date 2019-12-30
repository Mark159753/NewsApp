package com.example.newsapp.test

import com.example.newsapp.model.Article
import com.example.newsapp.model.Source
import com.example.newsapp.ui.ViewTypes

class DataFrom {

    fun getNewsList():List<Article>{
        val article = ArrayList<Article>()
        article.add(
            Article( ViewTypes.MAIN_ARTICLE, "news","Joseph Young", "Overnight, the bitcoin price rose to as high as \$7,642 and is up by more than \$1,200 within five days. With the reversal, the dominant cryptocurrency crucially avoided miner capitulation and retest of an ascending channel dating back to 2015.",
                "Overnight, the bitcoin price rose to as high as $7,642 and is up by more than $1,200 within five days. With the reversal, the dominant cryptocurrency crucially avoided miner capitulation and retest of an ascending channel dating back to 2015. #1: No bitcoin m…",
                "2019-12-23T09:43:30Z", Source(null,"Newsbtc.com"),
                "Bitcoin Just Spiked to $7,600 and It’s Showing 2 Big Signs of a Real Recovery",
                "https://www.newsbtc.com/2019/12/23/bitcoin-just-spiked-to-7600-and-its-showing-2-big-signs-of-a-real-recovery/",
                "https://www.newsbtc.com/wp-content/uploads/2019/12/shutterstock_697369813-1200x780.jpg"
            )
        )
        for (i in 0..4){
            article.add(
                Article( ViewTypes.ORDINARY_ARTICLE, "news","Joseph Young", "Overnight, the bitcoin price rose to as high as \$7,642 and is up by more than \$1,200 within five days. With the reversal, the dominant cryptocurrency crucially avoided miner capitulation and retest of an ascending channel dating back to 2015.",
                    "Overnight, the bitcoin price rose to as high as $7,642 and is up by more than $1,200 within five days. With the reversal, the dominant cryptocurrency crucially avoided miner capitulation and retest of an ascending channel dating back to 2015. #1: No bitcoin m…",
                    "2019-12-23T09:43:30Z", Source(null,"Newsbtc.com"),
                    "Bitcoin Just Spiked to $7,600 and It’s Showing 2 Big Signs of a Real Recovery",
                    "https://www.newsbtc.com/2019/12/23/bitcoin-just-spiked-to-7600-and-its-showing-2-big-signs-of-a-real-recovery/",
                    "https://www.newsbtc.com/wp-content/uploads/2019/12/shutterstock_697369813-1200x780.jpg"
                )
            )
        }

        article.add(
            Article(
                ViewTypes.TITLE_GROUP, "business", null, null, null, null,
                null, "Business", null, null
            )
        )

        article.add(
            Article(ViewTypes.BEIGE_ARTICLE, "business","Joseph Young", "Overnight, the bitcoin price rose to as high as \$7,642 and is up by more than \$1,200 within five days. With the reversal, the dominant cryptocurrency crucially avoided miner capitulation and retest of an ascending channel dating back to 2015.",
                "Overnight, the bitcoin price rose to as high as $7,642 and is up by more than $1,200 within five days. With the reversal, the dominant cryptocurrency crucially avoided miner capitulation and retest of an ascending channel dating back to 2015. #1: No bitcoin m…",
                "2019-12-23T09:43:30Z", Source(null,"Newsbtc.com"),
                "Bitcoin Just Spiked to $7,600 and It’s Showing 2 Big Signs of a Real Recovery",
                "https://www.newsbtc.com/2019/12/23/bitcoin-just-spiked-to-7600-and-its-showing-2-big-signs-of-a-real-recovery/",
                "https://www.newsbtc.com/wp-content/uploads/2019/12/shutterstock_697369813-1200x780.jpg"
            )
        )

        for (i in 0..4){
            article.add(
                Article( ViewTypes.ORDINARY_ARTICLE, "business","Joseph Young", "Overnight, the bitcoin price rose to as high as \$7,642 and is up by more than \$1,200 within five days. With the reversal, the dominant cryptocurrency crucially avoided miner capitulation and retest of an ascending channel dating back to 2015.",
                    "Overnight, the bitcoin price rose to as high as $7,642 and is up by more than $1,200 within five days. With the reversal, the dominant cryptocurrency crucially avoided miner capitulation and retest of an ascending channel dating back to 2015. #1: No bitcoin m…",
                    "2019-12-23T09:43:30Z", Source(null,"Newsbtc.com"),
                    "Bitcoin Just Spiked to $7,600 and It’s Showing 2 Big Signs of a Real Recovery",
                    "https://www.newsbtc.com/2019/12/23/bitcoin-just-spiked-to-7600-and-its-showing-2-big-signs-of-a-real-recovery/",
                    "https://www.newsbtc.com/wp-content/uploads/2019/12/shutterstock_697369813-1200x780.jpg"
                )
            )
        }
        return article
    }
}