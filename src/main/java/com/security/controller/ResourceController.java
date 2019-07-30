package com.security.controller;

import com.security.annotation.JpaPage;
import com.security.entity.Resource;
import com.security.service.ResourceService;
import com.security.util.Pagination;
import com.security.constant.ErrorEnum;
import com.security.model.ResourceDto;
import com.security.util.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Api(tags = "Resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;


    @GetMapping("/api/resource")
    @JpaPage
    @ApiOperation(value = "分页查询")
    public ResponseObject<Pagination<Resource>> findPage() {
        Page<Resource> pageList = resourceService.findPage();
        Pagination<Resource> pagination = new Pagination<Resource>((int) pageList.getTotalElements(), pageList.getContent());
        return ResponseObject.success(pagination);
    }

    @ApiOperation(value = "通过ID查找")
    @GetMapping("/api/resource/{id}")
    public ResponseObject findOne(@PathVariable("id") Long id) {
        Optional<Resource> entity = resourceService.findById(id);
        if (entity.isPresent()) {
            return ResponseObject.success(resourceService.toDto(entity.get()));
        }
        return ResponseObject.fail(ErrorEnum.NOT_FOUND);
    }

    @PostMapping("/api/resource")
    @ApiOperation(value = "保存实体")
    public ResponseObject save(@ApiParam @Validated @RequestBody ResourceDto dto) {
        Resource entity = resourceService.toEntity(dto);

        resourceService.save(entity);
        return ResponseObject.success("OK");
    }


    @PatchMapping("/api/resource")
    @ApiOperation(value = "部分更新")
    public ResponseObject patchUpdate(@ApiParam @RequestBody ResourceDto dto) {
        Optional<Resource> op = resourceService.findById(dto.getId());
        if (op.isPresent()) {
            Resource entity = op.get();
            BeanUtils.copyProperties(dto, entity);
            resourceService.save(entity);
            return ResponseObject.success("OK");
        }
        return ResponseObject.fail(ErrorEnum.SERVER_ERROR);
    }

    @ApiOperation(value = "删除单个实体")
    @DeleteMapping("/api/resource/{id}")
    public ResponseObject<String> delete(@PathVariable("id") Long id) {
        resourceService.deleteById(id);
        return ResponseObject.success("OK");
    }
}
