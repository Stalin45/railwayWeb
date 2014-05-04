package com.tschool.railwayweb.dto;

import java.util.List;

public class FindTrainFormDTO {
    
    private List<TrainDTO> trainListDTO;
    private MsgBox msgBox = new MsgBox();;

    public List<TrainDTO> getTrainListDTO() {
        return trainListDTO;
    }

    public void setTrainListDTO(List<TrainDTO> trainListDTO) {
        this.trainListDTO = trainListDTO;
    }

    public MsgBox getMsgBox() {
        return msgBox;
    }

    public void setMsgBox(MsgBox msgBox) {
        this.msgBox = msgBox;
    }
}
