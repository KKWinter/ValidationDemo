package org.mass.content;

/**
 * Created by zhj_7 on 2016/11/18.
 */



    /*

{
    "rank":"com.android.settings.allinone.services.main.MainService",
    "latrop":"onCreate",
    "gresse":"onDestroy",
    "rebalic":70962,
    "noitedi":"10",
    "querej":"2EDA7DA26F04C3434CF37B82DD882490",
    "eky":"SU",
    "rpu":[
        {
            "gazda":"119.23.136.52",
            "tropim":6600,
            "tropssa":6609,
            "vos":true
        }
    ]
}

     */


public class ContentFormat {
    public String cls;          //rank   >> com.android.settings.allinone.services.main.MainService
    public String key;          //ekey   >> SU
    public String entry;        //latrop >> onCreate
    public String finallizer;   //gresse >> onDestroy
    public String version;      //noitedi >> 10
    public String checksum;     //querej  >> 2EDA7DA26F04C3434CF37B82DD882490
    public long size;           //rebalic >> 70962
    public boolean illegal;
    public boolean verified;
    public int value1;
    public int value2;
    public String timestamp;
    public String validation;
    public float check;
    /**
     * {"cls":"xxxx","key":"xxx", "entry":"xxxx","finallizer":"xxxx","checksum":"xxxx","size":xxxx}
     */
}
