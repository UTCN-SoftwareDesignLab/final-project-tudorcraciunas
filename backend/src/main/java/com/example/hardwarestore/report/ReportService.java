package com.example.hardwarestore.report;

import com.example.hardwarestore.item.model.DTO.ItemDTO;
import javassist.bytecode.ByteArray;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface ReportService {
    ByteArrayOutputStream export(List<ItemDTO> itemDTOS);

    ReportType getType();
}
