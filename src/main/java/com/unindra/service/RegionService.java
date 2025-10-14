package com.unindra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unindra.model.response.DistrictResponse;
import com.unindra.model.response.ProvinceResponse;
import com.unindra.model.response.RegencyResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final ProvinceService provinceService;

    private final RegencyService regencyService;

    private final DistrictService districtService;

    public List<ProvinceResponse> getProvinceList() {
        return provinceService.getProvinceList();
    }

    public ProvinceResponse getProvinceById(String provinceId) {
        return provinceService.getProvinceById(provinceId);
    }

    public List<RegencyResponse> getRegenciesByProvinceId(String provinceId) {
        return regencyService.getRegencyListByProvinceId(provinceId);
    }

    public RegencyResponse getRegencyById(String regencyId) {
        return regencyService.getById(regencyId);
    }

    public List<DistrictResponse> getDistrictsByRegencyId(String regencyId) {
        return districtService.getDistrictListByRegencyId(regencyId);
    }

    public DistrictResponse getDistrictById(String districtId) {
        return districtService.getDistrictById(districtId);
    }
}