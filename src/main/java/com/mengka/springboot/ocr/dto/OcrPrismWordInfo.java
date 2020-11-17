package com.mengka.springboot.ocr.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OcrPrismWordInfo {

    @SerializedName("word")
    @Expose
    public String word;

    @SerializedName("direction")
    @Expose
    public Integer direction;

    @SerializedName("pos")
    @Expose
    public List<OcrPos> pos;
}
