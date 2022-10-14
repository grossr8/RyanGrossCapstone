package com.example.ryangrosscapstone;

import androidx.annotation.Nullable;

public class BoxSize {
    private Integer boxId;
    private Integer boxSize;

    public BoxSize(Integer boxSize, @Nullable Integer boxId){
        this.boxSize = boxSize;
        this.boxId = boxId;
    }
    public Integer getBoxId(){
        return boxId;
    }
    public void setBoxId(Integer boxId){
        this.boxId = boxId;
    }

    public Integer getBoxSize(){
        return boxSize;
    }
    public void setBoxSize(Integer boxSize){
        this.boxSize = boxSize;
    }


}
