package com.security.controller;

import com.security.annotation.JpaPage;
import com.security.entity.Role;
import com.security.service.RoleService;
import com.security.util.Pagination;
import com.security.constant.ErrorEnum;
import com.security.model.RoleDto;
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
@Api(tags = "Role")
public class RoleController {

    @Autowired
    RoleService roleService;


    @GetMapping("/api/role")
    @JpaPage
    @ApiOperation(value = "分页查询")
    public ResponseObject<Pagination<Role>> findPage() {
        Page<Role> pageList = roleService.findPage();
        Pagination<Role> pagination = new Pagination<Role>((int) pageList.getTotalElements(), pageList.getContent());
        return ResponseObject.success(pagination);
    }

    @ApiOperation(value = "通过ID查找")
    @GetMapping("/api/role/{id}")
    public ResponseObject findOne(@PathVariable("id") Long id) {
        Optional<Role> entity = roleService.findById(id);
        if (entity.isPresent()) {
            return ResponseObject.success(roleService.toDto(entity.get()));
        }
        return ResponseObject.fail(ErrorEnum.NOT_FOUND);
    }

    @PostMapping("/api/role")
    @ApiOperation(value = "保存实体")
    public ResponseObject save(@ApiParam @Validated @RequestBody RoleDto dto) {
        Role entity = roleService.toEntity(dto);

        roleService.save(entity);
        return ResponseObject.success("OK");
    }


    @PatchMapping("/api/role")
    @ApiOperation(value = "部分更新")
    public ResponseObject patchUpdate(@ApiParam @RequestBody RoleDto dto) {
        Optional<Role> op = roleService.findById(dto.getId());
        if (op.isPresent()) {
            Role entity = op.get();
            BeanUtils.copyProperties(dto, entity);
            roleService.save(entity);
            return ResponseObject.success("OK");
        }
        return ResponseObject.fail(ErrorEnum.SERVER_ERROR);
    }

    @ApiOperation(value = "删除单个实体")
    @DeleteMapping("/api/role/{id}")
    public ResponseObject<String> delete(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return ResponseObject.success("OK");
    }
}
