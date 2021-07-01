package com.example.hardwarestore.report;


import com.example.hardwarestore.item.model.DTO.ItemDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

import static com.example.hardwarestore.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {

    @Override
    public ReportType getType() {
        return CSV;
    }

    @Override
    public ByteArrayOutputStream export(List<ItemDTO> itemDTOS) {
        try{
            CSVWriter csvWriter = new CSVWriter(new FileWriter("OutOfStock.csv"));
            for(ItemDTO itemDTO : itemDTOS){
                csvWriter.writeNext(new String[]{String.valueOf(itemDTO.getName())});
                csvWriter.writeNext(new String[]{String.valueOf(itemDTO.getDetails())});
                csvWriter.writeNext(new String[]{String.valueOf(itemDTO.getType())});
                csvWriter.writeNext(new String[]{String.valueOf(itemDTO.getPrice())});
            }
            csvWriter.flush();
            csvWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        byte[] buffer = new byte[4096];
        BufferedInputStream bis;
        try {

            bis = new BufferedInputStream(new FileInputStream("OutOfStock.csv"));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            int bytes;
            while ((bytes = bis.read(buffer, 0, buffer.length)) > 0) {
                output.write(buffer, 0, bytes);
            }
            bis.close();
            return  output;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
