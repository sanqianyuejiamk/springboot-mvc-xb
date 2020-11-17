package com.mengka.springboot.ocr.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OcrPos {

    @SerializedName("x")
    @Expose
    public Integer x;

    @SerializedName("y")
    @Expose
    public Integer y;
}
