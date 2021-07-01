package com.example.hardwarestore.item;

import com.example.hardwarestore.item.model.DTO.ItemDTO;
import com.example.hardwarestore.item.model.DTO.SellItemDTO;
import com.example.hardwarestore.report.ReportServiceFactory;
import com.example.hardwarestore.report.ReportType;
import com.example.hardwarestore.user.dto.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.hardwarestore.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<ItemDTO> findAllItems(){
        return itemService.findAllDto();
    }

    @PostMapping
    public ItemDTO create(@RequestBody ItemDTO itemDTO){
        return itemService.create(itemDTO);
    }

    @PatchMapping(ENTITY)
    public ItemDTO edit(@PathVariable Long id, @RequestBody ItemDTO itemDTO){
        return itemService.edit(id, itemDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        itemService.delete(id);
    }

    @PatchMapping()
    public void sell(@RequestBody List<SellItemDTO> toSell, @AuthenticationPrincipal UserDetailsImpl userDetails){



        itemService.sellItems(toSell);
        itemService.sellEmail(userDetails.getId(), toSell);
    }

    @GetMapping(EXPORT_TYPE)
    public ResponseEntity<?> exportReport(@PathVariable ReportType type){

        ByteArrayOutputStream bodyOutput = reportServiceFactory.getReportService(type).export(itemService.findOutOfStock());
        ByteArrayResource byteArrayResource = new ByteArrayResource(bodyOutput.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Items_Out_Of_Stock_PDFBox.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }
    //
    //TODO: poate un search mai smecher
}
