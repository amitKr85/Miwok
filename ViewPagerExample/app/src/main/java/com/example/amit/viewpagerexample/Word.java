package com.example.amit.viewpagerexample;

/**
 * Created by AMIT on 16-03-2018.
 */

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId;
    private int mAudioResourceId;

    public static final int NO_IMAGE_PROVIDED=-1;

    Word(String mDT, String mMT, int mARI){
        mDefaultTranslation=mDT;
        mMiwokTranslation=mMT;
        mImageResourceId=NO_IMAGE_PROVIDED;
        mAudioResourceId=mARI;
    }

    Word(String mDT, String mMT, int mIRI, int mARI){
        mDefaultTranslation=mDT;
        mMiwokTranslation=mMT;
        mImageResourceId=mIRI;
        mAudioResourceId=mARI;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }
    public int getResourceId(){
        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId!=NO_IMAGE_PROVIDED;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mAudioResourceId=" + mAudioResourceId +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }
}
