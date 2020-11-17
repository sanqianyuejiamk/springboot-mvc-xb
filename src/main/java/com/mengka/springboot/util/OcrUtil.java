package com.mengka.springboot.util;

import com.google.gson.GsonBuilder;
import com.mengka.springboot.ocr.dto.OcrRspDto;
import java.time.LocalDate;

public class OcrUtil {

    public static OcrRspDto convertOcrRspDto(String sourceData){
        OcrRspDto neoWsDataGson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateAdapter())
                .create()
                .fromJson(sourceData, OcrRspDto.class);

        return neoWsDataGson;
    }
}
