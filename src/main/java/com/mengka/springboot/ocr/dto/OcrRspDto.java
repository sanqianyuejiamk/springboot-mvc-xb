package com.mengka.springboot.ocr.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OcrRspDto {

    @SerializedName("sid")
    @Expose
    public String sid;

    @SerializedName("prism_version")
    @Expose
    public String prismVersion;

    @SerializedName("prism_wnum")
    @Expose
    public Integer prismWnum;

    @SerializedName("prism_wordsInfo")
    @Expose
    public List<OcrPrismWordInfo> prismWordsInfo;

    @SerializedName("height")
    @Expose
    public Integer height;

    @SerializedName("width")
    @Expose
    public Integer width;

    @SerializedName("orgHeight")
    @Expose
    public Integer orgHeight;

    @SerializedName("orgWidth")
    @Expose
    public Integer orgWidth;

    @SerializedName("content")
    @Expose
    public String content;
}
