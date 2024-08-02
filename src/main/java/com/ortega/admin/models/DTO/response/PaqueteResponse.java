package com.ortega.admin.models.DTO.response;

import com.ortega.admin.models.DTO.PaqueteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaqueteResponse {
    private List<PaqueteDTO> Tours;
    private List<PaqueteDTO> Promociones;
}
