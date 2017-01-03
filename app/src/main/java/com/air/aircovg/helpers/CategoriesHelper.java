package com.air.aircovg.helpers;

import com.air.aircovg.R;

/**
 * Created by ayush AS on 27/12/16.
 */

public class CategoriesHelper {

    public static String[] getCategories(){

        return new String[] {
                "general",
                "sport",
                "technology",
                "business",
                "entertainment",
                "music",
                "science-and-nature"
        };
    }

    public static int[] getCount(){
        return new int[]{
                4,5,5,6,2,2,2
        };
    }

    public static int[] getCategoriesImage(){
        return new int[]{
                R.drawable.general_image,
                R.drawable.sport,
                R.drawable.technology,
                R.drawable.business,
                R.drawable.entertainment,
                R.drawable.music,
                R.drawable.science_and_nature
        };
    }

}
