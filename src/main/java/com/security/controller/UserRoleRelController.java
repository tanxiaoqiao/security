package com.security.controller;

import com.security.annotation.JpaPage;
import com.security.entity.UserRoleRel;
import com.security.service.UserRoleRelService;
import com.security.util.Pagination;
import com.security.constant.ErrorEnum;
import com.security.model.UserRoleRelDto;
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
@Api(tags = "UserRoleRel")
public class UserRoleRelController {

    @Autowired
    UserRoleRelService userRoleRelService;


    @GetMapping("/api/userRoleRel")
    @JpaPage
    @ApiOperation(value = "分页查询")
    public ResponseObject<Pagination<UserRoleRel>> findPage() {
        Page<UserRoleRel> pageList = userRoleRelService.findPage();
        Pagination<UserRoleRel> pagination = new Pagination<UserRoleRel>((int) pageList.getTotalElements(), pageList.getContent());
        return ResponseObject.success(pagination);
    }

    @ApiOperation(value = "通过ID查找")
    @GetMapping("/api/userRoleRel/{id}")
    public ResponseObject findOne(@PathVariable("id") Long id) {
        Optional<UserRoleRel> entity = userRoleRelService.findById(id);
        if (entity.isPresent()) {
            return ResponseObject.success(userRoleRelService.toDto(entity.get()));
        }
        return ResponseObject.fail(ErrorEnum.NOT_FOUND);
    }

    @PostMapping("/api/userRoleRel")
    @ApiOperation(value = "保存实体")
    public ResponseObject save(@ApiParam @Validated @RequestBody UserRoleRelDto dto) {
        UserRoleRel entity = userRoleRelService.toEntity(dto);

        userRoleRelService.save(entity);
        return ResponseObject.success("OK");
    }


    @PatchMapping("/api/userRoleRel")
    @ApiOperation(value = "部分更新")
    public ResponseObject patchUpdate(@ApiParam @RequestBody UserRoleRelDto dto) {
        Optional<UserRoleRel> op = userRoleRelService.findById(dto.getId());
        if (op.isPresent()) {
            UserRoleRel entity = op.get();
            BeanUtils.copyProperties(dto, entity);
            userRoleRelService.save(entity);
            return ResponseObject.success("OK");
        }
        return ResponseObject.fail(ErrorEnum.SERVER_ERROR);
    }

    @ApiOperation(value = "删除单个实体")
    @DeleteMapping("/api/userRoleRel/{id}")
    public ResponseObject<String> delete(@PathVariable("id") Long id) {
        userRoleRelService.deleteById(id);
        return ResponseObject.success("OK");
    }
}
