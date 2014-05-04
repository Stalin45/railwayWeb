package com.tschool.railwayweb.dto;

import java.util.List;

public class TrainFormDTO {
    private TrainDTO trainDTO;
    private List<TrainTypeDTO> trainTypeListDTO;
    private List<PathDTO> pathListDTO;

    public TrainFormDTO() {
    }

    public TrainFormDTO(TrainDTO trainDTO, List<TrainTypeDTO> trainTypeListDTO, List<PathDTO> pathListDTO) {
        this.trainDTO = trainDTO;
        this.trainTypeListDTO = trainTypeListDTO;
        this.pathListDTO = pathListDTO;
    }

    public TrainDTO getTrainDTO() {
        return trainDTO;
    }

    public void setTrainDTO(TrainDTO trainDTO) {
        this.trainDTO = trainDTO;
    }

    public List<TrainTypeDTO> getTrainTypeListDTO() {
        return trainTypeListDTO;
    }

    public void setTrainTypeListDTO(List<TrainTypeDTO> trainTypeListDTO) {
        this.trainTypeListDTO = trainTypeListDTO;
    }

    public List<PathDTO> getPathListDTO() {
        return pathListDTO;
    }

    public void setPathListDTO(List<PathDTO> pathListDTO) {
        this.pathListDTO = pathListDTO;
    }
}
