package com.hms.departments;

import java.util.UUID;

public record DepartmentDto(UUID id, String code, String name, String description, boolean active) {
    public static DepartmentDto from(Department department) {
        return new DepartmentDto(department.getId(), department.getCode(), department.getName(),
                department.getDescription(), department.isActive());
    }
}
