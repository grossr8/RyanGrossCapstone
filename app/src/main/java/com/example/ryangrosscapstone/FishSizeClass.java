package com.example.ryangrosscapstone;

public class FishSizeClass {
    private Integer fishId;
    private String fishName;
    private Double fishWeight;
    private Double fishDiagonalLength;

    public FishSizeClass(String fishName, Double fishWeight, Double fishDiagonalLength){
        this.fishName = fishName;
        this.fishWeight = fishWeight;
        this.fishDiagonalLength = fishDiagonalLength;
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

}
