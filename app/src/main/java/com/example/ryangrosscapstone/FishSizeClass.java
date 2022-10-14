package com.example.ryangrosscapstone;

import androidx.annotation.Nullable;

public class FishSizeClass {
    private Integer fishId;
    private String fishName;
    private Double fishWeight;
    private Double fishDiagonalLength;
    private Integer fishBoxSize;

    public FishSizeClass(String fishName, Double fishWeight, Double fishDiagonalLength, @Nullable Integer fishId, @Nullable Integer fishBoxSize){
        this.fishName = fishName;
        this.fishWeight = fishWeight;
        this.fishDiagonalLength = fishDiagonalLength;
        this.fishId = fishId;
        this.fishBoxSize = fishBoxSize;
    }
    public Integer getFishId(){
        return fishId;
    }
    public void setFishId(Integer fishId){
        this.fishId = fishId;
    }

    public String getFishName(){
        return fishName;
    }
    public void setFishName(String fishName){
        this.fishName = fishName;
    }

    public Double getFishWeight(){
        return fishWeight;
    }
    public void setFishWeight(Double fishWeight){
        this.fishWeight = fishWeight;
    }

    public Double getFishDiagonalLength(){
        return fishDiagonalLength;
    }
    public void setFishDiagonalLength(Double fishDiagonalLength){
        this.fishDiagonalLength = fishDiagonalLength;
    }
    public Integer getFishBoxSize(){
        return fishBoxSize;
    }
    public void setFishBoxSize(Integer fishBoxSize){
        this.fishBoxSize = fishBoxSize;
    }
}
