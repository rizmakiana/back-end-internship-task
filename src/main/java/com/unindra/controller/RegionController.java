package com.unindra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unindra.model.response.DistrictResponse;
import com.unindra.model.response.ProvinceResponse;
import com.unindra.model.response.RegencyResponse;
import com.unindra.model.response.WebResponse;
import com.unindra.service.RegionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/region")
public class RegionController {

    private final RegionService service;

    @GetMapping("/provinces")
    public ResponseEntity<WebResponse<List<ProvinceResponse>>> getProvinceList() {
        return ResponseEntity.ok(
                WebResponse.<List<ProvinceResponse>>builder()
                        .data(service.getProvinceList())
                        .build());
    }

    @GetMapping("/provinces/{provinceId}")
    public ResponseEntity<WebResponse<ProvinceResponse>> getProvinceById(@PathVariable String provinceId) {
        return ResponseEntity.ok(
                WebResponse.<ProvinceResponse>builder()
                        .data(service.getProvinceById(provinceId))
                        .build());
    }

    @GetMapping("/regencies")
    public ResponseEntity<WebResponse<List<RegencyResponse>>> getRegenciesByProvince(
            @RequestParam("provinceId") String provinceId) {
        return ResponseEntity.ok(
                WebResponse.<List<RegencyResponse>>builder()
                        .data(service.getRegenciesByProvinceId(provinceId))
                        .build());
    }

    @GetMapping("/regencies/{id}")
    public ResponseEntity<WebResponse<RegencyResponse>> getRegencyById(@PathVariable String id) {
        return ResponseEntity.ok(
                WebResponse.<RegencyResponse>builder()
                        .data(service.getRegencyById(id))
                        .build());
    }

    @GetMapping("/districts")
    public ResponseEntity<WebResponse<List<DistrictResponse>>> getDistrictsByRegency(
            @RequestParam("regencyId") String regencyId) {
        return ResponseEntity.ok(
                WebResponse.<List<DistrictResponse>>builder()
                        .data(service.getDistrictsByRegencyId(regencyId))
                        .build());
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<WebResponse<DistrictResponse>> getDistrictById(@PathVariable String id) {
        return ResponseEntity.ok(
                WebResponse.<DistrictResponse>builder()
                        .data(service.getDistrictById(id))
                        .build());
    }

}